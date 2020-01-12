import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection {
    private static String[] patterns = {"up\\\"\\:\\\"\\+", "right\\\"\\:\\\"\\+", "down\\\"\\:\\\"\\+", "left\\\"\\:\\\"\\+"};

    public static String makeFile (Field[][] solution, String path) throws IOException {
        File answer = new File(path);
        BufferedWriter bWriter = new BufferedWriter(new FileWriter(answer));
        for (int i = 0; i < solution[0].length; i++) {
            StringBuilder cLine = new StringBuilder();
            for(int a = 0 ; a < solution.length; a++){
                cLine.append(solution[a][i].type);
            }
            bWriter.append(cLine.toString()).append(System.lineSeparator());
        }
        bWriter.close();
        return answer.getPath();
    }

    public static String send (int numberOfLabyrinth, String UID, String path) throws FileNotFoundException {
        StringBuilder address = new StringBuilder();
        address.append("http://tesla.iem.pw.edu.pl:4444/").append(UID).append("/").append(numberOfLabyrinth).append("/upload");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address.toString())).POST(HttpRequest.BodyPublishers.ofFile(Paths.get(path))).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request,HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e)
        { e.printStackTrace(); }
        return  response.body();
    }

    public static boolean move (int numberOfLabyrinth, String UID, String direction){
        if (direction != "right" && direction != "left" && direction != "up" && direction != "down"){
            System.err.print("Incorrect way");
            return false;
        }
        StringBuilder addressCreator = new StringBuilder();
        String address = addressCreator.append("http://tesla.iem.pw.edu.pl:4444/").append(UID).append('/').append(numberOfLabyrinth).append("/move/").append(direction).toString();
        HttpClient con = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address)).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = null;
        try {
            response = con.send(request, HttpResponse.BodyHandlers.ofString());
            Pattern compilePattern = Pattern.compile("You can not move in this direction");
            Matcher matcher = compilePattern.matcher(response.body());
            if (matcher.find()){
                System.err.print("Ruch w ścianę.");
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void uploadMap (int numberOfLabyrinth, String UID,String file) throws FileNotFoundException {
        Path path = Paths.get(file);
        StringBuilder addressCreator = new StringBuilder();
        String address = addressCreator.append("http://tesla.iem.pw.edu.pl:4444/").append(UID).append('/').append(numberOfLabyrinth).append("/upload").toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address)).POST(HttpRequest.BodyPublishers.ofFile(path)).build();
        HttpResponse<String> response = null;
        try{
            response = client.send(request,HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static char[] getPossibilities(int numberOfLabyrinth, String UID) {
        char[] anserw = new char[4];
        try {
            //Creating address from data passed by user
            StringBuilder addressCreator = new StringBuilder();
            String address = addressCreator.append("http://tesla.iem.pw.edu.pl:4444/").append(UID).append('/').append(numberOfLabyrinth).append("/possibilities").toString();
            //Making response from server into return values
            String body = getBodyResponse(address);
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

    public static int[] getStartPosition(int numberOfLabyrinth, String UID) {
        int[] position = new int[2];
        try {
            //Creating address from data passed by user
            StringBuilder addressCreator = new StringBuilder();
            String address = addressCreator.append("http://tesla.iem.pw.edu.pl:4444/").append(UID).append('/').append(numberOfLabyrinth).append("/startposition").toString();
            //Making response from server into return values
            String body = getBodyResponse(address);
            int tmp = body.indexOf(",");
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
        //Making response from server into return values. Method getBodyResponse couldn't been used to reset position.
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address.toString())).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (InterruptedException | IOException e) {
            e.printStackTrace(); }
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