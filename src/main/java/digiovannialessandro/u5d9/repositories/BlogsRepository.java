package digiovannialessandro.u5d9.repositories;


import digiovannialessandro.u5d9.entities.Author;
import digiovannialessandro.u5d9.entities.Blogpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BlogsRepository extends JpaRepository<Blogpost, Integer> {
    List<Blogpost> findByAuthor(Author author);
}
