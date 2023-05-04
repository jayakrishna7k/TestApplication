package Inmar.Test.app.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CategoriesResponse {
   private List<CategoryResponse> categories;
}
