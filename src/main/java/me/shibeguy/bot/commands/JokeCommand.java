package me.shibeguy.bot.commands;

import com.jtelegram.api.commands.Command;
import com.jtelegram.api.commands.CommandHandler;
import com.jtelegram.api.events.message.TextMessageEvent;
import me.shibeguy.bot.handler.BotRegistry;

import java.security.SecureRandom;

public class JokeCommand implements CommandHandler {

    private final String commandName = "joke";
    private final BotRegistry registry;

    private final SecureRandom random = new SecureRandom();

    private final String[] JOKES = new String[] {
            "madaidan",
            "you"
    };

    public JokeCommand(BotRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onCommand(TextMessageEvent event, Command command) {
        registry.getMain().reply(event, JOKES[random.nextInt(JOKES.length - 1)]);
    }
}
