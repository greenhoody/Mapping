import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        char[] tmp = Connection.getPossibilities(3, "f95a1461");
        System.out.println(tmp);
        int[] size = Connection.getSize(3, "f95a1461");
        System.out.printf("%d %d", size[0], size[1]);
        int[] position = Connection.getPosition(3, "f95a1461");
        System.out.printf("%d %d", position[0], position[1]);
        Connection.move(3, "f95a1461", "left");
        tmp = Connection.getPossibilities(3, "f95a1461");
        System.out.printf("%d %d", size[0], size[1]);


    }
}