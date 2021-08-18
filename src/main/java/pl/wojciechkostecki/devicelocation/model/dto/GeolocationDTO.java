package pl.wojciechkostecki.devicelocation.model.dto;

import lombok.Data;

@Data
public class GeolocationDTO {
    private Long deviceId;

    private double latitude;

    private double longitude;
}
