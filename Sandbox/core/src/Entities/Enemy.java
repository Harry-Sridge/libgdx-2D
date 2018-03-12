package Entities;

import Box2D.Box2DHelper;
import Box2D.Box2DWorld;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.sandbox.game.Enums;

/**
 * Created by Southridge on 2018-03-10.
 */

public class Enemy extends Entity{

    String enemyName;
    String enemyDescription;

    int maxHealth;
    int health;
    int minGold;
    int maxGold;

    private Texture front, back, left, right;
    private Entity player;

    public Enemy(Vector3 pos, Box2DWorld box2D, Texture f, Texture b, Texture l, Texture r)
    {
        type = Enums.entityType.Enemy;
        width = 5;
        height = 5;
        this.pos = pos;

        front = f;
        back = b;
        left = l;
        right = r;
        texture = front;

        speed = 2f;

        body = Box2DHelper.CreateBody(box2D.world, width, height/2, 0, 0, pos, BodyDef.BodyType.DynamicBody);
        sensor = Box2DHelper.CreateSensor(box2D.world, width, height/2, 0, 0, pos, BodyDef.BodyType.DynamicBody);
        hashcode = sensor.getFixtureList().get(0).hashCode();
    }

    public void start()
    {

    }
}
