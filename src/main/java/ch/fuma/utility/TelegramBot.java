package ch.fuma.utility;

import ch.fuma.forms.EventForm;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;

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
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd:HHmmss");
            String startDate = format.format(eventForm.getFrom()).replace(":", "T");
            String endTime = String.valueOf(Integer.parseInt(startDate.substring(9, 11)) + 1) + startDate.substring(11);
            String endDate = startDate.substring(0, 9) + endTime;
            String link = "http://www.google.com/calendar/event?action=TEMPLATE&src=qfq3qv4m3jgefoeeb0imp530q4@group.calendar.google.com&text=" + eventForm.getDescription() + " mit "+eventForm.getName()+ "&dates=" + startDate + "/" + endDate + "&details=&sprop=&location=";

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
