package me.shibeguy.bot.commands;

import com.jtelegram.api.commands.Command;
import com.jtelegram.api.commands.CommandHandler;
import com.jtelegram.api.events.message.TextMessageEvent;
import me.shibeguy.bot.handler.BotRegistry;

import java.text.MessageFormat;

public class CopypastaCommand implements CommandHandler {

    private final String[] commands = new String[] {
            "thatsfunny",
            "arch"
    };
    private final BotRegistry registry;

    public CopypastaCommand(BotRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onCommand(TextMessageEvent event, Command command) {
        if (command.getBaseCommand().equalsIgnoreCase("thatsfunny")) {
            String recipient = command.getArgsAsText();
            String message = "That’s funny %1$s - you know, I’ve always thought you were a funny guy. I might have the brains, the looks, and the fame, but I don’t make people laugh like you do.\n" +
                    "\n" +
                    "latiku, priv4te, willow, ajx. Just one look at that emaciated face of yours and all of us are laughing. In fact, I’m laughing right now, just looking at you.\n" +
                    "\n" +
                    "You know, I haven't laughed this hard since I came inside your mom, Sarah, and she made this weird face... \n" +
                    "\n" +
                    "What, you didn't know about that? Yeah, after I saved her from that Indian mugger, I took her for a ride on my 'rocket', if you get what I'm saying. God, she was so keen, %1$s - I've never fucked a broad that was so eager. \n" +
                    "\n" +
                    "I barely had my overalls off when she threw her legs over me and started pumpin' her hoochy-coochy on my little paisan. I tell ya, %1$s, it was like fucking a spring, how she was just hopping up and down on my cock, panting with her tongue out like a dumb mutt. And - and this is the funny part - when I came inside her, %1$sWhen I came, she made this face... It was this stupid fucking smile, %1$s, Like her mind went blank or somethin', and she was just moaning with her mouth hanging open like an idiot. She even drooled, %1$s. Like a dumb fucking dog, or some kinda retard or somethin' - I had her drool in my chest hair, %1$s. Fucking saliva, y'know? It was the funniest thing I'd ever seen, %1$s. I'd never seen that before, a woman just losing her mind like that. It was the most pathetic thing I'd ever seen - what a whore, am I right? I just started to laugh at her, you know? I was just laughing and laughing. And even thinking about it now, I'm still laughing.";

            registry.getMain().reply(event, String.format(message, recipient));
        }
    }

    @Override
    public boolean test(TextMessageEvent event, Command command) {
        for (String cmds : commands) {
            if (command.getBaseCommand().equalsIgnoreCase(cmds)) {
                onCommand(event, command);
                return true;
            }
            return false;
        }
        return false;
    }
}
