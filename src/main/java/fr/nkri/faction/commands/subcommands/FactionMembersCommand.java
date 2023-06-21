package fr.nkri.faction.commands.subcommands;

import fr.nkri.faction.FactionPlugin;
import fr.nkri.faction.commands.SubCommand;
import fr.nkri.faction.managers.FactionManager;
import fr.nkri.faction.objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FactionMembersCommand extends SubCommand {
    @Override
    public String getSubName() {
        return "members";
    }

    @Override
    public String getLore() {
        return "Permet d'afficher les joueurs dans une faction !";
    }

    @Override
    public String getSyntax() {
        return "/faction <members> <nom>";
    }

    @Override
    public void perform(Player player, String[] args, FactionManager manager) {

        if(args.length > 1){

            String name = args[1];
            Faction faction = manager.getFaction(name);

            if(faction != null){
                StringBuilder members = new StringBuilder();
                for(String str : manager.getFactionMembers(faction)){
                    members.append(str).append(" ");
                }

                player.sendMessage(ChatColor.GREEN + "Membres de la faction "
                        + ChatColor.GOLD
                        + name
                        + ChatColor.GREEN
                        + ": "
                        + ChatColor.YELLOW + members);
            }
            else{
                player.sendMessage(ChatColor.RED + "Cette faction nexiste pas !");
            }
        }
    }
}
