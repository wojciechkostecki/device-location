package pl.wojciechkostecki.devicelocation.model.dto;

import lombok.Data;
import pl.wojciechkostecki.devicelocation.model.Geolocation;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class DeviceDTO {
    private Long id;

    @NotBlank(message = "Producer is mandatory")
    private String producer;

    @NotBlank(message = "Model is mandatory")
    private String model;

    private List<Geolocation> geolocations = new ArrayList<>();
}
