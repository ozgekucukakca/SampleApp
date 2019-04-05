

package hello;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    //private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private HashMap<String, String> hmap = new HashMap<String, String>();

    @GetMapping("/message")
    @ResponseBody
    public String sayHello(@RequestParam(name = "msg") String name) {

        String hashing = sha256(name);
        hmap.put(hashing, name);

        return hashing;
    }

    @GetMapping("/hash")
    @ResponseBody
    public String sayHash(@RequestParam(name = "hash") String hashing) {
        System.out.println("sayHash called!");
        return hmap.get(hashing);
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
