package pl.wojciechkostecki.devicelocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechkostecki.devicelocation.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
