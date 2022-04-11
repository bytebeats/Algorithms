package me.bytebeats.algo.graph;

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created at 2022/4/11 20:53
 * @Version 1.0
 * @Description TO-DO
 */

public class Topological {
    private Iterable<Integer> order;

    public Topological(DirectionalGraph dg) {
        DGCircleDetector detector = new DGCircleDetector(dg);
        if (detector.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(dg);
            order = dfs.reversePost();
        }
    }

    public Iterable<Integer> order() {
        return order;
    }
}
