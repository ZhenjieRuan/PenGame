/*File: Player.java
 *Purpose:Choose an intelligent move for computer player
 *        Implemented with Minmax tree and alpha beta pruning
 * Aurthor:Zhenjie Ruan(zhenjier@bu.edu) Yihong Guo(billguo@bu.edu)
*/
public class Player {
    private final int D = 4;
     
    public Move chooseMove(Graph G) {
        int max = -10000;
        Move best = null;
        for (int u = 0; u < 6; u++) {
            for (int v = 0; v < 6; v++) {
                if ((!G.isEdge(u, v)) && (u != v)) {
                    G.addEdge(u, v, -1);
                    int val = minMax(G, 1, -10000, 10000);
                    System.out.println(max+" "+val);
                    if (val > max) {
                        best = new Move(u, v);
                        max = val;
                    }
                    G.removeEdge(u, v);
                }
            }
        }
        if(best == null){
            for (int u = 0; u < 6; u++) {
                for (int v = 0; v < 6; v++) {
                    if ((!G.isEdge(u, v)) && (u != v)) {
                        return new Move(u,v);
                    }
                }
            }
             
        }
        return best;
    }
     
    int minMax(Graph G, int depth, int alpha, int beta) {
        boolean flag = false;
        for (int u = 0; u < 6; u++) {
            for (int v = 0; v < 6; v++) {
                if ((!G.isEdge(u, v)) && (u != v)) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag == false || depth == D) {
            return eval(G);
        } else if(depth%2 == 0){
            int val = -10000;
            for (int u = 0; u < 6; u++) {
                for (int v = 0; v < 6; v++) {
                    if ((!G.isEdge(u, v)) && (u != v)) {
                        G.addEdge(u, v, -1);
                        alpha = max(alpha, val);
                        if (beta < alpha) {
                            G.removeEdge(u, v);
                            break;
                        }
                        val = max(val, minMax(G, depth + 1, alpha, beta));
                        G.removeEdge(u, v);
                    }
                }
            }
            return val;
        }else{
            int val = 10000;
            for (int u = 0; u < 6; u++) {
                for (int v = 0; v < 6; v++) {
                    if ((!G.isEdge(u, v)) && (u != v)) {
                        G.addEdge(u, v, 1);
                        beta = min(beta, val);
                        if (beta < alpha) {
                            G.removeEdge(u, v);
                            break;
                        }
                        val = min(val, minMax(G, depth + 1, alpha, beta));
                        G.removeEdge(u, v);
                    }
                }
            }
            return val;
             
        }
    }
     
    public int max(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }
     
    public int min(int a, int b){
        if(a < b){
            return a;
        }
        return b;
    }
     
    public int eval(Graph G) {
        int count = 0;
        if (G.isCycleOfLength(3,-1)){
            return -10000;
        } else if (G.isCycleOfLength(3, 1))
            return 10000;
         
        for (int u = 0; u < 6; u++) {
            int A = G.degree(u, 1);  //red
            int B = G.degree(u, -1);  //blue
             
            if (A > 1) {
                count = count + A;
            }
            if (B > 1) {
                count = count - B;
            }
        }
        return count;
    }
}
