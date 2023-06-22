package fr.nkri.faction.managers.json;

import fr.nkri.faction.FactionPlugin;
import fr.nkri.faction.models.FPlayer;
import fr.nkri.faction.models.Faction;
import fr.nkri.faction.utils.FileUtils;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class JsonManager {

    private final File saveDirFactions, saveDirPlayers, saveDirClaims;
    private final FactionPlugin main;

    public JsonManager(FactionPlugin main) {
        this.main = main;
        this.saveDirFactions = new File(main.getDataFolder(), "/datas/factions/");//Dossier ou seront les Json des factions
        this.saveDirPlayers = new File(main.getDataFolder(), "/datas/players/");//Dossier ou seront les Json des Fplayer
        this.saveDirClaims = new File(main.getDataFolder(), "/datas/claims/");//Dossier ou seront les Json des Claims
    }

    public void load() {
        loadFactions();
        loadPlayers();
        loadClaims();
    }

    //Récupération des données des factions
    private void loadFactions() {
        if (!saveDirFactions.exists()) {
            return;
        }

        for (File file : saveDirFactions.listFiles()) {
            if (file.isFile()) {
                String json = FileUtils.loadContent(file);
                Faction faction = main.getJsonSerializer().deserialize(json, Faction.class);

                if (faction != null) {
                    main.getFactionManager().createFaction(faction.getPlayerOwner(), faction.getName(), faction.getDescription(), faction.getUUID());
                }
            }
        }
    }

    //Récupération des données des fplayers
    private void loadPlayers() {
        if (!saveDirPlayers.exists()) {
            return;
        }

        for (File file : saveDirPlayers.listFiles()) {
            if (file.isFile()) {
                String json = FileUtils.loadContent(file);
                FPlayer fPlayer = main.getJsonSerializer().deserialize(json, FPlayer.class);

                if (fPlayer != null) {
                    main.getFactionManager().createFPlayer(fPlayer.getName(), fPlayer.getUUIDFaction(), fPlayer.getRole().getRoleEnum());
                }
            }
        }
    }

    //Récupération des données des claims
    private void loadClaims(){
        if(!saveDirClaims.exists()){
            return;
        }

        for(File file : saveDirClaims.listFiles()){
            if(file.isFile()){
                String json = FileUtils.loadContent(file);
                Long chunk = main.getJsonSerializer().deserialize(json, Long.class);
                UUID uuid = UUID.fromString(file.getName().replace(".json", ""));
                main.getFactionManager().setClaim(chunk, uuid);
            }
        }
    }

    public void save() {
        saveFactions();
        savePlayers();
        saveClaims();
    }

    //Sauvegarde des données des factions
    private void saveFactions() {
        for (UUID uuid : main.getFactionManager().getFactionsUUID().keySet()) {
            File fileFaction = new File(saveDirFactions, uuid.toString() + ".json");
            Faction faction = main.getFactionManager().getFaction(uuid);
            String json = main.getJsonSerializer().serialize(faction);
            FileUtils.save(fileFaction, json);
        }
    }

    //Sauvegarde des données des fplayers
    private void savePlayers() {
        for (String name : main.getFactionManager().getFactionPlayers().keySet()) {
            File filePlayer = new File(saveDirPlayers, name + ".json");
            FPlayer fPlayer = main.getFactionManager().getFPlayer(name);
            String json = main.getJsonSerializer().serialize(fPlayer);

            FileUtils.save(filePlayer, json);
        }
    }

    //Récupération des données des claims
    private void saveClaims(){
        for(Map.Entry<Long, UUID> entry : main.getFactionManager().getClaims().entrySet()){
            File fileClaims = new File(saveDirClaims, entry.getValue().toString() + ".json");
            String json = main.getJsonSerializer().serialize(entry.getKey());
            FileUtils.save(fileClaims, json);
        }
    }
}