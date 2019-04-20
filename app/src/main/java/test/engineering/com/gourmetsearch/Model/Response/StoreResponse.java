package test.engineering.com.gourmetsearch.Model.Response;

import java.util.List;

public class StoreResponse {

    private String id;
    private String name;
    private String address;
    private double lat;
    private double lng;
    private String access;
    private Urls urls;
    private String open;

    public StoreResponse(String id, String name, String address, double lat, double lng, String access, Urls urls, String open) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.access = access;
        this.urls = urls;
        this.open = open;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAccess() {
        return this.access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Urls getUrls() {
        return this.urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public String getOpen() {
        return this.open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
}
