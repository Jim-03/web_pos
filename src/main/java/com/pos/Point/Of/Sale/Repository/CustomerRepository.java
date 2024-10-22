package com.pos.Point.Of.Sale.Repository;

import com.pos.Point.Of.Sale.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Boolean existsByPhone(String phone);

    Customer findByPhone(String oldPhone);
}
