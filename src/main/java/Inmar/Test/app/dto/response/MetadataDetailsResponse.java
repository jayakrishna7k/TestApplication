package Inmar.Test.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetadataDetailsResponse {
    private List<MetadataResponse> metadataDetails;

}
