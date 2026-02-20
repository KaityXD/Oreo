package net.amar.oreojava.commands.text.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.amar.oreojava.commands.Categories;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RevokeInvites extends Command {

    public RevokeInvites() {
        this.name = "revokeinvites";
        this.help = "revoke all guild invites";
        this.ownerCommand = true;
        this.aliases = new String[]{"ri"};
        this.category = Categories.owner;
    }
    @Override
    protected void execute(CommandEvent event) {

        event.getGuild().retrieveInvites().queue(invites -> {

            List<AuditableRestAction<Void>> deletions = invites.stream()
                    .filter(in -> !in.getCode().equals("VHdwQFsaGX"))
                    .map(Invite::delete)
                    .toList();

            RestAction.allOf(deletions).queue(
                    success -> event.reply("Deleted " + deletions.size() + " invites."),
                    failure -> event.reply("Something failed while deleting invites.")
            );
        });
    }

}
