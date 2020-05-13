/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Week2;

import java.io.*;
import java.util.StringTokenizer;

/**
 *
 * @author Abdo
 */
class Thread implements Comparable<Thread> {

    int finishTime;
    int threadIndex;

    public Thread(int finishTime, int threadIndex) {
        this.finishTime = finishTime;
        this.threadIndex = threadIndex;
    }

    @Override
    public int compareTo(Thread o) {
        if (finishTime < o.finishTime) {
            return -1;
        } else if (finishTime > o.finishTime) {
            return 1;
        }
        else if (threadIndex < o.threadIndex) {
            return -1;
        }
        else if (threadIndex > o.threadIndex) {
            return 1;
        }
        return 0;
    }

}

public class JobQueue {

    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobsEnhanced() {
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
       // java.util.PriorityQueue
        java.util.PriorityQueue<Thread> q =new java.util.PriorityQueue<>();
        for (int i = 0; i < numWorkers; i++) {
            q.offer(new Thread(0, i));
        }
        for (int i = 0; i < jobs.length; i++) {
            Thread th= q.poll();
            assignedWorker[i]=th.threadIndex;
            startTime[i]=th.finishTime;
            th.finishTime+=jobs[i];
            q.add(th);
        }
    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        long[] nextFreeTime = new long[numWorkers];
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            int bestWorker = 0;
            for (int j = 0; j < numWorkers; ++j) {
                if (nextFreeTime[j] < nextFreeTime[bestWorker]) {
                    bestWorker = j;
                }
            }
            assignedWorker[i] = bestWorker;
            startTime[i] = nextFreeTime[bestWorker];
            nextFreeTime[bestWorker] += duration;
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobsEnhanced();
        writeResponse();
        out.close();
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
