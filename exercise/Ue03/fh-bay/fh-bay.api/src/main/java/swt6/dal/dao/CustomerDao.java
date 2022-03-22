package swt6.dal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.dal.domain.Customer;

import java.util.Optional;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {

    Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
