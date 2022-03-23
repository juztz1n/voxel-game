#include "display.h"

GLFWwindow* Display::identifier;
int Display::width, Display::height;
Display::DisplayState Display::state;
const char* Display::title;
bool Display::currentKeys[GLFW_KEY_LAST];
bool Display::currentButtons[GLFW_MOUSE_BUTTON_LAST];
glm::vec2 Display::scrollWheel;
bool Display::grabbed, Display::focused;
double Display::newX, Display::newY, Display::prevX, Display::prevY, Display::dx, Display::dy;
bool Display::rotX, Display::rotY;
float Display::opacity;

static void focusCallback(GLFWwindow* identifier, int focused)
{
	Display::setFocusedInternal(focused == 0 ? false : true);
}

static void scrollWheelCallback(GLFWwindow* identifier, double xOffset, double yOffset)
{
	glm::vec2 scroll = Display::getScrollWheel();

	Display::setScrollWheelInternal(glm::vec2(scroll.x + xOffset, scroll.y + yOffset));
}

static void framebufferCallback(GLFWwindow* identifier, int width, int height)
{
	Display::setWidthInternal(width);
	Display::setHeightInternal(height);

	glViewport(0, 0, width, height);
}

void Display::create(int width, int height, const char* title, DisplayState state)
{
	Display::width = width;
	Display::height = height;
	Display::title = title;
	Display::state = state;

	if (!glfwInit())
	{
		std::cerr << "Failed to initialize GLFW. Terminating." << std::endl;
		exit(-1);
	}

	glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

	switch(state)
	{
		case DisplayState::WINDOWED:
			identifier = glfwCreateWindow(width, height, title, NULL, NULL);
			glfwSetFramebufferSizeCallback(identifier, framebufferCallback);
			break;
		case DisplayState::BORDERLESS:
			glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
			identifier = glfwCreateWindow(width, height, title, NULL, NULL);
			break;
		case DisplayState::FULLSCREEN:
			identifier = glfwCreateWindow(width, height, title, NULL, NULL);
			break;
		default:
			identifier = glfwCreateWindow(width, height, title, NULL, NULL);
			break;
	}

	glfwMakeContextCurrent(identifier);
	
	if (glewInit() != GLEW_OK)
	{
		std::cerr << "Failed to initialize GLEW. Terminating." << std::endl;
		exit(-1);
	}

	glewExperimental = GL_TRUE;
	
	glfwSwapInterval(0);

	glEnable(GL_DEPTH_TEST);
	glEnable(GL_TEXTURE_2D);
	glEnable(GL_CULL_FACE);
	glCullFace(GL_BACK);

	glFrontFace(GL_CW);

	glViewport(0, 0, width, height);

	glfwSetWindowFocusCallback(identifier, focusCallback);
	glfwSetScrollCallback(identifier, scrollWheelCallback);

	for (int i = 0; i < GLFW_KEY_LAST; i++)
		currentKeys[i] = false;

	for (int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++)
		currentButtons[i] = false;

	newX = width / 2.0;
	newY = height / 2.0;

	prevX = 0;
	prevY = 0;

	rotX = false;
	rotY = false;
}

void Display::updateInput()
{
	for (int i = 32; i < GLFW_KEY_LAST; i++)
		currentKeys[i] = isKeyDown(i);

	for (int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++)
		currentButtons[i] = isMouseButtonDown(i);

	if (grabbed)
	{
		glfwGetCursorPos(identifier, &newX, &newY);

		double deltaX = newX - width / 2;
		double deltaY = newY - height / 2;

		rotX = newX != prevX;
		rotY = newY != prevY;

		if (rotX)
			dx = deltaX;

		if (rotY)
			dy = deltaY;

		prevX = newX;
		prevY = newY;

		glfwSetCursorPos(identifier, width / 2, height / 2);
	}
	else
	{
		dy = dx = 0;
	}
}

void Display::update()
{
	updateInput();
	glfwPollEvents();
	glfwSwapBuffers(identifier);
}

Display::~Display()
{
	glfwDestroyWindow(identifier);
	glfwTerminate();
}
