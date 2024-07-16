package bank_app.controller;

import bank_app.repos.CustomersRepo;
import bank_app.service.CustomersService;
import bank_app.util.SortUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@Validated
@RestController
@RequestMapping("/customers")
public class CustomersController {

    private final CustomersService customersService;
    private CustomersRepo customersRepo;
    private bank_app.repos.OpfRepo opfRepo;
    final List<String> validSortFields = Arrays.asList("id_customer", "name_customer", "full_name_customer", "address", "opf_id");

    public CustomersController(CustomersService customersService, CustomersRepo customersRepo1, bank_app.repos.OpfRepo opfRepo) {
        this.customersService = customersService;
        this.customersRepo = customersRepo;
        this.opfRepo = opfRepo;
    }

    @PostMapping
    public bank_app.models.Customers createCustomer(@Valid @RequestBody bank_app.models.Customers customers) {
        if (!opfRepo.existsById(customers.getOpf()))
            return customersRepo.save(customers);
        return customers;
    }


    @PutMapping("/{id}")
    public bank_app.models.Customers updateCustomer(@PathVariable Long id, @Valid @RequestBody bank_app.models.Customers customersDetails) {
        return customersService.updateCustomers(id, customersDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomers(@PathVariable Long id) {
        if (customersRepo.existsById(id)) {
            customersRepo.deleteById(id);
            return ResponseEntity.ok("Клиент успешно удалён");
        }
        return null;
    }
}
/*@GetMapping()
    public List<bank_app.models.Customers> getAllCustomers(@RequestParam(required = false) String[] sortBy) {
        if (sortBy != null && sortBy.length > 0) {
            if (SortUtil.CheckSort(sortBy, validSortFields)) {
                return customersRepo.findAll(Sort.by(sortBy));

        } else {
            return customersRepo.findAll();
        }
    }*=/
}
