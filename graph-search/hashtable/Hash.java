// Implement 2-SUM problem with hashtable
import java.io.IOException;
import java.io.*;
import java.util.*;

public class Hash {
    TreeMap<Long, Integer> input;
    
    public Hash() {
        input = new TreeMap<Long, Integer>();
    }

    public static void main(String[] args) throws IOException {
        File file = new File("2-sum.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String [] tokens;
        long x, y;
        int i=0;
        long count=0;
        long T_low=-10000;
        long T_high=10000;
        long low, high;
        TreeSet<Long> sum = new TreeSet<Long>();

        Hash h = new Hash();

        while ((line=br.readLine())!=null) {
            tokens = line.split("\\s+");
            x = Long.parseLong(tokens[0]);

            h.input.put(x, i);
            i = i+1;
        }
        System.out.println("The size of dataset : "+h.input.size());

        long start = System.currentTimeMillis();
            for (long k: h.input.keySet()){
                high = T_high +1 - k;
                low = T_low - k;
                
                Set<Long> bound = h.input.subMap(low, high).keySet();

                for (long s: bound) {
                    sum.add(s + k);
                }
            }
        count = sum.size();

        System.out.println("The number of pair is: "+count);
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("The time elapsed is "+ elapsedTime);
    }
}