/*File: Graph.class
 *Purpose: Provide Basic functionality to store undirected graph
 *         and look for triangles
 * Aurthor:Zhenjie Ruan(zhenjier@bu.edu) Yihong Guo(billguo@bu.edu)
 * Date: April 25 2014
 */
import java.util.*;

public class Graph{
    private static int[][] B = new int[6][6];
    //constructor
    public Graph(int N){
        
    }
    
    //add an edge between two dots
    public void addEdge(int u, int v, int w){
        B[u][v] = w;
        B[v][u] = w;
    }
    
    //remove an edge from two dots
    public void removeEdge(int u,int v){
        addEdge(u,v,0);
    }
    

    //return the value of the edge
    public int getEdge(int u, int v){
        return B[u][v];
    }
    
    //return true or false depends on if there is an edge
    public boolean isEdge(int u, int v){
        return (B[u][v] != 0);
    }
    

    //return the number of edges connected, either color
    public int degree(int v){
        int count = 0;
        for(int i = 0; i < B[v].length; ++i){
            if(B[v][i] != 0){
                ++count;
            }
        }
        return count;
    }
    

    //return the number of edges connected of specific color
    public int degree(int v,int w){
        int count = 0;
        for(int i = 0; i < B[v].length; ++i){
            if(B[v][i] == w){
                ++count;
            }
        }
        return count;
    }
    

    //print our all the edges
    //just a debugger
    public void printEdges(){
        System.out.print("    ");
        for(int i = 0; i < B[0].length; ++i){
            System.out.print(i + "    ");
        }
        System.out.println();
        for(int i = 0; i < B[0].length; ++i){
            System.out.print(i + ":");
            for(int j = 0; j < B[0].length; ++j){
                System.out.print(B[i][j] + "    ");
            }
            System.out.println();
            System.out.println();
        }
    }
    

    //check if there is a cycle
    public boolean isCycleOfLength(int n, int w){
        int i = 0;
        while(i < 6){
            //System.out.println(isCycleHelper(i,i,n,w));
            if(isCycleHelper(i,i,n,w)){
                return true;
            }
            ++i;
        }
        return false;
    }
    
    private boolean isCycleHelper(int u, int v, int n, int w){
        if(n == 1){
            int[] adjacent = new int[10];
            adjacent = adjacent(u,w);
            //System.out.println(Arrays.toString(adjacent));
            for(int i = 0; i < adjacent.length; ++i){
                if(v == adjacent[i])
                    return true;
            }
            return false;
        }
        //System.out.println(u+" "+v);
        int[] temp = new int[10];
        temp = this.adjacent(u,w);
        if(temp.length == 6){
            return false;
        }
        for (int i = 0; i < temp.length; i++)
        {
            if (isCycleHelper(temp[i], v, n - 1, w))
                return true;
        }
        return false;
    }
    

    //get the adjacent list
    public int[] adjacent(int u, int w){
        int[] a = new int[B[u].length];
        int count = 0;
        for(int i = 0; i < a.length; ++i){
            if(B[u][i] == w){
                a[count] = i;
                ++count;
            }
        }
        if(!(count == 0)){
            int[] b = new int[count];
            for(int i = 0; i < b.length; ++i){
                b[i] = a[i];
            }
            return b;
        }else{
            return a;
        }
    }
    
    //unit test
    public static void main(String[] args){
        Graph G = new Graph(6);
        System.out.println("Add blue edge to 0,1");
        G.addEdge(0,1,-1);
        G.printEdges();
        System.out.println("Add blue edge to 0,2");
        G.addEdge(0,2,-1);
        G.printEdges();
        System.out.println("Add blue edge to 1,2");
        G.addEdge(1,2,-1);
        G.printEdges();
        System.out.println("Formed a cycle of 3 now");
        System.out.println("is cycle of 3: " + G.isCycleOfLength(3,-1));
        System.out.println("remove edge 0,1 now");
        G.removeEdge(0,1);
        G.printEdges();
        System.out.println("Degree of 2, should be 2: " + G.degree(2));
        System.out.println("Degree of red line of 2, should be 0: " + G.degree(2,1));
    }
}
