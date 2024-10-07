package org.wcs.myBlog.DTO;

import java.util.List;

public class AuthorDTO {
    private long id;
    private String firstName;
    private String lastName;
    private List<String> articles;

    //Setter
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //Getter
    public String getFirstName() {
        return firstName;
    }

    public long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }
}
