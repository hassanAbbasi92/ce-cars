package com.ce.cars;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ce.cars.controller.CarController;
import com.ce.cars.dto.CarDto;
import com.ce.cars.entity.Car;
import com.ce.cars.service.CarService;

@WebMvcTest(CarController.class)
public class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    private List<Car> carList;

    @BeforeEach
    public void setUp() {
        Car redCar = new Car();
        redCar.setLength(4.0);
        redCar.setWeight(1500.0);
        redCar.setVelocity(220.0);
        redCar.setColor("Red");

        Car blueCar = new Car();
        blueCar.setLength(4.5);
        blueCar.setWeight(1600.0);
        blueCar.setVelocity(240.0);
        blueCar.setColor("Blue");

        carList = Arrays.asList(redCar, blueCar);
    }

    @Test
    public void testGetAllCars() throws Exception {
        List<CarDto> carDtos = carList.stream()
                .map(car -> {
                    CarDto dto = new CarDto();
                    dto.setLength(car.getLength());
                    dto.setWeight(car.getWeight());
                    dto.setVelocity(car.getVelocity());
                    dto.setColor(car.getColor());
                    return dto;
                })
                .collect(Collectors.toList());

        Mockito.when(carService.searchCars(null, null, null, null)).thenReturn(carDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\"length\":4.0,\"weight\":1500.0,\"velocity\":220.0,\"color\":\"Red\"}," +
                        "{\"length\":4.5,\"weight\":1600.0,\"velocity\":240.0,\"color\":\"Blue\"}]"));
    }

    @Test
    public void testDownloadAllCarsAsXml() throws Exception {
        List<CarDto> carDtos = carList.stream()
                .map(car -> {
                    CarDto dto = new CarDto();
                    dto.setLength(car.getLength());
                    dto.setWeight(car.getWeight());
                    dto.setVelocity(car.getVelocity());
                    dto.setColor(car.getColor());
                    return dto;
                })
                .collect(Collectors.toList());

        Mockito.when(carService.searchCars(null, null, null, null)).thenReturn(carDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/xml"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().xml(
                        "<cars>" +
                        "<car><length>4.0</length><weight>1500.0</weight><velocity>220.0</velocity><color>Red</color></car>" +
                        "<car><length>4.5</length><weight>1600.0</weight><velocity>240.0</velocity><color>Blue</color></car>" +
                        "</cars>"));
    }

    @Test
    public void testSearchCarsWithLengthFilter() throws Exception {
        List<CarDto> filteredCarDtos = carList.stream()
                .filter(car -> car.getLength() == 4.0)
                .map(car -> {
                    CarDto dto = new CarDto();
                    dto.setLength(car.getLength());
                    dto.setWeight(car.getWeight());
                    dto.setVelocity(car.getVelocity());
                    dto.setColor(car.getColor());
                    return dto;
                })
                .collect(Collectors.toList());

        Mockito.when(carService.searchCars(4.0, null, null, null)).thenReturn(filteredCarDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/cars")
                .param("length", "4.0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\"length\":4.0,\"weight\":1500.0,\"velocity\":220.0,\"color\":\"Red\"}]"));
    }

    @Test
    public void testDownloadFilteredCarsAsXml() throws Exception {
        List<CarDto> filteredCarDtos = carList.stream()
                .filter(car -> car.getWeight() == 1500.0)
                .map(car -> {
                    CarDto dto = new CarDto();
                    dto.setLength(car.getLength());
                    dto.setWeight(car.getWeight());
                    dto.setVelocity(car.getVelocity());
                    dto.setColor(car.getColor());
                    return dto;
                })
                .collect(Collectors.toList());

        Mockito.when(carService.searchCars(null, 1500.0, null, null)).thenReturn(filteredCarDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/xml")
                .param("weight", "1500.0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().xml(
                        "<cars>" +
                        "<car><length>4.0</length><weight>1500.0</weight><velocity>220.0</velocity><color>Red</color></car>" +
                        "</cars>"));
    }

    @Test
    public void testEmptySearchResults() throws Exception {
        Mockito.when(carService.searchCars(10.0, null, null, null)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/cars")
                .param("length", "10.0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    public void testEmptyXmlDownload() throws Exception {
        Mockito.when(carService.searchCars(10.0, null, null, null)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/xml")
                .param("length", "10.0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().xml("<cars/>"));
    }
}
