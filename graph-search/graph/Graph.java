// Your task is to code up the algorithm from the video lectures for computing strongly connected components (SCCs), 
// and to run this algorithm on the given graph.

import java.io.IOException;
import java.io.*;
import java.util.*;

public class Graph {
    Map<Integer, LinkedList<Integer>> adj;
    Integer numSCC;
    Map<Integer, List<Integer>> scc;

    public Graph() {
        adj = new HashMap<Integer, LinkedList<Integer>>();
        scc = new HashMap<Integer, List<Integer>>();
    }

    public void addNode(Integer node) {
        adj.computeIfAbsent(node, k->new LinkedList<Integer>());
    }

    public void addEdge(Integer v, Integer w) {
        adj.get(v).add(w);
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

    void dfsUtil(Integer v, List<Integer> explored) {
        explored.add(v);
        //System.out.print(v + " "); 
        Iterator<Integer> i = adj.get(v).listIterator();
        
        while (i.hasNext()) {
            Integer n = i.next();
            if (!explored.contains(n)) 
                dfsUtil(n, explored);
        }
    }

    void dfsUtilScc(Integer v, Boolean explored[], Integer SCC) {
        explored[v] = true;

        this.scc.get(SCC).add(v);
        //System.out.print("Add "+SCC+" "+v);
        
        Iterator<Integer> i = adj.get(v).listIterator();
        
        while (i.hasNext()) {
            Integer n = i.next();
            if (explored[n] == false) 
                dfsUtilScc(n, explored, SCC);
        }
    }

    void DFS(Integer v) {
        List<Integer> explored = new ArrayList<Integer>();
        dfsUtil(v, explored);
        System.out.println(explored);
    }

    void topoSortUtil(int v, Boolean explored[], Stack<Integer> stack) {
        explored[v] = true;
        
        Iterator<Integer> it = adj.get(v).listIterator();
        
        while (it.hasNext()) {
            Integer i = it.next();
            if (explored[i] == false) 
                topoSortUtil(i, explored, stack);
        }

        stack.push(new Integer(v));
    }

    public Stack<Integer> topoSort() {
        Stack<Integer> stack = new Stack<Integer>();
        Boolean explored[] = new Boolean[adj.size()+1];
        for (Integer i:adj.keySet()){
            explored[i] = false;
        }

        for (Integer i: adj.keySet()) {
            if (explored[i] == false)
                topoSortUtil(i, explored, stack);
        }
        return stack;  
    }

    public Graph reverse() {
        Graph reverse = new Graph();
        Integer w;
        for (Integer v: adj.keySet()) {
            Iterator<Integer> it = adj.get(v).listIterator();
            while (it.hasNext()) {
                w = it.next();
                reverse.addNode(w);
                reverse.addNode(v);
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    List<Integer> sorted() {
        List<Integer> countSCC = new ArrayList<Integer>();
        
        for (Integer s: this.scc.keySet()){
            countSCC.add(this.scc.get(s).size());
        }
        Collections.sort(countSCC);
        Collections.reverse(countSCC);
        return countSCC;
    }

    void kosaraju(){
        Graph reverse = this.reverse();
        // First pass of DFS
        System.out.println("The first pass...");
        Stack<Integer> stack = reverse.topoSort();
        
        // Second pass
        System.out.println("The second pass...");
        Boolean explored[] = new Boolean[adj.size()+1];
        for (Integer i:adj.keySet()){
            explored[i] = false;
        }
        this.numSCC = 0;
        while (stack.empty()==false) {
            Integer v = stack.pop();
            if (explored[v] == false){
                this.numSCC = this.numSCC +1 ;
                this.scc.computeIfAbsent(this.numSCC, k->new ArrayList<Integer>());
                this.dfsUtilScc(v, explored, this.numSCC);
            }
        }
        List<Integer> countSCC = this.sorted();
        System.out.println("The number of SCC: " + numSCC);
        
        // for (Integer v:this.scc.keySet()){
        //     Iterator<Integer> it = this.scc.get(v).listIterator();
        //     System.out.print(v.toString());

        //     while (it.hasNext()) {
        //         Integer w = it.next();
        //         System.out.print(" "+w.toString());
        //     }
        //     System.out.println();
        // }

        System.out.println("Top 5 SCCs:");
        Integer s=0;
        for (Integer i: countSCC) {
            System.out.println(i);
            s = s+1;
            if (s>4) {
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File("SCC.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String [] tokens;

        Graph g = new Graph();
        while ((line=br.readLine()) != null)
        {
            tokens = line.split("\\s+");
            g.addNode(Integer.parseInt(tokens[0]));
            g.addNode(Integer.parseInt(tokens[1]));
            g.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
        }
        br.close();
        System.out.println("SCCs:");
        g.kosaraju();
    }
}