package entity;

/**
 * Created by alaguitard on 27/01/17.
 */
public enum WeaponType {
    SHIELD("shield.png"),
    ATTACK("attack.png");

    public String filename;

    WeaponType(String filename)
    {
        this.filename = filename;
    }
}
