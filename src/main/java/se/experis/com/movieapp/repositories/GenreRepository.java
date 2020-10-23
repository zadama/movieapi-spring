package se.experis.com.movieapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.com.movieapp.models.Genre;

public interface GenreRepository extends JpaRepository<Genre,Integer> {
}
