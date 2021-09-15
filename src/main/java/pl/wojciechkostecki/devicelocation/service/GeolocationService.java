package pl.wojciechkostecki.devicelocation.service;

import org.springframework.stereotype.Service;
import pl.wojciechkostecki.devicelocation.mapper.DeviceMapper;
import pl.wojciechkostecki.devicelocation.mapper.GeolocationMapper;
import pl.wojciechkostecki.devicelocation.model.Device;
import pl.wojciechkostecki.devicelocation.model.Geolocation;
import pl.wojciechkostecki.devicelocation.model.dto.GeolocationDTO;
import pl.wojciechkostecki.devicelocation.repository.GeolocationRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class GeolocationService {
    private final GeolocationRepository geolocationRepository;
    private final DeviceService deviceService;
    private final GeolocationMapper geolocationMapper;
    private final DeviceMapper deviceMapper;

    public GeolocationService(GeolocationRepository geolocationRepository, DeviceService deviceService, GeolocationMapper geolocationMapper, DeviceMapper deviceMapper) {
        this.geolocationRepository = geolocationRepository;
        this.deviceService = deviceService;
        this.geolocationMapper = geolocationMapper;
        this.deviceMapper = deviceMapper;
    }

    public GeolocationDTO save(GeolocationDTO geolocationDTO) {
        Geolocation geolocation = geolocationMapper.toEntity(geolocationDTO);
        Device device = deviceMapper.toEntity(deviceService.findById(geolocationDTO.getDeviceId())
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find a Device with passed id")));
        geolocation.setDevice(device);
        device.getGeolocations().add(geolocation);
        return geolocationMapper.toDto(geolocationRepository.save(geolocation));
    }
}
