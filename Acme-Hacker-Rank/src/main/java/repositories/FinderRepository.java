
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Position;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select p from Position p where p.salary <= ?1 and p.finalMode='1'")
	Collection<Position> filterByMaxSalary(Double maxSalary);

	@Query("select p from Position p where p.salary >= ?1 and p.finalMode='1'")
	Collection<Position> filterByMinSalary(Double minSalary);

	@Query("select p from Position p where (p.title LIKE ?1 or p.description LIKE " +
			"?1 or p.ticker like ?1 or p.skills like ?1 or p.technologies like ?1 or p.profile like ?1) and p.finalMode='1'")
	Collection<Position> filterByKeyword(String keyword);

	@Query("select p from Position p where p.deadline <= ?1 and p.finalMode='1'")
	Collection<Position> filterByDeadline(Date deadline);
}
