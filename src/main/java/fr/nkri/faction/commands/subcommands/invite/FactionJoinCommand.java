package fr.nkri.faction.commands.subcommands.invite;

import fr.nkri.faction.commands.SubCommand;
import fr.nkri.faction.enums.FactionRoleEnum;
import fr.nkri.faction.managers.FactionManager;
import fr.nkri.faction.models.Faction;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FactionJoinCommand extends SubCommand {
    @Override
    public String getSubName() {
        return "join";
    }

    @Override
    public String getLore() {
        return "Permet de rejoindre une faction uniquement si vous avez était invité !";
    }

    @Override
    public String getSyntax() {
        return "/faction <join> <nom>";
    }

    @Override
    public void perform(Player player, String[] args, FactionManager manager) {

        if(args.length > 1){
            final String name = args[1];
            final Faction faction = manager.getFaction(name);

            if(faction != null){
                if(manager.getPlayer(player) != null){

                    final Faction factionJoin = manager.getFaction(manager.getPlayer(player).getUUIDFaction());

                    manager.createFPlayer(player.getName(), factionJoin, FactionRoleEnum.RECRUE);
                    manager.removeRequest(player);

                    player.sendMessage(ChatColor.GREEN + "Vous venez de rejoignre la faction " + factionJoin.getName() + " !");
                    manager.broadCastMessageFaction("§6" + player.getName() + " vient de rejoindre la faction, bienvenue !", factionJoin);
                }
                else{
                    player.sendMessage(ChatColor.RED + "Vous n'avez pas d'invitation pour rejoindre une faction !");
                }
            }
            else{
                player.sendMessage(ChatColor.RED + "Cette faction nexiste pas !");
            }
        }
    }
}
