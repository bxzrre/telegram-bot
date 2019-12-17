package me.shibeguy.bot.commands.admin;

import com.jtelegram.api.chat.Chat;
import com.jtelegram.api.chat.ChatMemberStatus;
import com.jtelegram.api.commands.Command;
import com.jtelegram.api.commands.CommandHandler;
import com.jtelegram.api.events.message.TextMessageEvent;
import com.jtelegram.api.requests.chat.GetChatMember;
import com.jtelegram.api.requests.chat.admin.UnbanChatMember;
import com.jtelegram.api.user.User;
import me.shibeguy.bot.handler.BotRegistry;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class UnbanCommand implements CommandHandler {

    private final BotRegistry registry;
    private final String commandName = "unban";

    private final String[] SUCCESS = new String[] {
            "i am unban negor for you sar??",
            "FUCKING RATAD UNBANNED NOW NIGA",
            "HE IS FUCKING CRYYYYYYYYYYY",
            "shibe the bot unbanned this retard",
            "ok"
    };

    private final String[] FAILED = new String[]{
            "You are think im stupid like negor sar  ?  ? ??",
            "YOU ARE FUCKING NOT ADMIN?!?!?!",
            "you are try to promote but you are not admin sar?",
            "YOU THINK IM FUCKING IDIOATWE SAR? ?,"
    };

    public UnbanCommand(BotRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onCommand(TextMessageEvent event, Command command) {
        AtomicBoolean isAdmin = new AtomicBoolean();
        SecureRandom random = new SecureRandom();


        registry.getMain().getBot().perform(
                GetChatMember.builder()
                        .chatId(event.getMessage().getChat().getChatId())
                        .userId(command.getSender().getId())
                        .callback(chatMember -> {
                            if (chatMember.getStatus().ordinal() <= ChatMemberStatus.ADMINISTRATOR.ordinal()) {
                                isAdmin.set(true);
                            }

                            if (isAdmin.get()) {
                                User target = event.getMessage().getReplyToMessage().getSender();

                                unban(event.getMessage().getChat(), target.getId());
                                registry.getMain().reply(event, SUCCESS[random.nextInt(SUCCESS.length - 1)]);
                            } else {
                                registry.getMain().reply(event, FAILED[random.nextInt(FAILED.length - 1)]);
                            }
                        }).build()
        );
    }

    @Override
    public boolean test(TextMessageEvent event, Command command) {
        if (command.getBaseCommand().equalsIgnoreCase(commandName)) {
            onCommand(event, command);
            return true;
        }
        return false;
    }

    void unban(Chat chat, long userId) {
        registry.getMain().getBot().perform(
                UnbanChatMember.builder()
                .chatId(chat.getChatId())
                .userId(userId)
                .build()
        );
    }
}
