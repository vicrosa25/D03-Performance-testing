package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Proclaim;

@Repository
public interface ProclaimRepository extends JpaRepository<Proclaim, Integer> {
	
	@Query("select ch.proclaims from Chapter ch where ch.id = ?1")
	Collection<Proclaim> findByChapter(int chapterId);

}
