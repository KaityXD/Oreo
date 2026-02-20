package net.amar.oreojava.commands.slash.staff;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.amar.oreojava.Oreo;
import net.amar.oreojava.commands.Categories;
import net.amar.oreojava.db.tables.Case;
import net.amar.oreojava.handlers.Verdict;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class SupportbanSlash extends SlashCommand {

    public SupportbanSlash() {
        this.name = "supportban";
        this.help = "ban someone from support channels";
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
        Member mod = event.getMember();
        Member u = event.getOption("user").getAsMember();
        String reason = event.getOption("reason").getAsString();

        assert mod != null;
        assert u != null;

        if (mod.canInteract(u) && event.getGuild()!=null && Oreo.getSupportbanRole()!=null) {
            event.getGuild().addRoleToMember(u, Oreo.getSupportbanRole()).queue(s -> {
                Case c = new Case(
                        u.getUser().getId(),
                        u.getUser().getName(),
                        mod.getUser().getId(),
                        mod.getUser().getName(),
                        "SUPPORT BAN",
                        reason,
                        "",
                        true
                );
                Verdict.buildVerdict(c, Oreo.getVerdictChannel(), u.getUser(), null);
                event.replyFormat("Support banned **%s** for *%s*", u.getEffectiveName(), reason).queue();
            }
            ,f -> event.replyFormat("Something went wrong\n[%s]",f.getMessage()).queue());
        } else {
            event.reply("You can't punish a member higher than you").queue();
        }
    }
}
