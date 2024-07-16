package bank_app.controller;

import bank_app.service.BanksService;
import bank_app.models.Banks;
import bank_app.repos.BanksRepo;
import bank_app.service.BanksService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BanksController.class)
public class BanksControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BanksService banksService;

    @MockBean
    private BanksRepo banksRepo;

    @Test
    public void createBankTest() throws Exception {

        Banks bank = new Banks(1L, "343434343");

        when(banksRepo.save(any(Banks.class))).thenReturn(bank);

        mockMvc.perform(post("/banks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bic\": \"343434343\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_banks").value(1L))
                .andExpect(jsonPath("$.bic").value("04452878"));
    }

    @Test
    public void createWithWrongLengthTest() throws Exception {

        mockMvc.perform(post("/banks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bic\": \"04452\"}"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.bic").value("Бик - девятизначное число"));
    }

    @Test
    public void createWithLettersTest() throws Exception {

        mockMvc.perform(post("/banks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bic\": \"asdffdfds\"}"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.bic").value("Бик - девятизначное число"));
    }

    @Test
    public void getBankByIdTest() throws Exception {

        Banks bank = new Banks(1L, "111111111");

        when(banksRepo.findById(1L)).thenReturn(Optional.of(bank));

        mockMvc.perform(get("/banks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_banks").value(1L))
                .andExpect(jsonPath("$.bic").value("111111111"));
    }



    @Test
    public void letterInBikUpdateBankTest() throws Exception {
        Banks existingBank = new Banks(1L, "123456789");

        Banks updatedBank = new Banks(1L, "asdfdfdfd");

        when(banksService.updateBank(anyLong(), any(Banks.class))).thenReturn(updatedBank);
        when(banksRepo.findById(1L)).thenReturn(Optional.of(existingBank));

        mockMvc.perform(put("/banks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bic\": \"asdfdfdfd\"}"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.bic").value("Бик - число"));
    }

    @Test
    public void deleteBankTest() throws Exception {

        when(banksRepo.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/banks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Банк успешно удален"));
    }

    @Test
    public void deleteWrongBankTest() throws Exception {

        mockMvc.perform(delete("/banks/1"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Такого банка не существует"));
    }


    @Test
    public void getAllBanksNotEmptyTest() throws Exception {

        Banks bank1 = new Banks(1, "111111111");

        Banks bank2 = new Banks(2, "222222222");

        when(banksRepo.findAll()).thenReturn(Arrays.asList(bank1, bank2));

        mockMvc.perform(get("/banks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_banks").value(1))
                .andExpect(jsonPath("$[0].bic").value("111111111"))
                .andExpect(jsonPath("$[1].id_banks").value(2))
                .andExpect(jsonPath("$[1].bic").value("222222222"));
    }

    @Test
    public void getAllBanksEmptyTest() throws Exception {


        mockMvc.perform(get("/banks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getAllBanksSortedTest() throws Exception {
        Banks bank1 = new Banks(1, "123456789");
        Banks bank2 = new Banks(2, "987654321");
        Banks bank3 = new Banks(3, "555555555");

        List<Banks> banksList = Arrays.asList(bank1, bank2, bank3);

        List<Banks> expectedSortedBanks = Arrays.asList(bank3, bank1, bank2);

        when(banksRepo.findAll()).thenReturn(banksList);
        when(banksRepo.findAll(Sort.by("bik"))).thenReturn(expectedSortedBanks);

        mockMvc.perform(get("/banks")
                        .param("sortBy", "bic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bic").value("044765247"))
                .andExpect(jsonPath("$[1].bic").value("044525187"))
                .andExpect(jsonPath("$[2].bic").value("044525593"));

    }

}
