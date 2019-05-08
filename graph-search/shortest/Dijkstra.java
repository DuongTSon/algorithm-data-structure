// Your task is to code up the algorithm from the video lectures for computing strongly connected components (SCCs), 
// and to run this algorithm on the given graph.

import java.io.IOException;
import java.io.*;
import java.util.*;

public class Dijkstra {
    Map<Integer, LinkedList<Integer>> adj;
    Map<Integer, LinkedList<Integer>> adj_len;

    public Dijkstra() {
        adj = new HashMap<Integer, LinkedList<Integer>>();
        adj_len = new HashMap<Integer, LinkedList<Integer>>();
    }

    public void addNode(Integer node) {
        adj.computeIfAbsent(node, k->new LinkedList<Integer>());
        adj_len.computeIfAbsent(node, k->new LinkedList<Integer>());
    }

    public void addEdge(Integer v, Integer w, Integer l) {
        adj.get(v).add(w);
        adj_len.get(v).add(l);
    }

    public void printGraph(){
        for (Integer v:adj.keySet()){
            Iterator<Integer> it = adj.get(v).listIterator();
            
            while (it.hasNext()) {
                Integer w = it.next();
                System.out.print(v.toString() + " " + w.toString());
            }
            System.out.println();
        }
    }

    public Integer size(){
        return adj.size();
    }

    public LinkedList<Integer> get(Integer v) {
        return adj.get(v);
    }

    Integer minDistance(int dist[], Boolean sptSet[]) {
        int min = Integer.MAX_VALUE, min_index=-1;
        for (int v: adj.keySet()) {
            if (sptSet[v]==false && dist[v]<=min) {
                min = dist[v];
                min_index = v;
            }
        }
        return min_index;
    }

    void printSolution(int dist[]){
        List<Integer> vertex= Arrays.asList(7,37,59,82,99,115,133,165,188,197);
        System.out.println("Vertex    Distance from Source");
        for (int v: vertex) {
                System.out.println(v+"      "+dist[v]);
        }
    }

    void dijkstra(int src) {
        int V = adj.size()+1;
        int dist[] = new int[V];
        Boolean sptSet[] = new Boolean[V];

        for (int v: adj.keySet()){
            dist[v] = Integer.MAX_VALUE;
            sptSet[v] = false;
        }
        dist[src] = 0;

        for (int count: adj.keySet()) {
            int u = minDistance(dist, sptSet);
            sptSet[u] = true;
            int idx ;
            for (int v: adj.keySet()) {
                    idx = adj.get(u).indexOf(v);
                    if (idx!=-1 && !sptSet[v] && adj_len.get(u).get(idx)!=0 &&
                            dist[u]!=Integer.MAX_VALUE &&
                            dist[u]+adj_len.get(u).get(idx)<dist[v])
                        dist[v] = dist[u] + adj_len.get(u).get(idx);
            }
        }
        printSolution(dist);
    }

    public static void main(String[] args) throws IOException {
        File file = new File("dijkstraData.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String [] tokens;

        Integer s, v, l;

        Dijkstra g = new Dijkstra();
        while ((line=br.readLine()) != null)
        {
            tokens = line.split("\\s+");
            s = Integer.parseInt(tokens[0]);
            g.addNode(s);
            for (int i=1; i<tokens.length; i++) {
                String[] parts = tokens[i].split(",");
                v = Integer.parseInt(parts[0]);
                l = Integer.parseInt(parts[1]);
                g.addNode(v);
                g.addEdge(s, v, l);
            }
        }
        br.close();
        g.dijkstra(1);
    }
}