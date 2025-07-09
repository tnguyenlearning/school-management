package com.school.autonum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.school.autonum.entity.AutoNumber;
import com.school.constant.AnumType;

import java.util.Optional;

@Repository
public interface AutoNumberRepository extends JpaRepository<AutoNumber, Long> {

	Optional<AutoNumber> findFirstByTypeAndReservedFalseOrderByNumberAsc(AnumType type);

	Optional<AutoNumber> findByTypeAndNumberAndReservedTrue(AnumType type, String number);

	@Modifying
	@Query("UPDATE AutoNumber a SET a.reserved = :reserved WHERE a.id = :id")
	void updateReservationStatus(@Param("id") Long id, @Param("reserved") Boolean reserved);

}
