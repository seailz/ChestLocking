package com.seailz.template.commands.main;

import com.seailz.template.TemplatePlugin;
import com.seailz.template.commands.main.sub.CommandReport;
import games.negative.framework.command.Command;
import games.negative.framework.command.annotation.CommandInfo;
import games.negative.framework.message.Message;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "main",
        playerOnly = true
)
public class CommandMain extends Command {

    String color = "&" + TemplatePlugin.getInstance().getColor().getChar();
    String name = TemplatePlugin.getInstance().getPluginName();
    String author = TemplatePlugin.getInstance().getDeveloper();
    String url;

    public CommandMain() {
        this.addSubCommands(new CommandReport());
        setName(TemplatePlugin.instance.getPluginName().replaceAll(" ", ""));
        if (TemplatePlugin.getInstance().getURL() == null) url = TemplatePlugin.getInstance().getURL();
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (url == null) {
            new Message(
                    color + "&m------------------------",
                    "&f        " + name,
                    "&f  Developed by " + color + author,
                    color + "&m------------------------"
            ).send(sender);
            return;
        }
            new Message(
                    color + "&m------------------------",
                    "&f        " + name,
                    "&f  Developed by " + color + author,
                    color + "     " + url,
                    color + "&m------------------------"
            ).send(sender);
    }
}
