package ch.fuma.controller;

import ch.fuma.Application;
import ch.fuma.forms.EventForm;
import ch.fuma.utility.SecurityHandler;
import ch.fuma.utility.TelegramBot;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MainController {

    @GetMapping("/")
    public String greetingForm(Model model, HttpServletRequest request, @ModelAttribute("success") final String msg) {
        model.addAttribute("eventForm", new EventForm());
        model.addAttribute("success", msg);
        return "calendar";
    }

    @PostMapping("/")
    public String greetingSubmit(@ModelAttribute EventForm eventForm, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String ipAddr = request.getRemoteAddr();

        // if it is a new day, revert block.
        SecurityHandler.resetLocksIfNewDay(ipAddr);

        // adds +1 to counter, if ip already exists, if not, create new entry with 1.
        SecurityHandler.updateCounterForIp(ipAddr);

        if ( SecurityHandler.isBelowMaxAttempts(ipAddr)) {
            eventForm.setIpAddress(ipAddr);
            TelegramBot telegramBot = Application.getTelegramBot();

            telegramBot.send(eventForm);

        } else {
            SecurityHandler.lockUser(ipAddr);
            return "sadPony";
        }


        redirectAttributes.addFlashAttribute("success", "Thank you for suggesting the appointment! I will contact you shortly, if I know who you are :)");

        return "redirect:/";
    }




}