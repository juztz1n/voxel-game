#version 330 core

in vec3 Normal;

out vec4 Color;

void main()
{
    float phong = dot(Normal, vec3(-1.0));
    phong = max(phong, 0.15);

	Color = vec4(vec3(phong), 1.0);
}