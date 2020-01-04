public class Algorithm {

    public Field[][] maze;
    int xCurrent;
    int yCurrent;
    int xChange;
    int yChange;

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
            maze[xCurrent][yCurrent].visited = true;
            String direction = checkForVisited(xCurrent,yCurrent, numberOfLabyrinth, UID);
            if (direction == "stop"){


            }
            else{
                xCurrent += xChange;
                yCurrent += yChange;
                Connection.move(numberOfLabyrinth, UID, direction);
            }
        }
    }
    public String checkForVisited (int xCurrent, int yCurrent, int numberOfLabyrinth, String UID){
        /*Metoda mapuje wszystkie nieodwiedzone węzły wokół aktualnego węzła po czym zwraca kierunek
          najbliższego nieodwiedzonego węzła patrząc od góry ("UP" oznacza górę, "RIGHT" wschód, "DOWN" południe, "LEFT" zachód)
          lub "STOP" jeśli takiego węzła nie ma
        */

        char[] possibilities = Connection.getPossibilities(numberOfLabyrinth, UID);
        String answer = "STOP";
        if (possibilities[3] == '0'){
            maze[xCurrent - 1][yCurrent].type = '0';
            maze[xCurrent - 2][yCurrent].type = '0';
            if (maze[xCurrent - 2][yCurrent].visited == false){
                xChange = -2;
                yChange = 0;
                answer = "left";
            }
        }
        else{
            maze[xCurrent - 1][yCurrent].type = '+';
        }

        if (possibilities[2] == '0'){
            maze[xCurrent][yCurrent + 1].type = '0';
            maze[xCurrent][yCurrent + 2].type = '0';
            if (maze[xCurrent][yCurrent + 2].visited == false){
                xChange = 0;
                yChange = 2;
                answer = "down";
            }
        }
        else{
            maze[xCurrent][yCurrent + 1].type = '+';
        }

        if (possibilities[1] == '0'){
            maze[xCurrent + 1][yCurrent].type = '0';
            maze[xCurrent + 2][yCurrent].type = '0';
            if (maze[xCurrent + 2][yCurrent].visited == false){
                xChange = 2;
                yChange = 0;
                answer = "right";
            }
        }
        else{
            maze[xCurrent + 1][yCurrent].type = '+';
        }

        if (possibilities[0] == '0'){
            maze[xCurrent][yCurrent - 1].type = '0';
            maze[xCurrent][yCurrent - 2].type = '0';
            if (maze[xCurrent][yCurrent - 2].visited == false){
                xChange = 0;
                yChange = -2;
                answer = "up";
            }
        }
        else{
            maze[xCurrent][yCurrent - 1].type = '+';
        }

        return answer;
    }

}
