package ch.fuma;

import ch.fuma.utility.SecurityHandler;
import ch.fuma.utility.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@SpringBootApplication
public class Application {

	private static TelegramBot telegramBot;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		ApiContextInitializer.init();

		TelegramBotsApi botsApi = new TelegramBotsApi();

		try {
			telegramBot = new TelegramBot();
			botsApi.registerBot(telegramBot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public static TelegramBot getTelegramBot() {
		return telegramBot;
	}
}
