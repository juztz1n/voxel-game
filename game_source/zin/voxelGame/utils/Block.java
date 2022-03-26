package zin.voxelGame.utils;

public class Block
{

	private Chunk parent;
	private int x, y, z;
	private BlockType type;

	public Block(int x, int y, int z, BlockType type, Chunk parent)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
		this.parent = parent;
	}

	public void setParent(Chunk parent)
	{
		this.parent = parent;
	}

	public Chunk getParent()
	{
		return parent;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getZ()
	{
		return z;
	}

	public void setType(BlockType type)
	{
		this.type = type;
	}

	public BlockType getType()
	{
		return type;
	}

	public Block toWorldCoordinates()
	{
		x = x + (parent.getWidth() * parent.getX());
		z = z + (parent.getHeight() * parent.getY());

		return this;
	}

	public Block toChunkCoordinates()
	{
		x = x - (parent.getWidth() * parent.getX());
		z = z - (parent.getHeight() * parent.getY());

		return this;
	}

}
