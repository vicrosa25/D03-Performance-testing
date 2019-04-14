
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface CurriculaRepository extends JpaRepository<Position, Integer> {

}
