package org.task.one;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.task.pojo.comment.Comment;
import org.task.pojo.post.Post;
import org.task.pojo.todo.Todo;
import org.task.pojo.user.User;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class for performing various operations related to users, posts, comments, and todos.
 *
 * @author Shalaiev Ivan.
 * @version 1.0.0 06.10.2023
 */
public class UtilClass {
    public static final String CONTENT_TYPE = "content-type";
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";
    public static final String URL_POSTS = "https://jsonplaceholder.typicode.com/posts";
    private final String URL_USERS = "https://jsonplaceholder.typicode.com/users";
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Makes a POST request to create a new user.
     *
     * @param user The user object to be created.
     */
    public void postUser(User user) {
        HttpClient client = HttpClient.newHttpClient();
        String jsonUser = null;
        try {
            jsonUser = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpRequest.Builder post = HttpRequest.newBuilder()
                .uri(URI.create(URL_USERS))
                .header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .POST(HttpRequest.BodyPublishers.ofString(jsonUser));
        HttpRequest request = post.build();
        HttpResponse<?> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    /**
     * Makes a PUT request to update an existing user.
     *
     * @param user   The updated user object.
     * @param userID The ID of the user to be updated.
     */
    public void putUser(User user, String userID) {
        String uri = URL_USERS + "/" + userID;
        HttpClient httpClient = HttpClient.newHttpClient();
        String jsonUser = null;
        try {
            jsonUser = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonUser))
                .build();
        HttpResponse<?> response = null;
        try {
            response = httpClient
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    /**
     * Makes a DELETE request to delete a user.
     *
     * @param userID The ID of the user to be deleted.
     */
    public void deleteUser(String userID) {
        String uri = URL_USERS + "/" + userID;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .DELETE()
                .build();
        HttpResponse response = null;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("status code for get JSON object: " + response.statusCode());
        System.out.println("response body: " + response.body());
    }

    /**
     * Retrieves a list of all users.
     *
     * @return The list of users.
     */
    public List<User> getAllUsers() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(URL_USERS))
                .header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JavaType typeToken = objectMapper.getTypeFactory().constructParametricType(List.class, User.class);
        List<User> users = null;
        try {
            users = objectMapper.readValue(response.body(), typeToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userID The ID of the user to retrieve.
     * @return The JSON representation of the user.
     */
    public String getUserByID(String userID) {
        String uri = URL_USERS + "/" + userID;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("status code for get JSON object: " + response.statusCode());
        return response.body();
    }

    /**
     * Retrieves a user by username.
     *
     * @param userName The username of the user to retrieve.
     * @return The JSON representation of the user.
     */
    public String getUserByUserName(String userName) {
        String uri = URL_USERS + "?username=" + userName;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("status code for get JSON object: " + response.statusCode());
        return response.body();
    }

    /**
     * Retrieves all comments for the last post made by a user and writes them to a JSON file.
     *
     * @param userID The ID of the user.
     */
    public void allCommentsToLastPostToFile(String userID) {
        List<Post> allUserPosts = getAllUserPosts(userID);
        Integer postMaxID = findPostMaxID(allUserPosts);
        String uriComments = URL_POSTS + "/" + postMaxID + "/comments";
        List<Comment> commentsToLastPost = allCommentsToLastPost(uriComments);
        jsonToFile(userID, commentsToLastPost, postMaxID);
    }

    /**
     * Retrieves all open tasks (todos) for a user.
     *
     * @param userID The ID of the user.
     * @return The list of open tasks.
     */
    public List<Todo> allOpenTasks(String userID) {
        String uri = URL_USERS + "/" + userID + "/todos";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JavaType typeToken = objectMapper.getTypeFactory().constructParametricType(List.class, Todo.class);
        List<Todo> todos = null;
        try {
            todos = objectMapper.readValue(response.body(), typeToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<Todo> notComplited = todos.stream()
                .filter(it -> !it.isCompleted())
                .toList();
        return notComplited;
    }

    /**
     * Utility method that writes an object to a file in JSON format.
     *
     * @param userID             The ID of the user.
     * @param commentsToLastPost list of comments to last post
     * @param postMaxID          id of last post
     */
    private void jsonToFile(String userID, List<Comment> commentsToLastPost, Integer postMaxID) {
        String result;
        try {
            result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(commentsToLastPost);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try (FileWriter writer = new FileWriter(String.format("Files/user-%s-post-%d-comments.json", userID, postMaxID))) {
            writer.write(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a list of all comments to last post.
     *
     * @param uriComments uri comments.
     * @return The list of comments.
     */
    private List<Comment> allCommentsToLastPost(String uriComments) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uriComments))
                .header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JavaType typeToken = objectMapper.getTypeFactory().constructParametricType(List.class, Comment.class);
        List<Comment> comments = null;
        try {
            comments = objectMapper.readValue(response.body(), typeToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return comments;
    }

    /**
     * Utility method that find an id with max number.
     *
     * @param posts list of posts.
     * @return The max id number.
     */
    private Integer findPostMaxID(List<Post> posts) {
        Integer maxID = posts.stream()
                .max(Comparator.comparingInt(Post::getId))
                .map(Post::getId)
                .orElseThrow();
        return maxID;
    }

    /**
     * Retrieves a list of all users posts.
     *
     * @param userID user id.
     * @return The list of posts.
     */
    private List<Post> getAllUserPosts(String userID) {
        String uri = URL_USERS + "/" + userID + "/posts";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JavaType typeToken = objectMapper.getTypeFactory().constructParametricType(List.class, Post.class);
        List<Post> posts = null;
        try {
            posts = objectMapper.readValue(response.body(), typeToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }
}
