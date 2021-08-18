package pl.wojciechkostecki.devicelocation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.wojciechkostecki.devicelocation.model.Geolocation;
import pl.wojciechkostecki.devicelocation.model.dto.GeolocationDTO;

@Mapper(componentModel = "spring")
public interface GeolocationMapper extends EntityMapper<GeolocationDTO, Geolocation>{

    @Mapping(target = "device.id",source = "deviceId")
    Geolocation toEntity(GeolocationDTO geolocationDTO);
}
