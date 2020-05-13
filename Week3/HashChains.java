/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Week3;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Abdo
 */
class HashSetString {

    private LinkedList<String>[] elems;
    int size;
    int defaultCapacity = 4;

    public HashSetString() {
        elems = new LinkedList[defaultCapacity];
    }

    public HashSetString(int capacity) {
        elems = new LinkedList[capacity];
    }

    void Add(String key) {
        String currentKey = findContact(key);
        if (currentKey != null) {
            return;
        }

        int pos = hash(key);
        if (elems[pos] == null) {
            elems[pos] = new LinkedList<>();
        }
        elems[pos].addFirst(key);
    }

    private int hash(String x) {
        long hash = 0;
        for (int i = x.length() - 1; i >= 0; --i) {
            hash = (hash * multiplier + x.charAt(i)) % prime;
        }
        return (int) hash % elems.length;
    }

    boolean hasKey(String key) {
        return findContact(key) != null;
    }

    void delete(String key) {
        String contact = findContact(key);
        if (contact == null) {
            return;
        }
        int pos = hash(key);
        elems[pos].remove(contact);
    }

    private String findContact(String key) {
        int pos = hash(key);
        LinkedList<String> list = elems[pos];
        if (list == null || list.size() == 0) {
            return null;
        }
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String con = iterator.next();
            if (con.equals(key)) {
                return con;
            }
        }
        return null;
    }

    public int getPosition(String x) {
        return hash(x);
    }

    String get(String key) {
        return findContact(key);
    }

    private void resize() {
        int newSize = size * 2;
        LinkedList<String>[] newArr = new LinkedList[newSize];
        for (int i = 0; i < size; i++) {
            newArr[i] = elems[i];
        }
        elems = newArr;
    }

    private int prime = 1000000007;
    private int multiplier = 263;

    Iterator<String> elementAt(int ind) {
        if (elems[ind] == null || elems[ind].isEmpty()) {
            return Collections.emptyIterator(); 
        } else {
            return elems[ind].iterator();
        }
    }
}

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    HashSetString hashSet = new HashSetString();
    private int prime = 1000000007;
    private int multiplier = 263;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
         out.flush();
    }

    private void processQuery(Query query) {
        switch (query.type) {
            case "add":
                hashSet.Add(query.s);
                break;
            case "del":
                hashSet.delete(query.s);
                break;
            case "find":
                writeSearchResult(hashSet.hasKey(query.s));
                break;
            case "check":
                Iterator<String> elements = hashSet.elementAt(query.ind);
                while (elements.hasNext()) {
                    String next = elements.next();
                    out.print(next+" ");
                }

                out.println();
                // Uncomment the following if you want to play with the program interactively.
                out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        int bucketCount = in.nextInt();
        hashSet = new HashSetString(bucketCount);
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {

        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class FastScanner {

        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
