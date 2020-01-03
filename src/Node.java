import java.util.ArrayList;


public class Node {

    public boolean visited;
    public Node visitedFrom;
    public int index;
    public int x;
    public int y;
    public ArrayList<Node> neighbours;

    public int findNeighbour(Node n){
        for (int i = 0; i < neighbours.size(); i++){
            if (neighbours.get(i) == n)
                return i;
        }
        throw new IllegalArgumentException("Nie ma takiego sąsiada");
    }

    public Node(int index){
        visited = false;
        visitedFrom = null;
        this.index = index;
    }
}

