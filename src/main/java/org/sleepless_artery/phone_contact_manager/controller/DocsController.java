package org.sleepless_artery.phone_contact_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DocsController {

    @GetMapping("/docs")
    public String docs() {
        return "/swagger-ui/index.html";
    }
}

