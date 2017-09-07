import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class BlogService {

    private static final int HTTP_BAD_REQUEST = 400;

    interface Validable {
        boolean isValid();
    }

    static class NewPostPayload implements Validable {
        private String title;
        private List categories;
        private String content;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public List getCategories() { return categories; }
        public void setCategories(List categories){ this.categories = categories; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public boolean isValid() {
            return title != null && !title.isEmpty() && !categories.isEmpty();
        }
    }

    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }

    // fiddler POST test:
    // Host: localhost:4567
    // Content-Type: application/json; charset=utf-8
    // {"title": "zxczxc", "categories": ["xzczxc", "cxvdss"], "content":"ewrwer"}
    public static void main( String[] args) {
        Model model = new Model();

        post("/posts", (request, response) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                NewPostPayload creation = mapper.readValue(request.body(), NewPostPayload.class);
                if (!creation.isValid()) {
                    response.status(HTTP_BAD_REQUEST);
                    return "";
                }
                int id = model.createPost(creation.getTitle(), creation.getContent(), creation.getCategories());
                response.status(200);
                response.type("application/json");
                return id;
            } catch (JsonParseException jpe) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
        });

        get("/posts", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return dataToJson(model.getAllPosts());
        });
    }

    public static class Model {
        private int nextId = 1;
        private Map posts = new HashMap<>();

        class Post {
            private int id;
            private String title;
            private List categories;
            private String content;

            public int getId() { return id; }
            public void setId(int id) { this.id = id; }
            public String getTitle() { return title; }
            public void setTitle(String title) { this.title = title; }
            public List getCategories() { return categories; }
            public void setCategories(List categories){ this.categories = categories; }
            public String getContent() { return content; }
            public void setContent(String content) { this.content = content; }
        }

        public int createPost(String title, String content, List categories){
            int id = nextId++;
            Post post = new Post();
            post.setId(id);
            post.setTitle(title);
            post.setContent(content);
            post.setCategories(categories);
            posts.put(id, post);
            return id;
        }

        public List getAllPosts(){
            return (List) posts.keySet().stream().sorted().map((id) -> posts.get(id)).collect(Collectors.toList());
        }
    }
}