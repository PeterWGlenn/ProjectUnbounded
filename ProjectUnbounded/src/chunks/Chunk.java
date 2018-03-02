package chunks;

import java.util.ArrayList;
import java.util.Random;
import blocks.Block;
import blocks.BlockData;
import models.RawModel;
import renderEngine.Loader;

public class Chunk {

	private static final float SIZE = 10;
	
	private static final int cubesPerChunk = (int) Math.pow(SIZE, 3);
	
	private float[][] v = {
		{0,0,0}, //0
		{1,0,0}, //1
		{0,0,1}, //2
		{1,0,1}, //3
		{0,1,0}, //4
		{1,1,0}, //5
		{0,1,1}, //6
		{1,1,1} //7
	};
	
	private float[] vertices;
	private float[] colors;
	private float[] normals;
	private int[] indices;
		
	private static ArrayList<Block> blocks;
	
	private float x;
	private float y;
	private float z;
	private RawModel model;
	
	public Chunk(int gridX, int gridY, int gridZ, Loader loader) {
		this.x = gridX * SIZE;
		this.y = gridY * SIZE;
		this.z = gridZ * SIZE;
		
		initilizeBlocks();
		generateBlockMaterial();
		
		this.model = generateChunk(loader);
	}
	
	private void initilizeBlocks() {
		blocks = new ArrayList<Block>();
		// X Blocks
		for(int x = 0; x < SIZE; x++) {
			// Y Blocks
			for(int y = 0; y < SIZE; y++) {
				// Z Blocks
				for(int z = 0; z < SIZE; z++) {
					blocks.add(new Block(this.x,this.y,this.z,x,y,z,0));
				}
			}
		}
	}
	
	private void generateBlockMaterial() {
		for(Block block : blocks) {
			Random random = new Random();
			int randID = random.nextInt(100);
			double height = (block.getcY()+1)-(SIZE-block.getY());
			
			if(height >= WorldChunks.getBlockPercentage(2)) {
				block.setId(0);
			} else {
				block.setId(1);
			}
		}
	}
	
	private RawModel generateChunk(Loader loader){
		
		for(Block b : blocks) {
			if(b.getId() != 0)
				addCube(b,(int) b.getId(), loader);
		}
		
		if(vertices != null)
			return loader.loadToChunkVAO(this.vertices, this.colors, this.normals, this.indices);
		else 
			return null;
	}
	
	private void addCube(Block block, int id, Loader loader) {
		
		int x = (int) block.getX(), y = (int) block.getY(), z = (int) block.getZ();
		
		Random r = new Random();
		float g = r.nextFloat() * 0.025F;	
		
		if(!hasSharedFace("top",x,y,z,loader))
			addFace("top",x,y,z,id,g);
		if(!hasSharedFace("bottom",x,y,z,loader))
			addFace("bottom",x,y,z,id,g);
		if(!hasSharedFace("front",x,y,z,loader))
			addFace("front",x,y,z,id,g);
		if(!hasSharedFace("back",x,y,z,loader))
			addFace("back",x,y,z,id,g);
		if(!hasSharedFace("right",x,y,z,loader))
			addFace("right",x,y,z,id,g);
		if(!hasSharedFace("left",x,y,z,loader))
			addFace("left",x,y,z,id,g);
	}
	
	private boolean hasSharedFace(String type, int x,int y,int z, Loader loader) {
		
		Block testBlock = getBlock(type,x,y,z);
		
		if(testBlock == null) {
			
			// Search chunk
			
			Chunk chunk = WorldChunks.getChunk(type, this.x, this.y, this.z, SIZE, loader);;
			
			int max = (int) (SIZE-1);
			int min = 0;
			
			if(chunk == null) {
				// If there is no chunk
				return false;
			} else {
				// If there is a chunk
				int searchX, searchY, searchZ;
				
				searchX = x;
				searchY = y;
				searchZ = z;
				
				if(type == "top") 
					searchY = max;
				if(type == "bottom") 
					searchY = min;
				if(type == "front") 
					searchZ = max;
				if(type == "back") 
					searchZ = min;
				if(type == "right") 
					searchX = max;
				if(type == "left") 
					searchX = min;
			
				Block searchBlock = chunk.searchForBlock(chunk.getBlocks(), searchX, searchY, searchZ);
				if(searchBlock != null && searchBlock.getId() != 0) {
					// Block in another chunk exists and is solid
					return true;
				} else {
					// Block in another chunk does not exist or is air
					return false;
				}
			}
		} else if(testBlock.getId() == 0) {
			return false;
		} else {
			return true;
		}
		
	}
	
	private Block getBlock(String type, int x, int y, int z) {
		int a,b,c,d,e;
		a=b=c=d=e=0;
		// Initialize
		if(type.equals("top")) {
			a = x;
			b = y+1;
			c = z;
			d = y;
			e = (int) SIZE;
		}
		if(type.equals("bottom")) {
			a = x;
			b = y-1;
			c = z;
			d = y;
			e = 0;
		}
		if(type.equals("front")) {
			a = x;
			b = y;
			c = z+1;
			d = z;
			e = (int) SIZE;
		}
		if(type.equals("back")) {
			a = x;
			b = y;
			c = z-1;
			d = z;
			e = 0;
		}
		if(type.equals("right")) {
			a = x+1;
			b = y;
			c = z;
			d = x;
			e = (int) SIZE;
		}
		if(type.equals("left")) {
			a = x-1;
			b = y;
			c = z;
			d = x;
			e = 0;
		}
		
		// Retrieve 
		if(d == e)
			return null;
		else {
			Block foundBlock = null;
			for(Block block : blocks) {
				if(block.getX() == a && block.getY() == b && block.getZ() == c) {
					foundBlock = block;
					break;
				}
			}
			return foundBlock;
		}
	}
	
	private Block searchForBlock(ArrayList<Block> list, int x, int y, int z) {
		Block foundBlock = null;
		for(Block block : list) {
			if(block.getX() == x && block.getY() == y && block.getZ() == z) {
				foundBlock = block;
				break;
			}
		}
		return foundBlock;
	}
	
	private int startingPointFromFloatList(float[] f) {
		int startAt;
		
		if(f == null)
			startAt = 0;
		else
			startAt = f.length;
		
		return startAt;
	}
	
	private int startingPointFromIntegerList(int[] i) {
		int startAt;
		
		if(i == null)
			startAt = 0;
		else
			startAt = i.length;
		
		return startAt;
	}
	
	private void addFace(String face, int x, int y, int z, int id, float gradient) {
		
		// Starting Locations
		
		int startAtVertex = startingPointFromFloatList(vertices);
		int startAtColor = startingPointFromFloatList(colors);
		int startAtNormal = startingPointFromFloatList(normals);
		int startAtIndex = startingPointFromIntegerList(indices);
		
		// Set Up Face Variables
		
		float v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12;
		v1=v2=v3=v4=v5=v6=v7=v8=v9=v10=v11=v12=0;
		float n1,n2,n3;
		n1=n2=n3=0;
		
		if(face.equals("bottom")) {
			v1 = x + v[1][0];
			v2 = y + v[1][1];
			v3 = z + v[1][2];
			v4 = x + v[0][0];
			v5 = y + v[0][1];
			v6 = z + v[0][2];
			v7 = x + v[3][0];
			v8 = y + v[3][1];
			v9 = z + v[3][2];
			v10 = x + v[2][0];
			v11 = y + v[2][1];
			v12 = z + v[2][2];
			
			n1 = 0;
			n2 = -1;
			n3 = 0;
		}
		
		if(face.equals("top")) {
			v1 = x + v[4][0];
			v2 = y + v[4][1];
			v3 = z + v[4][2];
			v4 = x + v[5][0];
			v5 = y + v[5][1];
			v6 = z + v[5][2];
			v7 = x + v[6][0];
			v8 = y + v[6][1];
			v9 = z + v[6][2];
			v10 = x + v[7][0];
			v11 = y + v[7][1];
			v12 = z + v[7][2];
			
			n1 = 0;
			n2 = 1;
			n3 = 0;
		}
		
		if(face.equals("front")) {
			v1 = x + v[6][0];
			v2 = y + v[6][1];
			v3 = z + v[6][2];
			v4 = x + v[7][0];
			v5 = y + v[7][1];
			v6 = z + v[7][2];
			v7 = x + v[2][0];
			v8 = y + v[2][1];
			v9 = z + v[2][2];
			v10 = x + v[3][0];
			v11 = y + v[3][1];
			v12 = z + v[3][2];
			
			n1 = 0;
			n2 = 0;
			n3 = 1;
		}
		
		if(face.equals("back")) {
			v1 = x + v[5][0];
			v2 = y + v[5][1];
			v3 = z + v[5][2];
			v4 = x + v[4][0];
			v5 = y + v[4][1];
			v6 = z + v[4][2];
			v7 = x + v[1][0];
			v8 = y + v[1][1];
			v9 = z + v[1][2];
			v10 = x + v[0][0];
			v11 = y + v[0][1];
			v12 = z + v[0][2];
			
			n1 = 0;
			n2 = 0;
			n3 = -1;
		}
		
		if(face.equals("right")) {
			v1 = x + v[7][0];
			v2 = y + v[7][1];
			v3 = z + v[7][2];
			v4 = x + v[5][0];
			v5 = y + v[5][1];
			v6 = z + v[5][2];
			v7 = x + v[3][0];
			v8 = y + v[3][1];
			v9 = z + v[3][2];
			v10 = x + v[1][0];
			v11 = y + v[1][1];
			v12 = z + v[1][2];
			
			n1 = 1;
			n2 = 0;
			n3 = 0;
		}
		
		if(face.equals("left")) {
			v1 = x + v[4][0];
			v2 = y + v[4][1];
			v3 = z + v[4][2];
			v4 = x + v[6][0];
			v5 = y + v[6][1];
			v6 = z + v[6][2];
			v7 = x + v[0][0];
			v8 = y + v[0][1];
			v9 = z + v[0][2];
			v10 = x + v[2][0];
			v11 = y + v[2][1];
			v12 = z + v[2][2];
			
			n1 = -1;
			n2 = 0;
			n3 = 0;
		}
		
		// Add Vertices
		
		float[] newVertices = new float[(startAtVertex)+12];
		
		if(vertices != null) {
			for(int i=0; i<vertices.length;i++) {
				newVertices[i] = vertices[i];
			}
		}
		
		newVertices[startAtVertex+0] = v1;
		newVertices[startAtVertex+1] = v2;
		newVertices[startAtVertex+2] = v3;
		
		newVertices[startAtVertex+3] = v4; 
		newVertices[startAtVertex+4] = v5;
		newVertices[startAtVertex+5] = v6;
		
		newVertices[startAtVertex+6] = v7;
		newVertices[startAtVertex+7] = v8;
		newVertices[startAtVertex+8] = v9;
		
		newVertices[startAtVertex+9] = v10;
		newVertices[startAtVertex+10] = v11;
		newVertices[startAtVertex+11] = v12;
		
		vertices = newVertices;
		
		// Add Normals
		
				float[] newNormals = new float[startAtNormal+12];
				
				if(normals != null) {
					for(int i=0; i<normals.length;i++) {
						newNormals[i] = normals[i];
					}
				}
				
				newNormals[startAtNormal+0] = n1;
				newNormals[startAtNormal+1] = n2;
				newNormals[startAtNormal+2] = n3;
				
				newNormals[startAtNormal+3] = n1;
				newNormals[startAtNormal+4] = n2;
				newNormals[startAtNormal+5] = n3;
				
				newNormals[startAtNormal+6] = n1;
				newNormals[startAtNormal+7] = n2;
				newNormals[startAtNormal+8] = n3;
				
				newNormals[startAtNormal+9] = n1;
				newNormals[startAtNormal+10] = n2;
				newNormals[startAtNormal+11] = n3;
				
				normals = newNormals;
		
		// Add Colors
		
		float[] newColors = new float[(startAtColor)+16];
		
		if(colors != null) {
			for(int i=0; i<colors.length;i++) {
				newColors[i] = colors[i];
			}
		}
		
		newColors[startAtColor+0] = newColors[startAtColor+4] = newColors[startAtColor+8] = newColors[startAtColor+12] = BlockData.getColor(id).x + gradient;
		newColors[startAtColor+1] = newColors[startAtColor+5] = newColors[startAtColor+9] = newColors[startAtColor+13] = BlockData.getColor(id).y + gradient;
		newColors[startAtColor+2] = newColors[startAtColor+6] = newColors[startAtColor+10] = newColors[startAtColor+14] = BlockData.getColor(id).z + gradient;
		newColors[startAtColor+3] = newColors[startAtColor+7] = newColors[startAtColor+11] = newColors[startAtColor+15] = BlockData.getColor(id).w + gradient;
		
		colors = newColors;
		
		// Add Indices
		
		int[] newIndices = new int[startAtIndex+6];
		
		if(indices != null) {
			for(int i=0; i<indices.length;i++) {
				newIndices[i] = indices[i];
			}
		}
		
		newIndices[startAtIndex+0] = 1+(startAtVertex/3);
		newIndices[startAtIndex+1] = 0+(startAtVertex/3);
		newIndices[startAtIndex+2] = 2+(startAtVertex/3);
		
		newIndices[startAtIndex+3] = 2+(startAtVertex/3);
		newIndices[startAtIndex+4] = 3+(startAtVertex/3);
		newIndices[startAtIndex+5] = 1+(startAtVertex/3);
		
		indices = newIndices;
		
	}
	
	// TEST
	public float[] getVertices() {
		float[] vertices = new float[cubesPerChunk*8];
		
		// Init Vertices
		int vert = 0;
		for(int b = 0; b < (cubesPerChunk*8); b++) {
			vertices[b] = blocks.get(b/8).getVertices()[vert];
			vert++;
			if(vert>7)
				vert = 0;
		}
		
		return vertices;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}

	public static float getSize() {
		return SIZE;
	}
	
	public RawModel getModel() {
		return model;
	}
	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}

}
