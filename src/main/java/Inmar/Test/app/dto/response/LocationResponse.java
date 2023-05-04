package Inmar.Test.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationResponse {
    private long locationId;
    private String location;
    private String description;
}
