import java.util.ArrayList;


public class Node {

    public boolean visited;
    public Node visitedFrom;
    public int index;
    public int x;
    public int y;
  
    public Node(boolean visited, Node visitedFrom, int x, int y){
        this.visited = visited;
        this.visitedFrom = visitedFrom;
        this.x = x;
        this.y = y;
    }
}

