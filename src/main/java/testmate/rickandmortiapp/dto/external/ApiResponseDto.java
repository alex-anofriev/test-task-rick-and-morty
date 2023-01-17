package testmate.rickandmortiapp.dto.external;

import lombok.Data;

@Data
public class ApiResponseDto {
    private ApiInfoDto info;
    private ApiCharacterResponseDto[] results;
}
