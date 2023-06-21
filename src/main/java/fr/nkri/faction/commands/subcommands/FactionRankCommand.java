package fr.nkri.faction.commands.subcommands;

import fr.nkri.faction.FactionPlugin;
import fr.nkri.faction.commands.SubCommand;
import fr.nkri.faction.enums.FactionRoleEnum;
import fr.nkri.faction.managers.FactionManager;
import fr.nkri.faction.objects.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FactionRankCommand extends SubCommand {
    @Override
    public String getSubName() {
        return "rank";
    }

    @Override
    public String getLore() {
        return "Permet de rankup un membre de votre faciton !";
    }

    @Override
    public String getSyntax() {
        return "/faction <rank> <player> <leader:moderator:membre:recrue>";
    }

    @Override
    public void perform(Player player, String[] args, FactionManager manager) {
        if(manager.hasFPlayer(player.getName())){
            if(manager.hasPermission(player.getName(), "faction.rank") || player.hasPermission("faction.admin")){

                final String victimName = args[1];
                if(Bukkit.getPlayer(victimName) != null){

                    final Player victimPlayer = Bukkit.getPlayer(victimName);
                    final FPlayer fPlayerVictim = manager.getFPlayer(victimPlayer.getName());
                    final FPlayer fPlayer = manager.getFPlayer(player.getName());

                    if(fPlayerVictim.getRole().getRoleEnum().getPower() < fPlayer.getRole().getRoleEnum().getPower()){ //Vérifie si le pouvoir du role du joueur cible est inférrieur à celui de la personne qui execute la commande

                        switch (args[2].toLowerCase()){
                            case "leader":
                                manager.getFactionByPlayer(fPlayerVictim).setPlayerOwner(victimPlayer.getName());
                                fPlayerVictim.getRole().setRoleEnum(FactionRoleEnum.LEADER);
                                player.sendMessage(ChatColor.GREEN + "Vous venez de mettre le grade de faction: §r" + FactionRoleEnum.LEADER.getPrefix() + "§r§a à " + victimName);
                                break;

                            case "moderator":
                                fPlayerVictim.getRole().setRoleEnum(FactionRoleEnum.MODERATEUR);
                                player.sendMessage(ChatColor.GREEN + "Vous venez de mettre le grade de faction: §r" + FactionRoleEnum.MODERATEUR.getPrefix() + "§r§a à " + victimName);
                                break;

                            case "membre":
                                fPlayerVictim.getRole().setRoleEnum(FactionRoleEnum.MEMBRE);
                                player.sendMessage(ChatColor.GREEN + "Vous venez de mettre le grade de faction: §r" + FactionRoleEnum.MEMBRE.getPrefix() + "§r§a à " + victimName);
                                break;

                            case "recrue":
                                fPlayerVictim.getRole().setRoleEnum(FactionRoleEnum.RECRUE);
                                player.sendMessage(ChatColor.GREEN + "Vous venez de mettre le grade de faction: §r" + FactionRoleEnum.RECRUE.getPrefix() + "§r§a à " + victimName);
                                break;
                            default:
                                player.sendMessage("§a/faction rank <player> <leader:moderator:membre:recrue>");
                                break;
                        }
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Vous ne pouvez pas faire ceci, ce membre est plus gradé que vous !");
                    }
                }
                else{
                    player.sendMessage(ChatColor.RED + "Le joueur n'est pas en ligne !");
                }
            }
            else{
                player.sendMessage(ChatColor.RED + "Vous ne pouvez pas faire ceci !");
            }
        }
        else{
            player.sendMessage(ChatColor.RED + "Vous devez rejoignre une faction pour faire ceci !");
        }
    }
}
