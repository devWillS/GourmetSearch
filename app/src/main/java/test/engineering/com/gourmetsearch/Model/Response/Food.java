package test.engineering.com.gourmetsearch.Model.Response;

import java.io.Serializable;

public class Food implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Food(String name, String code) {
        this.name = name;
        this.code = code;
    }

    private String name;
    private String code;
}
