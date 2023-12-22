package partsimgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import java.util.Random;


public class World {
    private final Particle[][] grid;
    private final ArrayList<Particle> partList;
    private final int colCount;
    private final int rowCount;
    private final int scale;

    Random rand = new Random();
    public World(int scale)
    {
        this.scale = scale;
        this.colCount = Gdx.graphics.getWidth()/scale;
        this.rowCount = Gdx.graphics.getHeight()/scale;
        Gdx.app.log("Grid", "colCount: "+colCount+" rowCount:"+rowCount);
        this.grid = new Particle[(int) rowCount][(int) colCount];
        this.partList = new ArrayList<>();
    }
    public int getColCount()
    {
        return this.colCount;
    }
    public int getRowCount()
    {
        return this.rowCount;
    }
    public Particle[][] getGrid()
    {
        return this.grid;
    }
    public ArrayList<Particle> getPartList()
    {
        return this.partList;
    }
    public void popGrid()
    {
        for(int y = 1;y<grid.length;y++)
        {
            for(int x =1;x< grid[y].length;x++)
            {
                if(rand.nextDouble()>.7)
                {
                    grid[y][x]=new Particle(1,y,x,this);
                }

            }
        }
    }
    public void gridUpdate()
    {
        int partCnt = 0;
        for(int i =0;i<grid.length;i++)
        {
            for(int j = 0;j<grid[i].length;j++) {
                if(this.grid[i][j]!=null) {
                    partCnt++;
                    int newRow=(int)(grid[i][j].getPos().y);
                    int newCol=(int)(grid[i][j].getPos().x);
                    if(newRow!=j || newCol!=i)
                    {
                        Particle temp = grid[i][j];
                        grid[i][j] = null;
                        grid[newRow][newCol] = temp;
                    }
                }
            }
        }
        Gdx.app.log("Parts", "Total Particles: "+partCnt);
    }
    public void particleDraw(ShapeRenderer rend)
    {
        for(int y =1;y<grid.length;y++)
        {
            for(int x = 1;x<grid[y].length;x++) {
                if(this.grid[y][x]!=null) {
                    int height = partGradCount(x,y);
                    rend.setColor((float)(y/height-1)*.3f,1,1,.1f);
                    rend.rect( x*scale, y*scale, scale, scale);
                }
            }
        }
    }
    public void gridBalance() {
        for (int y = 1; y < grid.length - 1; y++) {
            for (int x = 1; x < grid[y].length - 1; x++) {
                if (grid[y][x] != null && grid[y + 1][x] == null && grid[y - 1][x] != null && safeMove(x,grid[y][x])) {
                    int leftCol = partCount(x - 1);
                    int rightCol = partCount(x + 1);
                    int thisCol = partCount(x);

                    if (leftCol < thisCol && leftCol < rightCol && grid[y][x - 1] == null && thisCol-leftCol>1) {
                        moveParticle(x, y, x - 1, y);
                    } else if (rightCol < thisCol && rightCol < leftCol && grid[y][x + 1] == null && thisCol-rightCol>1) {
                        moveParticle(x, y, x + 1, y);
                    }
                    else if (rightCol == leftCol && leftCol < thisCol && thisCol-leftCol>1) {
                        if (rand.nextDouble() > 0.5 && grid[y][x - 1] == null) {
                            moveParticle(x, y, x - 1, y);
                        } else if (grid[y][x + 1] == null) {
                            moveParticle(x, y, x + 1, y);
                        }
                    }
                }
            }
        }
    }

    private void moveParticle(int oldX, int oldY, int newX, int newY) {
        Particle temp = grid[oldY][oldX];
        grid[oldY][oldX] = null;
        grid[newY][newX] = temp;
        temp.getPos().x = newX;
        temp.getPos().y = newY;
    }
    public boolean safeMove(int col, Particle part)
    {
        int count=0;
        for(int y=1;y< grid.length;y++)
        {
            if(grid[y][col]!=null) {
                count++;
            }
            else
                break;
        }
        return count == (int) part.getPos().y;
    }
    public int partCount(int col)
    {
        int count = 0;
        for(int y = 1;y<grid.length;y++)
            if(grid[y][col]!=null)
                count++;
        return count;
    }
    public int partGradCount(int col, int row)
    {
        int count = 0;
        for(int y = row;y<grid.length;y++)
            if(grid[y][col]!=null)
                count++;
        return count;
    }

}
