package main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.persistence.*;
import javax.swing.DefaultListModel;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Class models an addressbook (list of main.BuddyInfo objects) Can be saved and
 * imported from file (using Java Serialization)
 * 
 * @author Jacob Laboissonniere (101031913)
 *
 */
@Entity
public class AddressBook {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<BuddyInfo> buddyList = new ArrayList<BuddyInfo>();

	/**
	 * Creates a new instance of main.AddressBook
	 */
	public AddressBook() {

	}

	/**
	 * Gets the id of this main.AddressBook.
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id of this main.AddressBook to the specified value.
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Add buddy to the list
	 * 
	 * @param buddy
	 *            to be added to list
	 */
	public void addBuddy(BuddyInfo buddy) {

		if (buddy != null) {
			buddyList.add(buddy);
		}

	}

	/**
	 * Getter for the buddylist object
	 * 
	 * @return
	 */

	public List<BuddyInfo> getBuddyList() {
		return buddyList;
	}

	/**
	 * Setter for the buddylist
	 * 
	 * @param list
	 */
	public void setBuddyList(List<BuddyInfo> list) {
		buddyList = list;
	}

	/**
	 * Remove buddy from the list
	 * 
	 * @param index
	 *            of the buddy to be removed
	 */
	public void removeBuddy(int index) {
		if (index >= 0 && index < buddyList.size()) {
			buddyList.remove(index);
		}
	}

	/**
	 * Get buddylist size
	 * 
	 * @return the size of the buddylist
	 */
	public int size() {
		return buddyList.size();
	}

	/**
	 * Empty the buddylist
	 */
	public void clear() {
		buddyList.clear();
	}

	public String toString() {
		String output = "";
		for(Object buddy : buddyList.toArray()) {
			output += buddy.toString() + "\n\n\n";
		}
		return output;
	}
	/**
	 * Serializes an addressbook and saves it to a file
	 *
	 * @param path
	 *            File path to save address book
	 *
	 */
	public void saveBook(String path) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in " + path);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Imports addressbook from serialized file
	 *
	 * @param filePath
	 *            File to import from
	 * @return Returns populated addressbook
	 */
	public static AddressBook importBook(String filePath) {
		AddressBook book = new AddressBook();
		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			book = (AddressBook) in.readObject();
			in.close();
			fileIn.close();
			return book;
		} catch (IOException i) {
			i.printStackTrace();
			return book;
		} catch (ClassNotFoundException c) {
			System.out.println("AddressBook class not found");
			c.printStackTrace();
			return book;
		}
	}
	/**
	 * Exports addressbook to an XML file
	 * @param path to export to
	 * @throws IOException
	 */
	public void exportToXMLFile(String path) throws IOException {
		try {
			StringBuffer buff = new StringBuffer("<book>");
			for (int i = 0; i < this.size(); i++) {
				buff.append(buddyList.get(i).toXMLString());
			}
			buff.append("</book>");
			BufferedWriter stream = new BufferedWriter(new FileWriter(path));
			stream.write(buff.toString());
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Imports an addressbook from an XML file
	 * @param path to export to
	 * @return Returns addressbook containing data from XML
	 * @throws FileNotFoundException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static AddressBook importFromXMlFile(String path) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		AddressBook book = new AddressBook();
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(path));
			NodeList list = doc.getElementsByTagName("book");
			for(int i = 0; i < list.getLength(); i++){
				book.addBuddy(BuddyInfo.createBuddy((Element)list.item(i)));
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (SAXException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} catch (ParserConfigurationException e){
			e.printStackTrace();
		}
		return book;
	}
}
