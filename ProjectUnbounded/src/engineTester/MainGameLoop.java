package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import blocks.Block;
import chunks.Chunk;
import chunks.WorldChunks;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {
	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		RawModel model = OBJLoader.loadObjModel("cube", loader);
		
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("gold")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(WorldChunks.getWorldLength()/2,WorldChunks.getWorldLength()/2,WorldChunks.getWorldLength()/2),0,0,0,1);
		Light light = new Light(new Vector3f(WorldChunks.getWorldLength()/2,WorldChunks.getWorldLength()/2,WorldChunks.getWorldLength()/2),new Vector3f(1,1,1));
		
		Camera camera = new Camera();
		camera.setPosition(WorldChunks.getWorldLength()/2, WorldChunks.getWorldLength()/8, WorldChunks.getWorldLength()/2);

		WorldChunks worldChunks = new WorldChunks(loader);
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			camera.move();
			
			renderer.processEntity(entity);
			
			for(Chunk c : worldChunks.getWorldChunks()) {
				if(c.getModel() != null)
					renderer.processChunk(c);
			}
			
			renderer.render(light, camera);
			
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}
