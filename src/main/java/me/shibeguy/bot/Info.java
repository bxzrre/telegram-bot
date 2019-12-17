package me.shibeguy.bot;

import me.shibeguy.bot.Config;

public class Info {
    private static final Config CONFIG = new Config();

    public static final String BOT_KEY = CONFIG.getValue("bot-token");
    public static final String URBAN_KEY = CONFIG.getValue("urban-token");
}
