package edu.birzeit.dijkstra.controllers;

import edu.birzeit.dijkstra.models.Country;
import edu.birzeit.dijkstra.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping
    public List<Country> getCountries() {
        return countryService.getCountries();
    }

    @GetMapping("/path")
    public PathDto findPath(@RequestParam String source, @RequestParam String destination) {
        return countryService.findPath(source, destination);
    }

}
