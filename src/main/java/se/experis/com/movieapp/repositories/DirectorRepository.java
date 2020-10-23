package se.experis.com.movieapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.com.movieapp.models.Director;

public interface DirectorRepository extends JpaRepository<Director,Integer> {
}
