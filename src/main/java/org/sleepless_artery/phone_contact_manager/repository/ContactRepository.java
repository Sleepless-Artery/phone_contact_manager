package org.sleepless_artery.phone_contact_manager.repository;

import org.sleepless_artery.phone_contact_manager.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByName(String name);

    Optional<Contact> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByName(String name);
}
