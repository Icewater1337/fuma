package ch.fuma.utility;

import ch.fuma.forms.EventForm;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class TelegramBot extends TelegramLongPollingBot {

    //244571618
    private static Long chatId = null;

    @Override
    public void onUpdateReceived(Update update) {
        if (chatId == null) {
            chatId= update.getMessage().getChatId();
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

    public void send(EventForm eventForm) {
        if (chatId == null) {
            chatId = 244571618L;
        }
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
            DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HHmmss");
            String startTime = "T"+ eventForm.getFromTime().format(formatTime);


            String startDate = formatDate.format(eventForm.getFrom())+ startTime;
            String endTime = String.valueOf(Integer.parseInt(startTime.substring(1, 3)) + 1) + startTime.substring(3);
            String endDate = formatDate.format(eventForm.getFrom()) + "T" + endTime;
            String link = "http://www.google.com/calendar/event?action=TEMPLATE&src=qfq3qv4m3jgefoeeb0imp530q4@group.calendar.google.com&text=" + eventForm.getDescription().replace(" ","_") + "_mit_"+eventForm.getName()+ "&dates=" + startDate + "/" + endDate + "&details=&sprop=&location=";

            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(chatId)
                .setText("Hey, the person with name: " + eventForm.getName() + " \n and the ip: " + eventForm.getIpAddress() + " sent you the following proposal: " + eventForm.getDescription() + "\n the link is: \n" + link);

            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored) {

        }
    }
}
