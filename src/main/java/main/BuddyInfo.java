package main;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Class models a "buddy" with attributes such as name, address, and phone
 * number
 * 
 * @author Jacob Laboissonniere (101031913)
 *
 */
@Entity
public class BuddyInfo implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String name;
	private String address;
	private String phoneNumber;
	private int age;

	/** Constructor with no args **/
	public BuddyInfo() {

	}

	public BuddyInfo(String name, String address, String phoneNumber) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public BuddyInfo(BuddyInfo b) {
		this.name = b.getName();
		this.address = b.getAddress();
		this.phoneNumber = b.getPhoneNumber();
	}


	/**
	 * Gets the id of this main.BuddyInfo.
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id of this main.BuddyInfo to the specified value.
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}


	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public boolean isOver18() {
		return (age >= 18);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String toString() {
		return (getName() + " " + getAddress() + " " + getPhoneNumber());
	}

	public String greeting() {
		return "Hey " + name + "! How are you?";
	}

	/**
	 * Creates an XML string form of the buddy object
	 * @return returns buddy in XML string form
	 */
	public String toXMLString(){
		return "<name>" + this.name + "</name>"
				+ "<address>" + this.address + "</address>"
				+ "<phone>" + this.phoneNumber + "</phone>";

	}

	/**
	 * Creates a buddy based on a Element passed from the XML parser
	 * @param node to create buddy from
	 * @return returns buddy object representing node
	 */
	public static BuddyInfo createBuddy(Element node){
		BuddyInfo info = new BuddyInfo(null, null, null);
		NodeList list;

		list = node.getElementsByTagName("name");
		if(list.getLength() > 0){
			info.setName(list.item(0).getTextContent());
		}

		list = node.getElementsByTagName("address");
		if(list.getLength() > 0){
			info.setAddress(list.item(0).getTextContent());
		}

		list = node.getElementsByTagName("phone");
		if(list.getLength() > 0){
			info.setPhoneNumber(list.item(0).getTextContent());
		}

		return info;
	}

}
