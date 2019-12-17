package me.shibeguy.bot.commands.admin;

import com.jtelegram.api.chat.Chat;
import com.jtelegram.api.chat.ChatMember;
import com.jtelegram.api.chat.ChatMemberStatus;
import com.jtelegram.api.chat.id.ChatId;
import com.jtelegram.api.commands.Command;
import com.jtelegram.api.commands.CommandHandler;
import com.jtelegram.api.events.message.TextMessageEvent;
import com.jtelegram.api.ex.TelegramException;
import com.jtelegram.api.message.Message;
import com.jtelegram.api.requests.chat.GetChat;
import com.jtelegram.api.requests.chat.GetChatMember;
import com.jtelegram.api.requests.chat.admin.PromoteChatMember;
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

    private final String[] SUCCESS = new String[]{
            "YOU ARE FUCKING HAVE PRIVILEGE SAR???",
            "i aM FUCKING GRANT PRIVILEGE TO SAR",
            "he is fucking have privilege now sar?",
            "I AM FUCKING PROMOTE STUPD NEGOR FOR YOU SAR",
            "YOU ARE FUCKING PROMOTE REART SARRRRRR"
    };

    private final String[] FAILED = new String[]{
            "You are think im stupid like negor sar  ?  ? ??",
            "YOU ARE FUCKING NOT ADMIN?!?!?!",
            "you are try to promote but you are not admin sar?",
            "YOU THINK IM FUCKING IDIOATWE SAR? ?,"
    };

    @Override
    public void onCommand(TextMessageEvent event, Command command) {
        AtomicBoolean isAdmin = new AtomicBoolean(false);
        SecureRandom random = new SecureRandom();

        registry.getMain().getTelegramBot().perform(
                GetChatMember.builder()
                        .chatId(event.getMessage().getChat().getChatId())
                        .userId(command.getSender().getId())
                        .callback(chatMember -> {
                            if (chatMember.getStatus().ordinal() <= ChatMemberStatus.ADMINISTRATOR.ordinal() || chatMember.getStatus().ordinal() <= ChatMemberStatus.CREATOR.ordinal()) {
                                isAdmin.set(true);
                            }

                            if (isAdmin.get()) {
                                User toPromote = event.getMessage().getReplyToMessage().getSender();


                                // Check to see if the user is already admin
                                registry.getMain().getTelegramBot().perform(
                                        GetChatMember.builder().chatId(event.getMessage().getChat().getChatId()).userId(toPromote.getId()).callback(chatMember1 -> {
                                            if (chatMember1.getStatus().ordinal() <= ChatMemberStatus.ADMINISTRATOR.ordinal()) {
                                                registry.getMain().reply(event, "user is already an admin");
                                                return;
                                            } else {
                                                promote(event.getMessage().getChat(), toPromote);
                                                registry.getMain().reply(event, SUCCESS[random.nextInt(SUCCESS.length - 1)]);
                                                return;
                                            }
                                        }).build());
                            } else {
                                registry.getMain().reply(event, FAILED[random.nextInt(FAILED.length - 1)]);
                            }
                        }).build());
    }

    @Override
    public boolean test(TextMessageEvent event, Command command) {
        if (command.getBaseCommand().equalsIgnoreCase(commandName)) {
            onCommand(event, command);
            return true;
        }
        return false;
    }

    void promote(Chat chat, User user) {
        registry.getMain().getTelegramBot().perform(
                PromoteChatMember.builder()
                        .chatId(chat.getChatId())
                        .userId(user.getId())
                        .canChangeInfo(true)
                        .canDeleteMessages(true)
                        .canRestrictMembers(true)
                        .canPinMessages(true)
                        .canInviteUsers(true)
                        .canPromoteMembers(true)
                        .errorHandler(e -> {
                            System.out.println("uh oh, something's wrong");
                            e.printStackTrace();
                        }).build()
        );
    }
}

