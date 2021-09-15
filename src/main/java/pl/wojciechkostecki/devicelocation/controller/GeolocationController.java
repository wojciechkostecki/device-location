package pl.wojciechkostecki.devicelocation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wojciechkostecki.devicelocation.model.dto.GeolocationDTO;
import pl.wojciechkostecki.devicelocation.service.GeolocationService;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/geolocations")
public class GeolocationController {
    private final GeolocationService geolocationService;

    public GeolocationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<GeolocationDTO> saveGeolocation(@Valid @RequestBody GeolocationDTO geolocationDTO) {
        GeolocationDTO savedGeolocation = geolocationService.save(geolocationDTO);
        return new ResponseEntity<>(savedGeolocation, HttpStatus.CREATED);
    }
}
