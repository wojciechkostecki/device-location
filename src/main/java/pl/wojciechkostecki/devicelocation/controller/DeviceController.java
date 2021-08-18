package pl.wojciechkostecki.devicelocation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wojciechkostecki.devicelocation.model.Device;
import pl.wojciechkostecki.devicelocation.model.dto.DeviceDTO;
import pl.wojciechkostecki.devicelocation.service.DeviceService;

import java.util.List;

@RestController
@RequestMapping("api/devices")
public class DeviceController {
    private final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody DeviceDTO deviceDTO) {
        logger.debug("REST request to create Device: {}", deviceDTO);
        Device savedDevice = deviceService.save(deviceDTO);
        return new ResponseEntity<>(savedDevice, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        logger.debug("REST request to get all Devices");
        return ResponseEntity.ok(deviceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable Long id) {
        logger.debug("REST request to get Device: {}", id);
        return ResponseEntity.ok(deviceService.findById(id).get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody DeviceDTO deviceDTO) {
        logger.debug("REST request to update Device: {} with id {}", deviceDTO, id);
        return ResponseEntity.ok(deviceService.updateDevice(id, deviceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        logger.debug("REST request to delete Device: {}", id);
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
