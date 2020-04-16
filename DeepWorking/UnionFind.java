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
public class UnionFind implements IUnionFind {

    int[] ranks;
    int[] parents;

    public UnionFind() {
        ranks = new int[100];
        parents = new int[100];
    }

    @Override
    public int find(int i) {
        while (i != parents[i]) {
            i = parents[i];
        }
        return i;
    }

    public int find2(int i) {
        if (i == parents[i]) {
            return i;
        }
        parents[i] = find2(parents[i]);
        return parents[i];
    }

    @Override
    public void makeSet(int i) {
        parents[i] = i;
        ranks[i] = 0;
    }

    @Override
    public void union(int i, int j) {
        int iID = find2(i);
        int jID = find2(j);
        if (iID == jID) {
            return;
        }
        if (ranks[iID] > ranks[jID]) {
            parents[jID] = iID;
        } else if (ranks[iID] < ranks[jID]) {
            parents[iID] = jID;
        } else {
            parents[jID] = iID;
            ranks[iID]++;
        }

    }

    public int getHeight(int i) {
        int height = 0;
        while (i != parents[i]) {
            height++;
            i=parents[i];
        }
        return height;
    }

    public static void main(String[] args) {
        UnionFind unionFind = new UnionFind();
        for (int i = 1; i <= 60; i++) {
            unionFind.makeSet(i);
        }
        for (int i = 1; i <= 30; i++) {
            unionFind.union(i, 2 * i);
        }
        for (int i = 1; i <= 20; i++) {
            unionFind.union(i, 3 * i);
        }
        for (int i = 1; i <= 12; i++) {
            unionFind.union(i, 5 * i);
        }
        for (int i = 1; i <= 60; i++) {
            unionFind.find2(i);
        }
      
        int product = 1;
        int maxHeight=0;
        for (int i = 1; i <= 60; i++) {
           maxHeight=Math.max(unionFind.getHeight(i), maxHeight);
        }
        System.out.println(maxHeight);
    }

}
