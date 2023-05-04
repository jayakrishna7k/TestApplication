package Inmar.Test.app.controller;

import Inmar.Test.app.dto.response.CategoriesResponse;
import Inmar.Test.app.dto.response.DepartmentsResponse;
import Inmar.Test.app.dto.response.LocationsResponse;
import Inmar.Test.app.service.LocationService;
import Inmar.Test.app.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = "If-Match")

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private MetaDataService metaDataService;

    @GetMapping
    public ResponseEntity<?> getLocationDetails() {
        LocationsResponse response = locationService.getAvailableLocations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{location_id}/department")
    public ResponseEntity<?> getDepartments(@PathVariable(value="location_id") long locationId) {
        DepartmentsResponse response = metaDataService.getDepartmentFromMetadata(locationId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{location_id}/department/{department_id}/category}")
    public ResponseEntity<?> getCategories(@PathVariable(value="location_id") long locationId,@PathVariable(value="department_id") long departmentId) {
        CategoriesResponse response = metaDataService.getCategoriesFromMetadata(locationId,departmentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
