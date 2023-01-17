package testmate.rickandmortiapp.dto.mapper;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import testmate.rickandmortiapp.dto.CharacterResponseDto;
import testmate.rickandmortiapp.dto.external.ApiCharacterResponseDto;
import testmate.rickandmortiapp.model.Gender;
import testmate.rickandmortiapp.model.MovieCharacter;
import testmate.rickandmortiapp.model.Status;

@Log4j2
@Component
public class MovieCharacterMapper {

    public MovieCharacter parseApiCharacterResponseDto(ApiCharacterResponseDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setName(dto.getName());
        movieCharacter.setExternalId(dto.getId());
        movieCharacter.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        movieCharacter.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        return movieCharacter;
    }

    public CharacterResponseDto mapToDto(MovieCharacter movieCharacter) {
        CharacterResponseDto responseDto = new CharacterResponseDto();
        responseDto.setId(movieCharacter.getId());
        responseDto.setExternalId(movieCharacter.getExternalId());
        responseDto.setName(movieCharacter.getName());
        responseDto.setStatus(movieCharacter.getStatus().getValue());
        responseDto.setGender(movieCharacter.getGender().getValue());
        return responseDto;
    }
}
