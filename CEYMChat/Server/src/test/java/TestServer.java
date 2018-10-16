import com.CEYMChat.ServerModel;
import com.CEYMChat.SocketHandler;
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

}
