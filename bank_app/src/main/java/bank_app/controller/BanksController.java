package bank_app.controller;

import bank_app.models.Banks;
import bank_app.repos.BanksRepo;
import bank_app.service.BanksService;
import bank_app.util.SortUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@RequestMapping("/banks")
public class BanksController {

    public final BanksService banksService;
    public final BanksRepo banksRepo;
    List<String> validSortFields = Arrays.asList("id_banks", "bic");

    @Autowired
    public BanksController(BanksService bankService, BanksRepo banksRepo) {
        this.banksService = bankService;
        this.banksRepo = banksRepo;
    }

    @PostMapping
    public Banks createBank(@RequestBody @Valid Banks bank) {
        return banksRepo.save(bank);
    }


    @PutMapping("/{id}")
    public Banks updateBank(@PathVariable Long id, @Valid @RequestBody Banks bankDetails) {
        return banksService.updateBank(id, bankDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBank(@PathVariable Long id) {
        if (banksRepo.existsById(id)) {
            banksRepo.deleteById(id);
            return ResponseEntity.ok("Банк удален");
        }
        return null;
    }
    @GetMapping()
    public List<Banks> getAllBanks(@RequestParam(required = false) String[] sortBy) {
        if (sortBy != null && sortBy.length > 0) {
            if (SortUtil.CheckSort(sortBy, validSortFields)) {
                return banksRepo.findAll(Sort.by(sortBy));
            }
            return banksRepo.findAll();
        }
        return null;
    }
}
