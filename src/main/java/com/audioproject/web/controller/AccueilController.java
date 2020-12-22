package com.audioproject.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilController {

    /**
     * Page d'accueil de la librairie audio.
     * @return String. Retourne la page accueil.html.
     */
    @GetMapping(value = "/")
    public String accueil() {
        return "accueil";
    }
}
