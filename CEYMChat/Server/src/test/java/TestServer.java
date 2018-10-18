import com.CEYMChat.Model.ServerModel;
import com.CEYMChat.Services.SocketHandler;
import org.junit.*;

public class TestServer {
    @Test
    public void testLogin(){
    }

    @Test
    public void checkUser(){
        ServerModel sm = new ServerModel();
    }

    @Test
    public void testSocketHandlerStart(){
        SocketHandler sh = new SocketHandler(new ServerModel());
        sh.start();

    }
    @Test
    public void testAddUser(){

    }

}
