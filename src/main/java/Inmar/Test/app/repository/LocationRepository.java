package Inmar.Test.app.repository;

import Inmar.Test.app.jpa.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByLocation(String locationName);
}
