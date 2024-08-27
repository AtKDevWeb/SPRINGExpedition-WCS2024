package org.wcs.myBlog.models;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 50)
    private String name;

    //Constructeur

    //Getter
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //Setter
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
