package fr.nkri.faction.commands.subcommands;

import fr.nkri.faction.commands.SubCommand;
import fr.nkri.faction.managers.FactionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FactionLeaveCommand extends SubCommand {
    @Override
    public String getSubName() {
        return "leave";
    }

    @Override
    public String getLore() {
        return "Permet de quitter votre faction !";
    }

    @Override
    public String getSyntax() {
        return "/faction <leave>";
    }

    @Override
    public void perform(Player player, String[] args, FactionManager manager) {

        if(!manager.hasFPlayer(player)){
            player.sendMessage(ChatColor.RED + "Vous devez rejoignre une faction pour faire ceci !");
        }

        manager.leaveFaction(player, manager.getFaction(manager.getFPlayer(player).getUUIDFaction()));

    }
}
