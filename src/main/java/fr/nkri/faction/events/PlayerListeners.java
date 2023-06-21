package fr.nkri.faction.events;

import fr.nkri.faction.FactionPlugin;
import fr.nkri.faction.enums.FactionRoleEnum;
import fr.nkri.faction.objects.FPlayer;
import fr.nkri.faction.objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.UUID;

public class PlayerListeners implements Listener {
    private FactionPlugin main;
    public PlayerListeners(FactionPlugin main) {
        this.main =main;
    }


    //Configuration du Chat
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        final Player player = e.getPlayer();

        if(main.getFactionManager().hasFPlayer(player.getName())){
            final FPlayer fPlayer = main.getFactionManager().getFPlayer(player.getName());
            String prefix = fPlayer.getRole().getRoleEnum().getPrefix();
            String factionName = main.getFactionManager().getFaction(fPlayer.getUuidFaction()).getName();
            String displayName = player.getDisplayName();
            String message = e.getMessage();

            String format = String.format("§7[§r%s §a%s§7]§r %s§r§8»§7%s", prefix, factionName, displayName, message);
            e.setFormat(format);
        }
    }

    //Permet que le joueur A et B, si ils sont dans la même faction, ils ne peuvent PAS se tapper
    @EventHandler
    public void onPvp(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof  Player){
            if(e.getEntity() instanceof Player){
                Player playerOne = (Player) e.getDamager();
                Player playerTwo = (Player) e.getEntity();

                if(main.getFactionManager().sameFaction(playerOne, playerTwo)){
                    playerOne.sendMessage("§cCe joueur est dans votre faction vous ne pouvez pas le tapper !");
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onClaimLoad(ChunkLoadEvent e){
        if(main.getFactionManager().getClaim(e.getChunk()) != null){//Vérifier siil y a bien le claim
            main.getFactionManager().setClaim(e.getChunk(), main.getFactionManager().getClaim(e.getChunk()));
        }
    }

    @EventHandler
    public void onClaimUnLod(ChunkUnloadEvent e){
        final UUID uuid = main.getFactionManager().removeClaim(e.getChunk()); //si l'uuid il existe, alors c'est que une faciton à claim le chunk
        if(uuid != null){
            main.getFactionManager().setClaim(e.getChunk(), uuid);
        }
    }

    //Empeche la pose de block dans un claim si le joueur n'est pas dans la faction et n'a pas + de membre
    @EventHandler
    public void onPoseBlockInClaim(BlockPlaceEvent e){
        final Player player = e.getPlayer();
        final UUID uuid = main.getFactionManager().getClaim(player.getLocation().getChunk());
        final Faction faction = main.getFactionManager().getFaction(uuid);

        if(uuid != null){ //Vérifie si il y a un claim

            if(!main.getFactionManager().hasFPlayer(player)){
                player.sendMessage(ChatColor.RED + "Vous n'êtes pas dans la faction !");
                e.setCancelled(true);
                return;
            }

            final FPlayer fPlayer = main.getFactionManager().getFPlayer(player);

            if(fPlayer.getUuidFaction() != faction.getUuid()){//Vérfie la faction du joueur
                player.sendMessage(ChatColor.RED + "Vous n'êtes pas dans la faction !");
                e.setCancelled(true);
                return;
            }

            if(fPlayer.getRole().getRoleEnum().getPower() < FactionRoleEnum.MEMBRE.getPower()){
                player.sendMessage(ChatColor.RED + "Vous devez être membre pour faire ceci !");
                e.setCancelled(true);
            }
        }
    }

    //Empeche la casse de block dans un claim si le joueur n'est pas dans la faction et n'a pas + de membre
    @EventHandler
    public void onBreakBlockInClaim(BlockBreakEvent e){
        final Player player = e.getPlayer();
        final UUID uuid = main.getFactionManager().getClaim(player.getLocation().getChunk());
        final Faction faction = main.getFactionManager().getFaction(uuid);

        if(uuid != null){ //Vérifie si il y a un claim

            if(!main.getFactionManager().hasFPlayer(player)){
                player.sendMessage(ChatColor.RED + "Vous n'êtes pas dans la faction !");
                e.setCancelled(true);
                return;
            }

            final FPlayer fPlayer = main.getFactionManager().getFPlayer(player);

            if(fPlayer.getUuidFaction() != faction.getUuid()){//Vérfie la faction du joueur
                player.sendMessage(ChatColor.RED + "Vous n'êtes pas dans la faction !");
                e.setCancelled(true);
                return;
            }

            if(fPlayer.getRole().getRoleEnum().getPower() < FactionRoleEnum.MEMBRE.getPower()){
                player.sendMessage(ChatColor.RED + "Vous devez être membre pour faire ceci !");
                e.setCancelled(true);
            }
        }
    }

    //Empeche l'interaction dans un claim si le joueur n'est pas dans la faction et n'a pas + de membre
    @EventHandler
    public void onUseBlockInClaim(PlayerInteractEvent e){
        final Player player = e.getPlayer();
        final UUID uuid = main.getFactionManager().getClaim(player.getLocation().getChunk());
        final Faction faction = main.getFactionManager().getFaction(uuid);

        if(uuid != null){ //Vérifie si il y a un claim

            if(!main.getFactionManager().hasFPlayer(player)){
                player.sendMessage(ChatColor.RED + "Vous n'êtes pas dans la faction !");
                e.setCancelled(true);
                return;
            }

            final FPlayer fPlayer = main.getFactionManager().getFPlayer(player);

            if(fPlayer.getUuidFaction() != faction.getUuid()){//Vérfie la faction du joueur
                player.sendMessage(ChatColor.RED + "Vous n'êtes pas dans la faction !");
                e.setCancelled(true);
                return;
            }

            if(fPlayer.getRole().getRoleEnum().getPower() < FactionRoleEnum.MEMBRE.getPower()){
                player.sendMessage(ChatColor.RED + "Vous devez être membre pour faire ceci !");
                e.setCancelled(true);
            }
        }
    }

    //@EventHandler
    //public void onEntrerClaim(PlayerMoveEvent e){
      //  if (e.getFrom().getChunk() != e.getTo().getChunk()) {
         //   final Chunk chunk = e.getTo().getChunk();
           // final UUID uuid = main.getFactionManager().getClaim(chunk);
            //if(uuid != null){
               // e.getPlayer().sendMessage("Vous entrer dans le claim de : " + main.getFactionManager().getFaction(uuid).getName());
            //}
        //}
    //}
}
