
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface MiscellaneousDataRepository extends JpaRepository<Position, Integer> {

}
