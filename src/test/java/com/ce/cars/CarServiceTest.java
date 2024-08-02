package com.ce.cars;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ce.cars.dto.CarDto;
import com.ce.cars.entity.Car;
import com.ce.cars.repository.CarRepo;
import com.ce.cars.service.CarService;

public class CarServiceTest {
	@Mock
    private CarRepo carRepository;

    @InjectMocks
    private CarService carService;

    private Car redCar;
    private Car greenCar;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        redCar = new Car();
        redCar.setLength(4.0);
        redCar.setWeight(1000.0);
        redCar.setVelocity(200.0);
        redCar.setColor("Red");

        greenCar = new Car();
        greenCar.setLength(4.5);
        greenCar.setWeight(1200.0);
        greenCar.setVelocity(220.0);
        greenCar.setColor("Green");
    }

    @Test
    public void testFindAll() {
        when(carRepository.findAll()).thenReturn(List.of(redCar, greenCar));
        List<Car> cars = carService.findAll();
        assertEquals(2, cars.size());
    }

    @Test
    public void testSearchCars() {
        when(carRepository.findAll()).thenReturn(List.of(redCar, greenCar));
        List<CarDto> cars = carService.searchCars(4.0, 1000.0, 200.0, "Red");
        assertEquals(1, cars.size());
        assertEquals("Red", cars.get(0).getColor());
    }
}
