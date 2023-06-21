package fr.nkri.faction.enums;

public enum FactionRoleEnum {

    LEADER(100, "Chef", "§4"),
    MODERATEUR(90, "Modérateur", "§2"),
    MEMBRE(80, "Membre", "§e"),
    RECRUE(70, "Recrue", "§7"),
    ;

    private final int power;//Hiérarchie des rôles, correspondance avec des nombres, le plus haut nombre et le plus haut hiérarchiquement
    private final String name;//Nom du role
    private final String color;//Couleur du role

    FactionRoleEnum(final int power, final String name, final String color){
        this.power = power;
        this.name = name;
        this.color = color;
    }

    public int getPower() {
        return power;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    //Permet d'addition la couleur et le nom (exemple: "§4Chef")
    public String getPrefix(){
        return getColor()+getName();
    }
}
