package ch.fuma.utility;

import ch.fuma.forms.EventForm;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TelegramBot extends TelegramLongPollingBot {

    public static ArrayList<EventForm> events = new ArrayList<>();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if ( update.getMessage().getText().contains("update") && !events.isEmpty()) {
                for ( EventForm event : events) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd:HHmmss");
                    String startDate = format.format(event.getFrom()).replace(":", "T");
                    String endTime = String.valueOf(Integer.parseInt(startDate.substring(9,11))+1)+ startDate.substring(11);
                    String endDate = startDate.substring(0,9) + endTime;
                    String link = "http://www.google.com/calendar/event?action=TEMPLATE&src=qfq3qv4m3jgefoeeb0imp530q4@group.calendar.google.com&text="+event.getDescription()+"&dates="+startDate+"/"+endDate+"&details=&sprop=&location=";

                    SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                            .setChatId(update.getMessage().getChatId())
                            .setText("Hey, the person with the ip: "+ event.getIpAddress()+" sent you the following proposal: " + event.getDescription()+"\n the link is: \n"+link);
                    try {
                        execute(message); // Call method to send the message
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                events.clear();


            }

        }
    }

    @Override
    public String getBotUsername() {
        return "calendarBot";
    }

    @Override
    public String getBotToken() {
        return "503768713:AAHUgEV6cQb9rM6gxUNHihhnB_XfX9GJmyo";
    }
}
