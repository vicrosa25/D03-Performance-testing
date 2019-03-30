
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Coach;

public interface CoachRepository extends JpaRepository<Coach, Integer> {

	@Query("select b.coaches from Brotherhood b where b.id = ?1")
	Collection<Coach> findByBrotherhood(int brotherhoodId);

}
