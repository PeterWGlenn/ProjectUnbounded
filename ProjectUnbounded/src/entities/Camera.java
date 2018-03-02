package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Camera {
	
	private int sensitivity = 1;
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera() {}
	
	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z-=0.02f*5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x+=0.02f*5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x-=0.02f*5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z+=0.02f*5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			position.y+=0.02f*5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			position.y-=0.02f*5;
		}
		//rotation
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			roll-=0.22f*5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
			roll+=0.22f*5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			yaw-=0.22f*5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			yaw+=0.22f*5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			pitch-=0.22f*5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			pitch+=0.22f*5;
		}
		// mouse 
		/*
		if(Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
			yaw+=Mouse.getX()/10 * sensitivity;
			pitch+=Mouse.getY()/10 * sensitivity;
			Mouse.setCursorPosition(DisplayManager.getWidth()/2, DisplayManager.getHeight()/2);
		}
		*/
	}
	
	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
}