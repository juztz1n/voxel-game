package zin.voxelGame.core;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import zin.gammaEngine.core.Game;
import zin.gammaEngine.core.componentSystem.GameComponent;
import zin.gammaEngine.core.componentSystem.GameObject;
import zin.gammaEngine.graphics.Display;
import zin.gammaEngine.graphics.components.FreeMoveComponent;
import zin.gammaEngine.graphics.components.ModelComponent;
import zin.gammaEngine.graphics.components.ShaderComponent;
import zin.gammaEngine.graphics.components.SkyboxComponent;
import zin.gammaEngine.graphics.utils.DisplayState;
import zin.voxelGame.utils.Block;
import zin.voxelGame.utils.BlockType;
import zin.voxelGame.utils.Chunk;
import zin.voxelGame.utils.WorldObject;

public class VoxelGame extends Game
{

	private FreeMoveComponent camera;

//	private ArrayList<ChunkObject> chunkObjects;
	private GameObject selector, line;
	private WorldObject worldObject;
	private ShaderComponent shader;
	private Vector3f normalized_forward_vec;
//	private ChunkObject chunkObject;

	public VoxelGame()
	{
		super("Voxel Game", 1280, 720, DisplayState.BORDERLESS, 240);
		setFOV(120);
	}

	@Override
	public void init()
	{
		camera = new FreeMoveComponent();

		line = new GameObject();
		shader = new ShaderComponent("game_resources/shaders/selector.vsh", "game_resources/shaders/selector.fsh") {

			@Override
			public boolean init()
			{
				boolean success = super.init();

				bind();
				addUniform("color");
				setUniform("color", new Vector3f(0, 0, 0));
				return success;
			}
		};
		line.addComponent(
				new ShaderComponent("game_resources/shaders/selector.vsh", "game_resources/shaders/selector.fsh")
				{
					@Override
					public boolean init()
					{
						boolean success = super.init();

						bind();
						addUniform("color");
						setUniform("color", new Vector3f(1, 0, 0));
						return success;
					}

					@Override
					public void preRender()
					{
						super.preRender();
						setUniform("transform_Model", new Matrix4f().identity().translate(getParent().getPosition()));
					}
				});

		normalized_forward_vec = camera.getForward().normalize();
		line.addComponent(new GameComponent() {
			@Override
			public void render()
			{

				GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex3f(0, 0, 0);
				GL11.glVertex3f(normalized_forward_vec.x * 10, normalized_forward_vec.y * 10,
						normalized_forward_vec.z * 10);
				GL11.glEnd();

				GL11.glPointSize(10.0f);
				GL11.glBegin(GL11.GL_POINTS);
				GL11.glVertex3f(normalized_forward_vec.x * 10, normalized_forward_vec.y * 10,
						normalized_forward_vec.z * 10);
				GL11.glEnd();

				super.render();
			}
		});

		addObject(line);

		GameObject cameraObject = new GameObject();
		cameraObject.addComponent(camera);
		cameraObject.setScale(new Vector3f(1.0f));
		addObject(cameraObject);

		addComponent(new SkyboxComponent(new String[]
		{ "game_resources/skybox/zpos.jpg", "game_resources/skybox/zneg.jpg", "game_resources/skybox/ypos.jpg",
				"game_resources/skybox/yneg.jpg", "game_resources/skybox/xpos.jpg",
				"game_resources/skybox/xneg.jpg" }));

//		chunkObjects = new ArrayList<ChunkObject>();

		selector = new GameObject();
		selector.addComponent(new ModelComponent("game_resources/cube.obj"));
		selector.addComponent(new GameComponent() {
			@Override
			public void preRender()
			{
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
				super.preRender();
			}

			@Override
			public void postRender()
			{
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
				super.postRender();
			}
		});
		selector.addComponent(shader);

		addObject(selector);

		Chunk[][] chunks = new Chunk[][]
		{
				{ new Chunk(16, 16, 16, 0, 0), new Chunk(16, 16, 16, 0, 1), new Chunk(16, 16, 16, 1, 1),
						new Chunk(16, 16, 16, 1, 0) } };

		worldObject = new WorldObject(16, 16, 16, chunks);

		addObject(worldObject);
	}

	@Override
	public void loop()
	{
		Block ray = worldObject.getFirstRayIntersect(new Vector3f(camera.getParent().getPosition()),
				new Vector3f(camera.getForward()).normalize(), 5.0f, 0.1f);

		if (ray != null)
		{
			selector.setPosition(ray.getX(), ray.getY(), ray.getZ());
			selector.setScale(new Vector3f(1));

			if (Display.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_RIGHT))
			{
				Block block_before = worldObject.getBlockBeforeRayIntersect(
						new Vector3f(camera.getParent().getPosition()), new Vector3f(camera.getForward()).normalize(),
						5.0f, 0.1f);

				if (block_before != null)
				{
					int player_x = Math.round(camera.getParent().getPosition().x);
					int player_y = Math.round(camera.getParent().getPosition().y);
					int player_z = Math.round(camera.getParent().getPosition().z);

					if (!((player_x == block_before.getX()) && (player_y == block_before.getY())
							&& (player_z == block_before.getZ())))
					{
						worldObject.setBlock(block_before.getX(), block_before.getY(), block_before.getZ(),
								BlockType.COBBLESTONE);
					}
				}

				line.setPosition(camera.getParent().getPosition().x, camera.getParent().getPosition().y,
						camera.getParent().getPosition().z);

				normalized_forward_vec = camera.getForward().normalize();
			}

			if (Display.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_LEFT))
			{
				worldObject.setBlock(ray.getX(), ray.getY(), ray.getZ(), BlockType.AIR);

				line.setPosition(camera.getParent().getPosition().x, camera.getParent().getPosition().y,
						camera.getParent().getPosition().z);

				normalized_forward_vec = camera.getForward().normalize();
			}
		} else
		{
			selector.setPosition(new Vector3f(0, 0, 0));
			selector.setScale(new Vector3f(0, 0, 0));
		}

		if (Display.isKeyReleased(GLFW.GLFW_KEY_LEFT_ALT))
			Display.setCursorGrabbed(!Display.cursorIsGrabbed());

		if (Display.isKeyReleased(GLFW.GLFW_KEY_ESCAPE))
			requestClose();
	}

}
