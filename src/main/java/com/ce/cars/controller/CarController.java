package com.ce.cars.controller;

import java.io.StringWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.ce.cars.dto.CarDto;
import com.ce.cars.service.CarService;
import com.ce.cars.util.CarListWrapper;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class CarController {

	@Autowired
    private CarService carService;
	
	@GetMapping("/cars")
    public ResponseEntity<List<CarDto>> searchCars(
            @RequestParam(required = false) Double length,
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false) Double velocity,
            @RequestParam(required = false) String color) {
		
		log.info("search cars request recieved.");
        List<CarDto> cars = carService.searchCars(length, weight, velocity, color);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
	
	@GetMapping("/cars/xml")
    public ResponseEntity<String> downloadCarsAsXml(
            @RequestParam(required = false) Double length,
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false) Double velocity,
            @RequestParam(required = false) String color) {
        try {
        	
        	log.info("download cars file request recieved.");
        	
            List<CarDto> cars = carService.searchCars(length, weight, velocity, color);
            JAXBContext jaxbContext = JAXBContext.newInstance(CarListWrapper.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            CarListWrapper wrapper = new CarListWrapper(cars);
            StringWriter writer = new StringWriter();
            marshaller.marshal(wrapper, writer);
            String xmlOutput = writer.toString();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cars.xml");
            return new ResponseEntity<>(xmlOutput, headers, HttpStatus.OK);
        } catch (Exception e) {
        	log.error(e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
