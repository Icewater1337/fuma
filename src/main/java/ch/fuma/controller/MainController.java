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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


@Controller
public class MainController {

    public static HashMap<String, Integer> ipAddressesAndCalls = new HashMap<>();
    public static HashMap<String, String> lockedDate = new HashMap<>();

    @GetMapping("/")
    public String greetingForm(Model model, HttpServletRequest request) {
        model.addAttribute("eventForm", new EventForm());
        return "calendar";
    }

    @PostMapping("/")
    public String greetingSubmit(@ModelAttribute EventForm eventForm, HttpServletRequest request) {
        String ipAddr = request.getRemoteAddr();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // if it is a new day, revert block.
        if ( lockedDate.containsKey(ipAddr) &&  !dtf.format(LocalDate.now()).equals(lockedDate.get(ipAddr))) {
            lockedDate.remove(ipAddr);
            ipAddressesAndCalls.put(ipAddr,1);
        }
        if ( ipAddressesAndCalls.containsKey(ipAddr)) {
            ipAddressesAndCalls.put(ipAddr, ipAddressesAndCalls.get(ipAddr) +1);
        } else {
            ipAddressesAndCalls.put(ipAddr,1);
        }
        if ( ipAddressesAndCalls.get(ipAddr) <10) {
            eventForm.setIpAddress(ipAddr);
            TelegramBot.events.add(eventForm);
        } else {
            if ( !lockedDate.containsKey(ipAddr)) {
                String newEntryPoint = dtf.format(LocalDate.now());
                lockedDate.put(ipAddr, newEntryPoint);
            }
            return "sadPony";
        }


        return "calendar";
    }
}