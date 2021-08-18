package pl.wojciechkostecki.devicelocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechkostecki.devicelocation.model.Geolocation;

@Repository
public interface GeolocationRepository extends JpaRepository<Geolocation,Long> {
}
