import java.io.IOException;
import java.io.*;
import java.util.*;
import java.util.PriorityQueue;

public class Heap {
    PriorityQueue<Integer> min_heap;
    PriorityQueue<Integer> max_heap;

    public Heap(){
        min_heap = new PriorityQueue<>();
        max_heap = new PriorityQueue<>((x,y)->y-x);
    }

    public static void main(String[] args) throws IOException {
        File file = new File("median.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String [] tokens;
        int i=0;
        int x, y, z;
        int size;
        int median=0;
        int total = 0;
        Heap h = new Heap();
        while ((line=br.readLine()) != null) {
            tokens = line.split("\\s+");
            x = Integer.parseInt(tokens[0]);
            
            if (i==0) {
                h.max_heap.add(x);
                i = i+1;
            } else {
                y = h.max_heap.peek();
                
                if (x<y) {
                    h.max_heap.add(x);
                } else {
                    h.min_heap.add(x);
                }
                // Check balance between 2 heaps
                if (h.max_heap.size()-h.min_heap.size()==2){
                    h.min_heap.add(h.max_heap.poll());
                } else if (h.max_heap.size()-h.min_heap.size()==-2) {
                    h.max_heap.add(h.min_heap.poll());
                }
                i = i+1;
            }
            size = h.max_heap.size()+h.min_heap.size();
            if ((size%2)==0) {
                median = h.max_heap.peek();
            } else if ((size%2)!=0 && h.max_heap.size()<h.min_heap.size()) {
                median = h.min_heap.peek();
            } else if ((size%2)!=0 && h.max_heap.size()>h.min_heap.size()) {
                median = h.max_heap.peek();
            }
            total = total + median;
        }
        System.out.println("The total is: " + (total%10000));
    }
}