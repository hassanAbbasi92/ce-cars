package com.ce.cars.util;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ce.cars.dto.CarDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarListWrapper {
	
	@XmlElement(name = "car")
	private List<CarDto> cars;

    public CarListWrapper(List<CarDto> cars) {
        this.cars = cars;
    }
}
