package pl.wojciechkostecki.devicelocation.model.dto;

import lombok.Data;

@Data
public class DeviceDTO {
    private Long id;

    private String producer;

    private String model;

    private String geolocation;
}
