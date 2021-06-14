package com.atoscodechallenge.middleware.repository;

import com.atoscodechallenge.middleware.domain.MdwTransaction;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MdwTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MdwTransactionRepository extends PagingAndSortingRepository<MdwTransaction, String> {

	List<MdwTransaction> findAllByAccountIban(String accountIban, Pageable pageable);

	Optional<MdwTransaction> findByReference(String reference);
}
