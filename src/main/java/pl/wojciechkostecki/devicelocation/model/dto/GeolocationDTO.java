package pl.wojciechkostecki.devicelocation.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GeolocationDTO {
    @NotNull(message = "Device ID is mandatory")
    private Long deviceId;

    @NotNull(message = "Latitude is mandatory")
    private double latitude;

    @NotNull(message = "Longitude is mandatory")
    private double longitude;
}
