package bank_app.service;

import bank_app.repos.CustomersRepo;
import org.springframework.stereotype.Service;

@Service
public class CustomersService {

    private CustomersRepo customersRepo;
    private bank_app.repos.OpfRepo opfRepo;

    public CustomersService(CustomersRepo customersRepo, bank_app.repos.OpfRepo opfRepo) {
        this.customersRepo = customersRepo;
        this.opfRepo = opfRepo;
    }

    public bank_app.models.Customers updateCustomers(Long id, bank_app.models.Customers newCustomersData) {
        if (!opfRepo.existsById(newCustomersData.getOpf()))
        return customersRepo.findById(id)
                .map(client -> {
                    client.setAddress(newCustomersData.getAddress());
                    client.setOpf(newCustomersData.getOpf());
                    client.setFullNameCustomer(newCustomersData.getFullNameCustomer());
                    client.setNameCustomer(newCustomersData.getNameCustomer());
                    return customersRepo.save(client);
                })
                .orElseThrow(() -> new RuntimeException("Не найдено id " + id));
        return newCustomersData;
    }
}
