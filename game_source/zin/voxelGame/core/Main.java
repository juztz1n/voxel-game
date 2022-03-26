
package zin.voxelGame.core;

import zin.gammaEngine.core.Game;

public class Main
{

	private static Game game;

	public static void main(String[] args)
	{
		game = new VoxelGame();
	}

	public static Game getGame()
	{
		return game;
	}

}
