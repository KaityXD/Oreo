package net.amar.oreojava.commands.slash.general;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetEmoji extends SlashCommand {

    private final Pattern EMOJI_PATTERN = Pattern.compile("<(a?):\\w+:(\\d+)>");
    public GetEmoji() {
        this.name = "get-emoji";
        this.help = "get the url of an emoji";
        this.contexts = new InteractionContextType[]{
                InteractionContextType.GUILD,
                InteractionContextType.PRIVATE_CHANNEL,
                InteractionContextType.BOT_DM
        };
        this.options.add(new OptionData(OptionType.STRING, "emoji","custom emoji", true));
    }
    @Override
    protected void execute(@NotNull SlashCommandEvent event) {
        String input = event.getOption("emoji").getAsString();
        Matcher matcher = EMOJI_PATTERN.matcher(input);

        if (!matcher.matches()) {
            event.reply("❌ Please provide a **custom Discord emoji**.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        boolean animated = !matcher.group(1).isEmpty();
        String emojiId = matcher.group(2);

        String extension = animated ? "gif" : "png";
        String url = "``https://cdn.discordapp.com/emojis/" + emojiId + "." + extension+"``";

        event.reply(url).queue();
    }
}
