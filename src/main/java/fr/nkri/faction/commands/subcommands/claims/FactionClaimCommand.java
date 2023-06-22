package fr.nkri.faction.commands.subcommands.claims;

import fr.nkri.faction.commands.SubCommand;
import fr.nkri.faction.enums.FactionRoleEnum;
import fr.nkri.faction.managers.FactionManager;
import fr.nkri.faction.models.FPlayer;
import fr.nkri.faction.models.Faction;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FactionClaimCommand extends SubCommand {
    @Override
    public String getSubName() {
        return "claim";
    }

    @Override
    public String getLore() {
        return "Permet de claim un chunk !";
    }

    @Override
    public String getSyntax() {
        return "/faction claim";
    }

    @Override
    public void perform(Player player, String[] args, FactionManager factionManager) {
        final Location loc = player.getLocation();
        final Chunk chunk = loc.getChunk();

        if(factionManager.hasFPlayer(player)){
            final FPlayer fPlayer = factionManager.getFPlayer(player);

            if(factionManager.hasFaction(fPlayer)){
                final Faction faction = factionManager.getFactionByPlayer(fPlayer);

                if(factionManager.hasPermission(player.getName(), "faction.claims.admin") || fPlayer.getRole().getRoleEnum().getPower() >= FactionRoleEnum.MODERATEUR.getPower()){
                    factionManager.setClaim(chunk, faction.getUUID());
                    player.sendMessage(ChatColor.GREEN + "Vous venez de claim ce chunk !");
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
