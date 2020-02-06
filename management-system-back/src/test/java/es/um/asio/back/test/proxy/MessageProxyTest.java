package es.um.asio.back.test.proxy;

import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import es.um.asio.back.test.TestApplication;
import es.um.asio.service.proxy.MessageProxy;
import es.um.asio.service.proxy.impl.MessageProxyImpl;
import es.um.asio.service.service.MessageService;

@RunWith(SpringRunner.class)
public class MessageProxyTest {
    /**
     * User proxy
     */
    @Autowired
    private MessageProxy proxy;

    /**
     * User service mock bean.
     */
    @MockBean
    private MessageService service;

    @TestConfiguration
    @Import(TestApplication.class)
    static class UserProxyTestConfiguration {
        @Bean
        public MessageProxy userProxy() {
            return new MessageProxyImpl();
        }
    }

    @Before
    public void setUp() {
        Mockito.doNothing().when(this.service).save(any(String.class));
    }

    @Test
    public void whenInsertNewMessage_thenNoError() {
        this.proxy.save("Message 1");
    }
}
