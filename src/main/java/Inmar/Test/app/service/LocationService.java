package Inmar.Test.app.service;

import Inmar.Test.app.dto.response.LocationsResponse;
import Inmar.Test.app.jpa.model.Location;
import Inmar.Test.app.mapper.LocationDetailsMapper;
import Inmar.Test.app.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private LocationDetailsMapper locationDetailsMapper;
    @Autowired
    private LocationRepository locationRepository;

    public LocationsResponse getAvailableLocations() {
        return locationDetailsMapper.mapListOfLocationDetails(locationRepository.findAll());
    }

    public Location getLocationById(long id) {
        return locationRepository.findById(id).orElse(null);
    }

    public void saveALocation(String locationName) {
        Location location = new Location();
        Location existedLocation = locationRepository.findByLocation(locationName);
        if (existedLocation == null) {
            location.setLocation(locationName);
            location.setDescription("Test Description for" + locationName);
            locationRepository.save(location);
        }
    }

    public void deleteAllLocations() {
        locationRepository.deleteAll();
    }
}
