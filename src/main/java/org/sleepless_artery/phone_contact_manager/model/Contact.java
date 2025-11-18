package org.sleepless_artery.phone_contact_manager.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "contact")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "note")
    private String note;
}
