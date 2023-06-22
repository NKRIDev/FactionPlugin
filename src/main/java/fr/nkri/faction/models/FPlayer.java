package fr.nkri.faction.models;

import com.google.gson.annotations.Expose;

import java.util.UUID;

public class FPlayer {

    //Expose: annotation permettant de prendre en compte le paramètre lors de la sérialisation/désérialisation
    @Expose
    private UUID uuid; //uuid custom du joueur

    @Expose
    private String name;//nom du joueur

    @Expose
    private FRole role;//objet FRole

    @Expose
    private UUID uuidFaction;//uuid de la faction oû est le joueur


    public FPlayer(UUID uuidFaction, UUID uuid, String name, FRole role){
        this.uuidFaction = uuidFaction;
        this.uuid = uuid;
        this.name = name;
        this.role = role;
    }

    public UUID getUUID() {
        return uuid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FRole getRole() {
        return role;
    }

    public UUID getUUIDFaction() {
        return uuidFaction;
    }

    public void setUUIDFaction(UUID uuidFaction) {
        this.uuidFaction = uuidFaction;
    }
}
