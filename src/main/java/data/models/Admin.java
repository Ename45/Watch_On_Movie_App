package data.models;

import lombok.Data;

import java.util.List;
@Data

public class Admin {
    private String adminId;
    private String adminName;
    private List<Movie> movies;


}
