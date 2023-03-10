package testmate.rickandmortiapp.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testmate.rickandmortiapp.model.MovieCharacter;

@Repository
public interface MovieCharacterRepository extends
        JpaRepository<MovieCharacter, Long> {

    List<MovieCharacter> findAllByExternalIdIn(Set<Long> externalIds);

    List<MovieCharacter> findAllByNameContains(String letter);
}
