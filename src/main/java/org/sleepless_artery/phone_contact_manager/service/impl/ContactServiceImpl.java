package org.sleepless_artery.phone_contact_manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sleepless_artery.phone_contact_manager.dto.ContactDto;
import org.sleepless_artery.phone_contact_manager.exception.ContactAlreadyExistsException;
import org.sleepless_artery.phone_contact_manager.exception.ContactNotFoundException;
import org.sleepless_artery.phone_contact_manager.infrastructure.logging.LogEvent;
import org.sleepless_artery.phone_contact_manager.mapper.ContactMapper;
import org.sleepless_artery.phone_contact_manager.repository.ContactRepository;
import org.sleepless_artery.phone_contact_manager.service.ContactService;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;



@Slf4j
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;


    @Override
    public ContactDto createContact(ContactDto contactDto) {
        var phoneNumber = contactDto.getPhoneNumber();
        var name = contactDto.getName();

        log.info(
                LogEvent.CONTACT_CREATION_STARTED.name(),
                kv("phoneNumber", phoneNumber),
                kv("name", name)
        );

        if (contactRepository.existsByPhoneNumber(phoneNumber)) {
            log.warn(
                    LogEvent.CONTACT_CREATION_FAILED.name(),
                    kv("reason", LogEvent.CONTACT_ALREADY_EXISTS),
                    kv("phoneNumber", phoneNumber)
            );
            throw new ContactAlreadyExistsException("A contact with this phone number already exists");
        }

        if (contactRepository.existsByName(name)) {
            log.warn(
                    LogEvent.CONTACT_CREATION_FAILED.name(),
                    kv("reason", LogEvent.CONTACT_ALREADY_EXISTS),
                    kv("name", name)
            );
            throw new ContactAlreadyExistsException("A contact with this name already exists");
        }
        contactRepository.save(contactMapper.toEntity(contactDto));

        log.info(
                LogEvent.CONTACT_CREATION_SUCCESS.name(),
                kv("phoneNumber", phoneNumber),
                kv("name", name)
        );
        return contactDto;
    }


    @Override
    public ContactDto getContactById(Long id) {
        log.info(
                LogEvent.CONTACT_FETCH_STARTED.name(),
                kv("ID", id)
        );

        var contact = contactRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(
                            LogEvent.CONTACT_FETCH_FAILED.name(),
                            kv("reason", LogEvent.CONTACT_NOT_FOUND.name()),
                            kv("ID", id)
                    );
                    return new ContactNotFoundException("A contact with this ID does not exist");
                });

        log.info(
                LogEvent.CONTACT_FETCH_SUCCESS.name(),
                kv("ID", id)
        );

        return contactMapper.toDto(contact);
    }


    @Override
    public ContactDto getContactByName(String name) {
        log.info(
                LogEvent.CONTACT_FETCH_STARTED.name(),
                kv("name", name)
        );

        var contact = contactRepository.findByName(name)
                .orElseThrow(() -> {
                    log.warn(
                            LogEvent.CONTACT_FETCH_FAILED.name(),
                            kv("reason", LogEvent.CONTACT_NOT_FOUND.name()),
                            kv("name", name)
                    );
                    return new ContactNotFoundException("A contact with this name does not exist");
                });

        log.info(
                LogEvent.CONTACT_FETCH_SUCCESS.name(),
                kv("name", name)
        );

        return contactMapper.toDto(contact);
    }


    @Override
    public ContactDto getContactByPhoneNumber(String phoneNumber) {
        log.info(
                LogEvent.CONTACT_FETCH_STARTED.name(),
                kv("phoneNumber", phoneNumber)
        );

        var contact = contactRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> {
                    log.warn(
                            LogEvent.CONTACT_FETCH_FAILED.name(),
                            kv("reason", LogEvent.CONTACT_NOT_FOUND.name()),
                            kv("phoneNumber", phoneNumber)
                    );
                    return new ContactNotFoundException("A contact with this phone number does not exist");
                });

        log.info(
                LogEvent.CONTACT_FETCH_SUCCESS.name(),
                kv("phoneNumber", phoneNumber)
        );

        return contactMapper.toDto(contact);
    }


    @Override
    public List<ContactDto> getContacts() {
        return contactRepository.findAll().stream()
                .map(contactMapper::toDto)
                .toList();
    }


    @Override
    public ContactDto update(Long id, ContactDto contactDto) {
        log.info(
                LogEvent.CONTACT_UPDATE_STARTED.name(),
                kv("ID", id)
        );

        var existingContact = contactRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(
                            LogEvent.CONTACT_UPDATE_FAILED.name(),
                            kv("reason", LogEvent.CONTACT_NOT_FOUND.name()),
                            kv("ID", id)
                    );
                    return new ContactNotFoundException("A contact with this ID does not exist");
                });

        var newName = contactDto.getName();
        var newPhoneNumber = contactDto.getPhoneNumber();

        if (contactRepository.existsByPhoneNumber(newPhoneNumber) &&
                !existingContact.getPhoneNumber().equals(newPhoneNumber)
        ) {
            log.warn(
                    LogEvent.CONTACT_UPDATE_FAILED.name(),
                    kv("reason", LogEvent.CONTACT_ALREADY_EXISTS),
                    kv("ID", id),
                    kv("phoneNumber", newPhoneNumber)
            );
            throw new ContactAlreadyExistsException("A contact with this phone number already exists");
        }

        if (contactRepository.existsByName(newName) &&
                !existingContact.getName().equals(newName)
        ) {
            log.warn(
                    LogEvent.CONTACT_UPDATE_FAILED.name(),
                    kv("reason", LogEvent.CONTACT_ALREADY_EXISTS),
                    kv("ID", id),
                    kv("name", newName)
            );
            throw new ContactAlreadyExistsException("A contact with this name already exists");
        }

        existingContact.setName(contactDto.getName());
        existingContact.setPhoneNumber(contactDto.getPhoneNumber());
        existingContact.setNote(contactDto.getNote());

        var savedContact = contactRepository.save(existingContact);

        log.info(
                LogEvent.CONTACT_UPDATE_SUCCESS.name(),
                kv("ID", id)
        );
        return contactMapper.toDto(savedContact);
    }

    @Override
    public void deleteById(Long id) {
        log.info(
                LogEvent.CONTACT_DELETION_STARTED.name(),
                kv("ID", id)
        );

        if (!contactRepository.existsById(id)) {
            log.warn(
                    LogEvent.CONTACT_DELETION_FAILED.name(),
                    kv("reason", LogEvent.CONTACT_NOT_FOUND.name()),
                    kv("ID", id)
            );
            throw new ContactNotFoundException("A contact with this ID does not exist");
        }

        contactRepository.deleteById(id);

        log.info(
                LogEvent.CONTACT_DELETION_SUCCESS.name(),
                kv("ID", id)
        );
    }

    @Override
    public void deleteAll() {
        log.info(
                LogEvent.CONTACT_COMPLETE_DELETION_STARTED.name()
        );
        contactRepository.deleteAll();
    }
}
