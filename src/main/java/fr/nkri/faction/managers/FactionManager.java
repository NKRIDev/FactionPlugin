package fr.nkri.faction.managers;

import fr.nkri.faction.FactionPlugin;
import fr.nkri.faction.enums.FactionRoleEnum;
import fr.nkri.faction.objects.FPlayer;
import fr.nkri.faction.objects.FRole;
import fr.nkri.faction.objects.Faction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class FactionManager {

    private FactionPlugin main;
    private final Set<Faction> factions;
    private final Map<String, Faction> factionsName;
    private final Map<UUID, Faction> factionsUUID;
    private final Map<String, FPlayer> factionPlayers;
    private final Map<Player, FPlayer> requestJoin;
    private final HashMap<Long, UUID> claims;

    public FactionManager() {
        this.main = FactionPlugin.getInstance();
        this.factions = new HashSet<>();
        this.factionsName = new HashMap<>();
        this.factionsUUID = new HashMap<>();
        this.factionPlayers = new HashMap<>();
        this.requestJoin = new HashMap<>();
        this.claims = new HashMap<>();
    }

    /*=============================
                 @Factions
     ==============================*/
     //Permet de créer une faction, avec le nom d'un joueur
    public void createFaction(final String playerOwner, final String name, final String description, final UUID uuid){
        final Faction faction = new Faction(uuid, name, "Aucune.", playerOwner);

        if(description != null){
            faction.setDescription(description);
        }

        addFaction(faction, uuid, name);
        createFPlayer(playerOwner, faction, FactionRoleEnum.LEADER);

        addPermission(playerOwner, "faction.rank");
        addPermission(playerOwner, "faction.perms");
    }

    //Permet de créer une faction, avec l'objet Player
    public void createFaction(final Player playerOwner, final String name, final String description, final UUID uuid){
        final Faction faction = new Faction(uuid, name, "Aucune.", playerOwner.getName());

        if(description != null){
            faction.setDescription(description);
        }

        addFaction(faction, uuid, name);
        createFPlayer(playerOwner.getName(), faction, FactionRoleEnum.LEADER);

        addPermission(playerOwner.getName(), "faction.rank");
        addPermission(playerOwner.getName(), "faction.perms");
    }

    //Rajoute une faction
    public void addFaction(final Faction faction, final UUID uuid, final String name){
        factions.add(faction);
        factionsUUID.put(uuid, faction);
        factionsName.put(name, faction);
    }

    //Retire une faction
    public void removeFaction(final Faction faction){
        factions.remove(faction);
        factionsUUID.remove(faction.getUuid(), faction);
        factionsName.remove(faction.getName(), faction);
    }

    //Permet qu'un joueur quitte une faction
    public void leaveFaction(final Player player, final  Faction faction){
        faction.getMembers().remove(getFPlayer(player.getName()));
        factionPlayers.remove(player.getName());

        player.sendMessage(ChatColor.RED + "Vous venez de quitter la faction: " + faction.getName());
        broadCastMessageFaction(ChatColor.RED + player.getName() + " vient de quitter la faction !", faction);
    }

    //Recupère la liste des membres d'une sous forme de String.
    public List<String> getFactionMembers(final Faction faction){
        if(factionExist(faction)){
            return faction.getMembers().stream().map(fplayer -> fplayer.getName()).collect(Collectors.toList());
        }
        return null;
    }

    //Permet d'envoyer un message à tout les membres d'une faction
    public void broadCastMessageFaction(final String msg, final Faction faction){
        for (Map.Entry<String, FPlayer> entry : factionPlayers.entrySet()) {
            final String playerName = entry.getKey();
            final FPlayer fPlayer = entry.getValue();

            if (faction.getMembers().contains(fPlayer)) {
                if(Bukkit.getPlayer(playerName) != null){
                    Bukkit.getPlayer(playerName).sendMessage(msg);
                }
            }
        }
    }

    //Vérifie si 2 joueurs sont dans la même faction
    public boolean sameFaction(final Player playerOne, final Player playerTwo){
        if(playerOne == null || playerTwo == null){
            return false;
        }

        if(getFPlayer(playerOne).getUuidFaction() == getFPlayer(playerTwo).getUuidFaction()){
            return true;
        }
        return false;
    }

    //Récupère une faction grace à son nom
    public Faction getFaction(final String name){
        if(name == null){
            return null;
        }
        return factionsName.get(name);
    }

    //Récupère une faction grace à son UUID
    public Faction getFaction(final UUID uuid){
        if(uuid == null){
            return null;
        }
        return factionsUUID.get(uuid);
    }

    //Vérifie si le FPlayer à une faction
    public boolean hasFaction(final FPlayer fPlayer){
        return fPlayer.getUuid() != null;
    }

    //Vérifie si la faction exite
    public boolean factionExist(final Faction faction){
        return factionsUUID.containsKey(faction.getUuid());
    }

    /*=============================
                 @FPlayer
     ==============================*/

    //Permet de crée un FPLayer, avec une faciton
    public void createFPlayer(final String player, final Faction faction, final FactionRoleEnum roleEnum){
        if(!hasFPlayer(player)){
            final UUID uuid = UUID.randomUUID();
            final FRole fRole = new FRole(roleEnum);
            final FPlayer fPlayer = new FPlayer(faction.getUuid(), uuid, player, fRole);
            fPlayer.setUuidFaction(faction.getUuid());

            faction.getMembers().add(fPlayer);
            factionPlayers.put(player, fPlayer);
        }
    }

    //Permet de crée un FPLayer, avec l'uuid d'une faction
    public void createFPlayer(final String player, final UUID uuid, final FactionRoleEnum roleEnum){
        if(!hasFPlayer(player)){
            final FRole fRole = new FRole(roleEnum);
            final FPlayer fPlayer = new FPlayer(uuid, uuid, player, fRole);
            fPlayer.setUuidFaction(uuid);

            main.getFactionManager().getFaction(uuid).getMembers().add(fPlayer);
            factionPlayers.put(player, fPlayer);
        }
    }

    //Retourne un Fplayer en fonction du nom d'un joueur
    public FPlayer getFPlayer(final String playerName){
        if(playerName == null){
            return null;
        }
        return factionPlayers.get(playerName);
    }

    //Retourne un FPlayer en fontciont d'un joueur
    public FPlayer getFPlayer(final Player player){
        if(player == null){
            return null;
        }
        return factionPlayers.get(player.getName());
    }

    //Récupére la faction grâce à un FPLayer
    public Faction getFactionByPlayer(final FPlayer fPlayer){
        if (fPlayer == null){
            return null;
        }
        return getFaction(fPlayer.getUuidFaction());
    }

    //Vérifie si le joueur à un FPLayer grace à son pseudo
    public boolean hasFPlayer(final String playerName){
        return factionPlayers.containsKey(playerName);
    }

    //Vérifie si le joueur à un FPlayer
    public boolean hasFPlayer(final Player player){
        return factionPlayers.containsKey(player.getName());
    }

    /*=============================
         @Invite dans une faction
     ==============================*/

    //Récupère le fplayer d'un joueur
    public FPlayer getPlayer(final Player player){
        return requestJoin.get(player);
    }

    //Vérifie si le fplayer à une demande rejoindre une faction
    public boolean hasRequest(final FPlayer fPlayer){
        return requestJoin.containsValue(fPlayer);
    }

    //Rajout d'une requete d'invitation
    public void addRequest(final Player player, final FPlayer fPlayer){
        requestJoin.put(player, fPlayer);
    }

    //Retirer la requete
    public void removeRequest(final Player player){
        requestJoin.remove(player);
    }

        /*=============================
                 @Permissions
     ==============================*/

    //Récupére le FPLayer d'un joueur grâce à son pseudo et verifie si il a une permission
    public boolean hasPermission(final String playerName, String permissions){
        return getFPlayer(playerName).getRole().getPermissions().contains(permissions);
    }

    //Récupére le FPLayer d'un joueur et verifie si il a une permission
    public boolean hasPermission(final Player player, String permissions){
        return getFPlayer(player.getName()).getRole().getPermissions().contains(permissions);
    }


    //Rajouter une permission
    public void addPermission(final String playerName, String permissions){
        getFPlayer(playerName).getRole().getPermissions().add(permissions);
    }

    //Retire une permission
    public void removePermission(final String playerName, String permissions){
        getFPlayer(playerName).getRole().getPermissions().remove(permissions);
    }


    /*=============================
                 @Claims
     ==============================*/

    //2 Coordonées qui sont concaténé.
    public long chunkToCoords(Chunk chunk){
        return ((long) chunk.getX()) << 32 | ((long) chunk.getZ());
    }

    //Permet de récupérer l'uuid de la faction dans un chunk, si c'est null personne à claim le chunk.
    public UUID getClaim(Chunk chunk){
        return claims.get(chunkToCoords(chunk));
    }

    //Rajoute un claim avec un chunk.
    public void setClaim(Chunk chunk, UUID uuid){
        claims.put(chunkToCoords(chunk), uuid);
    }

    //Rajoute un claim avec un le format concaténé. (utiliser lors de la désérialisation)
    public void setClaim(Long chunk, UUID uuid){
        claims.put(chunk, uuid);
    }

    //Retirer un claim
    public UUID removeClaim(Chunk chunk){//retire un claim !
        return claims.remove(chunkToCoords(chunk));
    }

    /*=============================
                 @Getter
     ==============================*/

    public Map<UUID, Faction> getFactionsUUID() {
        return factionsUUID;
    }

    public Map<String, FPlayer> getFactionPlayers() {
        return factionPlayers;
    }

    public HashMap<Long, UUID> getClaims() {
        return claims;
    }

    public Map<Player, FPlayer> getRequestJoin() {
        return requestJoin;
    }
}
