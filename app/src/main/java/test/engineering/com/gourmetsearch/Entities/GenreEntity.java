package test.engineering.com.gourmetsearch.Entities;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GenreEntity extends RealmObject implements Serializable {
    @PrimaryKey
    private String code;
    private String name;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
