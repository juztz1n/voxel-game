#version 330 core

in vec3 input_Position;
in vec2 input_TexCoords;
in vec3 input_Normal;
in vec3 input_Tangent;
in vec3 input_biTangent;

out vec2 TexCoords;

out vec3 WorldPos;
out vec3 ViewPos;

out mat3 TBN;	

uniform mat4 transform_Projection;
uniform mat4 transform_Model;
uniform mat4 transform_View;

void main()
{
    WorldPos = vec3(transform_Model * vec4(input_Position, 1.0));
    TexCoords = input_TexCoords;
	
	TBN = mat3(
		normalize(mat3(transform_Model) * input_Tangent),
		normalize(mat3(transform_Model) * input_biTangent),
		normalize(mat3(transform_Model) * input_Normal)
	);
		
	ViewPos = (inverse(transform_View)[3]).xyz;
   
    gl_Position =  transform_Projection * transform_View * transform_Model * vec4(input_Position, 1.0);
}