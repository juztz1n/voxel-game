#ifndef SHADER_H
#define SHADER_H

#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <map>

#include <GL/glew.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

class Shader
{
	private:
		std::map<const char*, unsigned int> uniforms;

		GLuint identifier;

		const char* name;
		const char* vertexFileName;
		const char* fragmentFileName;

		unsigned int loadShader(const char* path, unsigned int type);
	public:
		Shader(const char* vertexFileName, const char* fragmentFileName);
		~Shader();
		bool init();
		void preRender(glm::mat4x4 model, glm::mat4x4 view, glm::mat4x4 proj);
		void postRender();

		void bind() { glUseProgram(identifier); }
		void unbind() { glUseProgram(0); }

		unsigned int getIdentifier() { return identifier; }

		void setAttribute(int index, const char* name)
		{ glBindAttribLocation(identifier, index, name); }
		void addUniform(const char* name)
		{  uniforms[name] = glGetUniformLocation(identifier, name); }
		bool exists(const char* name) { return !(uniforms[name] == NULL); }
		int getUniform(const char* name) 
		{ 
			if(!exists(name))
				addUniform(name);

			return glGetUniformLocation(identifier, name); 
		}

		void setUniform(const char* name, glm::vec2 value)
		{
			glUniform2f(getUniform(name), value.x, value.y);
		}

		void setUniform(const char* name, glm::vec3 value)
		{
			glUniform3f(getUniform(name), value.x, value.y, value.z);
		}

		void setUniform(const char* name, glm::mat4 value)
		{
			glUniformMatrix4fv(getUniform(name), 1, GL_FALSE, glm::value_ptr(value));
		}

		void setUniform(const char* name, float value)
		{
			glUniform1f(getUniform(name), value);
		}

		void setUniform(const char* name, int value)
		{
			glUniform1i(getUniform(name), value);
		}

};

#endif