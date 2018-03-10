package Items;

import com.badlogic.gdx.graphics.Texture;
import com.sandbox.game.Enums;

/**
 * Created by zliu on 2018-03-03.
 */

public class Weapon extends Item{

    private int minDamage;
    private int maxDamage;
    private int crit;

    private int level;
    private int currentExp;
    private int maxExp;

    public Weapon(String name, String tooltip, Texture texture, int minDamage, int maxDamage, int crit, int price)
    {
        super(price);
        itemType = Enums.itemType.Weapon;
        this.name = name;
        this.tooltip = tooltip;
        this.texture = texture;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.crit = crit;
    }

    public void AddExp(int amount)
    {
        currentExp += amount;
        if(currentExp >= maxExp)
        {
            currentExp -= maxExp;
            maxExp += (int)(maxExp * 0.2f);
            level++;
        }
    }
}
