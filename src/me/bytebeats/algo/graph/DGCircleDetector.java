package me.bytebeats.algo.graph;

import scala.Int;

import java.util.Stack;

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created at 2022/4/11 20:31
 * @Version 1.0
 * @Description TO-DO
 */

public class DGCircleDetector {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    private boolean[] onStack;


    public DGCircleDetector(DirectionalGraph dg) {
        onStack = new boolean[dg.getVertices()];
        edgeTo = new int[dg.getEdges()];
        marked = new boolean[dg.getVertices()];
        for (int v = 0; v < dg.getVertices(); v++) {
            if (!marked[v]) dfs(dg, v);
        }
    }

    private void dfs(DirectionalGraph dg, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (Integer w : dg.getAdj().get(v)) {
            if (hasCycle()) return;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(dg, w);
            } else if (onStack[w]) {
                cycle = new Stack<>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }
}
