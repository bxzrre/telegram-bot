package me.shibeguy.bot;

import com.jtelegram.api.TelegramBot;
import com.jtelegram.api.TelegramBotRegistry;
import com.jtelegram.api.commands.Command;
import com.jtelegram.api.requests.message.send.SendText;
import com.jtelegram.api.update.PollingUpdateProvider;
import com.jtelegram.api.update.UpdateType;
import me.shibeguy.bot.handler.BotRegistry;

public class Main {

    private final TelegramBotRegistry registry;
    private TelegramBot telegramBot;

    public Main(String key) {
        this.registry = TelegramBotRegistry.builder()
                .updateProvider(PollingUpdateProvider.builder()
                        .allowedUpdate(UpdateType.INLINE_QUERY)
                        .allowedUpdate(UpdateType.MESSAGE)
                        .build())
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

    public static void main(String[] args) {
        new Main(Info.BOT_KEY);
    }

    public TelegramBot getTelegramBot() {
        return telegramBot;
    }

    public void sendMessage(Command command, String string) {
        telegramBot.perform(
                SendText.builder()
                .chatId(command.getChat().getChatId())
                .text(string)
                .build()
        );
    }
}
