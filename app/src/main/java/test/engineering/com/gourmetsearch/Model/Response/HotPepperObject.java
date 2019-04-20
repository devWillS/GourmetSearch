package test.engineering.com.gourmetsearch.Model.Response;

public class HotPepperObject {

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public HotPepperObject(Results results) {
        this.results = results;
    }

    public HotPepperObject() {

    }

    private Results results;
}
