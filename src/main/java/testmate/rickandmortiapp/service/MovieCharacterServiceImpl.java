package testmate.rickandmortiapp.service;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import testmate.rickandmortiapp.dto.external.ApiCharacterResponseDto;
import testmate.rickandmortiapp.dto.external.ApiResponseDto;
import testmate.rickandmortiapp.dto.mapper.MovieCharacterMapper;
import testmate.rickandmortiapp.model.MovieCharacter;
import testmate.rickandmortiapp.repository.MovieCharacterRepository;

@Log4j2
@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final HttpClient httpClient;
    private final MovieCharacterRepository repository;
    private final MovieCharacterMapper characterMapper;

    public MovieCharacterServiceImpl(HttpClient httpClient,
                                     MovieCharacterRepository repository,
                                     MovieCharacterMapper characterMapper) {
        this.httpClient = httpClient;
        this.repository = repository;
        this.characterMapper = characterMapper;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * ?")
    @Override
    public void syncExternalCharacters() {
        ApiResponseDto apiResponseDto = httpClient.get("https://rickandmortyapi.com/api/character",
                ApiResponseDto.class);
        saveDtosToDb(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtosToDb(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter getRandomMovieCharacter() {
        long count = repository.count();
        return repository.findById(new Random().nextLong(count)).orElseThrow();
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String letter) {
        return repository.findAllByNameContains(letter);
    }

    private void saveDtosToDb(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterResponseDto> externalDtos = Arrays.stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterResponseDto::getId, Function.identity()));
        Set<Long> externalIds = externalDtos.keySet();

        List<MovieCharacter> existingCharacters
                = repository.findAllByExternalIdIn(externalIds);

        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));
        Set<Long> existingIds = existingCharactersWithIds.keySet();

        externalIds.removeAll(existingIds);
        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> characterMapper.parseApiCharacterResponseDto(externalDtos.get(i)))
                .collect(Collectors.toList());
        repository.saveAll(charactersToSave);
    }
}
