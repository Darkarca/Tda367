import com.CEYMChat.ServerModel;
import org.junit.*;

import static java.lang.System.out;
import static org.junit.Assert.*;

import javax.validation.constraints.AssertFalse;

public class TestServer {



    @Test
    public void testLogin(){

    }
    @Test
    public void checkUser(){
        ServerModel sm = new ServerModel();
        boolean val = sm.checkUser();
        assertFalse(val);
    }



}
