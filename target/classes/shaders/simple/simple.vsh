#version 330 core

in vec3 input_Position;
in vec3 input_Normal;
in vec2 input_TexCoords;

out vec3 Normal;

uniform mat4 transform_Model;
uniform mat4 transform_View;
uniform mat4 transform_Projection;

void main()
{
	gl_Position = transform_Projection * transform_View * transform_Model * vec4(input_Position, 1.0);

	Normal = input_Normal;
}