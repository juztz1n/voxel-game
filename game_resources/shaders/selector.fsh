#version 330

in vec2 texCoords;

out vec4 Color;

uniform vec3 color;

void main()
{
    Color = vec4(color, 1.0);
}  