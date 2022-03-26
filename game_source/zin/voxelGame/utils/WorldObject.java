package zin.voxelGame.utils;

import org.joml.Vector3f;

import zin.gammaEngine.core.componentSystem.GameObject;
import zin.gammaEngine.graphics.components.ShaderComponent;
import zin.gammaEngine.graphics.components.TextureComponent;
import zin.gammaEngine.graphics.utils.TextureType;
import zin.voxelGame.graphics.ChunkObject;

public class WorldObject extends GameObject
{

	private int chunkWidth, chunkHeight, chunkLength;
	private Chunk[][] chunks;
	private ChunkObject[][] chunkObjects;

	public WorldObject(int chunkWidth, int chunkHeight, int chunkLength, Chunk[][] chunks)
	{
		this.chunks = chunks;
		chunkObjects = new ChunkObject[chunks.length][chunks[0].length];
		this.chunkWidth = chunkWidth;
		this.chunkHeight = chunkHeight;
		this.chunkLength = chunkLength;
	}

	@Override
	public boolean init()
	{
		addComponent(new TextureComponent("game_resources/textures/atlas.png", TextureType.ALBEDO, false));

		for (int x = 0; x < chunks.length; x++)
		{
			for (int y = 0; y < chunks[0].length; y++)
			{
				Chunk chunk = chunks[x][y].populate();

				ChunkObject chunkObject = new ChunkObject(chunk);
				chunkObjects[x][y] = chunkObject;

				chunkObject.addComponent(
						new ShaderComponent("game_resources/shaders/chunk.vsh", "game_resources/shaders/chunk.fsh"));

				addChild(chunkObject);
			}
		}

		return super.init();
	}

	public Block getFirstRayIntersect(Vector3f origin, Vector3f direction, float radius, float step)
	{
		Chunk originChunk = getChunkContainingBlock(Math.round(origin.x + direction.x * radius),
				Math.round(origin.z + direction.z * radius));
		Chunk directionChunk = getChunkContainingBlock(Math.round(origin.x + direction.x * radius),
				Math.round(origin.z + direction.z * radius));

		Block originChunkIntersectionResult = null, directionChunkIntersectionResult = null;

		if (originChunk != null)
		{
			try
			{
				originChunkIntersectionResult = getChunkRenderComponent(originChunk).getFirstRayIntersect(origin,
						direction, radius, step);
			} catch (Exception e)
			{
			}
		}

		if (originChunkIntersectionResult != null)
			return originChunkIntersectionResult;

		if (directionChunk != null)
		{
			try
			{
				directionChunkIntersectionResult = getChunkRenderComponent(directionChunk)
						.getFirstRayIntersect(origin, direction, radius, step).toWorldCoordinates();
			} catch (Exception e)
			{
			}
		}

		if (directionChunkIntersectionResult != null)
			return directionChunkIntersectionResult;

		return null;
	}

	public Block getBlockBeforeRayIntersect(Vector3f origin, Vector3f direction, float radius, float step)
	{
		Chunk originChunk = getChunkContainingBlock(Math.round(origin.x + direction.x * radius),
				Math.round(origin.z + direction.z * radius));
		Chunk directionChunk = getChunkContainingBlock(Math.round(origin.x + direction.x * radius),
				Math.round(origin.z + direction.z * radius));

		Block originChunkIntersectionResult = null, directionChunkIntersectionResult = null;

		if (originChunk != null)
		{
			try
			{
				originChunkIntersectionResult = getChunkRenderComponent(originChunk)
						.getBlockBeforeRayIntersect(origin, direction, radius, step).toWorldCoordinates();
			} catch (Exception e)
			{
			}
		}

		if (originChunkIntersectionResult != null)
			return originChunkIntersectionResult;

		if (directionChunk != null)
		{
			try
			{
				directionChunkIntersectionResult = getChunkRenderComponent(directionChunk)
						.getBlockBeforeRayIntersect(origin, direction, radius, step).toWorldCoordinates();
			} catch (Exception e)
			{
			}
		}

		if (directionChunkIntersectionResult != null)
			return directionChunkIntersectionResult;

		return null;
	}

	public void setBlock(int x, int y, int z, Block value)
	{
		Chunk chunk = getChunkContainingBlock(x, z);

		if (chunk != null)
		{
			chunk.setBlock(x, y, z, value);
			getChunkRenderComponent(chunk).reconstructMesh();
		}
	}

	public void setBlock(int x, int y, int z, BlockType type)
	{
		setBlock(x, y, z, new Block(x, y, z, type, getChunkContainingBlock(x, z)));
	}

	public boolean hasBlock(int x, int z)
	{
		return getChunkContainingBlock(x, z) != null;
	}

	public Chunk getChunkContainingBlock(int x, int z)
	{
		try
		{
			return chunks[Math.round(x / chunkWidth)][Math.round(z / chunkLength)];
		} catch (Exception e)
		{
		}

		return null;
	}

	public Vector3f convertToChunkCoordinates(int x, int y, int z, Chunk chunk)
	{
		return new Vector3f(x - (chunk.getWidth() * chunk.getX()), y, z - (chunk.getHeight() * chunk.getY()));
	}

	public Vector3f convertToWorldCoordinates(int x, int y, int z, Chunk chunk)
	{
		return new Vector3f(x + (chunk.getWidth() * chunk.getX()), y, z + (chunk.getHeight() * chunk.getY()));
	}

	public int getChunkWidth()
	{
		return chunkWidth;
	}

	public int getChunkHeight()
	{
		return chunkHeight;
	}

	public int getChunkLength()
	{
		return chunkLength;
	}

	public Chunk[][] getChunks()
	{
		return chunks;
	}

	public ChunkObject getChunkRenderComponent(Chunk chunk)
	{
		try
		{
			return chunkObjects[chunk.getX()][chunk.getY()];
		} catch (Exception e)
		{
		}

		return null;
	}

}
