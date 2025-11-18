package org.sleepless_artery.phone_contact_manager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.sleepless_artery.phone_contact_manager.dto.ContactDto;
import org.sleepless_artery.phone_contact_manager.model.Contact;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContactMapper {

    ContactDto toDto(Contact contact);

    Contact toEntity(ContactDto contactDto);
}
