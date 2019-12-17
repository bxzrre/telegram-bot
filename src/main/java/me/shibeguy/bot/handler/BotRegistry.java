package me.shibeguy.bot.handler;

import me.shibeguy.bot.Main;
import me.shibeguy.bot.commands.DefineCommand;
import me.shibeguy.bot.commands.admin.PromoteCommand;

public class BotRegistry {

    private final Main main;

    public BotRegistry(Main main) {
        this.main = main;

        main.getTelegramBot().getCommandRegistry().registerCommand(new DefineCommand(this));
        main.getTelegramBot().getCommandRegistry().registerCommand(new PromoteCommand(this));
    }

    public Main getMain() {
        return main;
    }
}
