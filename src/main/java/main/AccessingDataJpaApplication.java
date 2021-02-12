package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class AccessingDataJpaApplication {

    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

    @Autowired
    private AddressBookRepository abRepository;
    @Autowired
    private BuddyInfoRepository buddyRepository;

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class, args);
    }

    @Bean
    public void demo() {
        // save a few buddyInfos
        AddressBook ab = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("Max", "13 Test Road", "6138501111");
        BuddyInfo buddy2 = new BuddyInfo("John", "13 Test Avenue", "6138501111");

        ab.addBuddy(buddy);
        ab.addBuddy(buddy2);
        abRepository.save(ab);

        // fetch all buddyinfos
        log.info("BuddyInfos found with findAll():");
        log.info("-------------------------------");
        for (AddressBook book : abRepository.findAll()) {
            log.info(book.getId() + book.toString());
        }
        log.info("");
    }
}