/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeepWorking;

/**
 *
 * @author Abdo
 */
public class HeapSort {
    private static void sort(Integer[]arr)
    {
        PriorityQueue<Integer> q=new PriorityQueue(arr.length);
        q.heapSortInPlace(arr);
      
    }
    public static void main(String[] args) {
     Integer[]arr={1,3,2,5,7,0,10,100,6};
        sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println("");
    }
    
}
