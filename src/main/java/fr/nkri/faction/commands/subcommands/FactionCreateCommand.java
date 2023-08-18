package fr.nkri.faction.commands.subcommands;

import fr.nkri.faction.commands.SubCommand;
import fr.nkri.faction.managers.FactionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.StringJoiner;
import java.util.UUID;

public class FactionCreateCommand extends SubCommand {

    @Override
    public String getSubName() {
        return "create";
    }

    @Override
    public String getLore() {
        return "Permet de crée votre faction avec un nom et une description, la description n'est pas obligatoire.";
    }

    @Override
    public String getSyntax() {
        return "/faction <create> <nom> <descrition>";
    }

    @Override
    public void perform(Player player, String[] args, FactionManager manager) {

        String name;

        if(args.length > 1){
            name = args[1];
        }

        if(manager.getFaction(name) == null){
            if(manager.getFPlayer(player) == null){

                if(args.length == 2){

                    manager.createFaction(player, name, null, UUID.randomUUID());
                    player.sendMessage(ChatColor.GREEN + "Vous venez de crée la faction: " + ChatColor.GREEN + name);
                }
                else if(args.length > 3){

                    final StringBuilder lores = new StringBuilder();
                    final StringJoiner joiner = new StringJoiner(", ");

                    for (int i = 2; i < args.length; i++) {
                        joiner.add(args[i]);
                    }
                    lores.append(joiner.toString());

                    manager.createFaction(player, name, lores.toString(), UUID.randomUUID());
                    player.sendMessage(ChatColor.GREEN + "Vous venez de crée la faction: " + ChatColor.GREEN + name);
                }
                else{
                    player.sendMessage(getSyntax());
                }
            }
            else{
                player.sendMessage(ChatColor.RED + "Vous avez déjà une faction !");
            }
        }
        else{
            player.sendMessage(ChatColor.RED + "Ce nom est déjà utilisé !");
        }
    }
}
