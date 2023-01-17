package testmate.rickandmortiapp.dto.external;

import lombok.Data;

@Data
public class ApiCharacterResponseDto {
    private Long id;
    private String name;
    private String status;
    private String gender;
}
