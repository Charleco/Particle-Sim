package partsimgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;


public class World {
    public Particle[][] grid;
    public ArrayList<Particle> partList;
    public float colCount;
    public float rowCount;
    public World()
    {
        colCount = Gdx.graphics.getWidth()/10;
        rowCount = Gdx.graphics.getHeight()/10;
        grid = new Particle[(int) rowCount][(int) colCount];
        partList = new ArrayList<>();
    }
    public void partDraw (ShapeRenderer rend)
    {
        rend.setColor(Color.BLUE);
        for(int i =1;i<grid.length;i++)
        {
            for(int j = 1;j<grid[i].length;j++) {
                if(this.grid[i][j]!=null) {
                    rend.rect((int) j*10, (int)i*10, 10, 10);
                }
            }
        }
    }
    public void gridUpdate()
    {
        for(int i =1;i<grid.length;i++)
        {
            for(int j = 1;j<grid[i].length;j++) {
                if(this.grid[i][j]!=null) {
                    int newRow=(int)(grid[i][j].pos.y);
                    int newCol=(int)(grid[i][j].pos.x);
                    if(newRow!=j || newCol!=i)
                    {
                        Particle temp = grid[i][j];
                        grid[i][j] = null;
                        grid[newRow][newCol] = temp;
                    }
                }
            }
        }
    }
    public void particleDebug(ShapeRenderer rend)
    {
        for(int i =1;i<grid.length;i++)
        {
            for(int j = 1;j<grid[i].length;j++) {
                if(this.grid[i][j]!=null) {
                    rend.setColor(Color.BLUE);
                    rend.rect(Math.abs(grid[i][j].pos.x*10), Math.abs(grid[i][j].pos.y*10), 10, 10);
                    rend.setColor(Color.RED);
                    rend.circle((j*10)+5, (i*10)+5, 2);
                }
            }
        }
    }
    public void gridDraw(ShapeRenderer rend)
    {
        rend.setColor(Color.WHITE);
        for(int i =0;i<grid.length;i++)
        {
            for(int j = 0;j<grid[i].length;j++) {
                rend.line(j*10,0,j*10,Gdx.graphics.getHeight());
                rend.line(0,i*10,Gdx.graphics.getWidth(),i*10);
            }
        }
    }

}
