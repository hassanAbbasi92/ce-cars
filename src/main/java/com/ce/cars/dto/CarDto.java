package com.ce.cars.dto;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "car")
public class CarDto {
    private double length;
    private double weight;
    private double velocity;
    private String color;
}
