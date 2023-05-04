package Inmar.Test.app.controller;

import Inmar.Test.app.dto.response.MetadataDetailsResponse;
import Inmar.Test.app.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = "If-Match")

@RestController
@RequestMapping("/api/v1/metadata")
public class MetaDataController {

    @Autowired
    private MetaDataService metaDataService;

    @GetMapping("/load")
    public ResponseEntity<?> loadMetaData(){

        return new ResponseEntity<>(metaDataService.savedMetaData(), HttpStatus.OK);
    }

    @GetMapping("/{location_id}/department/{department_id}/category/{category_id}/subcategory/{subcategory_id}")
    public ResponseEntity<?> getMetaData(@PathVariable(value="location_id") long locationId, @PathVariable(value="department_id") long departmentId,@PathVariable(value="category_id") long categoryId,@PathVariable(value="subcategory_id") long subCategoryId) {
        MetadataDetailsResponse response = metaDataService.getMetaDataFromParameters(locationId,departmentId,categoryId,subCategoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
