package main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void newAddressBookShouldSave() throws Exception {
        assertThat(this.restTemplate.postForLocation("http://localhost:" + port + "/new/addressbook",
                String.class, new Object())).toString().contains("saved");
    }

    @Test
    public void viewAddressBook() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/addressbook?id=1", String.class)).contains("John");
    }
} 