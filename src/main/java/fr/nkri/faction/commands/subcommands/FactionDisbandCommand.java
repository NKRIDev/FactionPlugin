package fr.nkri.faction.commands.subcommands;

import fr.nkri.faction.commands.SubCommand;
import fr.nkri.faction.managers.FactionManager;
import fr.nkri.faction.models.Faction;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FactionDisbandCommand extends SubCommand {
    @Override
    public String getSubName() {
        return "disband";
    }

    @Override
    public String getLore() {
        return "Permet de supprimer/disband une faction !";
    }

    @Override
    public String getSyntax() {
        return "/faction <disband> <nom>";
    }

    @Override
    public void perform(Player player, String[] args, FactionManager manager) {

        if(args.length > 1){
            String name = args[1];
            Faction faction = manager.getFaction(name);

            if(faction != null){
                if(!player.hasPermission("faction.admin") || !faction.getPlayerOwner().equals(player.getUniqueId())){
                    player.sendMessage(ChatColor.RED + "Vous ne pouvez pas faire cela !");
                }

                manager.broadCastMessageFaction(ChatColor.RED + "Votre faction vient d'être supprimer !", faction);
                manager.removeFaction(faction);
                player.sendMessage(ChatColor.RED + "Vous venez de supprimer votre faction !");
            }
            else{
                player.sendMessage(ChatColor.RED + "Cette faction nexiste pas !");
            }
        }
    }
}
