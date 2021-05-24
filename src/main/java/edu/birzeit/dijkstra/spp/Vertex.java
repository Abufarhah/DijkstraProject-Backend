package edu.birzeit.dijkstra.spp;

import edu.birzeit.dijkstra.models.Country;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Vertex implements Comparable {
    private Country country;
    private List<Vertex> shortestPath = new LinkedList<>();
    private Double distance = Double.MAX_VALUE;
    private boolean known;
    private Map<Vertex, Double> adjacentVertices = new HashMap<>();

    public Vertex() {
    }

    public Vertex(Country country) {
        this.country = country;
    }

    public Vertex(Country country, List<Vertex> shortestPath, Double distance, boolean known, Map<Vertex, Double> adjacentVertices) {
        this.country = country;
        this.shortestPath = shortestPath;
        this.distance = distance;
        this.known = known;
        this.adjacentVertices = adjacentVertices;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Vertex> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Vertex> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Map<Vertex, Double> getAdjacentVertices() {
        return adjacentVertices;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public void setAdjacentVertices(Map<Vertex, Double> adjacentVertices) {
        this.adjacentVertices = adjacentVertices;
    }

    public void addAdjacent(Vertex vertex, double distance) {
        adjacentVertices.put(vertex, distance);
    }

    public void addAdjacent(Vertex vertex) {
        adjacentVertices.put(vertex, getDistance(this.country, vertex.getCountry()));
    }

    private double getDistance(Country source, Country destination) {
        double theta = source.getLongitude() - destination.getLongitude();
        double dist = Math.sin(Math.toRadians(source.getLatitude()))
                * Math.sin(Math.toRadians(destination.getLatitude()))
                + Math.cos(Math.toRadians(source.getLatitude()))
                * Math.cos(Math.toRadians(destination.getLatitude()))
                * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        return dist;
    }

    @Override
    public int compareTo(Object o) {
        return distance.compareTo(((Vertex) o).getDistance());
    }
}
