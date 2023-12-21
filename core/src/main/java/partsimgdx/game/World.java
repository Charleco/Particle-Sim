package partsimgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;


public class World {
    public Particle[][] grid;
    public ArrayList<Particle> partList;
    public float colCount;
    public float rowCount;
    public int scale;

    Random rand = new Random();
    public World(int scale)
    {
        this.scale = scale;
        colCount = Gdx.graphics.getWidth()/scale;
        rowCount = Gdx.graphics.getHeight()/scale;
        Gdx.app.log("Grid", "colCount: "+colCount+" rowCount:"+rowCount);
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
                    rend.rect(Math.abs(grid[i][j].pos.x*10), Math.abs(grid[i][j].pos.y*10), scale, scale);
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
        for(int y =1;y<grid.length;y++)
        {
            for(int x = 1;x<grid[y].length;x++) {
                if(this.grid[y][x]!=null) {
                    rend.setColor(Color.BLUE);
                    rend.rect((int) x*scale, (int)y*scale, scale, scale);
                    rend.setColor(Color.RED);
                    rend.circle((x*scale)+scale/2, (y*scale)+scale/2, scale/5);
                }
            }
        }
    }
    public void gridDraw(ShapeRenderer rend)
    {
        rend.setColor(Color.WHITE);
        for(int y =0;y<grid.length;y++)
        {
            for(int x = 0;x<grid[y].length;x++) {
                rend.line(x*scale,0,x*scale,Gdx.graphics.getHeight());
                rend.line(0,y*scale,Gdx.graphics.getWidth(),y*scale);
            }
        }
    }
    public void rightGridBalance()
    {
        for(int y =1;y<grid.length-1;y++)
        {
            for(int x = 1;x<grid[y].length-1;x++) {
                if(this.grid[y][x]!=null) {
                    if(this.grid[y][x+1]==null&&y>1&& this.grid[y][x].vel.y==0) {
                        this.grid[y][x].pos.x+=1;
                    }

                }
            }
        }
    }
    public void leftGridBalance()
    {
        for(int y =grid.length-1;y>=1;y--)
        {
            for(int x = grid[y].length-1;x>=1;x--) {
                if(this.grid[y][x]!=null) {
                    if(this.grid[y][x-1]==null&&y>1&& this.grid[y][x].vel.y==0) {
                        this.grid[y][x].pos.x-=1;
                    }

                }
            }
        }
    }
    public void gridBalance()
    {
        for(int y =1;y<grid.length-1;y++)
        {
            for(int x = 1;x<grid[y].length-1;x++) {
                //if statement hell
                if(this.grid[y][x]!=null&&this.grid[y+1][x]==null&&this.grid[y-1][x]!=null) {
                    int leftCol = this.partCount(x-1);
                    int rightCol = this.partCount(x+1);
                    int thisCol = this.partCount(x);
                    if((leftCol<thisCol)&&(leftCol<rightCol)&&this.grid[y][x-1]==null)
                    {
                        this.grid[y][x].pos.x-=1;
                    }
                    else if((rightCol<thisCol)&&(rightCol<leftCol)&&this.grid[y][x+1]==null)
                    {
                        this.grid[y][x].pos.x+=1;
                    }
                    else if((rightCol==leftCol && leftCol<thisCol)&&this.grid[y][x+1]==null&&this.grid[y][x-1]==null)
                    {
                        if(rand.nextDouble()>.5){
                            this.grid[y][x].pos.x-=1;
                        }
                        else{
                            this.grid[y][x].pos.x+=1;
                        }

                    }
                }
            }
        }
    }
    public int partCount(int col)
    {
        int count = 0;
        for(int y = 1;y<grid.length;y++)
            if(grid[y][col]!=null&&grid[y][col].vel.y==0)
                count++;
        return count;
    }

}
