package testmate.rickandmortiapp.service;

import java.util.List;
import testmate.rickandmortiapp.model.MovieCharacter;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter getRandomMovieCharacter();

    List<MovieCharacter> findAllByNameContains(String letter);
}
