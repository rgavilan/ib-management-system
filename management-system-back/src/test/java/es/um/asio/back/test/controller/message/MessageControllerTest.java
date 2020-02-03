package es.um.asio.back.test.controller.message;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izertis.abstractions.exception.NoSuchEntityException;

import es.um.asio.back.controller.message.MessageController;
import es.um.asio.service.proxy.MessageProxy;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    /**
     * MVC test support
     */
    @Autowired
    private MockMvc mvc;

    /**
     * User service
     */
    @MockBean
    private MessageProxy proxy;

    /**
     * JSON Object mapper
     */
    @Autowired
    private ObjectMapper objectMapper;
    
    @TestConfiguration
    static class UserProxyTestConfiguration {
        @Bean
        public MessageController userController() {
            return new MessageController();
        }
    }

    @Before
    public void setUp() throws NoSuchEntityException {
        // Mock data
        Mockito.doNothing().when(this.proxy).save(any(String.class));
    }
    
    @Test
    public void whenInsertNewMessage_thenNoError() throws Exception {
        
        // @formatter:off

        this.mvc.perform(post("/message")
                .content("Message 1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

        // @formatter:on
    }
}
