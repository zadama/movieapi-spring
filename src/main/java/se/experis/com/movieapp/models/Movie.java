package se.experis.com.movieapp.models;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId(){
        return id;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public float getImdb_rating() {
        return imdb_rating;
    }

    public void setImdb_rating(float imdb_rating) {
        this.imdb_rating = imdb_rating;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    @Column(length = 2048)
    private String plot;


    @Column(columnDefinition = "DATE")
    private LocalDate release_date;

    @Column(columnDefinition="Decimal(2,1)")
    private float imdb_rating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="director_id")
    private Director director;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="genre_id")
    private Genre genre;

    @ManyToMany(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "movie_actor",
            joinColumns = { @JoinColumn(name = "movie_id") },
            inverseJoinColumns = { @JoinColumn(name = "actor_id") }
    )
    private List<Actor> actors;

}
