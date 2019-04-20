package test.engineering.com.gourmetsearch.Model.Response;

import java.util.List;

public class Results {

    private String api_version;
    private String results_returned;
    private String results_start;
    private List<StoreResponse> shop;
    private List<GenreResponse> genre;
    private String results_available;

    public Results(String api_version, String results_returned, String results_start, List<StoreResponse> shop, List<GenreResponse> genre, String results_available) {
        this.api_version = api_version;
        this.results_returned = results_returned;
        this.results_start = results_start;
        this.shop = shop;
        this.genre = genre;
        this.results_available = results_available;
    }

    public String getApi_version() {
        return this.api_version;
    }

    public void setApi_version(String api_version) {
        this.api_version = api_version;
    }

    public String getResults_returned() {
        return this.results_returned;
    }

    public void setResults_returned(String results_returned) {
        this.results_returned = results_returned;
    }

    public String getResults_start() {
        return this.results_start;
    }

    public void setResults_start(String results_start) {
        this.results_start = results_start;
    }

    public List<StoreResponse> getShop() {
        return this.shop;
    }

    public void setShop(List<StoreResponse> shop) {
        this.shop = shop;
    }

    public List<GenreResponse> getGenre() {
        return this.genre;
    }

    public void setGenre(List<GenreResponse> genre) {
        this.genre = genre;
    }

    public String getResults_available() {
        return this.results_available;
    }

    public void setResults_available(String results_available) {
        this.results_available = results_available;
    }
}
