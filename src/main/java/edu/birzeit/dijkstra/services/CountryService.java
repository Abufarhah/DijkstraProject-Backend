package edu.birzeit.dijkstra.services;

import edu.birzeit.dijkstra.controllers.PathDto;
import edu.birzeit.dijkstra.models.Country;
import edu.birzeit.dijkstra.spp.GraphUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    public List<Country> getCountries() {
        return GraphUtil.vertices.values().stream().map(vertex -> vertex.getCountry()).collect(Collectors.toList());
    }

    public PathDto findPath(String source, String destination) {
        return GraphUtil.findPath(source, destination);
    }
}
