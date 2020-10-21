package se.experis.com.movieapp.repositories;


import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.experis.com.movieapp.models.Actor;


import java.time.LocalDate;
import java.util.List;

public interface ActorRepository extends JpaRepository<Actor,Integer> {

    @Query("SELECT a FROM Actor a where a.date_of_birth >=?1 AND a.date_of_birth <= ?2")
            public List<Actor> filterByDate(LocalDate from, LocalDate to);

}
