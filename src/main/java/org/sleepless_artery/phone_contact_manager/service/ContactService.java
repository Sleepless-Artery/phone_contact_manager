package org.sleepless_artery.phone_contact_manager.service;

import org.sleepless_artery.phone_contact_manager.dto.ContactDto;

import java.util.List;


public interface ContactService {

    ContactDto getContactById(Long id);

    ContactDto getContactByName(String name);

    ContactDto getContactByPhoneNumber(String phoneNumber);

    List<ContactDto> getContacts();

    ContactDto createContact(ContactDto contactDto);

    ContactDto update(Long id, ContactDto contactDto);

    void deleteById(Long id);

    void deleteAll();
}
