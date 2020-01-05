import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Connection.reset(1, "f95a1461");
        /*char[] tmp = Connection.getPossibilities(1, "f95a1461");
        System.out.println(tmp);
        int[] size = Connection.getSize(1, "f95a1461");
        System.out.printf("%d %d \n", size[0], size[1]);
        int[] position = Connection.getStartPosition(1, "f95a1461");
        System.out.printf("%d %d \n", position[0], position[1]);
        Connection.move(1, "f95a1461", "left");
        tmp = Connection.getPossibilities(1, "f95a1461");*/

        Algorithm rozwiazywator = new Algorithm();
        rozwiazywator.createMaze(1, "f95a1461");
        rozwiazywator.mapMaze(1, "f95a1461");
        for (int y = 0; y < rozwiazywator.maze.length; y++ ){
            for (int x = 0; x < rozwiazywator.maze[0].length; x++){
                System.out.print(rozwiazywator.maze[y][x]);
            }
            System.out.println();
        }
        //System.out.println(tmp);



    }
}