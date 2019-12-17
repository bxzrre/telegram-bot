package me.shibeguy.bot.commands.admin;

import com.jtelegram.api.chat.Chat;
import com.jtelegram.api.chat.ChatMember;
import com.jtelegram.api.chat.ChatMemberStatus;
import com.jtelegram.api.commands.Command;
import com.jtelegram.api.commands.CommandHandler;
import com.jtelegram.api.events.message.TextMessageEvent;
import com.jtelegram.api.message.Message;
import com.jtelegram.api.requests.chat.GetChatMember;
import com.jtelegram.api.user.User;
import me.shibeguy.bot.handler.BotRegistry;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class PromoteCommand implements CommandHandler {

    private final String commandName = "promote";
    private final BotRegistry registry;

    public PromoteCommand(BotRegistry registry) {
        this.registry = registry;
    }

    private final String[] SUCCESS = new String[] {
            "YOU ARE FUCKING HAVE PRIVILEGE SAR???",
            "i aM FUCKING GRANT PRIVILEGE TO SAR",
            "he is fucking have privilege now sar?",
            "I AM FUCKING PROMOTE STUPD NEGOR FOR YOU SAR",
            "willow",
            "YOU ARE FUCKING PROMOTE REART SARRRRRR"
};

    private final String[] FAILED = new String[] {
            "You are think im stupid like negor sar  ?  ? ??",
            "YOU ARE FUCKING NOT ADMIN?!?!?!",
            "you are try to promote but you are not admin sar?",
            "YOU THINK IM FUCKING IDIOATWE SAR? ?,"
    };

    @Override
    public void onCommand(TextMessageEvent event, Command command) {
        AtomicBoolean isAdmin = new AtomicBoolean(false);
        SecureRandom random = new SecureRandom();

        System.out.println("beginning to check");
        registry.getMain().getTelegramBot().perform(
                GetChatMember.builder()
                .chatId(event.getMessage().getChat().getChatId())
                .userId(command.getSender().getId())
                .callback(chatMember -> {
                    if (chatMember.getStatus().ordinal() <= ChatMemberStatus.ADMINISTRATOR.ordinal() || chatMember.getStatus().ordinal() <= ChatMemberStatus.CREATOR.ordinal()) {
                        isAdmin.set(true);
                        System.out.println("set adnmin to true");
                    }

                    if (isAdmin.get()) {
                        Message repliedToMessage = event.getMessage().getReplyToMessage();
                        User toPromote = event.getMessage().getReplyToMessage().getSender();

                        registry.getMain().getTelegramBot().perform(
                                GetChatMember.builder()
                                .chatId(event.getMessage().getChat().getChatId())
                                .userId(toPromote.getId())
                                .callback(chatMember1 -> {
                                    if (chatMember1.getStatus().ordinal() <= ChatMemberStatus.ADMINISTRATOR.ordinal()) {
                                        System.out.println("wow! all of this works?");
                                    } else  {
                                        registry.getMain().reply(event, "this user is already admin");
                                    }
                                }).build()
                        );

                        registry.getMain().reply(event, SUCCESS[random.nextInt(SUCCESS.length - 1)]);
                        return;
                    } else {
                        registry.getMain().reply(event, FAILED[random.nextInt(FAILED.length - 1)]);
                        return;
                    }
                })
                .build());
    }

    @Override
    public boolean test(TextMessageEvent event, Command command) {
        if (command.getBaseCommand().equalsIgnoreCase(commandName)) {
            onCommand(event, command);
            return true;
        }
        return false;
    }
}
