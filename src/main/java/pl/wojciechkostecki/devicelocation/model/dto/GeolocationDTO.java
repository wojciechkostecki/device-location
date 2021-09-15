package pl.wojciechkostecki.devicelocation.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GeolocationDTO {
    private Long id;

    @NotNull(message = "Device ID is mandatory")
    private Long deviceId;

    @NotNull(message = "Latitude is mandatory")
    private Double latitude;

    @NotNull(message = "Longitude is mandatory")
    private Double longitude;
}
