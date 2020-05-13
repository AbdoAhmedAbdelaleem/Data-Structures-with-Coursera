/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/**
 *
 * @author Abdo
 */
class DictionaryIntger {

    LinkedList<PhoneBook.Contact> arr[];
    int p;
    int a, b;

    public DictionaryIntger(int capacity) {
        arr = new LinkedList[capacity];
        for (int i = capacity; i < 2 * capacity; i++) {
            if (isPrime(i)) {
                p = i;
                break;
            }
        }
        Random rnd = new Random();
        a = rnd.nextInt(p) + 1;
        b = rnd.nextInt(p) + 1;
    }

    private boolean isPrime(int x) {
        int sqrt = (int) Math.sqrt(x);
        for (int i = 2; i <= sqrt; i++) {

            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    void Add(int key, String value) {
        PhoneBook.Contact currentConta = findContact(key);
        if (currentConta != null) {
            currentConta.name = value;
            return;
        }
        PhoneBook.Contact con = new PhoneBook.Contact(value, key);
        int pos = hash(key);
        if (arr[pos] == null) {
            arr[pos] = new LinkedList<>();
        }
        arr[pos].add(con);
    }

    private int hash(int x) {
        return (int)(((a * (long)x + b) % p) % arr.length);
    }

    boolean hasKey(int key) {
        return findContact(key) != null;
    }

    void delete(int key) {
        PhoneBook.Contact contact = findContact(key);
        if (contact == null) {
            return;
        }
        int pos=hash(key);
        arr[pos].remove(contact);
    }

    private PhoneBook.Contact findContact(int key) {
        int pos = hash(key);
        LinkedList<PhoneBook.Contact> list = arr[pos];
        if (list == null || list.size() == 0) {
            return null;
        }
        Iterator<PhoneBook.Contact> iterator = list.iterator();
        while (iterator.hasNext()) {
            PhoneBook.Contact con = iterator.next();
            if (con.number == key) {
                return con;
            }
      
        }
        return null;
    }

    String get(int key) {
        PhoneBook.Contact contact = findContact(key);
        return contact == null ? null : contact.name;
    }
}

class DictionaryDirectAddressing {

    String[] arr;
    final int capacity = 10000000;

    public DictionaryDirectAddressing() {
        arr = new String[capacity];
    }

    void Add(int key, String value) {
        arr[key] = value;
    }

    boolean hasKey(int key) {
        return arr[key] != null;
    }

    void delete(int key) {
        arr[key] = null;
    }

    String get(int key) {
        return arr[key];
    }
}

public class PhoneBook {

    private FastScanner in = new FastScanner();
    // Keep list of all existing (i.e. not deleted yet) contacts.
    private List<Contact> contacts = new ArrayList<>();
    DictionaryIntger dict;

    public static void main(String[] args) {
        new PhoneBook().processQueries();
    }

    private Query readQuery() {
        String type = in.next();
        int number = in.nextInt();
        if (type.equals("add")) {
            String name = in.next();
            return new Query(type, name, number);
        } else {
            return new Query(type, number);
        }
    }

    private void writeResponse(String response) {
        System.out.println(response);
    }

    private void processQuery(Query query) {
        if (query.type.equals("add")) {
            dict.Add(query.number, query.name);
        } else if (query.type.equals("del")) {
            dict.delete(query.number);
        } else {
            String response = "not found";
            if (dict.hasKey(query.number)) {
                response = dict.get(query.number);
            }
            writeResponse(response);
        }
    }

    private void processQueryNaive(Query query) {
        if (query.type.equals("add")) {
            // if we already have contact with such number,
            // we should rewrite contact's name
            boolean wasFound = false;
            for (Contact contact : contacts) {
                if (contact.number == query.number) {
                    contact.name = query.name;
                    wasFound = true;
                    break;
                }
            }
            // otherwise, just add it
            if (!wasFound) {
                contacts.add(new Contact(query.name, query.number));
            }
        } else if (query.type.equals("del")) {
            for (Iterator<Contact> it = contacts.iterator(); it.hasNext();) {
                if (it.next().number == query.number) {
                    it.remove();
                    break;
                }
            }
        } else {
            String response = "not found";
            for (Contact contact : contacts) {
                if (contact.number == query.number) {
                    response = contact.name;
                    break;
                }
            }
            writeResponse(response);
        }
    }

    public void stressTest() {
        int x = 0;
        Random rnd = new Random();
        DictionaryIntger dict = new DictionaryIntger(1000);
        Hashtable<Integer, String> dict2 = new Hashtable<>();
        while (++x < 100) {
            int n = rnd.nextInt(20000);
            for (int i = 0; i < n; i++) {
                int typeInt = rnd.nextInt(3) + 1;
                int number = rnd.nextInt(20);
                String type = "del";
                if (typeInt == 1) {
                    //add
                    String name = "" + rnd.nextInt();
                    dict.Add(number, name);
                    dict2.put(number, name);
                } else if (typeInt == 2) {
                    // type = "find";
                    if ((dict.hasKey(number) && !dict2.containsKey(number))
                            || (!dict.hasKey(number) && dict2.containsKey(number))
                            ) {
                        System.out.println("Bad");
                        break;
                    } else {
                        if (dict.hasKey(number)&&!dict.get(number).equals(dict2.get(number))) {
                            System.out.println("Bad");
                            break;
                        } else {
                            System.err.println("Good");
                        }
                    }
                } else //delete
                {
                    dict.delete(number);
                    dict2.remove(number);
                }
            }
        }
    }

    public void processQueries() {
        int queryCount = in.nextInt();
        dict = new DictionaryIntger(queryCount);
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
    }

    static class Contact {

        String name;
        int number;

        public Contact(String name, int number) {
            this.name = name;
            this.number = number;
        }
    }

    static class Query {

        String type;
        String name;
        int number;

        public Query(String type, String name, int number) {
            this.type = type;
            this.name = name;
            this.number = number;
        }

        public Query(String type, int number) {
            this.type = type;
            this.number = number;
        }
    }

    class FastScanner {

        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
