package controllers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import models.Chat;
import models.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ChatRepository;
import services.QuoteService;

import java.util.Optional;
@Service
public class BotController {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    QuoteService service;
    private final TelegramBot bot;

    public BotController() {
        bot = new TelegramBot("BOT_TOKEN");
        bot.setUpdatesListener(updates -> {

            for (Update update: updates) {
                handleUpdate(update);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void handleUpdate(Update update) {
        String text = update.message().text();
        long chatId = update.message().chat().id();
        Optional<Chat> rawChat = chatRepository.findByChatIdEquals(chatId);
        Chat chat;
        if(rawChat.isPresent()) {
            chat = rawChat.get();
        } else {
            Chat newChat = new Chat();
            newChat.setChatId(chatId);
            newChat.setLastId(0);
            chat = chatRepository.save(newChat);
        }
        switch (text){
            case "/start":
            case "/next":
                sendNextQuote(chat);
                break;
            case "/prev":
                sendPrevQuote(chat);
                break;
            case "/rand":
                sendRandom(chat);
                break;
        }
    }

    private void sendNextQuote(Chat chat) {
        Quote quote = null;
        int newId = chat.getLastId();
        while (quote == null){
            newId++;
            quote = service.getById(newId);
        }
        chat.setLastId(quote.getQuoteId());
        chatRepository.save(chat);
        sendText(chat.getChatId(), quote.getText());
    }
    private void sendPrevQuote(Chat chat) {
        Quote quote = null;
        int newId = chat.getLastId();
        while (quote == null){
            newId--;
            if(newId < 2) newId = 2;
            quote = service.getById(newId);
        }
        chat.setLastId(quote.getQuoteId());
        chatRepository.save(chat);
        sendText(chat.getChatId(), quote.getText());
    }
    private void sendRandom(Chat chat) {
        Quote quote = service.getRandom();
        sendText(chat.getChatId(), quote.getText());
    }
    private void sendText(long chatId, String text){
        bot.execute(new SendMessage(chatId, text));
    }
}