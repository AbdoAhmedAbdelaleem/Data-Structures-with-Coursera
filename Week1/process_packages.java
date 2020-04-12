/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Week1;

/**
 *
 * @author Abdo
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Request {

    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}

class Response {

    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {

    public Buffer(int size) {
        this.size_ = size;
        this.finish_time_ = new LinkedList<>();
    }

    public Response Process(Request request) {

        boolean isDroped = true;
        int startTime = -1;
        if (finish_time_.size() == 0) {
            if (size_ > 0) {
                startTime = request.arrival_time;
                isDroped = false;
                finish_time_.addLast(request.arrival_time + request.process_time);
            }
            return new Response(isDroped, startTime);
        }
        if (request.arrival_time >= finish_time_.getFirst()) {
            finish_time_.removeFirst();
        }
        //if buffer can process more element add it to buffer
        if (finish_time_.size() < size_) {
            if (finish_time_.size() == 0) {
                startTime = request.arrival_time;
            } else {
                startTime = finish_time_.getLast();
            }
            finish_time_.addLast(startTime + request.process_time);
            isDroped = false;
        }
        return new Response(isDroped, startTime);
    }

    private int size_;
    LinkedList<Integer> finish_time_;
}

class process_packages {

    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
    }
}
