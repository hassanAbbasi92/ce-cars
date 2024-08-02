package com.ce.cars.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ce.cars.dto.CarDto;
import com.ce.cars.entity.Car;
import com.ce.cars.repository.CarRepo;

@Service
public class CarService {

	@Autowired
	private CarRepo carRepository;

	public List<Car> findAll() {
		return carRepository.findAll();
	}

	public List<CarDto> searchCars(Double length, Double weight, Double velocity, String color) {
		String colorValue = (color == null || color.isEmpty()) ? null : color;
		List<Car> cars = carRepository.findAll().stream().filter(car -> (length == null || car.getLength() == length))
				.filter(car -> (weight == null || car.getWeight() == weight))
				.filter(car -> (velocity == null || car.getVelocity() == velocity))
				.filter(car -> (colorValue == null || car.getColor().equalsIgnoreCase(color))).toList();

		return cars.stream().map(car -> {
			CarDto dto = new CarDto();
			dto.setLength(car.getLength());
			dto.setWeight(car.getWeight());
			dto.setVelocity(car.getVelocity());
			dto.setColor(car.getColor());
			return dto;
		}).collect(Collectors.toList());
	}
}
