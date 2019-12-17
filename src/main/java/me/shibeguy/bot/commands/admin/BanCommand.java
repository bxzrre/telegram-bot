package me.shibeguy.bot.commands.admin;

import com.jtelegram.api.commands.Command;
import com.jtelegram.api.commands.CommandHandler;
import com.jtelegram.api.events.message.TextMessageEvent;
import me.shibeguy.bot.handler.BotRegistry;

public class BanCommand implements CommandHandler {

    private final String commandName = "ban";
    private final BotRegistry registry;

    public BanCommand(BotRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onCommand(TextMessageEvent event, Command command) {

    }
}
