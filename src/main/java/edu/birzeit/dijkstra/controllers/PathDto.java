package edu.birzeit.dijkstra.controllers;

import edu.birzeit.dijkstra.models.Country;

import java.util.List;

public class PathDto {
    private List<Country> countries;
    private double distance;

    public PathDto() {
    }

    public PathDto(List<Country> countries, double distance) {
        this.countries = countries;
        this.distance = distance;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "PathDto{" +
                "countries=" + countries +
                ", distance=" + distance +
                '}';
    }
}
