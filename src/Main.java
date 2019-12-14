import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class Main {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://tesla.iem.pw.edu.pl:4444/f95a1461/1/possibilities");
            HttpClient con = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://tesla.iem.pw.edu.pl:4444/f95a1461/1/possibilities")).build();
            HttpResponse<String> response = con.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.printf("%s \n",response.statusCode());
            System.out.printf(response.body());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}