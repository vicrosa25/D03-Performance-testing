package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {
	
	@Query("select p from Procession p where p.brotherhood.id = ?1 and p.draftMode = 'false'")
	Collection<Procession> findByBrotherhoodNotDraft(int brotherhoodId);
	
	@Query("select p from Procession p where p.brotherhood.id = ?1 and p.draftMode = 'false' and p.status = 'APPROVED'")
	Collection<Procession> findByBrotherhoodNotDraftAndApproved(int brotherhoodId);

	@Query("select p from Procession p where p.brotherhood.id = ?1 order by p.status")
	Collection<Procession> getProcessionsSortedByStatus(int brotherhoodId);

	@Query("select p from Procession p where p.status = 'APPROVED'")
	Collection<Procession> findAllAccepted();

}
