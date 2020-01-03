public class Algorithm {

    public Field[][] maze;

    public void createMaze(int numberOfLabyrinth, String UID) {
        /*Generujemy strukturę labiryntu na której będziemy wykonywać mapowanie.
          Labirynt funkcjonuje w takim samym schemacie jak w projekcie indywidualnym
          Macierz 11x11 opisuje labirynt 5x5
        */
        int[] size = Connection.getSize(numberOfLabyrinth, UID);
        int height = size[0];
        int width = size[1];
        maze = new Field[height][width];
        for (int i = 0; i < 2*height+1; i++){
            for (int j = 0; j < 2*width+1; j++){
                maze[i][j] = new Field();
            }
        }
        for (int i = 0; i < 2*width+1; i++){
            maze[0][i].type = '+';
        }
        for (int i = 0; i < 2*width+1; i++){
            maze[height*2][i].type = '+';
        }
        for (int i = 0; i < 2*height+1; i++){
            maze[i][0].type = '+';
        }
        for (int i = 0; i < 2*height+1; i++){
            maze[i][width*2].type = '+';
        }


    }

    public void mapMaze(int numberOfLabyrinth, String UID){
        int[] position = Connection.getStartPosition(numberOfLabyrinth , UID);
        int xStart = position[0]*2-1;
        int yStart = position[1]*2-1;
        int xCurrent = xStart;
        int yCurrent = yStart;
        maze[xStart][yStart].type = '0';
        maze[xStart][yStart].visited = true;

        boolean mazeMapped = false;
        while (mazeMapped != true){

            if (checkForVisited(xCurrent,yCurrent) == true){

            }
        }
    }
    public boolean checkForVisited (int xCurrent, int yCurrent){
        /*metoda zwraca true, jeśli wszystkie węzły, do których można dojść z danego węzła zostały już odwiedzone
          metoda zwraca false, jeśli z danego węzła można przejść do innego, nieodwiedzonego jeszcze węzła
        */
        boolean ifVisited;
        ifVisited=true;
        return ifVisited;
    }

}
