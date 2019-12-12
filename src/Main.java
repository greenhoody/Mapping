import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        try {
            URL url = new URL("tesla.iem.pw.edu.pl:4444/f95a1461/1/possibilities");
            HttpClient con = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("tesla.iem.pw.edu.pl:4444/f95a1461/1/possibilities")).GET().build();
            HttpResponse response = con.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}