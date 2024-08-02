package com.ce.cars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ce.cars.entity.Car;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {

}
