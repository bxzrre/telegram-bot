package me.shibeguy.bot;

import com.jtelegram.api.TelegramBot;
import com.jtelegram.api.TelegramBotRegistry;
import com.jtelegram.api.events.message.TextMessageEvent;
import com.jtelegram.api.requests.framework.TelegramRequest;
import com.jtelegram.api.requests.message.send.SendText;
import com.jtelegram.api.update.PollingUpdateProvider;
import me.shibeguy.bot.handler.BotRegistry;

public class Main {
    private final TelegramBotRegistry registry;
    private TelegramBot telegramBot;

    public Main(String key) {
        this.registry = TelegramBotRegistry.builder()
                .eventThreadCount(4)
                .updateProvider(new PollingUpdateProvider())
                .build();

        registry.registerBot(key, (bot, error) -> {
            if (error != null) {
                System.out.println(error.getDescription());
                return;
            }

            this.telegramBot = bot;
            new BotRegistry(this);
        });
    }

    public void reply(TextMessageEvent event, String message) {
        telegramBot.perform((TelegramRequest) SendText.builder().chatId(event.getMessage().getChat().getChatId()).text(message).replyToMessageID(event.getMessage().getMessageId()));
    }

    public static void main(String[] args) {
        new Main(Info.BOT_KEY);
    }

    public TelegramBot getBot() {
        return telegramBot;
    }
}
