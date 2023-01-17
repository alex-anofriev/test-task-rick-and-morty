package testmate.rickandmortiapp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import testmate.rickandmortiapp.dto.CharacterResponseDto;
import testmate.rickandmortiapp.dto.mapper.MovieCharacterMapper;
import testmate.rickandmortiapp.model.MovieCharacter;
import testmate.rickandmortiapp.service.MovieCharacterService;

@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper mapper;

    public MovieCharacterController(MovieCharacterService movieCharacterService,
                                    MovieCharacterMapper mapper) {
        this.movieCharacterService = movieCharacterService;
        this.mapper = mapper;
    }

    @GetMapping("/random")
    public CharacterResponseDto getRandom() {
        MovieCharacter randomMovieCharacter = movieCharacterService.getRandomMovieCharacter();
        return mapper.mapToDto(randomMovieCharacter);
    }

    @GetMapping("find-by-name")
    public List<CharacterResponseDto> findByNamePart(@RequestParam("name") String namePart) {
        return movieCharacterService.findAllByNameContains(namePart).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
