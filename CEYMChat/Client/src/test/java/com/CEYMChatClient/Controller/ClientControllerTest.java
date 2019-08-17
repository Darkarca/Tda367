package com.CEYMChatClient.Controller;

import com.CEYMChatClient.Model.ClientModel;
import com.CEYMChatLib.Message;
import com.CEYMChatLib.UserInfo;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest {

    private MockMvc mockMvc;

    //@Mock
    //ClientModel model;

    @Mock
    TextField chatBox;

    //@Mock
    //Stage disconnectPopup;

    @InjectMocks
    ClientController cc;

    public final String testString = "test_test";



    @Before
    public void setup() {
        //mockMvc = MockMvcBuilders.standaloneSetup(cc).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendString() {
       /* UserInfo yazan = new UserInfo();
        Message<String> message = new Message<>("Hello", yazan);
        cc.chatBoxAppendText(testString);
        assertEquals(chatBox.getText(),testString);

        //when(chatBox.getText()).thenReturn("Hello");
        //when(model.getUInfo()).thenReturn(new UserInfo());
        //verify(model.addMessage(ArgumentCaptor))

*/

    }

}