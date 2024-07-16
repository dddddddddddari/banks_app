package bank_app.controller;


import bank_app.models.Customers;
import com.fasterxml.jackson.databind.ObjectMapper;
import bank_app.repos.CustomersRepo;
import bank_app.service.CustomersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomersController.class)
public class CustomersControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomersService customersService;

    @MockBean
    private CustomersRepo customersRepo;

    @MockBean
    private bank_app.repos.OpfRepo opfRepo;


    @Test
    public void createCustomersTest(Customers customers) throws Exception {

        bank_app.models.Customers customers = new bank_app.models.Customers(4, "ОДК-АВИАДВИГАТЕЛЬ", "ОДК", "ул. проспект Комсомольский, д. 93 корп. 61", 4);
        when(customersRepo.save(any(bank_app.models.Customers.class))).thenReturn(customers);

        String customersJson = new ObjectMapper().writeValueAsString(customers);

        when(opfRepo.existsById(any(Long.class))).thenReturn(true);

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customersJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_customers").value(4))
                .andExpect(jsonPath("$.name_customers").value("ОДК-АВИАДВИГАТЕЛЬ"))
                .andExpect(jsonPath("$.full_name_customer").value("ОДК"))
                .andExpect(jsonPath("$.address").value("ул. проспект Комсомольский, д. 93 корп. 61"))
                .andExpect(jsonPath("$.opf_id").value(4));
    }


    @Test
    public void createCustomersWithSomeEmptyField(byte[] customersJson) throws Exception {
        when(opfRepo.existsById(any(Long.class))).thenReturn(true);

        String clientJson = "{\"full_name_customer\":\"ОДК\", \"address\":\"проспект Комсомольский, д. 93 корп. 61\", \"opf\": 4}";

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customersJson))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.name").value("Поле заполнено"));
    }

    @Test
    public void getCustomersByIdTest() throws Exception {

        bank_app.models.Customers customers = new bank_app.models.Customers(4, "ОДК-АВИАДВИГАТЕЛЬ", "ОДК", "ул. проспект Комсомольский, д. 93 корп. 61", 4);

        when(customersRepo.findById(1L)).thenReturn(Optional.of(customers));

        mockMvc.perform(get("/customers/4"))
                .andExpect(jsonPath("$.id_customers").value(4))
                .andExpect(jsonPath("$.name_customers").value("ОДК-АВИАДВИГАТЕЛЬ"))
                .andExpect(jsonPath("$.full_name_customer").value("ОДК"))
                .andExpect(jsonPath("$.address").value("ул. проспект Комсомольский, д. 93 корп. 61"))
                .andExpect(jsonPath("$.opf_id").value(4));
    }

    @Test
    public void getClientByWrongIdTest() throws Exception {

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Такого клиента неn"));
    }


    @Test
    public void deleteClientTest() throws Exception {

        when(customersRepo.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Клиент удалён"));
    }
}
