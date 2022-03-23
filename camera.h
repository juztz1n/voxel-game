#include <glm/vec3.hpp>
#include <glm/gtc/quaternion.hpp>
#include <glm/gtc/matrix_transform.hpp>

#include "display.h"

class Camera
{
    private:
        glm::vec3 position, forward, right, up;
        float pitch, yaw;
    public:
        Camera()
        {
            position = glm::vec3(0, 0, 0);
            forward = glm::vec3(0, 0, 1);
            right = glm::vec3();
            up = glm::vec3(0, 1, 0);
        }

        void input(float frame_time)
        {
            forward = glm::normalize(glm::vec3(
                glm::cos(glm::radians(yaw)) * glm::cos(glm::radians(pitch)),
                glm::sin(glm::radians(pitch)),
                glm::sin(glm::radians(yaw)) * glm::cos(glm::radians(pitch))
            ));

            right = glm::normalize(glm::cross(glm::vec3(0, 1, 0), forward));
            up = glm::normalize(glm::cross(forward, right));

            float cameraSpeed = 5.0f * frame_time;
            yaw += (float) Display::getDeltaMouse().x * frame_time * 250.0f;
            pitch -= (float) Display::getDeltaMouse().y * frame_time * 250.0f;
            
            pitch = pitch > -90.0f ? pitch : -90.0f;
            pitch = pitch > 90.0f ? 90.0f : pitch;
            
            if (yaw > 360.0f)
                yaw = -360.0f;
            if (yaw < -360.0f)
                yaw = 360.0f;

            if (Display::isKeyDown(GLFW_KEY_W))
                position += forward * cameraSpeed;
            if (Display::isKeyDown(GLFW_KEY_A))
                position += right * cameraSpeed;
            if (Display::isKeyDown(GLFW_KEY_S))
                position -= forward * cameraSpeed;
            if (Display::isKeyDown(GLFW_KEY_D))
                position -= right * cameraSpeed;

            if (Display::isKeyDown(GLFW_KEY_SPACE))
                position += glm::vec3(0, 1, 0) * cameraSpeed;
            if (Display::isKeyDown(GLFW_KEY_LEFT_SHIFT))
                position -= glm::vec3(0, 1, 0) * cameraSpeed;
        }

        glm::mat4 getTransformView()
        {
            return glm::lookAt(position, position + forward, up);
        }
};