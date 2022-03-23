#include "shader.h"

Shader::Shader(const char* vertexFileName, const char* fragmentFileName)
{
	this->name = std::string(vertexFileName).append(fragmentFileName).c_str();
	this->vertexFileName = vertexFileName;
	this->fragmentFileName = fragmentFileName;
}

bool Shader::init()
{
	identifier = glCreateProgram();

	unsigned int vertexShader = loadShader(vertexFileName, GL_VERTEX_SHADER);
	unsigned int fragmentShader = loadShader(fragmentFileName, GL_FRAGMENT_SHADER);

	glAttachShader(identifier, vertexShader);
	glAttachShader(identifier, fragmentShader);
	glLinkProgram(identifier);

	int successful;

	char infoLog[512];
	glGetProgramiv(identifier, GL_LINK_STATUS, &successful);
	if(!successful)
	{
		glGetProgramInfoLog(identifier, 512, NULL, infoLog);
		std::cerr << "Failed to link shader program:\n" << infoLog << std::endl;

		return false;
	}

	glDetachShader(identifier, vertexShader);
	glDetachShader(identifier, fragmentShader);
	glDeleteShader(vertexShader);
	glDeleteShader(fragmentShader);

	bind();
	setAttribute(0, "input_Position");
	setAttribute(1, "input_TexCoords");

	addUniform("transform_Model");
	addUniform("transform_View");
	addUniform("transform_Projection");
	addUniform("text");

	return true;
}

unsigned int Shader::loadShader(const char* path, unsigned int type)
{
	unsigned int identifier;
	std::string source;
	std::ifstream stream;

	stream.exceptions(std::ifstream::failbit | std::ifstream::badbit);

	try
	{
		stream.open(path);

		std::stringstream stringStream;

		stringStream << stream.rdbuf();

		stream.close();
		
		source = stringStream.str();
	}
	catch (std::ifstream::failure e)
	{
		std::cerr << "Failed to read shader file." << std::endl;
	}

	const char* sourceChar = source.c_str();

	identifier = glCreateShader(type);
	glShaderSource(identifier, 1, &sourceChar, NULL);
	glCompileShader(identifier);

	int successful;

	glGetShaderiv(identifier, GL_COMPILE_STATUS, &successful);

	if (!successful)
	{
		char infoLog[512];
		glGetShaderInfoLog(identifier, 512, NULL, infoLog);

		std::cerr << "Failed to compile shader: " << infoLog << std::endl;
	}

	return identifier;
}

void Shader::preRender(glm::mat4x4 model, glm::mat4x4 view, glm::mat4x4 proj)
{
	bind();
	setUniform("transform_Model", model);
	setUniform("transform_View", view);
	setUniform("transform_Projection", proj);
	setUniform("text", (int) 0);
}

void Shader::postRender()
{
	unbind();
}

Shader::~Shader()
{
	glDeleteProgram(identifier);
}