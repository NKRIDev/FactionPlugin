package fr.nkri.faction.objects;

import com.google.gson.annotations.Expose;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Faction {

    //Expose: annotation permettant de prendre en compte le paramètre lors de la sérialisation/désérialisation
    @Expose
    private UUID id; //id de la faction

    @Expose
    private String name;//nom de la faction

    @Expose
    private String description;//description de la faction (return null si il n'y en a pas)

    @Expose
    private String playerOwner;//Nom du leader de la faction

    @Expose
    private Set<FPlayer> members;//Liste des membres sous l'objet FPlayer


    public Faction(UUID id, String name, String description, String playerOwner){
        this.id = id;
        this.name = name;
        this.description = description;
        this.playerOwner = playerOwner;
        this.members = new HashSet<>();
    }


    public UUID getUuid() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlayerOwner() {
        return playerOwner;
    }

    public void setPlayerOwner(String playerOwner) {
        this.playerOwner = playerOwner;
    }

    public Set<FPlayer> getMembers() {
        return members;
    }

}
