package com.olbati.repositories;

import com.olbati.utils.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The email repository.
 *
 * @author MAZIGH Med Belhassen.
 */

@Repository
public interface EmailRepository extends MongoRepository<Email, String> {

    List<Email> findBySubject(@Param("subject") String subject);

    List<Email> findByAddressIgnoreCase(@Param("address") String address);
}
