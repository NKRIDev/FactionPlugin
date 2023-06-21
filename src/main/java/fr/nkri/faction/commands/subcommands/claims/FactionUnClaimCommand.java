package fr.nkri.faction.commands.subcommands.claims;

import fr.nkri.faction.commands.SubCommand;
import fr.nkri.faction.enums.FactionRoleEnum;
import fr.nkri.faction.managers.FactionManager;
import fr.nkri.faction.objects.FPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FactionUnClaimCommand extends SubCommand {
    @Override
    public String getSubName() {
        return "unclaim";
    }

    @Override
    public String getLore() {
        return "Permet de d'unclaim un chunk !";
    }

    @Override
    public String getSyntax() {
        return "/faction unclaim";
    }

    @Override
    public void perform(Player player, String[] args, FactionManager factionManager) {
        final Location loc = player.getLocation();
        final Chunk chunk = loc.getChunk();
        if(factionManager.hasFPlayer(player)){
            final FPlayer fPlayer = factionManager.getFPlayer(player);

            if(factionManager.hasFaction(fPlayer)){
                if(factionManager.hasPermission(player.getName(), "faction.claims.admin") || fPlayer.getRole().getRoleEnum().getPower() >= FactionRoleEnum.MODERATEUR.getPower()){
                    factionManager.removeClaim(chunk);

                    player.sendMessage(ChatColor.GREEN + "Vous venez de retirer le claim de ce chunk !");
                }
                else{
                    player.sendMessage(ChatColor.RED + "Vous ne pouvez pas faire ceci !");
                }
            }
            else{
                player.sendMessage("Pas de faction !");
            }
        }
        else{
            player.sendMessage("Pas de faction !");
        }
    }
}
