/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Week1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

/**
 *
 * @author Abdo
 */
class Bracket {

    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']') {
            return true;
        }
        if (this.type == '{' && c == '}') {
            return true;
        }
        if (this.type == '(' && c == ')') {
            return true;
        }
        return false;
    }

    char type;
    int position;
}
public class check_brackets {

    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();
        int failedIndex = -1;
        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
                // Process opening bracket, write your code here
                opening_brackets_stack.push(new Bracket(next, position));
            }

            if (next == ')' || next == ']' || next == '}') {
                if (opening_brackets_stack.empty() || !opening_brackets_stack.pop().Match(next)) {
                    failedIndex = position;
                    break;
                }
            }
        }
        if (failedIndex == -1 && opening_brackets_stack.empty()) {
            System.out.print("Success");
        } else if (failedIndex == -1 && !opening_brackets_stack.empty()) {
            System.out.print(opening_brackets_stack.pop().position+1);
        } else {
            System.out.print(failedIndex + 1);
        }

    }
}


