package edu.birzeit.dijkstra.spp;

import edu.birzeit.dijkstra.controllers.PathDto;
import edu.birzeit.dijkstra.models.Country;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Log4j2
public class GraphUtil {

    public static Graph graph = new Graph();
    public static Map<String, Vertex> vertices = new HashMap<>();

    static {
        File inputFile = null;
        try {
            inputFile = ResourceUtils.getFile("classpath:static/countries.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int numberOfVertices = 0;
        int numberOfEdges = 0;
        String line;
        int index = 0;
        String countryName = "";
        double countryLatitude = 0;
        double countryLongitude = 0;
        Vertex vertex = null;
        String first = null;
        String second = null;
        Vertex firstV = null;
        Vertex secondV = null;
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            if (index == 0) {
                numberOfVertices = Integer.parseInt(line.split(" ")[0]);
                numberOfEdges = Integer.parseInt(line.split(" ")[1]);
                log.info("Number of the vertices is: " + numberOfVertices);
                log.info("Number of the edges is: " + numberOfEdges);
            } else if (index <= numberOfVertices) {
                log.info("Start reading the countries");
                countryName = line.split(" ")[0];
                countryLatitude = Double.parseDouble(line.split(" ")[1]);
                countryLongitude = Double.parseDouble(line.split(" ")[2]);
                vertex = new Vertex(new Country(countryName, countryLatitude, countryLongitude));
                graph.addNode(vertex);
                if (index == numberOfVertices) {
                    log.info("Fill the map of vertices");
                    GraphUtil.graph.getVertices().stream().forEach(v -> vertices.put(v.getCountry().getCountryName(), v));
                }
            } else if (index <= numberOfVertices + numberOfEdges) {
                log.info("Start reading the edges");
                first = line.split(" ")[0];
                second = line.split(" ")[1];
                firstV = vertices.get(first);
                secondV = vertices.get(second);
                firstV.addAdjacent(secondV);
            }
            ++index;
        }
        log.info("Reading file finished");
    }

    public static PathDto findPath(String sourceCountryName, String destinationCountryName) {
        Vertex source = vertices.get(sourceCountryName);
        Vertex destination = vertices.get(destinationCountryName);
        calculateShortestPathFromSource(source);
        List<Country> path = new LinkedList<>();
        destination.getShortestPath().forEach(vertex -> path.add(vertex.getCountry()));
        if (path.size() != 0 || sourceCountryName.equals(destinationCountryName)) {
            path.add(destination.getCountry());
        }
        PathDto pathDto = new PathDto(path, destination.getDistance() * 111);
        return pathDto;
    }

    public static void calculateShortestPathFromSource(Vertex source) {
        graph.getVertices().forEach(vertex -> {
            vertex.setDistance(Double.MAX_VALUE);
            vertex.getShortestPath().clear();
            vertex.setKnown(false);
        });
        source.setDistance(0d);
        PriorityQueue<Vertex> verticesHeap = new PriorityQueue<>();

        verticesHeap.add(source);

        while (verticesHeap.size() != 0) {
            Vertex currentVertex = verticesHeap.poll();
            //iterate for each adjacent vertex of the current vertex
            currentVertex.getAdjacentVertices().forEach((vertex, distance) -> {
                if (!vertex.isKnown()) {
                    // set new value for the vertex if the distance from the current vertex
                    // less then the already calculated distance
                    // and set the shortest path
                    if (currentVertex.getDistance() + distance < vertex.getDistance()) {
                        vertex.setDistance(currentVertex.getDistance() + distance);
                        // get the shortest path from the source to the current
                        // add the the current
                        // set it as the shortest path to the vertex
                        List<Vertex> shortestPath = new LinkedList<>(currentVertex.getShortestPath());
                        shortestPath.add(currentVertex);
                        vertex.setShortestPath(shortestPath);
                    }
                    verticesHeap.add(vertex);
                }
            });
            currentVertex.setKnown(true);
        }
    }
}
