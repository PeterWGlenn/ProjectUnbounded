package blocks;

import org.lwjgl.util.vector.Vector4f;

public class BlockData {

	public static Vector4f getColor(int id) {
		double r = 0,g = 0,b = 0, t = 1;
		
		// Set Color
		
		if(id == 1) {
			r = 0.0;
			g = 0.8;
			b = 0.0;
			t = 1.0;
		}
		
		if(id == 2) {
			r = 0.5;
			g = 0.5;
			b = 0.5;
			t = 1.0;
		}
		if(id == 3) {
			r = 0.2;
			g = 0.8;
			b = 0.9;
			t = 1.0;
		}
		if(id == 4) {
			r = 0.9;
			g = 0.2;
			b = 0.2;
			t = 1.0;
		}
			
		// Return
		Vector4f color = new Vector4f((float) r,(float) g,(float) b,(float) t);
		return color;	
	}
	
}
