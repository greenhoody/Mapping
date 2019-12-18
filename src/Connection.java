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

    public static void move (int numberOfLabyrinth, String UID, String direction){
        if (direction != "right" && direction != "left" && direction != "up" && direction != "down"){
            System.err.print("Incorrect way");
            return;
        }
        //TODO
        StringBuilder addressCreator = new StringBuilder();
        String address = addressCreator.append("http://tesla.iem.pw.edu.pl:4444/").append(UID).append('/').append(numberOfLabyrinth).append("/move/").append(direction).toString();
        HttpClient con = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address)).build();
    }

    public static void uploadMap (char[][] map){
        //TODO
    }

    public static char[] getPossibilities(int numberOfLabyrinth, String UID) {
        char[] anserw = new char[4];
        try {
            //Creating address from data passed by user
            StringBuilder addressCreator = new StringBuilder();
            String address = addressCreator.append("http://tesla.iem.pw.edu.pl:4444/").append(UID).append('/').append(numberOfLabyrinth).append("/possibilities").toString();
            //Making response from server into return values
            String body = getBodyResponse(address);
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
            //Making response from server into return values
            String body = getBodyResponse(address);
            System.out.printf(body, "\n");
            int tmp = body.indexOf("x");
            size[0] = Integer.parseInt(body.substring(0, (tmp)));
            size[1] = Integer.parseInt(body.substring(tmp + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    public static int[] getPosition(int numberOfLabyrinth, String UID) {
        int[] position = new int[2];
        try {
            //Creating address from data passed by user
            StringBuilder addressCreator = new StringBuilder();
            String address = addressCreator.append("http://tesla.iem.pw.edu.pl:4444/").append(UID).append('/').append(numberOfLabyrinth).append("/startposition").toString();
            //Making response from server into return values
            String body = getBodyResponse(address);
            System.out.printf(body, "\n");
            int tmp = body.indexOf("x");
            position[0] = Integer.parseInt(body.substring(0, (tmp)));
            position[1] = Integer.parseInt(body.substring(tmp + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return position;
    }

    public static void reset(int numberOfLabyrinth, String UID) throws IOException, InterruptedException {
        StringBuilder addressCreator = new StringBuilder();
        String address = addressCreator.append("http://tesla.iem.pw.edu.pl:4444/").append(UID).append('/').append(numberOfLabyrinth).append("/reset").toString();
        //Making response from server into return values
        String body = getBodyResponse(address);
    }

    public static int numberOfMoves(int numberOfLabyrinth, String UID) throws IOException, InterruptedException {
        StringBuilder addressCreator = new StringBuilder();
        String address = addressCreator.append("http://tesla.iem.pw.edu.pl:4444/").append(UID).append('/').append(numberOfLabyrinth).append("/reset").toString();
        return Integer.parseInt(getBodyResponse(address));
    }

    private static String getBodyResponse(String address) throws IOException, InterruptedException {
        //Making request to server
        HttpClient con = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address)).build();
        HttpResponse<String> response = con.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}