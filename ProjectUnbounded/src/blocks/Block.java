package blocks;

public class Block {

	private double x,y,z,id;
	private float cX,cY,cZ;
	
	public Block(float cX, float cY, float cZ, int x, int y, int z, int id) {
		this.cX = cX;
		this.cY = cY;
		this.cZ = cZ;
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public float getcX() {
		return cX;
	}

	public float getcY() {
		return cY;
	}

	public float getcZ() {
		return cZ;
	}

	public double getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public float[] getVertices() {
		float[] vertices = {
				// Bottom
				(float) x,(float) y,(float) z,
				(float) x+1,(float) y,(float) z,
				(float) x,(float) y,(float) z+1,
				(float) x+1,(float) y,(float) z+1,
				// Top
				(float) x,(float) y+1,(float) z,
				(float) x+1,(float) y+1,(float) z, 
				(float) x,(float) y+1,(float) z+1,
				(float) x+1,(float) y+1,(float) z+1
		};
		return vertices;
	}
}














