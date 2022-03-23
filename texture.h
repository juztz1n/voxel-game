#ifndef TEXTURE_H
#define TEXTURE_H

#include <iostream>

#define STB_IMAGE_IMPLEMENTATION
#include "stbi_image.h"

#include <GL/glew.h>


class Texture
{
    public:
        unsigned int identifier;
        const char* fileName;

        Texture(const char* fileName) 
        {
            this->fileName = fileName;
        }

        bool init() 
        {
            glGenTextures(1, &identifier);
            glBindTexture(GL_TEXTURE_2D, identifier); 

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            int width, height, nrChannels;
            stbi_set_flip_vertically_on_load(true); 

            unsigned char *data = stbi_load(
                fileName, 
                &width, 
                &height, 
                &nrChannels, 
                0
            );

            if (data)
            {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
                return true;
            }
            else
            {
                std::cerr << "Failed to load texture" << std::endl;
                return false;
            }
        };

        void bind() { glBindTexture(GL_TEXTURE_2D, identifier);};
        void unbind() { glBindTexture(GL_TEXTURE_2D, 0); };
};

#endif