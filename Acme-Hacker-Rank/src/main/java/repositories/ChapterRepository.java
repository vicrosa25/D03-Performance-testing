
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chapter;
import domain.Procession;



@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

	@Query("select c from Chapter c where c.id = ?1")
	Chapter findById(int id);

	@Query("select c from Chapter c where c.userAccount.username = ?1")
	Chapter findByUserName(String username);

	@Query("select c from Chapter c where c.userAccount.id = ?1")
	Chapter findByUserAccountId(int id);
	
	@Query("select p from Brotherhood b join b.processions p where b.area.chapter.id = ?1 and p.draftMode = false order by p.status")
	Collection<Procession> findProccesionsByChapter(int chapterId);
}
