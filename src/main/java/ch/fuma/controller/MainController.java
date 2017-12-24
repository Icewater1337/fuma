package ch.fuma.controller;

import ch.fuma.forms.EventForm;
import ch.fuma.forms.Greeting;
import ch.fuma.utility.TelegramBot;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class MainController {

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("eventForm", new EventForm());
        return "calendar";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute EventForm eventForm) {
        TelegramBot.events.add(eventForm);
        return "calendar";
    }
}