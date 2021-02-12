package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WebApplicationTest {

    @Autowired
    private AddressBookController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

}