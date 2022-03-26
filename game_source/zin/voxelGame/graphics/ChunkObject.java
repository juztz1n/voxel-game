package zin.voxelGame.graphics;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import zin.gammaEngine.core.componentSystem.GameObject;
import zin.gammaEngine.graphics.components.MeshComponent;
import zin.voxelGame.utils.Block;
import zin.voxelGame.utils.BlockType;
import zin.voxelGame.utils.Chunk;
import zin.voxelGame.utils.DefaultBlock;
import zin.voxelGame.utils.TextureCoordinates;
import zin.voxelGame.utils.Vertex;

public class ChunkObject extends GameObject
{

	private Chunk chunk;

	private MeshComponent mesh;

	public ChunkObject(Chunk chunk)
	{
		this.chunk = chunk;
	}

	@Override
	public boolean init()
	{
		constructMesh();

		addComponent(mesh);

		setPosition(chunk.getX() * chunk.getWidth() - 0.5f, 0 - 0.5f, chunk.getY() * chunk.getLength() - 0.5f);

		return super.init();
	}

	public void setChunk(Chunk chunk)
	{
		this.chunk = chunk;
	}

	public Chunk getChunk()
	{
		return chunk;
	}

	@Override
	public void render()
	{
		GL11.glFrontFace(GL11.GL_CW);
		super.render();
		GL11.glFrontFace(GL11.GL_CCW);
	}

	private void constructMesh()
	{
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<TextureCoordinates> texCoords = new ArrayList<TextureCoordinates>();

		for (int x = 0; x < chunk.getWidth(); x++)
		{
			for (int y = 0; y < chunk.getHeight(); y++)
			{
				for (int z = 0; z < chunk.getLength(); z++)
				{
					if (chunk.getBlock(x, y, z).getType() == BlockType.AIR)
						continue;

					int faces = 0;

					if (y >= chunk.getHeight() - 1)
					{
						for (int i = 0; i < DefaultBlock.TOP_FACE.length; i += 3)
							vertices.add(new Vertex(DefaultBlock.TOP_FACE[i] + x, DefaultBlock.TOP_FACE[i + 1] + y,
									DefaultBlock.TOP_FACE[i + 2] + z));
						faces++;
					} else if (chunk.getBlock(x, y + 1, z).getType() == BlockType.AIR)
					{
						for (int i = 0; i < DefaultBlock.TOP_FACE.length; i += 3)
							vertices.add(new Vertex(DefaultBlock.TOP_FACE[i] + x, DefaultBlock.TOP_FACE[i + 1] + y,
									DefaultBlock.TOP_FACE[i + 2] + z));
						faces++;
					}

					if (y <= 0)
					{
						for (int i = 0; i < DefaultBlock.BOTTOM_FACE.length; i += 3)
							vertices.add(new Vertex(DefaultBlock.BOTTOM_FACE[i] + x,
									DefaultBlock.BOTTOM_FACE[i + 1] + y, DefaultBlock.BOTTOM_FACE[i + 2] + z));
						faces++;
					} else if (chunk.getBlock(x, y - 1, z).getType() == BlockType.AIR)
					{
						for (int i = 0; i < DefaultBlock.BOTTOM_FACE.length; i += 3)
							vertices.add(new Vertex(DefaultBlock.BOTTOM_FACE[i] + x,
									DefaultBlock.BOTTOM_FACE[i + 1] + y, DefaultBlock.BOTTOM_FACE[i + 2] + z));
						faces++;
					}

					if (x >= chunk.getWidth() - 1)
					{
						for (int i = 0; i < DefaultBlock.BACK_FACE.length; i += 3)
							vertices.add(new Vertex(DefaultBlock.BACK_FACE[i] + x, DefaultBlock.BACK_FACE[i + 1] + y,
									DefaultBlock.BACK_FACE[i + 2] + z));
						faces++;
					} else if (chunk.getBlock(x + 1, y, z).getType() == BlockType.AIR)
					{
						for (int i = 0; i < DefaultBlock.BACK_FACE.length; i += 3)
							vertices.add(new Vertex(DefaultBlock.BACK_FACE[i] + x, DefaultBlock.BACK_FACE[i + 1] + y,
									DefaultBlock.BACK_FACE[i + 2] + z));
						faces++;
					}

					if (x <= 0)
					{
						for (int i = 0; i < DefaultBlock.FRONT_FACE.length; i += 3)
							vertices.add(new Vertex(DefaultBlock.FRONT_FACE[i] + x, DefaultBlock.FRONT_FACE[i + 1] + y,
									DefaultBlock.FRONT_FACE[i + 2] + z));
						faces++;
					} else if (chunk.getBlock(x - 1, y, z).getType() == BlockType.AIR)
					{
						for (int i = 0; i < DefaultBlock.FRONT_FACE.length; i += 3)
							vertices.add(new Vertex(DefaultBlock.FRONT_FACE[i] + x, DefaultBlock.FRONT_FACE[i + 1] + y,
									DefaultBlock.FRONT_FACE[i + 2] + z));
						faces++;
					}

					if (z >= chunk.getLength() - 1)
					{
						for (int i = 0; i < DefaultBlock.RIGHT_FACE.length; i += 3)
							vertices.add(new Vertex(DefaultBlock.RIGHT_FACE[i] + x, DefaultBlock.RIGHT_FACE[i + 1] + y,
									DefaultBlock.RIGHT_FACE[i + 2] + z));
						faces++;
					} else if (chunk.getBlock(x, y, z + 1).getType() == BlockType.AIR)
					{
						for (int i = 0; i < DefaultBlock.RIGHT_FACE.length; i += 3)
							vertices.add(new Vertex(DefaultBlock.RIGHT_FACE[i] + x, DefaultBlock.RIGHT_FACE[i + 1] + y,
									DefaultBlock.RIGHT_FACE[i + 2] + z));
						faces++;
					}

					if (z <= 0)
					{
						for (int i = 0; i < DefaultBlock.LEFT_FACE.length; i += 3)
							vertices.add(new Vertex(DefaultBlock.LEFT_FACE[i] + x, DefaultBlock.LEFT_FACE[i + 1] + y,
									DefaultBlock.LEFT_FACE[i + 2] + z));
						faces++;
					} else if (chunk.getBlock(x, y, z - 1).getType() == BlockType.AIR)
					{
						for (int i = 0; i < DefaultBlock.LEFT_FACE.length; i += 3)
							vertices.add(new Vertex(DefaultBlock.LEFT_FACE[i] + x, DefaultBlock.LEFT_FACE[i + 1] + y,
									DefaultBlock.LEFT_FACE[i + 2] + z));
						faces++;
					}

					if (chunk.getBlock(x, y, z).getType() == BlockType.DIRT)
					{
						for (int r = 0; r < faces; r++)
						{
							for (int i = 0; i < DefaultBlock.TEXCOORDS_DIRT.length; i += 2)
								texCoords.add(new TextureCoordinates(DefaultBlock.TEXCOORDS_DIRT[i],
										DefaultBlock.TEXCOORDS_DIRT[i + 1]));
						}
					} else if (chunk.getBlock(x, y, z).getType() == BlockType.COBBLESTONE)
					{
						for (int r = 0; r < faces; r++)
						{
							for (int i = 0; i < DefaultBlock.TEXCOORDS_COBBLESTONE.length; i += 2)
								texCoords.add(new TextureCoordinates(DefaultBlock.TEXCOORDS_COBBLESTONE[i],
										DefaultBlock.TEXCOORDS_COBBLESTONE[i + 1]));
						}
					} else if (chunk.getBlock(x, y, z).getType() == BlockType.STONE)
					{
						for (int r = 0; r < faces; r++)
						{
							for (int i = 0; i < DefaultBlock.TEXCOORDS_STONE.length; i += 2)
								texCoords.add(new TextureCoordinates(DefaultBlock.TEXCOORDS_STONE[i],
										DefaultBlock.TEXCOORDS_STONE[i + 1]));
						}
					} else
					{
						for (int r = 0; r < faces; r++)
						{
							for (int i = 0; i < DefaultBlock.TEXCOORDS_STONE.length; i += 2)
								texCoords.add(new TextureCoordinates(DefaultBlock.TEXCOORDS_STONE[i],
										DefaultBlock.TEXCOORDS_STONE[i + 1]));
						}
					}
				}
			}
		}

		float[] verticesArray = new float[vertices.size() * 3];
		float[] texCoordsArray = new float[texCoords.size() * 2];

		int counter = 0;
		for (int i = 0; i < vertices.size() * 3; i += 3)
		{
			verticesArray[i] = vertices.get(counter).getX();
			verticesArray[i + 1] = vertices.get(counter).getY();
			verticesArray[i + 2] = vertices.get(counter).getZ();
			counter++;
		}

		counter = 0;
		for (int i = 0; i < texCoords.size() * 2; i += 2)
		{
			texCoordsArray[i] = texCoords.get(counter).getU();
			texCoordsArray[i + 1] = texCoords.get(counter).getV();
			counter++;
		}

		mesh = new MeshComponent(verticesArray, texCoordsArray);
	}

	public void reconstructMesh()
	{
		mesh.remove();

		constructMesh();

		addComponent(mesh);
		mesh.init();
	}

	public Block getFirstRayIntersect(Vector3f origin, Vector3f direction, float radius, float step)
	{
		int x = Math.round(origin.x);
		int y = Math.round(origin.y);
		int z = Math.round(origin.z);

		if (chunk.hasSolidBlock(x, y, z))
		{
			return chunk.getBlockInWorldCoordinates(x, y, z);
		}

		float stepsTaken = 0.0f;
		while (stepsTaken < radius)
		{
			x = Math.round(origin.x + direction.x * stepsTaken);
			y = Math.round(origin.y + direction.y * stepsTaken);
			z = Math.round(origin.z + direction.z * stepsTaken);

			if (chunk.hasSolidBlock(x, y, z))
			{
				return chunk.getBlockInWorldCoordinates(x, y, z);
			}

			stepsTaken += step;
		}

		return null;
	}

	public Block getBlockBeforeRayIntersect(Vector3f origin, Vector3f direction, float radius, float step)
	{
		int x = Math.round(origin.x);
		int y = Math.round(origin.y);
		int z = Math.round(origin.z);

		if (chunk.hasSolidBlock(x, y, z))
		{
			return null;
		}

		float stepsTaken = 0.0f;
		boolean intersection = false;
		while (stepsTaken < radius)
		{
			x = Math.round(origin.x + direction.x * stepsTaken);
			y = Math.round(origin.y + direction.y * stepsTaken);
			z = Math.round(origin.z + direction.z * stepsTaken);

			if (chunk.hasSolidBlock(x, y, z))
			{
				intersection = true;
			}

			if (intersection == true)
			{
				x = Math.round(origin.x + direction.x * (stepsTaken - step));
				y = Math.round(origin.y + direction.y * (stepsTaken - step));
				z = Math.round(origin.z + direction.z * (stepsTaken - step));

				if (chunk.hasBlock(x, y, z))
				{
					return chunk.getBlockInWorldCoordinates(x, y, z);
				} else
				{
					return null;
				}
			}

			stepsTaken += step;
		}

		return null;
	}

}
