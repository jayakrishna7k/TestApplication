package Inmar.Test.app.mapper;

import Inmar.Test.app.dto.response.LocationResponse;
import Inmar.Test.app.dto.response.LocationsResponse;
import Inmar.Test.app.jpa.model.Location;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationDetailsMapper {

    public LocationsResponse mapListOfLocationDetails(List<Location> locations) {
        LocationsResponse locationsResponse=new LocationsResponse();
        if(!locations.isEmpty()) {
            List<LocationResponse> locationList=locations.stream().filter(location -> location != null && !StringUtils.isEmpty(location.getLocation())).map(location -> new LocationResponse(location.getLocationId(),location.getLocation(),location.getDescription())).collect(Collectors.toList());
            locationsResponse.setLocations(locationList);
        }
        else{
            locationsResponse.setLocations(new ArrayList<>());
        }
        return locationsResponse;
    }
}
