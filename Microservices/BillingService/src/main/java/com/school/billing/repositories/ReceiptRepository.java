package com.school.billing.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.billing.constants.PaymentMethod;
import com.school.billing.entities.Receipt;

@RepositoryRestResource(path = "receipts")
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query("SELECT r FROM Receipt r WHERE "
         + "(:studentAccountId IS NULL OR r.studentAccount.id = :studentAccountId) AND "
         + "(:paymentMethod IS NULL OR r.paymentMethod = :paymentMethod) AND "
         + "(:fromDate IS NULL OR r.receiptDate >= :fromDate) AND "
         + "(:toDate IS NULL OR r.receiptDate <= :toDate)")
    Page<List<Receipt>> searchReceipts(
        @Param("studentAccountId") Long studentAccountId,
        @Param("paymentMethod") PaymentMethod paymentMethod,
        @Param("fromDate") LocalDate fromDate,
        @Param("toDate") LocalDate toDate,
        Pageable pageable
    );
    
}
