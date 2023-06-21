package fr.nkri.faction.commands.subcommands;

import fr.nkri.faction.FactionPlugin;
import fr.nkri.faction.commands.SubCommand;
import fr.nkri.faction.managers.FactionManager;
import fr.nkri.faction.objects.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FactionPermsCommand extends SubCommand {
    @Override
    public String getSubName() {
        return "perms";
    }

    @Override
    public String getLore() {
        return "Permet d'ajouter une permission de faction !";
    }

    @Override
    public String getSyntax() {
        return "/faction <perms> <player> <add:remove> <permission>";
    }

    @Override
    public void perform(Player player, String[] args, FactionManager manager) {
        if(args.length == 4){
            if(manager.hasFPlayer(player)){
                if(manager.hasPermission(player, "faction.perms") || player.hasPermission("faction.admin")){
                    final String victimString = args[1];
                    if(Bukkit.getPlayer(victimString) != null){
                        final Player victimmPlayer = Bukkit.getPlayer(victimString);
                        final FPlayer fvictimmPlayer = manager.getFPlayer(victimmPlayer);
                        final FPlayer fPlayers = manager.getFPlayer(player);

                        if(fvictimmPlayer.getRole().getRoleEnum().getPower() < fPlayers.getRole().getRoleEnum().getPower()){
                            final String perms = args[3];

                            if(args[2].equalsIgnoreCase("add")){
                                manager.addPermission(victimmPlayer.getName(), perms);
                                player.sendMessage(ChatColor.GREEN + "Vous venez d'ajouter la permission: '" + perms + "' à " + victimString);
                            }
                            else if(args[2].equalsIgnoreCase("remove")){
                                if(perms.equals("faction.rank") || perms.equals("faction.perms")){
                                    player.sendMessage(ChatColor.RED + "Vous ne pouvez pas retirer ces permission de faction !");
                                }
                                manager.removePermission(victimmPlayer.getName(), perms);
                                player.sendMessage(ChatColor.RED + "Vous venez de retirer la permission: '" + perms + "' à " + victimString);
                            }
                            else{
                                player.sendMessage("§a/faction perms <player> <add:remove> <permission>");
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
            else {
                player.sendMessage(ChatColor.RED + "Vous devez rejoignre une faction pour faire ceci !");
            }
        }
        else{
            player.sendMessage("§a/faction perms <player> <add:remove> <permission>");
        }
    }
}
