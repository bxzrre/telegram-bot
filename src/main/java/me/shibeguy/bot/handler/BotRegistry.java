package me.shibeguy.bot.handler;

import me.shibeguy.bot.Main;
import me.shibeguy.bot.commands.CopypastaCommand;
import me.shibeguy.bot.commands.DefineCommand;
import me.shibeguy.bot.commands.JokeCommand;
import me.shibeguy.bot.commands.admin.BanCommand;
import me.shibeguy.bot.commands.admin.DemoteCommand;
import me.shibeguy.bot.commands.admin.PromoteCommand;
import me.shibeguy.bot.commands.admin.UnbanCommand;

public class BotRegistry {

    private final Main main;

    public BotRegistry(Main main) {
        this.main = main;

        main.getBot().getCommandRegistry().registerCommand(new DefineCommand(this));
        main.getBot().getCommandRegistry().registerCommand(new PromoteCommand(this));
        main.getBot().getCommandRegistry().registerCommand(new JokeCommand(this));
        main.getBot().getCommandRegistry().registerCommand(new CopypastaCommand(this));
        main.getBot().getCommandRegistry().registerCommand(new DemoteCommand(this));
        main.getBot().getCommandRegistry().registerCommand(new BanCommand(this));
        main.getBot().getCommandRegistry().registerCommand(new UnbanCommand(this));
    }

    public Main getMain() {
        return main;
    }
}
