package Inmar.Test.app.service;

import Inmar.Test.app.dto.response.LocationResponse;
import Inmar.Test.app.dto.response.LocationsResponse;
import Inmar.Test.app.jpa.model.Location;
import Inmar.Test.app.mapper.LocationDetailsMapper;
import Inmar.Test.app.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationServiceTest {
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private LocationDetailsMapper locationDetailsMapper;
    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLocationDetailsResponse() {
        LocationsResponse response = new LocationsResponse();
        response.setLocations(populateLocationDetails());
        Mockito.when(locationDetailsMapper.mapListOfLocationDetails(Mockito.anyList())).thenReturn(response);
        Mockito.when(locationRepository.findAll()).thenReturn(populateLocations());
        LocationsResponse locs = locationService.getAvailableLocations();
        assertEquals(2, locs.getLocations().size());

    }

    private List<LocationResponse> populateLocationDetails() {
        List<LocationResponse> locations = new ArrayList<>();
        LocationResponse perimeter = new LocationResponse(1, "Perimeter", "Test Description forPerimeter");
        LocationResponse center = new LocationResponse(2, "Center", "Test Description forCenter");
        locations.addAll(Arrays.asList(perimeter, center));
        return locations;
    }

    private List<Location> populateLocations() {
        List<Location> locations = new ArrayList<>();
        Location perimeter = new Location();
        perimeter.setDescription("Test Description forPerimeter");
        perimeter.setLocation("Perimeter");
        perimeter.setLocationId(1);
        Location center = new Location();
        center.setDescription("Test Description forCenter");
        center.setLocation("Center");
        center.setLocationId(2);
        locations.addAll(Arrays.asList(perimeter, center));
        return locations;
    }

}
