package net.amar.oreojava.commands.text.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.amar.oreojava.Oreo;
import net.amar.oreojava.Util;
import net.amar.oreojava.commands.Categories;
import net.dv8tion.jda.api.entities.Guild;

public class LeaveServers extends Command {

    public LeaveServers() {
        this.name = "leave";
        this.help = "leave all guilds";
        this.ownerCommand = true;
        this.category = Categories.owner;
    }
    @Override
    protected void execute(CommandEvent event) {
        StringBuilder sb = new StringBuilder();
        for (Guild g : Oreo.getJDA().getGuilds()) {
            if (g.getId().equals(Util.serverId()) ||
                g.getId().equals(Util.serverId2()))
                continue;
            sb.append(g.getName()).append("\n");
            g.leave().queue();
        }
        event.replySuccess("Left guilds:\n %s".formatted(sb));
    }
}
