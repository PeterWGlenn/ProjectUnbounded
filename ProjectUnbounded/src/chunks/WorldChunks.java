package chunks;

import java.util.ArrayList;
import renderEngine.Loader;

public class WorldChunks {
	
	private static ArrayList<Chunk> worldChunks = new ArrayList<Chunk>();
	private static int worldChunkLength = 25;
	
	// Public Methods
	
	public WorldChunks(Loader loader) {
		
		generateWorldChunks(loader);
		
	}
	
	public ArrayList<Chunk> getWorldChunks() {
		return worldChunks;
	}

	public void setWorldChunk(int index, Chunk element) {
		worldChunks.set(index, element);
	}
	
	public void addWorldChunk(Chunk element) {
		worldChunks.add(element);
	}
	
	public static Chunk getChunk(String type, float a, float b, float c, float SIZE, Loader loader) {
		float x,y,z;
		
		if(type.equals("top")) {
			x = a; y = b + SIZE; z = c; 
		} else if(type.equals("bottom")) {
			x = a; y = b - SIZE; z = c; 
		} else if(type.equals("front")) {
			x = a; y = b; z = c + SIZE; 
		} else if(type.equals("back")) {
			x = a; y = b; z = c - SIZE; 
		} else if(type.equals("right")) {
			x = a + SIZE; y = b; z = c; 
		} else if(type.equals("left")) {
			x = a - SIZE; y = b; z = c; 
		} else {
			x=y=z=0;
		}
		
		Chunk foundChunk = null;
		
		for(Chunk chunk : worldChunks) {
			if(chunk.getX() == x && chunk.getY() == y && chunk.getZ() == z) {
				foundChunk = chunk;
				break;
			}
		}
		
		return foundChunk;
	}
	
	public static double getBlockPercentage(double d) {
		return ((worldChunkLength * Chunk.getSize()) * (d/100))-1;
	}
	
	public static int getWorldLength() {
		return (int) (worldChunkLength * Chunk.getSize());
	}
	
	// Private Methods
	
	private void generateWorldChunks(Loader loader) {
		
		int h = worldChunkLength;
		for(int x = 0; x < h; x++) {
			for(int y = 0; y < h; y++) {
				for(int z = 0; z < h; z++) {
					Chunk chunk = new Chunk(x,y,z,loader);
					worldChunks.add(chunk);
				}
			}
		}
		
	}
	
}
