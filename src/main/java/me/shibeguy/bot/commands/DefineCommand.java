package me.shibeguy.bot.commands;

import com.jtelegram.api.chat.ChatType;
import com.jtelegram.api.commands.Command;
import com.jtelegram.api.commands.CommandHandler;
import com.jtelegram.api.events.message.TextMessageEvent;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.shibeguy.bot.Info;
import me.shibeguy.bot.handler.BotRegistry;
import org.json.JSONObject;

public class DefineCommand implements CommandHandler {

    private final String commandName = "define";
    private final BotRegistry registry;

    public DefineCommand(BotRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onCommand(TextMessageEvent event, Command command) {
        try {
            if (command.getArgsAsText().equalsIgnoreCase("madaidan") || command.getArgsAsText().equalsIgnoreCase("aidan")) {
                registry.getMain().sendMessage(command, "Definition of madaidan: Some asshat who thinks he's the best.");
                return;
            }

            HttpResponse<String> response = Unirest.get("https://mashape-community-urban-dictionary.p.mashape.com/define?term=" + String.valueOf(command.getArgs()).replace(" ", "+"))
                    .header("X-Mashape-Key", Info.URBAN_KEY)
                    .header("Accept", "text/plain")
                    .asString();
            JSONObject object = new JSONObject(response.getBody());

            if (object.getJSONArray("list").length() == 0) {
                registry.getMain().sendMessage(command, "No definition found for " + command.getArgsAsText() + "!");
                return;
            }

            JSONObject definition = object.getJSONArray("list").getJSONObject(0);
            registry.getMain().sendMessage(command, "Definition of " + command.getArgsAsText() + ": " + definition.getString("definition"));
            registry.getMain().sendMessage(command, definition.getString("example"));
        } catch (UnirestException ex) {
            registry.getMain().sendMessage(command, "Failed to find the definition of " + command.getArgsAsText());
            ex.printStackTrace();
        }
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
