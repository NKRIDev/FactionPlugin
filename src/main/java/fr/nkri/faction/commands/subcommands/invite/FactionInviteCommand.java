package fr.nkri.faction.commands.subcommands.invite;

import fr.nkri.faction.commands.SubCommand;
import fr.nkri.faction.managers.FactionManager;
import fr.nkri.faction.models.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FactionInviteCommand extends SubCommand {
    @Override
    public String getSubName() {
        return "invite";
    }

    @Override
    public String getLore() {
        return "Permet d'inviter un joueur dans votre Faction !";
    }

    @Override
    public String getSyntax() {
        return "/faction <invite> <player>";
    }

    @Override
    public void perform(Player player, String[] args, FactionManager manager) {
        String victim = args[1];
        if(Bukkit.getPlayerExact(victim) != null){
            if(manager.hasFPlayer(player)){
                final Player receiverPlayer = Bukkit.getPlayerExact(victim);
                final FPlayer requesterPlayer = manager.getFPlayer(player);

                if(receiverPlayer != player){
                    if(!manager.hasRequest(requesterPlayer)){

                        player.sendMessage(ChatColor.GREEN + "Vous venez d'envoyé une invitation dans votre faction à " + receiverPlayer.getName());
                        receiverPlayer.sendMessage(ChatColor.GREEN + "Vous venez de recevoir une invitation pour rejoignre la faction: "
                                + ChatColor.YELLOW + manager.getFaction(requesterPlayer.getUUIDFaction()).getName()
                                + ChatColor.GRAY + "/faction join " + manager.getFaction(requesterPlayer.getUUIDFaction()).getName() + ", pour rejoignre ! ou alors /faction deny " + manager.getFaction(requesterPlayer.getUUIDFaction()).getName() + " pour ne pas rejoignre !");

                        manager.addRequest(receiverPlayer, requesterPlayer);
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Vous avez déjà envoyé une demande à ce joueur !");
                    }
                }
                else{
                    player.sendMessage(ChatColor.RED + "Vous ne pouvez vous envoyé un invitation !");
                }
            }
            else{
                player.sendMessage(ChatColor.RED + "Vous devez rejoignre une faction pour faire ceci !");
            }
        }
        else{
            player.sendMessage(ChatColor.RED + "Le joueur n'est pas connecté !");
        }
    }
}
