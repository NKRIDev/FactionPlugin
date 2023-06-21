package fr.nkri.faction.commands;

import fr.nkri.faction.FactionPlugin;
import fr.nkri.faction.managers.FactionManager;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract String getSubName(); //-> /faction <getCommandName>
    public abstract String getLore(); //-> cette commande permet de faire ...
    public abstract String getSyntax(); //-> montre comment utiliser la commande
    public abstract void perform(Player player, String args[], FactionManager factionManager); //-> fonction qui execute le code de la commande


}
