package pl.wojciechkostecki.devicelocation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.wojciechkostecki.devicelocation.model.Geolocation;
import pl.wojciechkostecki.devicelocation.model.dto.GeolocationDTO;

@Mapper(componentModel = "spring")
public interface GeolocationMapper extends EntityMapper<GeolocationDTO, Geolocation>{

    @Mapping(target = "deviceId",source = "device.id")
    GeolocationDTO toDto(Geolocation geolocation);
}
