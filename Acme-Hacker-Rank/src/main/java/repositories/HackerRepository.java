
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hacker;

@Repository
public interface HackerRepository extends JpaRepository<Hacker, Integer> {

	@Query("select h from Hacker h where h.id = ?1")
	Hacker findById(int id);

	@Query("select h from Hacker h where h.userAccount.username = ?1")
	Hacker findByUserName(String username);

	@Query("select h from Hacker h where h.userAccount.id = ?1")
	Hacker findByUserAccountId(int id);

	@Query("select h from Hacker h where h.finder.id = ?1")
	Hacker findByFinder(int finderId);
}
