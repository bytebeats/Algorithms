package me.bytebeats.algo.graph;

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created at 2022/4/12 12:02
 * @Version 1.0
 * @Description TO-DO
 */

public class UndirectionalGraphCycleDetector {
    private boolean[] marked;

    public UndirectionalGraphCycleDetector(DirectionalGraph dg) {
        marked = new boolean[dg.getVertices()];
    }

    public boolean hasCycle(DirectionalGraph dg) {
        for (int v = 0; v < dg.getVertices(); v++) {
            if (dfs(dg, v)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Not sure its right
     *
     * @param dg
     * @param v
     * @return
     */
    private boolean dfs(DirectionalGraph dg, int v) {
        if (marked[v]) return true;
        marked[v] = true;
        for (Integer w : dg.getAdj().get(v)) {
            if (dfs(dg, w)) {
                return true;
            }
        }
        marked[v] = false;
        return false;
    }
}
