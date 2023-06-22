package fr.nkri.faction.models;

import com.google.gson.annotations.Expose;
import fr.nkri.faction.enums.FactionRoleEnum;
import java.util.HashSet;
import java.util.Set;

public class FRole {

    @Expose
    private final Set<String> permissions;//Liste des permissions sous forme de String
    @Expose
    private FactionRoleEnum roleEnum;//Enumération des roles

    public FRole(FactionRoleEnum roleEnum) {
        this(roleEnum, new HashSet<>());
    }

    public FRole(FactionRoleEnum roleEnum, Set<String> permissions) {
        this.roleEnum = roleEnum;
        this.permissions = new HashSet<>(permissions);
    }

    public Set<String> getPermissions() {
        return permissions;
    }


    public FactionRoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(FactionRoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }
}
