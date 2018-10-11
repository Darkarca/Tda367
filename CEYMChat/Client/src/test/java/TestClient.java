import com.CEYMChat.Model.ClientModel;
import com.CEYMChat.Services.Connection;
import org.junit.Test;


public class TestClient{

    @Test
    public void testStart() {

    }


    /**
     * Fails if connection couldn't be started
     */
    @Test
    public void testConnectionStart(){
        Connection c = new Connection(new ClientModel());
        c.start();

    }


}
