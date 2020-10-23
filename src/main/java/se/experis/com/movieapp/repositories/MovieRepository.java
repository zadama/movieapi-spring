package se.experis.com.movieapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import se.experis.com.movieapp.models.Actor;
import se.experis.com.movieapp.models.Movie;
import javax.transaction.Transactional;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Integer> {


    @Query("SELECT m FROM Movie m WHERE UPPER(m.title) LIKE CONCAT('%',:title,'%')")
    public List<Movie> getMovieByTitleIsLike(String title);

    @Modifying
    @Transactional
    @Query(value = "DELETE from movie_actor WHERE actor_id = ?1",nativeQuery = true)
    void deleteActor(Integer id);

}
