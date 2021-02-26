package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ComponentScan("main")
@EntityScan("main")
@Controller
public class AddressBookController {
    @Autowired
    private AddressBookRepository abRepository;
    @Autowired
    private BuddyInfoRepository buddyRepository;

    /**
     * Renders a template with the contents of an addressbook
     * @param id
     * @param model
     * @return Template view of addressbook
     */
    @GetMapping("/addressbook")
    public String addressBook(@RequestParam(name="id", required=true) Integer id, Model model) {
        model.addAttribute("id", id);
        AddressBook ab = abRepository.findById(id);
        model.addAttribute("content", ab.toString());
        return "addressbook";
    }

    @GetMapping("/addressBookForm")
    public String addressBookForm(Model model) {
        model.addAttribute("addressBook", new AddressBook());
        return "addressbookform";
    }

    @GetMapping("/buddyInfoForm")
    public String buddyInfoForm(Model model) {
        model.addAttribute("buddyInfo", new BuddyInfo());
        return "buddyinfoform";
    }

    @PostMapping("/buddyInfoSubmit")
    public String buddyInfoSubmit(@RequestParam("addressBookID") Integer id, @ModelAttribute BuddyInfo buddyInfo, Model model) {
        AddressBook ab = abRepository.findById(id);
        ab.addBuddy(buddyInfo);
        abRepository.save(ab);
        model.addAttribute("content", ab.toString());
        return "addressbook";
    }

    @PostMapping("/addressBookSubmit")
    public String greetingSubmit(@ModelAttribute AddressBook addressBook, Model model) {
        abRepository.save(addressBook);
        model.addAttribute("content", addressBook.toString());
        return "addressbook";
    }

    /**
     * Creates a new addressbook
     * @param model
     * @return "saved" if it was saved
     */
    @PostMapping("/new/addressbook")
    public ResponseEntity<String> newAddressBook(Model model) {

        AddressBook ab = new AddressBook();
        abRepository.save(ab);

        return new ResponseEntity<String>("saved", HttpStatus.OK);
    }

    /**
     * Allows a new BuddyInfo to be inserted into an addressbook
     * @param abId
     * @param name
     * @param address
     * @param phone
     * @param model
     * @return Returns a representation of the resulting addressbook
     */
    @PostMapping("/insert/buddy")
    public ResponseEntity<String> insertBuddy(@RequestParam(name="abId") Integer abId,
                                              @RequestParam(name="name") String name,
                                              @RequestParam(name="address") String address,
                                              @RequestParam(name="phone") String phone,
                                              Model model) {

        model.addAttribute("id", abId);
        BuddyInfo buddy3 = new BuddyInfo(name, address, phone);
        System.out.println(buddy3.toString());
        AddressBook ab = abRepository.findById(abId);
        ab.addBuddy(buddy3);
        abRepository.save(ab);

        return new ResponseEntity<String>(ab.toString(), HttpStatus.OK);
    }

    /**
     * This action allows lets you delete a buddyinfo given it's addressbook id and index in the buddylist
     * @param abId AddressBook ID
     * @param buddyId Buddy Index
     * @param model
     * @return Returns a representation of the resulting AddressBook
     */
    @DeleteMapping("/delete/buddy")
    public ResponseEntity<String> insertBuddy(@RequestParam(name="abId") Integer abId,
                                              @RequestParam(name="buddyId") Integer buddyId,
                                              Model model) {

        model.addAttribute("id", abId);
        AddressBook ab = abRepository.findById(abId);
        ab.removeBuddy(buddyId);
        abRepository.save(ab);

        return new ResponseEntity<String>(ab.toString(), HttpStatus.OK);
    }
}