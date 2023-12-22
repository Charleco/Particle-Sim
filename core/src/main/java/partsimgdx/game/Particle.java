package partsimgdx.game;

import com.badlogic.gdx.math.Vector2;

public class Particle {

    public final float mass;
    private Vector2 pos;
    private Vector2 vel;
    private Vector2 accel;
    private World world;
    public Particle(float mass,int y,int x, World world)
    {
        this.pos = new Vector2(x,y);
        this.vel = new Vector2(0f,0f);
        this.accel = new Vector2(0f,0f);
        this.mass = mass;
        this.world =world;
        world.getPartList().add(this);
        world.getGrid()[(int) (pos.y)][(int) (pos.x)] = this;
    }
    public Vector2 getPos()
    {
        return this.pos;
    }
    public Vector2 getVel()
    {
        return this.vel;
    }
    public Vector2 getAccel()
    {
        return this.accel;
    }
    public World getWorld()
    {
        return this.world;
    }
    public void move(float delta)
    {

        if(pos.y-.5<1)
        {
            pos.y=1;
        }
        else if(world.getGrid()[(int) ((pos.y-1))][(int) (pos.x)]!=null)
        {
            vel.y=0;
            pos.y= Math.abs(pos.y);
        }
        else{
            pos.x += vel.x*delta;
            pos.y += vel.y*delta;
        }
    }
    public void grav(float delta)
    {
        vel.y -= 50f*delta;
    }


}
