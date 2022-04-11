package me.bytebeats.algo.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created at 2022/4/11 20:46
 * @Version 1.0
 * @Description TO-DO
 */

public class DepthFirstOrder {
    private final boolean[] marked;
    private final Queue<Integer> pre;
    private final Queue<Integer> post;

    public DepthFirstOrder(DirectionalGraph dg) {
        pre = new LinkedList<>();
        post = new LinkedList<>();
        marked = new boolean[dg.getVertices()];
        for (int v = 0; v < dg.getVertices(); v++) {
            if (!marked[v]) dfs(dg, v);
        }
    }

    private void dfs(DirectionalGraph dg, int v) {
        pre.add(v);
        marked[v] = true;
        for (Integer w : dg.getAdj().get(v)) {
            if (!marked[w]) dfs(dg, w);
        }
        post.add(v);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        List<Integer> reverse = new LinkedList<>();
        for (Integer integer : post()) {
            reverse.add(0, integer);
        }
        return reverse;
    }
}
