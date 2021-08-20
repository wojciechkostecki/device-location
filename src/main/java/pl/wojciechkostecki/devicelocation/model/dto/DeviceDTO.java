package pl.wojciechkostecki.devicelocation.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeviceDTO {
    private Long id;

    @NotBlank(message = "Producer is mandatory")
    private String producer;

    @NotBlank(message = "Model is mandatory")
    private String model;

    private String geolocation;
}
