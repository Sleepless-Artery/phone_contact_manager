package org.sleepless_artery.phone_contact_manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sleepless_artery.phone_contact_manager.dto.ContactDto;
import org.sleepless_artery.phone_contact_manager.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contacts")
@Tag(name = "Contacts API", description = "REST API для управления контактами")
public class ContactRestController {

    private final ContactService contactService;

    @GetMapping
    @Operation(
            summary = "Получить список всех контактов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список контактов успешно получен"
                    )
            }
    )
    public ResponseEntity<List<ContactDto>> getAll() {
        return ResponseEntity.ok(contactService.getContacts());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить контакт по ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Контакт найден"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Контакт не найден",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    public ResponseEntity<ContactDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    @PostMapping
    @Operation(
            summary = "Создать новый контакт",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Контакт успешно создан"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные"
                    )
            }
    )
    public ResponseEntity<ContactDto> create(@RequestBody ContactDto contactDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contactService.createContact(contactDto));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить существующий контакт",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Контакт обновлён"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Контакт не найден"
                    )
            }
    )
    public ResponseEntity<ContactDto> update(
            @PathVariable Long id,
            @RequestBody ContactDto contactDto
    ) {
        return ResponseEntity.ok(contactService.update(id, contactDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить контакт по ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Контакт удалён"),
                    @ApiResponse(responseCode = "404", description = "Контакт не найден")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-all")
    @Operation(
            summary = "Удалить все контакты",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Все контакты удалены")
            }
    )
    public ResponseEntity<Void> deleteAll() {
        contactService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}

