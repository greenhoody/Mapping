import java.util.ArrayList;

public class Algorithm {

    public Field[][] maze;
    int xCurrent;
    int yCurrent;
    int xChange;
    int yChange;
    int height;
    int width;

    public void createMaze(int numberOfLabyrinth, String UID) {
        /*Generujemy strukturę labiryntu na której będziemy wykonywać mapowanie.
          Labirynt funkcjonuje w takim samym schemacie jak w projekcie indywidualnym
          Macierz 11x11 opisuje labirynt 5x5
        */
        int[] size = Connection.getSize(numberOfLabyrinth, UID);
        height = size[0];
        width = size[1];
        maze = new Field[2*height+1][2*width+1];
        for (int i = 0; i < 2*height+1; i++){
            for (int j = 0; j < 2*width+1; j++){
                maze[i][j] = new Field();
            }
        }
        for (int i = 2; i < 2*height+1; i = i + 2){
            for (int j = 2; j < 2*width+1; j = j + 2){
                maze[i][j].type = '+';
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
        xCurrent = xStart;
        yCurrent = yStart;
        maze[xStart][yStart].type = '0';
        maze[xStart][yStart].visited = true;

        boolean mazeMapped = false;
        while (mazeMapped != true){
            maze[xCurrent][yCurrent].visited = true;
            String direction = checkForVisited(xCurrent,yCurrent, numberOfLabyrinth, UID);
            if (direction == "stop"){
                ArrayList<Direction> newDirection = BFS();
                if (newDirection.get(0).direction == "MAPPED"){
                    mazeMapped = true;
                }
                else{
                    for (int i = newDirection.size() - 1; i >= 0 ; i--){
                        xCurrent += newDirection.get(i).x;
                        yCurrent += newDirection.get(i).y;
                        Connection.move(numberOfLabyrinth, UID, newDirection.get(i).direction);
                    }
                }
            }
            else{
                xCurrent += xChange;
                yCurrent += yChange;
                Connection.move(numberOfLabyrinth, UID, direction);
            }
        }
        
        for (int i = 1; i < 2*height+1; i = i + 2){
            for (int j = 1; j < 2*width+1; j = j + 2){
                if (maze[i][j].visited == false){
                    maze[i][j].type = '+';
                    maze[i-1][j].type = '+';
                    maze[i+1][j].type = '+';
                    maze[i][j-1].type = '+';
                    maze[i][j+1].type = '+';
                }
            }
        }
    }
    public String checkForVisited(int xCurrent, int yCurrent, int numberOfLabyrinth, String UID){
        /*Metoda mapuje wszystkie nieodwiedzone węzły wokół aktualnego węzła po czym zwraca kierunek
          najbliższego nieodwiedzonego węzła patrząc od góry ("UP" oznacza górę, "RIGHT" wschód, "DOWN" południe, "LEFT" zachód)
          lub "STOP" jeśli takiego węzła nie ma
        */

        char[] possibilities = Connection.getPossibilities(numberOfLabyrinth, UID);
        String answer = "stop";
        if (possibilities[3] == '0'){
            maze[xCurrent - 1][yCurrent].type = '0';
            maze[xCurrent - 2][yCurrent].type = '0';
           /* if (checkIfVisit(xCurrent - 2,yCurrent))
                maze[xCurrent - 2][yCurrent].visited = true;*/
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
            if (checkIfVisit(xCurrent,yCurrent + 2))
                maze[xCurrent][yCurrent + 2].visited = true;
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
            if (checkIfVisit(xCurrent + 2,yCurrent))
                maze[xCurrent + 2][yCurrent].visited = true;
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
            if (checkIfVisit(xCurrent,yCurrent - 2))
                maze[xCurrent][yCurrent - 2].visited = true;
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



    public ArrayList<Direction> BFS(){
        ArrayList<Direction> answer = new ArrayList<>();
        ArrayList<Node> queue = new ArrayList<>();
        ArrayList<Node> addedNodes = new ArrayList<>();
        queue.add(new Node(true,null,xCurrent,yCurrent));
        addedNodes.add(new Node(true,null,xCurrent,yCurrent));
        while (queue.size() > 0){
            if (queue.get(0).visited == false){
                Node currentNode = queue.get(0);
                Node prevNode = currentNode.visitedFrom;
                while (prevNode != null){
                    answer.add(calculateDirection(prevNode.x, prevNode.y, currentNode.x, currentNode.y));
                    currentNode = prevNode;
                    prevNode = currentNode.visitedFrom;
                }
                return answer;
            }
            else{
                Node currentNode = queue.get(0);
                int x = currentNode.x;
                int y = currentNode.y;
                if (maze[x][y - 1].type == '0' && checkForNode(x,y-2,addedNodes) == false){
                    queue.add(new Node(maze[x][y - 2].visited,currentNode,x,y - 2));
                    addedNodes.add(new Node(maze[x][y - 2].visited,currentNode,x,y - 2));
                }
                if (maze[x][y + 1].type == '0' && checkForNode(x,y+2,addedNodes) == false){
                    queue.add(new Node(maze[x][y + 2].visited,currentNode,x,y + 2));
                    addedNodes.add(new Node(maze[x][y + 2].visited,currentNode,x,y + 2));
                }
                if (maze[x - 1][y].type == '0' && checkForNode(x-2,y,addedNodes) == false){
                    queue.add(new Node(maze[x - 2][y].visited,currentNode,x - 2,y));
                    addedNodes.add(new Node(maze[x - 2][y].visited,currentNode,x - 2,y));
                }
                if (maze[x + 1][y].type == '0' && checkForNode(x+2,y,addedNodes) == false){
                    queue.add(new Node(maze[x + 2][y].visited,currentNode,x + 2,y));
                    addedNodes.add(new Node(maze[x + 2][y].visited,currentNode,x + 2,y));
                }
                queue.remove(0);
            }
        }
        answer.add(new Direction("MAPPED",-1,-1));
        return answer;
    }
    public boolean checkIfVisit (int xCurrent, int yCurrent){
        if ((maze[xCurrent - 1][yCurrent].type == '0' && maze[xCurrent - 2][yCurrent].visited)
                || maze[xCurrent - 1][yCurrent].type == '+'){
            if ((maze[xCurrent + 1][yCurrent].type == '0' && maze[xCurrent + 2][yCurrent].visited)
                    || maze[xCurrent + 1][yCurrent].type == '+'){
                if ((maze[xCurrent][yCurrent - 1].type == '0' && maze[xCurrent][yCurrent -2].visited)
                        || maze[xCurrent][yCurrent - 1].type == '+'){
                    if ((maze[xCurrent][yCurrent + 1].type == '0' && maze[xCurrent][yCurrent + 2].visited)
                            || maze[xCurrent][yCurrent + 1].type == '+'){
                        return true;
                    }
                }
            }
        }
            return false;
    }
    public boolean checkForNode (int x, int y, ArrayList<Node> nodes){
        for (int i = 0; i < nodes.size(); i++){
            Node node = nodes.get(i);
            if (node.x == x && node.y == y){
                return true;
            }
        }
        return false;
    }
    public Direction calculateDirection(int xFirst, int yFirst, int xSecond, int ySecond){
        Direction direct = new Direction();
        if (xFirst == xSecond){
            if (yFirst > ySecond){
                direct.direction = "up";
                direct.x = 0;
                direct.y = -2;
                return direct;
            }
            else if (yFirst < ySecond){
                direct.direction = "down";
                direct.x = 0;
                direct.y = 2;
                return direct;
            }
            throw new IllegalArgumentException("Próbujesz przejść do tego samego wierzchołka");
        }
        else{
            if (xFirst > xSecond){
                direct.direction = "left";
                direct.x = -2;
                direct.y = 0;
                return direct;
            }
            else if (xFirst < xSecond){
                direct.direction = "right";
                direct.x = 2;
                direct.y = 0;
                return direct;
            }
            throw new IllegalArgumentException("Próbujesz przejść do tego samego wierzchołka");
        }
    }
}
