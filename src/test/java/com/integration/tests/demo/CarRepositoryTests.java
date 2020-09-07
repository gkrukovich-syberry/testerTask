package com.integration.tests.demo;

import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setup() {
        Car car1 = new Car();
        car1.setName("BMW");
        entityManager.persist(car1);
    }

    @Test
    public void FindCarsByName_NameIsProvided_ListWithOneCarReturned() throws Exception {
        List<Car> expectedCars = carRepository.findCarsByName("BMW");
        assertTrue(expectedCars
                .stream()
                .allMatch(car -> car.getName().equals("BMW")));
    }

    @Test
    public void FindCarsByName_NameIsProvided_ListWithNoCarReturned() throws Exception {
        List<Car> expectedCars = carRepository.findCarsByName("Nissan");
        assertEquals(0, expectedCars.size());
    }
}