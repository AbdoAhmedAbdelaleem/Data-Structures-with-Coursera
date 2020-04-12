/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Week1;

import java.util.*;
import java.io.*;

/**
 *
 * @author Abdo
 */
public class StackWithMax {

    class FastScanner {

        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements()) {
                tok = new StringTokenizer(in.readLine());
            }
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public void solve() throws IOException {
        FastScanner scanner = new FastScanner();
        int queries = scanner.nextInt();
        Stack<Integer> stack = new Stack<Integer>();
        Stack<Integer> helpMaxStack = new Stack<Integer>();

        for (int qi = 0; qi < queries; ++qi) {
            String operation = scanner.next();
            if ("push".equals(operation)) {
                int value = scanner.nextInt();
                if (helpMaxStack.empty() || value >= helpMaxStack.peek()) {
                    helpMaxStack.push(value);
                }
                stack.push(value);
            } else if ("pop".equals(operation)) {
                if (stack.peek().intValue() == helpMaxStack.peek().intValue()) {
                    helpMaxStack.pop();
                }
                stack.pop();

            } else if ("max".equals(operation)) {
                System.out.println(helpMaxStack.peek());
            }
        }
    }
    static public void main(String[] args) throws IOException {
        new StackWithMax().solve();
    }
}
