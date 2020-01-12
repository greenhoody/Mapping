import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        int labyrinth;
        String UID = "f95a1461";
        for (labyrinth = 1; labyrinth < 5; labyrinth++){
            Connection.reset(labyrinth, UID);
            Algorithm rozwiazywator = new Algorithm();
            rozwiazywator.createMaze(labyrinth, UID);
            rozwiazywator.mapMaze(labyrinth, UID);
            String response = Connection.send(labyrinth, UID, Connection.makeFile(rozwiazywator.maze, "./src/solution.txt"));
            System.out.println(response);
        }




        //System.out.println(tmp);



    }
}