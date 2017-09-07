import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Example showing a very simple (and stupid) authentication filter that is
 * executed before all other resources.
 *
 * When requesting the resource with e.g.
 *     http://localhost:4567/hello?user=some&password=guy
 * the filter will stop the execution and the client will get a 401 UNAUTHORIZED with the content 'You are not welcome here'
 *
 * When requesting the resource with e.g.
 *     http://localhost:4567/hello?user=foo&password=bar
 * the filter will accept the request and the request will continue to the /hello route.
 *
 * Note: There is a second "before filter" that adds a header to the response
 * Note: There is also an "after filter" that adds a header to the response
 */
public class FilterExample {

    private static Map<String, String> usernamePasswords = new HashMap<String, String>();

    public static void main(String[] args) {

        usernamePasswords.put("foo", "bar");
        usernamePasswords.put("admin", "admin");

        before((request, response) -> {
            String user = request.queryParams("user");
            String password = request.queryParams("password");

            String dbPassword = usernamePasswords.get(user);
            if (!(password != null && password.equals(dbPassword))) {
                halt(401, "You are not welcome here!!!");
            }
        });

        before("/hello", (request, response) -> response.header("Foo", "Set by second before filter"));

        get("/hello", (request, response) -> "Hello World!");

        after("/hello", (request, response) -> response.header("spark", "added by after-filter"));

        afterAfter("/hello", (request, response) -> response.header("finally", "executed even if exception is throw"));

        afterAfter((request, response) -> response.header("finally", "executed after any route even if exception is throw"));

        // use attributes
        get("/hi", (request, response) -> {
            request.attribute("foo", "bar");
            return null;
        });

        after("/hi", (request, response) -> {
            for (String attr : request.attributes()) {
                System.out.println("attr: " + attr);
            }
        });

        after("/hi", (request, response) -> {
            Object foo = request.attribute("foo");
            response.body(asXml("foo", foo));
        });
    }

    private static String asXml(String name, Object value) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><" + name +">" + value + "</"+ name + ">";
    }
}