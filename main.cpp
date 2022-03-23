#include <iostream>

#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include "shader.h"
#include "display.h"
#include "camera.h"
#include "chunk.h"

#define WIDTH 1280
#define HEIGHT 720
#define TITLE "Voxel Game"
#define STATE Display::DisplayState::WINDOWED

int main()
{
    Display::create(WIDTH, HEIGHT, TITLE, STATE);
    Camera camera = Camera();

    Display::show();

    Chunk chunk = Chunk();
    chunk.init();

    glm::mat4x4 proj = glm::perspective(70.0f, Display::getAspectRatio(), 0.1f, 1000.0f);

    glEnable(GL_CULL_FACE);
    glCullFace(GL_BACK);
    while(!Display::shouldClose() && !Display::isKeyReleased(GLFW_KEY_ESCAPE))
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        camera.input(1/5000.0f);

        if(Display::isKeyReleased(GLFW_KEY_R))
            chunk.reloadShader();

        if(Display::isKeyReleased(GLFW_KEY_LEFT_ALT))
            Display::setCursorGrabbed(!Display::cursorIsGrabbed());

        if(Display::isKeyReleased(GLFW_KEY_P))
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        
        if(Display::isKeyReleased(GLFW_KEY_O))
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        chunk.render(camera.getTransformView(), proj);
        
        Display::update();
    }

    return 0;
}