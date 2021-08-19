package pl.wojciechkostecki.devicelocation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.wojciechkostecki.devicelocation.model.Device;
import pl.wojciechkostecki.devicelocation.model.Geolocation;
import pl.wojciechkostecki.devicelocation.model.dto.GeolocationDTO;
import pl.wojciechkostecki.devicelocation.repository.DeviceRepository;
import pl.wojciechkostecki.devicelocation.repository.GeolocationRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test", roles = {"ADMIN", "USER"})
@Transactional
class GeolocationControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private GeolocationRepository geolocationRepository;

    @Test
    void saveGeolocationTest() throws Exception{
        //given
        Device device = new Device();
        device.setProducer("Apple");
        device.setModel("Iphone X");
        deviceRepository.save(device);

        GeolocationDTO geolocationDTO = new GeolocationDTO();
        geolocationDTO.setLatitude(44.23425);
        geolocationDTO.setLongitude(23.24285);
        geolocationDTO.setDeviceId(device.getId());

        int dbSize = geolocationRepository.findAll().size();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/geolocations")
                        .content(objectMapper.writeValueAsString(geolocationDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        //then
        int dbSizeAfter = geolocationRepository.findAll().size();

        assertThat(dbSizeAfter).isEqualTo(dbSize + 1);

        Geolocation savedGeolocation = geolocationRepository.getById
                (objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Geolocation.class).getId());

        assertThat(savedGeolocation).isNotNull();
        assertThat(savedGeolocation.getLatitude()).isEqualTo(geolocationDTO.getLatitude());
        assertThat(savedGeolocation.getLongitude()).isEqualTo(geolocationDTO.getLongitude());
        assertThat(savedGeolocation.getDevice()).isEqualTo(device);
    }
    
}