package com.pos.Point.Of.Sale.Repository;

import com.pos.Point.Of.Sale.Entity.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellRepository extends JpaRepository<Sell, Long> {
}
