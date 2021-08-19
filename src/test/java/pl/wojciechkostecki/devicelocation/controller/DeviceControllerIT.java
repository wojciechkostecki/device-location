package pl.wojciechkostecki.devicelocation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
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
import pl.wojciechkostecki.devicelocation.model.dto.DeviceDTO;
import pl.wojciechkostecki.devicelocation.repository.DeviceRepository;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test", roles = {"ADMIN", "USER"})
@Transactional
class DeviceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void createDeviceTest() throws Exception {
        //given
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setProducer("Huawei");
        deviceDTO.setModel("P30");

        int dbSize = deviceRepository.findAll().size();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/devices")
                        .content(objectMapper.writeValueAsString(deviceDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        //then
        int dbSizeAfter = deviceRepository.findAll().size();
        Assertions.assertThat(dbSizeAfter).isEqualTo(dbSize+1);

        Device savedDevice = deviceRepository.getById(objectMapper.readValue
                (mvcResult.getResponse().getContentAsString(),Device.class).getId());

        Assertions.assertThat(savedDevice).isNotNull();
        Assertions.assertThat(savedDevice.getProducer()).isEqualTo(deviceDTO.getProducer());
        Assertions.assertThat(savedDevice.getModel()).isEqualTo(deviceDTO.getModel());
    }
}