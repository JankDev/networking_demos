package university_networking_classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MulticastServer {
    private static final URI URL = URI.create("https://jsonplaceholder.typicode.com/posts");
    private static final int MAX_BUFFER_SIZE = 1024;

    private List<Post> posts = new ArrayList<>();
    private final Gson gson = new Gson();
    private final MulticastSocket socket;

    public MulticastServer(int port) throws IOException {
        this.socket = new MulticastSocket(port);
    }

    static class Post{
        int id;
        int userId;
        String title;
        String body;

        @Override
        public String toString() {
            return "Post{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", title='" + title + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }
    }

    public void start() throws ExecutionException, InterruptedException, UnknownHostException, SocketException {
        fetchPosts();

        final InetAddress group = InetAddress.getByName("225.0.113.0");
        posts.forEach(post -> {
            String json = gson.toJson(post);
            byte[] buf = new byte[MAX_BUFFER_SIZE];
            System.arraycopy(json.getBytes(),0,buf,0,json.getBytes().length);

            DatagramPacket packet = new DatagramPacket(buf,0,buf.length,group,8080);

            try {
                socket.send(packet);
                Thread.sleep(100);
            } catch (IOException | InterruptedException exception) {
                exception.printStackTrace();
            }
        });

        socket.close();

    }

    private void fetchPosts() throws ExecutionException, InterruptedException {
        var http = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URL).GET().build();

        posts = http.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> gson.<List<Post>>fromJson(response.body(), new TypeToken<List<Post>>(){}.getType()))
                .get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        new MulticastServer(8080).start();
    }
}
