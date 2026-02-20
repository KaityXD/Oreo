package net.amar.oreojava.commands.slash.staff;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.amar.oreojava.Oreo;
import net.amar.oreojava.commands.Categories;
import net.amar.oreojava.db.tables.Case;
import net.amar.oreojava.handlers.Verdict;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class UnbanSlash extends SlashCommand {

    public UnbanSlash() {
        this.name = "unban";
        this.help = "unban bad user";
        this.category = Categories.staff;
        this.contexts = new InteractionContextType[]{
                InteractionContextType.GUILD
        };
        this.userPermissions = new Permission[] {
                Permission.BAN_MEMBERS
        };

        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.USER, "user", "user to ban", true));
        options.add(new OptionData(OptionType.STRING, "reason", "why ban this user", true));
        this.options = options;
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        User u = event.getOption("user").getAsUser();
        String reason = event.getOption("reason").getAsString();

        event.getGuild().unban(u).reason(reason).queue(success -> {
            Case c = new Case(
                    u.getId(),
                    u.getName(),
                    event.getUser().getId(),
                    event.getUser().getName(),
                    "UNBAN",
                    reason,
                    "",
                    true
            );
            Verdict.buildVerdict(c, Oreo.getVerdictChannel(), u, null);
            event.replyFormat("Unbanned **%s**",u.getName()).queue();
        }
        ,failure -> event.replyFormat("Failed to unban\n[%s]",failure.getMessage()).queue()
        );
    }
}
