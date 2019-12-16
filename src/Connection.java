import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection {
    static String[] patterns = {"up\\\"\\:\\\"\\+", "right\\\"\\:\\\"\\+", "down\\\"\\:\\\"\\+", "left\\\"\\:\\\"\\+"};

    public static char[] getPossibilities(int numberOfLabyrinth, String UID) {
        char[] anserw = new char[4];
        try {
            //Creating address from data passed by user
            StringBuilder addressCreator = new StringBuilder();
            String address = addressCreator.append("http://tesla.iem.pw.edu.pl:4444/").append(UID).append('/').append(numberOfLabyrinth).append("/possibilities").toString();
            //Making request to server
            HttpClient con = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address)).build();
            HttpResponse<String> response = con.send(request, HttpResponse.BodyHandlers.ofString());
            //Making response from server into return values
            String body = response.body().toString();
            System.out.printf(body, "\n");
            for (int i = 0; i < 4; i++) {
                Pattern compilePattern = Pattern.compile(patterns[i]);
                Matcher matcher = compilePattern.matcher(body);
                if (matcher.find())
                    anserw[i] = '+';
                else
                    anserw[i] = '0';
            }
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return anserw;
    }

    public static int[] getSize(int numberOfLabyrinth, String UID) {
        int[] size = new int[2];
        try {
            //Creating address from data passed by user
            StringBuilder addressCreator = new StringBuilder();
            String address = addressCreator.append("http://tesla.iem.pw.edu.pl:4444/").append(UID).append('/').append(numberOfLabyrinth).append("/size").toString();
            //Making request to server
            HttpClient con = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address)).build();
            HttpResponse<String> response = con.send(request, HttpResponse.BodyHandlers.ofString());
            //Making response from server into return values
            String body = response.body();
            System.out.printf(body, "\n");
            int tmp = body.indexOf("x");
            size[0] = Integer.parseInt(body.substring(0,(tmp)));
            size[1] = Integer.parseInt(body.substring(tmp+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }
}