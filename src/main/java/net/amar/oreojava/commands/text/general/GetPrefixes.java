package net.amar.oreojava.commands.text.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.amar.oreojava.Oreo;
import net.amar.oreojava.commands.Categories;

public class GetPrefixes extends Command {

    public GetPrefixes() {
        this.name = "prefixes";
        this.help = "get all available bot prefixes";
        this.category = Categories.general;
        this.aliases = new String[]{"p"};
    }
    @Override
    protected void execute(CommandEvent event) {
     event.replySuccess("["+String.join(", ",Oreo.prefixes)+"]");
    }
}
