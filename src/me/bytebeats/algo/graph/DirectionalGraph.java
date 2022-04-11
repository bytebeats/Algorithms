package me.bytebeats.algo.graph;

import java.util.LinkedList;

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created at 2022/4/11 20:22
 * @Version 1.0
 * @Description TO-DO
 */

/**
 * Directional Graph
 */
public class DirectionalGraph {
    private final int vertices;
    private int edges = 0;
    private LinkedList<LinkedList<Integer>> adj;

    public DirectionalGraph(int vertices) {
        if (vertices < 0) {
            throw new IllegalArgumentException("Number of vertices in a DirectionalGraph must be non-negative");
        }
        this.vertices = vertices;
        this.edges = 0;
        this.adj = new LinkedList<>();
        for (int i = 0; i < vertices; i++) {
            this.adj.add(new LinkedList<>());
        }
    }

    public void addEdge(int v, int w) {
        if (v < 0 || v >= vertices) {
            throw new IndexOutOfBoundsException("vertex " + v + " is not in bounds");
        }
        if (w < 0 || w >= vertices) {
            throw new IndexOutOfBoundsException("vertex " + w + " is not in bounds");
        }
        this.adj.get(v).add(w);
        this.edges++;
    }

    public int getVertices() {
        return vertices;
    }

    public int getEdges() {
        return edges;
    }

    public LinkedList<LinkedList<Integer>> getAdj() {
        return adj;
    }
}
