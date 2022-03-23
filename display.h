#ifndef DISPLAY_H
#define DISPLAY_H

#include <iostream>

#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/vec2.hpp>
#include <glm/gtx/string_cast.hpp>

class Display
{
	public:
		enum class DisplayState
		{
			WINDOWED, BORDERLESS, FULLSCREEN
		};

	private:
		static const char* title;
		static int width, height;
		static DisplayState state;
		static GLFWwindow* identifier;

		static bool currentKeys[];
		static bool currentButtons[];
		static glm::vec2 scrollWheel;

		static bool grabbed, focused;

		static double newX, newY, prevX, prevY, dx, dy;
		static bool rotX, rotY;

		static float opacity;

		static void updateInput();
	public:
		~Display();

		static void create(int width, int height, const char* title, DisplayState state);
		static void update();

		static bool shouldClose() { return glfwWindowShouldClose(identifier); }
		static int getWidth() { return width; }
		static int getHeight() { return height; }
		static const char* getTitle() { return title; }
		static DisplayState getState() { return state; }
		static GLFWwindow* getIdentifier() { return identifier; }

		static int getMonitorWidth() { return glfwGetVideoMode(glfwGetPrimaryMonitor())->width; }
		static int getMonitorHeight() { return glfwGetVideoMode(glfwGetPrimaryMonitor())->height; }
		static int getRefreshRate() { return glfwGetVideoMode(glfwGetPrimaryMonitor())->refreshRate; }

		static bool isFocused() { return focused; }
		static float getOpacity() { return opacity; }
		static void setOpactiy(float value)
		{ glfwSetWindowOpacity(identifier, value); opacity = value; }
		static glm::vec2 getDeltaMouse() { return glm::vec2((float) dx, (float) dy); }
		static float getAspectRatio() { return (float) getWidth() / (float) getHeight(); }

		static void hide() { glfwHideWindow(identifier); }
		static void show() { glfwShowWindow(identifier); }

		static void setFocusedInternal(bool focused) { Display::focused = focused; }
		static void setWidthInternal(int width) { Display::width = width; }
		static void setHeightInternal(int height) { Display::height = height; }
		static void setScrollWheelInternal(glm::vec2 scrollWheel) { Display::scrollWheel = scrollWheel; }

		static bool isKeyDown(int keyCode) { return glfwGetKey(identifier, keyCode) == 1; }
		static bool isKeyPressed(int keyCode) { return (isKeyDown(keyCode) && !currentKeys[keyCode]); }
		static bool isKeyReleased(int keyCode) { return (!isKeyDown(keyCode) && currentKeys[keyCode]); }
		static bool isMouseButtonDown(int keyCode) { return glfwGetMouseButton(identifier, keyCode) == 1; }
		static bool isMouseButtonPressed(int keyCode) { return (isMouseButtonDown(keyCode) && !currentButtons[keyCode]); }
		static bool isMouseButtonReleased(int keyCode) { return (!isMouseButtonDown(keyCode) && currentButtons[keyCode]); }
		static glm::vec2 getScrollWheel() { return scrollWheel; }

		static bool cursorIsGrabbed() { return grabbed; }
		static void setCursorGrabbed(bool grabbed)
		{
			Display::grabbed = grabbed;

			if (grabbed)
			{
				glfwSetInputMode(identifier, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
			}
			else
			{
				glfwSetInputMode(identifier, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
			}
			glfwSetCursorPos(identifier, width / 2, height / 2);
		}
};

#endif