#version 330

in vec2 texCoords;

out vec4 Color;

uniform sampler2D diffuse;

void main()
{
    Color = texture(diffuse, texCoords);
}  