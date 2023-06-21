package fr.nkri.faction.commands.subcommands.invite;

import fr.nkri.faction.FactionPlugin;
import fr.nkri.faction.commands.SubCommand;
import fr.nkri.faction.managers.FactionManager;
import fr.nkri.faction.objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FactionDenyCommand extends SubCommand {
    @Override
    public String getSubName() {
        return "deny";
    }

    @Override
    public String getLore() {
        return "Permet de refuser l'invite à rejoindre une faction uniquement si vous avez était invité !";
    }

    @Override
    public String getSyntax() {
        return "/faction <deny> <nom>";
    }

    @Override
    public void perform(Player player, String[] args, FactionManager manager) {

        if(args.length > 1) {
            final String name = args[1];
            final Faction faction = manager.getFaction(name);

            if(faction != null){
                if(manager.getPlayer(player) != null){
                    final Faction factionDeny = manager.getFaction(manager.getPlayer(player).getUuidFaction());

                    player.sendMessage(ChatColor.RED + "Vous venez de refusé l'invitation à rejoignre la faction " + factionDeny.getName() + " !");
                    manager.removeRequest(player);
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
