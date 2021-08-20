package pl.wojciechkostecki.devicelocation.service;

import org.springframework.stereotype.Service;
import pl.wojciechkostecki.devicelocation.mapper.DeviceMapper;
import pl.wojciechkostecki.devicelocation.model.Device;
import pl.wojciechkostecki.devicelocation.model.dto.DeviceDTO;
import pl.wojciechkostecki.devicelocation.repository.DeviceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public DeviceService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    public Device save(DeviceDTO deviceDTO) {
        return deviceRepository.save(deviceMapper.toEntity(deviceDTO));
    }

    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    public Optional<Device> findById(Long id) {
        return deviceRepository.findById(id);
    }

    public Device updateDevice(Long id, DeviceDTO deviceDTO) {
        Device modifiedDevice = deviceRepository.getById(id);
        modifiedDevice.setProducer(deviceDTO.getProducer());
        modifiedDevice.setModel(deviceDTO.getModel());
        return deviceRepository.save(modifiedDevice);
    }

    public void delete(Long id) {
        deviceRepository.deleteById(id);
    }
}
