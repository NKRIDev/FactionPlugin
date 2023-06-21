package fr.nkri.faction.commands;

import fr.nkri.faction.FactionPlugin;
import fr.nkri.faction.commands.subcommands.*;
import fr.nkri.faction.commands.subcommands.claims.FactionClaimCommand;
import fr.nkri.faction.commands.subcommands.claims.FactionUnClaimCommand;
import fr.nkri.faction.commands.subcommands.invite.FactionDenyCommand;
import fr.nkri.faction.commands.subcommands.invite.FactionInviteCommand;
import fr.nkri.faction.commands.subcommands.invite.FactionJoinCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private FactionPlugin main;
    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    //Rajouter toute les classes qui extends de SubCommand
    public CommandManager(FactionPlugin main){
        this.main = main;
        subCommands.add(new FactionCreateCommand());
        subCommands.add(new FactionLeaveCommand());
        subCommands.add(new FactionMembersCommand());
        subCommands.add(new FactionDisbandCommand());
        subCommands.add(new FactionInviteCommand());
        subCommands.add(new FactionJoinCommand());
        subCommands.add(new FactionDenyCommand());
        subCommands.add(new FactionRankCommand());
        subCommands.add(new FactionPermsCommand());
        subCommands.add(new FactionClaimCommand());
        subCommands.add(new FactionUnClaimCommand());
    }

    //Commandes
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length > 0){
                getSubCommands().stream().filter(subCommand -> args[0].equalsIgnoreCase(subCommand.getSubName())).forEach(subCommand -> subCommand.perform(player, args, main.getFactionManager()));
            }
            else {
                player.sendMessage("§8§m--------------§r§8[§aFactionPlugin§8]§m--------------");
                player.sendMessage(" ");
                getSubCommands().stream().forEach(subCommand -> player.sendMessage(ChatColor.DARK_GREEN + subCommand.getSyntax() + ": " + ChatColor.GRAY+ subCommand.getLore()));
                player.sendMessage(" ");
                player.sendMessage(ChatColor.GRAY + "Developed by NKRI");
                player.sendMessage("§8§m--------------§r§8[§aFactionPlugin§8]§m--------------");
            }
        }
        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
