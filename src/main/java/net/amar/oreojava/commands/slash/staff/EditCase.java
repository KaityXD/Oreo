package net.amar.oreojava.commands.slash.staff;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.amar.oreojava.Oreo;
import net.amar.oreojava.commands.Categories;
import net.amar.oreojava.db.DBGetter;
import net.amar.oreojava.db.DBUpdater;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class EditCase extends SlashCommand {

    public EditCase() {
        this.name = "edit-case";
        this.help = "change the reason and proof of a case ";
        this.category = Categories.staff;
        this.userPermissions = new Permission[]{
                Permission.BAN_MEMBERS
        };
        this.contexts = new InteractionContextType[]{
                InteractionContextType.GUILD
        };
        this.options.add(new OptionData(OptionType.STRING, "case-id","the id of the desired case", true));
        this.options.add(new OptionData(OptionType.STRING, "reason", "updated reason", true));
        this.options.add(new OptionData(OptionType.ATTACHMENT, "proof", "updated proof",false));
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        String caseId = event.getOption("case-id").getAsString();
        String reason = event.getOption("reason").getAsString();
        Message.Attachment image;

        if (event.getOption("proof")!=null)
            image = event.getOption("proof").getAsAttachment();
        else {
            image = null;
        }

        String messageId = DBGetter.getCaseMessageId(Oreo.getConnection(), caseId);
        if (messageId==null) {
            event.reply("Case not found").setEphemeral(true).queue();
            return;
        }

        try {
            DBUpdater.updateReason(Oreo.getConnection(), Long.parseLong(caseId), reason);
            Oreo.getVerdictChannel().retrieveMessageById(messageId).queue(m -> {
                MessageEmbed me = m.getEmbeds().get(0);
                EmbedBuilder eb = new EmbedBuilder(me).clearFields();

                if (image!=null){
                    eb.setImage(image.getUrl());
                }

                List<MessageEmbed.Field> fields = me.getFields();
                for (MessageEmbed.Field f : fields) {
                    if (f.getName()!=null && f.getName().equals("Reason:"))
                        eb.addField("Reason:", reason, true);
                    else eb.addField(f);
                }
                m.editMessageEmbeds(eb.build()).queue();
                event.replyFormat("Successfully edited case #%s", caseId).queue();
            });
        } catch (Exception e) {
            event.replyFormat("Oppsie :3 [%s]",e).queue();
        }
    }
}
