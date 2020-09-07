package com.integration.tests.demo;

import com.integration.tests.demo.dtos.CarDTO;
import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.repositories.CarRepository;
import com.integration.tests.demo.services.CarServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTests {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarServiceImpl carService;


    @BeforeEach
    public void setup() {
        Car car1 = new Car();
        car1.setName("BMW");
        car1.setId(Long.valueOf("1"));

        Car car2 = new Car();
        car2.setName("Nissan");
        car1.setId(Long.valueOf("2"));

        carRepository.save(car1);
        carRepository.save(car2);
    }

    @Test
    public void Search_NoArgsProvided_ListWithTwoCarsReturned() throws Exception {
        List<Car> expectedCars = carService.search(null, null);
        assertEquals(2, expectedCars.size());
    }

    @Test
    public void Search_NameProvided_ListWithOneCarReturned() throws Exception {
        List<Car> expectedCars = carService.search("BMW", null);
        assertEquals(1, expectedCars.size());
    }

    @Test
    public void Search_WrongNameProvided_EmptyListReturned() throws Exception {
        List<Car> expectedCars = carService.search("Ford", null);
        assertEquals(0, expectedCars.size());
    }

    @Test
    public void Search_WrongIdProvided_EmptyListReturned() throws Exception {
        List<Car> expectedCars = carService.search(null, Long.valueOf("3"));
        assertEquals(0, expectedCars.size());
    }

    @Test
    public void AddCar_CarProvided_ListWithThreeCarsReturned() throws Exception {
        CarDTO newCar = new CarDTO();
        newCar.setName("Ford");
        carService.addCar(newCar);

        assertEquals(3, carRepository.findAll().size());
    }

    @AfterEach
    public void clean() {
        carRepository.deleteAll();
    }

}