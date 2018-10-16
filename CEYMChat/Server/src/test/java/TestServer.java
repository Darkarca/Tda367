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
    @Test
    public void testAddUser(){
       /* try {
            ServerSocket ss = new ServerSocket(9090);
            Socket s = ss.accept();
            ServerModel model = new ServerModel();
            User u = new User(s,model);
            model.addUser(u);
            assertEquals(model.getUserList().get(0),u);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

    }

}
