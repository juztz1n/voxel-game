#version 330

in vec3 Position;
in vec2 TexCoords;

out vec2 texCoords;

uniform mat4 transform_View;
uniform mat4 transform_Projection;
uniform mat4 transform_Model;

void main()
{
	gl_Position = transform_Projection * transform_View * transform_Model * vec4(Position * vec3(.501), 1.0);
	
	texCoords = TexCoords;
}