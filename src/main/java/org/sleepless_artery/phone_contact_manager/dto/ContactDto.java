package org.sleepless_artery.phone_contact_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    private Long id;

    @NotBlank(message = "Contact name cannot be blank")
    @Size(max = 100, message = "Contact name is too long")
    private String name;

    @Pattern(regexp = "[+]7 \\d{3} \\d{3} \\d{2} \\d{2}", message = "Illegal number format")
    private String phoneNumber;

    @Size(max = 100, message = "Note is too long")
    private String note;
}
