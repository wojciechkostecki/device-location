package pl.wojciechkostecki.devicelocation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(dbSizeAfter).isEqualTo(dbSize + 1);

        Device savedDevice = deviceRepository.getById(objectMapper.readValue
                (mvcResult.getResponse().getContentAsString(), Device.class).getId());

        assertThat(savedDevice).isNotNull();
        assertThat(savedDevice.getProducer()).isEqualTo(deviceDTO.getProducer());
        assertThat(savedDevice.getModel()).isEqualTo(deviceDTO.getModel());
    }

    @Test
    void getAllDevicesTest() throws Exception {
        //given
        Device device = new Device();
        device.setProducer("Apple");
        device.setModel("Iphone X");
        deviceRepository.save(device);
        Device device2 = new Device();
        device2.setProducer("Lenovo");
        device2.setModel("Legion 5");
        deviceRepository.save(device2);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/devices"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //then
        Device[] devices = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Device[].class);
        assertThat(devices).isNotNull();
        assertThat(devices).hasSize(2);
        assertThat(devices[0].getProducer()).isEqualTo(device.getProducer());
        assertThat(devices[0].getModel()).isEqualTo(device.getModel());
        assertThat(devices[1].getProducer()).isEqualTo(device2.getProducer());
        assertThat(devices[1].getModel()).isEqualTo(device2.getModel());
    }

    @Test
    void getDeviceTest() throws Exception {
        //given
        Device newDevice = new Device();
        newDevice.setProducer("Apple");
        newDevice.setModel("Iphone 8");
        deviceRepository.save(newDevice);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/devices/" + newDevice.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        //then
        Device device = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Device.class);
        assertThat(device).isNotNull();
        assertThat(device.getId()).isEqualTo(newDevice.getId());
        assertThat(device.getProducer()).isEqualTo(newDevice.getProducer());
        assertThat(device.getModel()).isEqualTo(newDevice.getModel());
        assertThat(device.getProducer()).isEqualTo("Apple");
        assertThat(device.getModel()).isEqualTo("Iphone 8");
    }

    @Test
    void updateDeviceTest() throws Exception {
        //given
        Device device = new Device();
        device.setProducer("Apple");
        device.setModel("Iphone X");
        deviceRepository.save(device);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/devices/" + device.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        Device originalDevice = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Device.class);
        assertThat(originalDevice.getId()).isEqualTo(device.getId());
        assertThat(originalDevice.getProducer()).isEqualTo(device.getProducer());
        assertThat(originalDevice.getModel()).isEqualTo(device.getModel());

        originalDevice.setProducer("Nokia");
        originalDevice.setModel("3310i");

        //when
        MvcResult mvcResultAfterUpdate =mockMvc.perform(MockMvcRequestBuilders.put("/api/devices/" + device.getId())
                        .content(objectMapper.writeValueAsString(originalDevice))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        //then
        Device changedDevice = objectMapper.readValue(mvcResultAfterUpdate.getResponse().getContentAsString(),Device.class);
        assertThat(changedDevice.getId()).isEqualTo(originalDevice.getId());
        assertThat(changedDevice.getProducer()).isEqualTo("Nokia");
        assertThat(changedDevice.getModel()).isEqualTo("3310i");
    }

    @Test
    void deleteDeviceTest() throws Exception {
        Device device = new Device();
        device.setProducer("Apple");
        device.setModel("Iphone X");
        deviceRepository.save(device);

        int dbSize = deviceRepository.findAll().size();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/devices/" + device.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        int dbSizeAfter = deviceRepository.findAll().size();

        assertThat(dbSizeAfter).isEqualTo(dbSize-1);
    }

}