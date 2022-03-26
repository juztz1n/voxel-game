package zin.voxelGame.utils;

import java.util.Random;

import zin.gammaEngine.core.utils.Logger;

public class Chunk
{

	private int width, height, length;
	private int x, y;

	private Block[][][] blocks;

	public Chunk(int width, int height, int length, int x, int y)
	{
		this.width = width;
		this.height = height;
		this.length = length;
		this.x = x;
		this.y = y;

		blocks = new Block[width][height][length];
	}

	public Chunk populate()
	{
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				for (int z = 0; z < length; z++)
				{
					BlockType type;

					switch (new Random().nextInt(0, 4))
					{
					case 0:
						type = BlockType.COBBLESTONE;
						break;
					case 1:
						type = BlockType.STONE;
						break;
					case 2:
						type = BlockType.DIRT;
						break;
					default:
						type = BlockType.AIR;
					}

					blocks[x][y][z] = new Block(x, y, z, type, this);
				}
			}
		}

		return this;
	}

	public void setBlock(int x, int y, int z, Block value)
	{
		blocks[x][y][z] = value;
	}

	public void setBlock(int x, int y, int z, BlockType type)
	{
		setBlock(x, y, z, new Block(x, y, z, type, this));
	}

	public Block getBlock(int x, int y, int z)
	{
		return blocks[x][y][z];
	}

	public Block getBlockInWorldCoordinates(int x, int y, int z)
	{
		Block block = blocks[x][y][z];

		return new Block(x + (width * this.x), y, z + (length * this.y), block.getType(), this);
	}

	public void setBlocks(Block[][][] value)
	{
		if (blocks.length != width)
		{

			Logger.error("Request to set blocks in chunk (" + x + ", " + y
					+ ") failed because new value is too wide or not wide enough.");
			return;
		}

		if (blocks[0].length != height)
		{
			Logger.error("Request to set blocks in chunk (" + x + ", " + y
					+ ") failed because new value is too tall or not tall enough.");
			return;
		}

		if (blocks[0][0].length != length)
		{
			Logger.error("Request to set blocks in chunk (" + x + ", " + y
					+ ") failed because new value is too long or not long enough.");
			return;
		}

		blocks = value;
	}

	public Block[][][] getBlocks()
	{
		return blocks;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getLength()
	{
		return length;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public boolean hasBlock(int x, int y, int z)
	{
		try
		{
			return getBlock(x, y, z) != null;
		} catch (Exception e)
		{

		}

		return false;
	}

	public boolean hasSolidBlock(int x, int y, int z)
	{
		try
		{
			return (getBlock(x, y, z) != null) && (getBlock(x, y, z).getType() != BlockType.AIR);
		} catch (Exception e)
		{

		}

		return false;
	}

}
