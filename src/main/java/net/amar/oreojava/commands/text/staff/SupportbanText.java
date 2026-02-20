package net.amar.oreojava.commands.text.staff;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.amar.oreojava.Oreo;
import net.amar.oreojava.commands.Categories;
import net.amar.oreojava.db.tables.Case;
import net.amar.oreojava.handlers.Verdict;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.InteractionContextType;

public class SupportbanText extends Command {

    public SupportbanText() {
        this.name = "supportban";
        this.help = "ban someone from support channels";
        this.aliases = new String[]{"sp"};
        this.category = Categories.staff;
        this.contexts = new InteractionContextType[]{
                InteractionContextType.GUILD
        };
        this.userPermissions = new Permission[] {
                Permission.BAN_MEMBERS
        };
        this.arguments = "<@user> [reason]";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split("\\s+", 2);

        if (args.length < 2) {
            event.replyError("Please provide all the arguments ``%s``".formatted(this.arguments));
            return;
        }

        String uid = args[0].replaceAll("\\D", "");
        String reason = args[1];
        Member mod = event.getMember();

        if (event.getGuild()!=null && Oreo.getSupportbanRole()!=null) {
            event.getGuild().retrieveMemberById(uid).queue(s -> {
                if (mod.canInteract(s)) {
                    event.getGuild().addRoleToMember(s, Oreo.getSupportbanRole()).queue(
                            ss -> {
                                Case c = new Case(
                                        s.getUser().getId(),
                                        s.getUser().getName(),
                                        mod.getUser().getId(),
                                        mod.getUser().getName(),
                                        "SUPPORT BAN",
                                        reason,
                                        "",
                                        true
                                );
                                Verdict.buildVerdict(c, Oreo.getVerdictChannel(), s.getUser(), null);
                                event.replySuccess("Support banned *%s* for **%s**".formatted(s.getEffectiveName(), reason));
                            }, f ->  event.replyError("Something went wrong\n[%s]".formatted(f.getMessage()))
                    );
                } else {
                    event.replyError("You can't punish a member higher than you");
                }
            } , f -> event.replyError("Something went wrong\n[%s]".formatted(f.getMessage())));
        }
    }
}
