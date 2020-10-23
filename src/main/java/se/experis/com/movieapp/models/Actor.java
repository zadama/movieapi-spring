package se.experis.com.movieapp.models;

import ch.qos.logback.classic.db.names.ColumnName;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String imdb_url;

    @Column(name = "date_of_birth", columnDefinition = "DATE")
    private LocalDate date_of_birth;

    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Movie> movies;

    public String getFirstname() {
        return firstname;
    }

    public Integer getId(){
        return id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImdb_url() {
        return imdb_url;
    }

    public void setImdb_url(String imdb_url) {
        this.imdb_url = imdb_url;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
