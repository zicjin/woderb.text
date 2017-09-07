import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import static spark.Spark.*;

public class HelloWorld {
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        //  port(5678); <- listen to port 5678 instead of the default 4567

        get("/hello", (request, response) -> "Hello World!");

        // Will serve all static file are under "/public" in classpath if the route isn't consumed by others routes.
        // When using Maven, the "/public" folder is assumed to be in "/main/resources/public"
        // if (localhost) {
        //     staticFiles.expireTime(3);
        // }
        staticFiles.location("/public");

        //Running curl -i -H "Accept: application/json" http://localhost:4567/hello.json json message is read.
        //Running curl -i -H "Accept: text/html" http://localhost:4567/hello.json HTTP 404 error is thrown.
        get("/hello.json", "application/json", (request, response) -> "{\"message\": \"Hello World\"}");
        get("/hello.json2", "application/json", (request, response) -> {
            return new Books.Book("xxxx", "yyyy");
        }, gson::toJson);

        post("/hello", (request, response) ->
                "Hello World: " + request.body()
        );

        get("/private", (request, response) -> {
            response.status(401);
            return "Go Away!!!";
        });

        get("/users/:name", (request, response) -> "Selected user: " + request.params(":name"));

        get("/news/:section", (request, response) -> {
            response.type("text/xml");
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><news>" + request.params("section") + "</news>";
        });

        get("/protected", (request, response) -> {
            halt(403, "I don't think so!!!");
            return null;
        });

        get("/redirect", (request, response) -> {
            response.redirect("/news/world");
            return null;
        });

        get("/", (request, response) -> "root");

        notFound("<html><body><h1>Custom 404 handling</h1></body></html>");
        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 500 handling\"}";
        });
        exception(JsonSyntaxException.class, (exception, request, res) -> {
            // Handle the exception
        });

        threadPool(8, 2, 30000);
    }
}