package net.amar.oreojava.commands.text.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.amar.oreojava.Log;
import net.amar.oreojava.Oreo;
import net.amar.oreojava.commands.Categories;
import net.amar.oreojava.db.DBEraser;

public class EraseData extends Command {

    public EraseData() {
        this.name = "erase";
        this.help = "erase a value from data table";
        this.arguments = "[fieldName]";
        this.ownerCommand = true;
        this.category = Categories.owner;
    }
    @Override
    protected void execute(CommandEvent event) {
        String args = event.getArgs();
        if (args.isEmpty()) {
            event.replyError("Please provide the required arguments: "+this.arguments);
            return;
        }
        if (DBEraser.eraseData(args, Oreo.getConnection())) {
            event.replySuccess("Erased field");
            Log.info("Deleted "+args+" from data table");
        } else {
            event.replyError("Something went wrong, couldn't erase *"+args+"*");
        }
    }
}
