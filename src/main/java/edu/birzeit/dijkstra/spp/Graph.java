package edu.birzeit.dijkstra.spp;

import java.util.HashSet;
import java.util.Set;

public class Graph {
    private Set<Vertex> vertices = new HashSet<>();

    public Graph() {
    }

    public Graph(Set<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void addNode(Vertex vertex) {
        vertices.add(vertex);
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(Set<Vertex> vertices) {
        this.vertices = vertices;
    }
}
