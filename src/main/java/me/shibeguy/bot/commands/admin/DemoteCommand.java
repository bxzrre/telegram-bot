package me.shibeguy.bot.commands.admin;

import com.jtelegram.api.chat.Chat;
import com.jtelegram.api.chat.ChatMemberStatus;
import com.jtelegram.api.chat.id.ChatId;
import com.jtelegram.api.chat.id.StringChatId;
import com.jtelegram.api.commands.Command;
import com.jtelegram.api.commands.CommandHandler;
import com.jtelegram.api.events.message.TextMessageEvent;
import com.jtelegram.api.requests.chat.GetChat;
import com.jtelegram.api.requests.chat.GetChatMember;
import com.jtelegram.api.requests.chat.admin.PromoteChatMember;
import com.jtelegram.api.user.User;
import me.shibeguy.bot.handler.BotRegistry;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class DemoteCommand implements CommandHandler {
    private final BotRegistry registry;
    private final String commandName = "demote";

    public DemoteCommand(BotRegistry registry) {
        this.registry = registry;
    }

    private final String[] SUCCESS = new String[]{
            "YOU ARE FUCKING REVOKE PRIVILEGE SAR???",
            "i AM FUCKING STRIP NAEKDED OF PRIVEILEGE SAR",
            "he is fucking NOT HAVE privilege now sar?",
            "I AM FUCKING DEMOTE STUPDI NEGOR FOR YOU SAR",
            "YOU ARE FUCKING DEMOTE REART SARRRRRR"
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

        registry.getMain().getBot().perform(
                GetChatMember.builder()
                        .chatId(event.getMessage().getChat().getChatId())
                        .userId(command.getSender().getId())
                        .callback(chatMember -> {
                            if (chatMember.getStatus().ordinal() <= ChatMemberStatus.ADMINISTRATOR.ordinal()) {
                                isAdmin.set(true);
                            }

                            if (isAdmin.get()) {
                                User toDemote = event.getMessage().getReplyToMessage().getSender();

                                // Check to see if the user isn't already admin
                                registry.getMain().getBot().perform(
                                        GetChatMember.builder().chatId(event.getMessage().getChat().getChatId()).userId(toDemote.getId()).callback(chatMember1 -> {
                                            if (chatMember1.getStatus().ordinal() >= ChatMemberStatus.ADMINISTRATOR.ordinal()) {
                                                registry.getMain().reply(event, "user is not an admin");
                                            } else {
                                                demote(event.getMessage().getChat(), toDemote.getId());
                                                registry.getMain().reply(event, SUCCESS[random.nextInt(SUCCESS.length - 1)]);
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


    void demote(Chat chat, long userId) {
        registry.getMain().getBot().perform(
                PromoteChatMember.builder()
                        .chatId(chat.getChatId())
                        .userId(userId)
                        .canChangeInfo(false)
                        .canDeleteMessages(false)
                        .canRestrictMembers(false)
                        .canPinMessages(false)
                        .canInviteUsers(false)
                        .canPromoteMembers(false)
                        .errorHandler(e -> {
                            System.out.println("uh oh, something's wrong");
                            e.printStackTrace();
                        }).build()
        );
    }
}
