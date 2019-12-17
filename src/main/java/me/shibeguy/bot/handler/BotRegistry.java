package me.shibeguy.bot.handler;

import me.shibeguy.bot.Main;
import me.shibeguy.bot.commands.DefineCommand;

public class BotRegistry {

    private final Main main;

    public BotRegistry(Main main) {
        this.main = main;

        main.getTelegramBot().getCommandRegistry().registerCommand(new DefineCommand(this));

    }

    public Main getMain() {
        return main;
    }
}
