package com.integration.tests.demo;

import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.services.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceImpl carService;

    private List<Car> twoCars;
    private List<Car> oneCarBMW;
    private List<Car> oneCarNissan;

    @BeforeEach
    void setup() {
        Car car = new Car();
        car.setName("BMW");
        car.setId(1L);

        Car car2 = new Car();
        car2.setName("Nissan");
        car2.setId(2L);

        this.twoCars = new ArrayList<>();
        this.oneCarBMW = new ArrayList<>();
        this.oneCarNissan = new ArrayList<>();
        this.twoCars.add(car);
        this.twoCars.add(car2);
        this.oneCarBMW.add(car);
        this.oneCarNissan.add(car2);
    }

    @Test
    public void Search_GetRequestWithNoArgsProvided_ListWithTwoCarsReturned() throws Exception {
        given(carService.search(null, null)).willReturn(twoCars);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/search"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name").value("BMW"))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].name").value("Nissan"))
                .andExpect(jsonPath("$[1].id").value("2"));
    }

    @Test
    public void Search_GetRequestWithNameProvided_ListWithOneCarReturned() throws Exception {
        given(carService.search("BMW", null)).willReturn(oneCarBMW);
        this.mockMvc.perform( MockMvcRequestBuilders
                .get("/search")
                .param("name", "BMW"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].name").value("BMW"))
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    public void Search_GetRequestWithIdProvided_ListWithOneCarReturned() throws Exception {
        given(carService.search(null, 1L)).willReturn(oneCarBMW);
        this.mockMvc.perform( MockMvcRequestBuilders
                .get("/search")
                .param("id", "1"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].name").value("BMW"))
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    public void Search_GetRequestWithNameAndIdProvided_ListWithOneCarReturned() throws Exception {
        given(carService.search("Nissan", 2L)).willReturn(oneCarNissan);
        this.mockMvc.perform( MockMvcRequestBuilders
                .get("/search")
                .param("id", "2")
                .param("name", "Nissan"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].name").value("Nissan"))
                .andExpect(jsonPath("$[0].id").value("2"));
    }

    @Test
    public void Search_PostRequestWithNoArgsProvided_405ResponseReturned() throws Exception {
        given(carService.search("BMW", 1L)).willReturn(oneCarBMW);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/search"))
                .andExpect(status().is(405));
    }

    @Test
    public void Search_PutRequestWithNoArgsProvided_405ResponseReturned() throws Exception {
        given(carService.search("BMW", 1L)).willReturn(oneCarBMW);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/search"))
                .andExpect(status().is(405));
    }

    @Test
    public void Search_DeleteRequestWithNoArgsProvided_405ResponseReturned() throws Exception {
        given(carService.search("BMW", 1L)).willReturn(oneCarBMW);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/search"))
                .andExpect(status().is(405));
    }

    @Test
    public void AddCar_PostRequestWithJsonProvided_200ResponseReturned() throws Exception {
        this.mockMvc.perform(post("/addCar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"BMW\"}"))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    public void AddCar_PutRequestWithJsonProvided_405ResponseReturned() throws Exception {
        this.mockMvc.perform(put("/addCar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"BMW\"}"))
                .andExpect(status().is(405));
    }

    @Test
    public void AddCar_GetRequestWithJsonProvided_405ResponseReturned() throws Exception {
        this.mockMvc.perform(get("/addCar"))
                .andExpect(status().is(405));
    }

    @Test
    public void AddCar_DeleteRequestWithJsonProvided_405ResponseReturned() throws Exception {
        this.mockMvc.perform(delete("/addCar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"BMW\"}"))
                .andExpect(status().is(405));
    }
}