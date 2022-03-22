package swt6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import swt6.dal.domain.Customer;
import swt6.dto.CustomerDto;
import swt6.exceptions.NotFoundException;
import swt6.logic.CustomerLogic;

import java.util.List;

@RestController
@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private CustomerLogic customerLogic;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/filter")
    @Operation(summary = "Get Customer by given filters", description = "Returns detailed data for a given customer.")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Customer entry not found")
    public CustomerDto findCustomer(@RequestParam String firstName,
                                    @RequestParam String lastName) {
        var customer = customerLogic.findByFirstnameAndLastname(firstName, lastName);
        if (customer.isEmpty()) {
            throw new NotFoundException(firstName, lastName);
        }

        return mapper.map(customer.get(), CustomerDto.class);
    }

    @GetMapping
    @Operation(summary = "Get all customers", description = "Returns all customers.")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<CustomerDto> findAllCustomers() {
        return customerLogic.findAll().stream().map(c -> mapper.map(c, CustomerDto.class)).toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Success")
    @Operation(summary = "Inserts customer", description = "Inserts new customer.")
    public CustomerDto insert(@RequestBody CustomerDto customer) {
        var result = customerLogic.insert(mapper.map(customer, Customer.class));
        return mapper.map(result, CustomerDto.class);
    }
}
