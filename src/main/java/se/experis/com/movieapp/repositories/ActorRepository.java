package se.experis.com.movieapp.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.com.movieapp.models.Actor;

public interface ActorRepository extends JpaRepository<Actor,Integer> {
}
