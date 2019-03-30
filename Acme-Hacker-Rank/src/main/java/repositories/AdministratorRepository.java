
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Brotherhood;
import domain.Chapter;
import domain.Procession;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	
	
	@Query("select admin from Administrator admin where admin.userAccount.id = ?1")
	Administrator findByUserAccountId(int id);

	// Queries level C
	@Query("select avg(b.enrols.size), min(b.enrols.size), max(b.enrols.size), stddev(b.enrols.size) from Brotherhood b")
	Object[] query1();

	@Query("select b1 from Brotherhood b1 where b1.enrols.size = (Select max(b2.enrols.size) from Brotherhood b2)")
	Collection<Brotherhood> query2();

	@Query("select b1 from Brotherhood b1 where b1.enrols.size = (Select min(b2.enrols.size) from Brotherhood b2)")
	Collection<Brotherhood> query3();

	@Query("select count(r1)*1.0 / (select count(r2)*1.0 from Request r2) from Request r1 group by r1.status")
	Collection<Double> query4();

	@Query("select p from Procession p where p.moment <= ?1")
	Collection<Procession> query5(Date date);
	
	@Query("select m.name, count(r), (select count(r2)*0.1 from Request r2) from Member m join m.requests r where r.status = 'APPROVED' group by m having count(r) >= (select count(r2)*0.1 from Request r2)")
	Collection<Object> query7();
	
	@Query("select p.englishName, p.enrol.size from Position p")
	Collection<Object> query8();
	
	
	// Queries level B
	@Query("select count(a)*1.0/(select count(b)*1.0 from Brotherhood b), count(a.brotherhoods.size), avg(a.brotherhoods.size), min(a.brotherhoods.size), max(a.brotherhoods.size), stddev(a.brotherhoods.size) from Area a")
	Object[] query9();
	
	@Query("select min(f.processions.size), max(f.processions.size), avg(f.processions.size), stddev(f.processions.size) from Finder f")
	Object[] query10();
	
	@Query("select count(f)*1.0 / (select count(f1)*1.0 from Finder f1 where f1.processions.size > 0) from Finder f where f.processions.size = 0")
	Double query11();
	
	
	
	// Chart queries
	@Query("select count(a) from Actor a where a.isSpammer = true")
	Integer getAllSpammers();

	@Query("select count(a) from Actor a where a.isSpammer = false")
	Integer getAllNotSpammers();
	
	@Query("select avg(a.score) from Actor a")
	Double getAveragePolarity();
	
	
	// ACME PARADE Queries level C
	@Query("select avg(h.recordNumber), min(h.recordNumber), max(h.recordNumber), stddev(h.recordNumber) from History h")
	Object[] query12();
	
	@Query("select b from Brotherhood b where b.history.recordNumber = (select max(b1.history.recordNumber) from Brotherhood b1)")
	Brotherhood query13();
	
	@Query("select b from Brotherhood b where b.history.recordNumber > (select avg(b1.history.recordNumber) from Brotherhood b1)")
	Collection<Brotherhood> query14();
	
	
	// ACME PARADE Queries level B
	@Query("select count(a)*1.0 / (select count(a1)*1.0 from Area a1) from Area a where a.chapter = null")
	Double query15();
	
	@Query("select count(a)*1.0 / (select count(a1)*1.0 from Area a1) from Area a where a.chapter = null")
	Object[] query16();
	
	@Query("select c from Chapter c join c.area a join a.brotherhoods b where b.processions.size >= (select  avg(b1.processions.size)*0.1 + avg(b1.processions.size) from Chapter c1 join c1.area a1 join a1.brotherhoods b1)")
	Collection<Chapter> query17();
	
	@Query("select count(p)*1.0 / (select count(p1)*1.0 from Procession p1 where p1.draftMode = false) from Procession p where p.draftMode = true")
	Double query18();
	
	@Query("select count(p)*1.0 / (select count(p1)*1.0 from Procession p1), p.status from Procession p where p.draftMode = false group by p.status")
	Collection<Object> query19();
	
	
	// ACME PARADE Queries level A
	@Query("select count(s)*1.0 / (select count(s1)*1.0 from Sponsorship s1) from Sponsorship s where s.active = true")
	Double query20();
	
	@Query("select avg(s.sponsorships.size), min(s.sponsorships.size), max(s.sponsorships.size), stddev(s.sponsorships.size) from Sponsor s join s.sponsorships ss where ss.active = true")
	Object[] query21();
	
	@Query("select s.name, s.sponsorships.size from Sponsor s join s.sponsorships ss where ss.active = true group by ss order by sum(s.sponsorships.size) desc")
	Collection<Object> query22();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
