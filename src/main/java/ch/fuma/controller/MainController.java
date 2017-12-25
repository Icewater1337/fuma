package ch.fuma.controller;

import ch.fuma.forms.EventForm;
import ch.fuma.forms.Greeting;
import ch.fuma.utility.TelegramBot;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class MainController {

    @GetMapping("/")
    public String greetingForm(Model model, HttpServletRequest request) {
        model.addAttribute("eventForm", new EventForm());
        return "calendar";
    }

    @PostMapping("/")
    public String greetingSubmit(@ModelAttribute EventForm eventForm, HttpServletRequest request) {
        eventForm.setIpAddress(request.getRemoteAddr());
        TelegramBot.events.add(eventForm);
        return "calendar";
    }
}