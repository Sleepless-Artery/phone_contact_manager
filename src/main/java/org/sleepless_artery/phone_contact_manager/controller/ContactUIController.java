package org.sleepless_artery.phone_contact_manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sleepless_artery.phone_contact_manager.dto.ContactDto;
import org.sleepless_artery.phone_contact_manager.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactUIController {

    private final ContactService contactService;

    @GetMapping("/ui")
    public String list(Model model) {
        model.addAttribute("contacts", contactService.getContacts());
        return "contacts";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("contact", new ContactDto(null, null, null, null));
        return "contact-form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("contact", contactService.getContactById(id));
        return "contact-form";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("contact", contactService.getContactById(id));
        return "contact-view";
    }

    @PostMapping
    public String createSubmit(@Valid @ModelAttribute("contact") ContactDto contactDto,
                               BindingResult result) {
        if (result.hasErrors()) return "contact-form";
        contactService.createContact(contactDto);
        return "redirect:/contacts/ui";
    }

    @PostMapping("/{id}")
    public String updateSubmit(
            @PathVariable Long id,
            @Valid @ModelAttribute("contact") ContactDto contactDto,
            BindingResult result
    ) {
        if (result.hasErrors()) return "contact-form";
        contactService.update(id, contactDto);
        return "redirect:/contacts/ui";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        contactService.deleteById(id);
        return "redirect:/contacts/ui";
    }

    @GetMapping("/delete-all")
    public String deleteAll() {
        contactService.deleteAll();
        return "redirect:/contacts/ui";
    }
}
