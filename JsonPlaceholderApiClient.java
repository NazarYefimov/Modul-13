import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class JsonPlaceholderApiClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static void main(String[] args) throws IOException {
        JsonPlaceholderApiClient apiClient = new JsonPlaceholderApiClient();


        String newUserJson = "{\"name\": \"John Doe\", \"username\": \"johndoe\", \"email\": \"johndoe@example.com\"}";
        String createdUserJson = apiClient.post("/users", newUserJson);
        System.out.println("Created user: " + createdUserJson);


        String updatedUserJson = apiClient.put("/users/1", "{\"name\": \"Jane Doe\"}");
        System.out.println("Updated user: " + updatedUserJson);


        int responseCode = apiClient.delete("/users/1");
        System.out.println("Delete user response code: " + responseCode);


        String allUsersJson = apiClient.get("/users");
        System.out.println("All users: " + allUsersJson);


        String userByIdJson = apiClient.get("/users/2");
        System.out.println("User by id: " + userByIdJson);



        String username = "johndoe"; 
        String userByUsernameJson = apiClient.get("/users?username=" + username);
        System.out.println("User by username: " + userByUsernameJson);
    }

    private String get(String path) throws IOException {
        URL url = new URL(BASE_URL + path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        return getResponse(con);
    }

    private String post(String path, String jsonBody) throws IOException {
        URL url = new URL(BASE_URL + path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
        return getResponse(con);
    }

    private String put(String path, String jsonBody) throws IOException {
        URL url = new URL(BASE_URL + path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
        return getResponse(con);
    }

    private int delete(String path) throws IOException {
        URL url = new URL(BASE_URL + path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        return con.getResponseCode();
    }

    private String getResponse(HttpURLConnection con) throws IOException {
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return response.toString();
        } else {
            throw new IOException("Response code: " + responseCode);
        }
    }
}