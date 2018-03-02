package renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import chunks.Chunk;
import models.RawModel;
import shaders.ChunkShader;
import toolbox.Maths;

public class ChunkRenderer {

	private ChunkShader shader;
	
	public ChunkRenderer(ChunkShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(List<Chunk> chunks) {
		for(Chunk chunk:chunks) {
			prepareChunk(chunk);
			loadModelMatrix(chunk);
			// final render method
			 GL11.glDrawElements(GL11.GL_TRIANGLES, chunk.getModel().getVertexCount(),
					 GL11.GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	private void prepareChunk(Chunk chunk) {
        RawModel rawModel = chunk.getModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
	}
	
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
	}
	
	private void loadModelMatrix(Chunk chunk) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(
				new Vector3f(chunk.getX(),chunk.getY(),chunk.getZ()), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
	}
	
}





















