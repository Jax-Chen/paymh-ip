import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenjc23273 on 2019/05/07
 *
 * @author chenjc23273
 */
public class MapTest {

    public static void main(String[] args) {

        Map<String ,User> map = new HashMap<String,User>();
//        map.put("22",new User());
        User u = map.get(null);
        System.out.println(u);
    }

    class User{

        int age;
        String name;


    }

}
