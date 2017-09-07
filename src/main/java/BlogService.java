import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class BlogService {

    // fiddler POST test:
    // Host: localhost:4567
    // Content-Type: application/json; charset=utf-8
    // {"title": "zxczxc", "categories": ["xzczxc", "cxvdss"], "content":"ewrwer"}
    public static void main( String[] args) {
        Model model = new Model();
        Gson gson = new Gson();

        post("/posts", (request, response) -> {
            try {
                NewPostPayload creation = gson.fromJson(request.body(), NewPostPayload.class);
                if (!creation.isValid()) {
                    response.status(400);
                    return "";
                }
                int id = model.createPost(creation.getTitle(), creation.getContent(), creation.getCategories());
                response.type("application/json");
                return id;
            } catch (JsonSyntaxException jpe) {
                return halt(500, "JsonSyntaxException!");
            }
        });

        get("/posts", (request, response) -> {
            response.type("application/json");
            return model.getAllPosts();
        }, gson::toJson);
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
}