package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
	@Query("select p from Position p join p.enrol e where e.id = ?1")
	Position findByEnrol(int enrolId);

}
