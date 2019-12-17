package me.shibeguy.bot;

import com.jtelegram.api.TelegramBot;
import com.jtelegram.api.TelegramBotRegistry;
import com.jtelegram.api.chat.Chat;
import com.jtelegram.api.chat.ChatMemberStatus;
import com.jtelegram.api.chat.id.ChatId;
import com.jtelegram.api.events.message.TextMessageEvent;
import com.jtelegram.api.requests.chat.GetChatMember;
import com.jtelegram.api.requests.message.send.SendText;
import com.jtelegram.api.update.PollingUpdateProvider;
import com.jtelegram.api.update.UpdateType;
import com.jtelegram.api.user.User;
import me.shibeguy.bot.handler.BotRegistry;

import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private final TelegramBotRegistry registry;
    private TelegramBot telegramBot;

    private AtomicBoolean isAdmin = new AtomicBoolean(false);

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
            updateMessage();
        });
    }

    public static void main(String[] args) {
        new Main(Info.BOT_KEY);
    }

    public TelegramBot getTelegramBot() {
        return telegramBot;
    }

    public void send(TextMessageEvent event, String string) {
        telegramBot.perform(
                SendText.builder()
                .chatId(event.getMessage().getChat().getChatId())
                .text(string)
                .build()
        );
    }

    public void reply(TextMessageEvent event, String string) {
        telegramBot.perform(
                SendText.builder()
                .chatId(event.getMessage().getChat().getChatId())
                .text(string)
                .replyToMessageID(event.getMessage().getMessageId())
                .build()
        );
    }

    private void updateMessage() {
        telegramBot.perform(
                SendText.builder()
                .chatId(ChatId.of("@shitposters"))
                .text("bot just updated! \nor maybe i just died\n\ni probably just died...")
                        .build()
        );
    }
}
