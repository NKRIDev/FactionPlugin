package fr.nkri.faction;

import fr.nkri.faction.commands.CommandManager;
import fr.nkri.faction.events.PlayerListeners;
import fr.nkri.faction.managers.FactionManager;
import fr.nkri.faction.managers.json.JsonManager;
import fr.nkri.faction.managers.json.JsonSerializer;
import fr.nkri.faction.objects.Faction;
import org.bukkit.Chunk;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FactionPlugin extends JavaPlugin {

    private static FactionPlugin instance;
    private FactionManager factionManager;

    private JsonSerializer jsonSerializer;
    private JsonManager jsonManager;

    @Override
    public void onEnable() {
        instance = this;

        factionManager = new FactionManager();

        loadJson();

        getCommand("faction").setExecutor(new CommandManager(this));
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
    }

    @Override
    public void onDisable() {
        jsonManager.save();
    }

    private void loadJson(){
        jsonSerializer = new JsonSerializer();
        jsonManager = new JsonManager(this);
        jsonManager.load();
    }

    public static FactionPlugin getInstance() {
        return instance;
    }

    public FactionManager getFactionManager() {
        return factionManager;
    }

    public JsonSerializer getJsonSerializer() {
        return jsonSerializer;
    }
}
