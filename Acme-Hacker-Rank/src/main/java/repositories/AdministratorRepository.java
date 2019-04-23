
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Company;
import domain.Hacker;
import domain.Position;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select admin from Administrator admin where admin.userAccount.id = ?1")
	Administrator findByUserAccountId(int id);

	// Queries level C
	@Query("select avg(c.positions.size), min(c.positions.size), max(c.positions.size), stddev(c.positions.size) from Company c")
	Object[] query1();
	
	@Query("select avg(h.applications.size), min(h.applications.size), max(h.applications.size), stddev(h.applications.size) from Hacker h")
	Object[] query2();
	
	@Query("select c1 from Company c1 where c1.positions.size = (Select max(c2.positions.size) from Company c2)")
	Collection<Company> query3();
	
	@Query("select h1 from Hacker h1 where h1.applications.size = (Select max(h2.applications.size) from Hacker h2)")
	Collection<Hacker> query4();
	
	@Query("select avg(p.salary), min(p.salary), max(p.salary), stddev(p.salary) from Position p")
	Object[] query5();
	
	@Query("select p1 from Position p1 where p1.salary = (Select max(p2.salary) from Position p2)")
	Collection<Position> query6a();
	
	@Query("select p1 from Position p1 where p1.salary = (Select min(p2.salary) from Position p2)")
	Collection<Position> query6b();
	
	// Queries level B
	@Query("select avg(h.curriculas.size), min(h.curriculas.size), max(h.curriculas.size), stddev(h.curriculas.size) from Hacker h")
	Object[] query7();
	
	@Query("select avg(f.positions.size), min(f.positions.size), max(f.positions.size), stddev(f.positions.size) from Finder f")
	Object[] query8();
	
	@Query("select count(f)*1.0 / (select count(f1)*1.0 from Finder f1 where f1.positions.size > 0) from Finder f where f.positions.size = 0")
	Double query9();

}
