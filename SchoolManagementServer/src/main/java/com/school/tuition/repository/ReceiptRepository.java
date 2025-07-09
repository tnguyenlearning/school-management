package com.school.tuition.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.student.enrollment.entity.Student;
import com.school.tuition.entity.Receipt;

@RepositoryRestResource(path = "receipts")
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

	List<Receipt> findByStudent(Student student);

    List<Receipt> findByStudentIdAndBalanceGreaterThan(Long studentId, Double balance);
    
    @Query("SELECT r FROM Receipt r WHERE r.student.id = :studentId AND r.balance > 0")
    List<Receipt> findAllByStudentIdAndBalanceGreaterThanZero(@Param("studentId") Long studentId);


}
