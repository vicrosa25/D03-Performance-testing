
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	
	@Query("Select h.applications from Hacker h where h.id = ?1")
	Collection<Application> findByHacker(int hackerId);

	@Query("select a from Application a where a.problem.id = ?1")
	Collection<Application> findByProblem(int problemId);

	@Query("select a from Application a where a.position.company.id = ?1 order by a.status")
	Collection<Application> findByCompany(int companyId);

}
