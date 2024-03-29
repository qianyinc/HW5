package edu.cmu.mis.iccfb.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Author {
    private Long id;
    private String name;
    protected Author() {}
    public Author(String name) {
        this.name = name;

    }
    public String toString() {
        return String.format("Author[id=%d, name='%s']", id, this.name);
    }

    public Long getAuthorId () {
        return this.id;
    }
    public void setAuthorId () {this.id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
