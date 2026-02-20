package net.amar.oreojava.events;

import net.amar.oreojava.Log;
import net.amar.oreojava.Oreo;
import net.amar.oreojava.db.tables.Case;
import net.amar.oreojava.handlers.Verdict;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class Honeypot extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (Oreo.getForbiddenChannel()==null) {
            Log.warn("honeypot isn't set");
            return;
        }
        String channelId = event.getChannel().getId();

        if (channelId.equals(Oreo.getForbiddenChannel().getId())) {
            event.getMessage().delete().reason("honeypot").queue();
            User u = event.getAuthor();
            Case c = new Case(
                    u.getId(),
                    u.getName(),
                    event.getJDA().getSelfUser().getId(),
                    event.getJDA().getSelfUser().getName(),
                    "KICK",
                    "Fell in the honeypot",
                    "",
                    true);
            if (Verdict.buildVerdict(c, Oreo.getVerdictChannel(), u, null))
                Log.info(u.getName()+" tried to send a message in forbidden channel");
            event.getGuild().ban(u,1, TimeUnit.DAYS)
                    .queue(success -> event.getGuild().unban(u).queue());

        }
    }
}
