package zin.voxelGame.utils;

public class DefaultBlock
{

	public static final float LEFT_FACE[] =
	{ 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f };

	public static final float RIGHT_FACE[] =
	{ 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f };

	public static final float FRONT_FACE[] =
	{ 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f };

	public static final float BACK_FACE[] =
	{ 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f };

	public static final float BOTTOM_FACE[] =
	{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };

	public static final float TOP_FACE[] =
	{ 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f };

	public static final float TEXCOORDS_FACE[] =
	{ 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f };

	public static final float[] TEXCOORDS_GRASS_TOP =
	{ -0.33333334f, -0.33333334f, -0.33333334f, -0.6666667f, -0.6666667f, -0.6666667f, -0.6666667f, -0.6666667f,
			-0.6666667f, -0.33333334f, -0.33333334f, -0.33333334f, };

	public static final float[] TEXCOORDS_GRASS_SIDE =
	{ -0.6666667f, -0.33333334f, -0.6666667f, -0.6666667f, -1.0f, -0.6666667f, -1.0f, -0.6666667f, -1.0f, -0.33333334f,
			-0.6666667f, -0.33333334f, };

	public static final float[] TEXCOORDS_OAK_LEAVES =
	{ -0.0f, -0.33333334f, -0.0f, -0.6666667f, -0.33333334f, -0.6666667f, -0.33333334f, -0.6666667f, -0.33333334f,
			-0.33333334f, -0.0f, -0.33333334f, };

	public static final float[] TEXCOORDS_OAK_PLANKS =
	{ -0.0f, -0.6666667f, -0.0f, -1.0f, -0.33333334f, -1.0f, -0.33333334f, -1.0f, -0.33333334f, -0.6666667f, -0.0f,
			-0.6666667f, };

	public static final float[] TEXCOORDS_OAK_LOG_TOP =
	{ -0.33333334f, -0.6666667f, -0.33333334f, -1.0f, -0.6666667f, -1.0f, -0.6666667f, -1.0f, -0.6666667f, -0.6666667f,
			-0.33333334f, -0.6666667f, };

	public static final float[] TEXCOORDS_OAK_LOG_SIDE =
	{ -0.6666667f, -0.6666667f, -0.6666667f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -0.6666667f, -0.6666667f,
			-0.6666667f, };

	public static final float[] TEXCOORDS_COBBLESTONE =
	{ -0.33333334f, -0.0f, -0.33333334f, -0.33333334f, -0.6666667f, -0.33333334f, -0.6666667f, -0.33333334f,
			-0.6666667f, -0.0f, -0.33333334f, -0.0f, };

	public static final float[] TEXCOORDS_STONE =
	{ -0.6666667f, -0.0f, -0.6666667f, -0.33333334f, -1.0f, -0.33333334f, -1.0f, -0.33333334f, -1.0f, -0.0f,
			-0.6666667f, -0.0f, };
	public static final float[] TEXCOORDS_DIRT =
	{ -0.0f, -0.0f, -0.0f, -0.33333334f, -0.33333334f, -0.33333334f, -0.33333334f, -0.33333334f, -0.33333334f, -0.0f,
			-0.0f, -0.0f, };

}
