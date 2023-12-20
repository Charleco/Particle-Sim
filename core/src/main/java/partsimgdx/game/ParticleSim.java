package partsimgdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class ParticleSim extends ApplicationAdapter implements InputProcessor{
    public ExtendViewport view;
    public OrthographicCamera cam;
    public ShapeRenderer rend;
    public World world;
    public float delta;

    public Particle part1;
    public Particle part2;

    @Override
    public void create() {
        cam = new OrthographicCamera();
        view = new ExtendViewport(100,100,cam);
        cam.position.set(view.getWorldWidth()/2,view.getWorldHeight()/2,0);
        cam.update();
        world = new World();
        rend = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);
        /*
        part1 = new Particle(1,10,5,world);
        part2 = new Particle(1,8,5,world);

        for(int i =0;i<world.grid.length;i++)
        {
            for(int j = 0;j<world.grid[i].length;j++) {
                if(j%2==0&&i%2==0)
                    new Particle(1,i,j,world);
            }
        }

         */


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        delta = Gdx.graphics.getDeltaTime();
        for(int i =1;i<world.grid.length;i++)
        {
            for(int j = 1;j<world.grid[i].length;j++) {
                if(world.grid[i][j]!=null)
                {
                    world.grid[i][j].grav(delta);
                    world.grid[i][j].move(delta);
                }
            }
        }
        world.gridUpdate();
        rend.begin(ShapeRenderer.ShapeType.Line);
        world.gridDraw(rend);
        rend.end();
        rend.begin(ShapeRenderer.ShapeType.Filled);
        world.partDraw(rend);
        //world.particleDebug(rend);
        rend.end();

    }
    @Override public void resize (int width, int height) {

        view.update(width, height, true);
    }

    @Override
    public void dispose() {
        rend.dispose();
    }

    Vector3 tp = new Vector3();
    boolean dragging;

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        System.out.println("test");
        cam.unproject(tp.set(screenX, screenY, 0));
        dragging = true;
        int x = (int) ((int)tp.x/world.rowCount);
        int y = (int)Math.abs(Gdx.graphics.getHeight()-tp.y)/10;
        System.out.println(tp.x+" "+tp.y+" "+x + " "+ y+" "+ world.grid.length);

        if(world.grid[y][x]==null)
            world.grid[y][x]=(new Particle(1,y,x,world));




        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        cam.unproject(tp.set(screenX, screenY, 0));
        dragging = false;
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(!dragging)
            return false;
        cam.unproject(tp.set(screenX, screenY, 0));
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        cam.unproject(tp.set(screenX, screenY, 0));
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
