package Entities;

/**
 * Created by zliu on 2018-02-16.
 */

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.sandbox.game.Asset;
import com.sandbox.game.Control;
import com.sandbox.game.Enums.entityType;
import com.sandbox.game.Inventory;

import Box2D.Box2DHelper;
import Box2D.Box2DWorld;

public class Player extends Entity {

    int health;
    int exp;
    String name;

    private ArrayList<Entity> interactEntities;

    public Player(Vector3 pos, Box2DWorld box2D)
    {
        //initialize entity
        type = entityType.Player;
        width = 5;
        height = 5;
        this.pos = pos;
        texture = Asset.player_front;
        speed = 20f;

        inventory = new Inventory();

        Reset(box2D, pos);
    }

    public void Reset(Box2DWorld box2D, Vector3 pos)
    {
        this.pos.set(pos);
        body = Box2DHelper.CreateBody(box2D.world, width, height/2, 0, 0, pos, BodyDef.BodyType.DynamicBody);
        hashcode = body.getFixtureList().get(0).hashCode();
        interactEntities = new ArrayList<Entity>();
    }
    public void update(Control control)
    {
        xDir = 0;
        yDir = 0;

        if(control.down)
            yDir = -1;
        
        if(control.up)
            yDir = 1;
        
        if(control.left)
            xDir = -1;
        
        if(control.right)
            xDir = 1;
        
        if(control.right && control.up) {
        	xDir = (float) Math.sqrt(0.5);
        	yDir = (float) Math.sqrt(0.5);
        }
        
        if(control.right && control.down) {
        	xDir = (float) Math.sqrt(0.5);
        	yDir = (float) - Math.sqrt(0.5);
        }
        
        if(control.left && control.up) {
        	xDir = (float) - Math.sqrt(0.5);
        	yDir = (float) Math.sqrt(0.5);
        }
        if(control.left && control.down) {
        	xDir = (float) - Math.sqrt(0.5);
        	yDir = (float) - Math.sqrt(0.5);
        }

        //movement
        body.setLinearVelocity(xDir*speed, yDir*speed);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - height/4;

        //Update texture
        if(xDir > 0f) //right
            texture = Asset.player_right;
        if(xDir < 0f) //left
            texture = Asset.player_left;
        if(yDir > 0f)
            texture = Asset.player_back;
        if(yDir < 0f)
            texture = Asset.player_front;

        //if interact key is pressed
        if(control.interact && interactEntities.size() > 0)
        {
            interactEntities.get(0).Interact(this);
            control.interact = false;
        }

        control.interact = false;
    }

    @Override
    public void Collision(Entity e, boolean isInTrigger)
    {
        if(isInTrigger)
            interactEntities.add(e);
        else
            interactEntities.remove(e);
    }
}
