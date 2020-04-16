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
public interface IUnionFind {

    public int find(int index);

    public void makeSet(int index);

    public void union(int i,int j);    

}
