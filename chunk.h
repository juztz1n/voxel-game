#ifndef CHUNK_H
#define CHUNK_H

#include <iostream>
#include <vector>

#include <GL/glew.h>

#include <glm/gtc/matrix_transform.hpp>
#include <glm/common.hpp>

#include "texture.h"


            
const float vertices_left[] = 
{
    0.0f, 0.0f, 0.0f,  // LEFT NEG X
    1.0f, 0.0f, 0.0f,  
    1.0f,  1.0f, 0.0f, 

    1.0f,  1.0f, 0.0f, 
    0.0f,  1.0f, 0.0f, 
    0.0f, 0.0f, 0.0f,  
};
           
const float vertices_right[] = 
{
    0.0f, 0.0f,  1.0f, // RIGHT POS X
    0.0f,  1.0f,  1.0f,
    1.0f,  1.0f,  1.0f,

    1.0f,  1.0f,  1.0f,
    1.0f, 0.0f,  1.0f, 
    0.0f, 0.0f,  1.0f, 
};
           
const float vertices_front[] = 
{
    0.0f,  1.0f,  1.0f,
    0.0f, 0.0f,  1.0f, 
    0.0f, 0.0f, 0.0f,  

    0.0f, 0.0f, 0.0f,  
    0.0f,  1.0f, 0.0f, 
    0.0f,  1.0f,  1.0f,// FRONT POS? Z
};
           
const float vertices_back[] = 
{
    1.0f,  1.0f,  1.0f,// BACK NEG? Z
    1.0f,  1.0f, 0.0f, 
    1.0f, 0.0f, 0.0f,  

    1.0f, 0.0f, 0.0f,  
    1.0f, 0.0f,  1.0f, 
    1.0f,  1.0f,  1.0f,
};
           
const float vertices_bottom[] = 
{
    0.0f, 0.0f, 0.0f,  
    0.0f, 0.0f,  1.0f, 
    1.0f, 0.0f,  1.0f, 

    1.0f, 0.0f,  1.0f, 
    1.0f, 0.0f, 0.0f,  
    0.0f, 0.0f, 0.0f,  // BOTTOM NEG Y
};
           
const float vertices_top[] = 
{
    0.0f,  1.0f, 0.0f, // TOP POS Y
    1.0f,  1.0f, 0.0f, 
    1.0f,  1.0f,  1.0f,

    1.0f,  1.0f,  1.0f,
    0.0f,  1.0f,  1.0f,
    0.0f,  1.0f, 0.0f, 
};

const float texCoords_left[] =
{
    0.0f, 0.0f, // LEFT NEG X
    1.0f, 0.0f,
    1.0f, 1.0f,

    1.0f, 1.0f,
    0.0f, 1.0f,
    0.0f, 0.0f,
};
           
const float texCoords_right[] = 
{
    0.0f, 0.0f, // RIGHT POS X
    1.0f, 0.0f,
    1.0f, 1.0f,

    1.0f, 1.0f,
    0.0f, 1.0f,
    0.0f, 0.0f,
};
           
const float texCoords_front[] = 
{
    1.0f, 0.0f, // FRONT POS? Z
    1.0f, 1.0f,
    0.0f, 1.0f,

    0.0f, 1.0f,
    0.0f, 0.0f,
    1.0f, 0.0f,
};
           
const float texCoords_back[] = 
{
    1.0f, 0.0f, // BACK NEG? Z
    1.0f, 1.0f,
    0.0f, 1.0f,

    0.0f, 1.0f,
    0.0f, 0.0f,
    1.0f, 0.0f,
};
           
const float texCoords_bottom[] = 
{
    0.0f, 1.0f, // BOTTOM NEG Y
    1.0f, 1.0f,
    1.0f, 0.0f,

    1.0f, 0.0f,
    0.0f, 0.0f,
    0.0f, 1.0f,
};
           
const float texCoords_top[] = 
{
    0.0f, 1.0f, // TOP POS Y
    1.0f, 1.0f,
    1.0f, 0.0f,

    1.0f, 0.0f,
    0.0f, 0.0f,
    0.0f, 1.0f
};

class Block
{
    public:
        int x, y, z;
        int block_type;   

        Block(int x, int y, int z) 
        { 
            this->x = x;
            this->y = y;
            this->z = z;
        } 
};

class Chunk
{
    private:
        std::vector<Block*> blocks;
        unsigned int VBO, TCBO, VAO;
        Texture texture;
        Shader shader;
        int vertex_count = 0;

        glm::mat4x4 model;
    public:
        Chunk() : texture("textures/dirt.png"), shader("shaders/vertex", "shaders/frag")
        {
            blocks = std::vector<Block*>();
        }

        void init()
        {
            model = glm::translate(glm::mat4x4(1.0), glm::vec3(0, 0, -1));

            blocks.push_back(new Block(0, 0, 0)); 
            blocks.push_back(new Block(0, 1, 0)); 
            shader = Shader("shaders/vertex", "shaders/frag");

            if(!shader.init())
                std::cerr << "Failed to load chunk shader." << std::endl; 

            vertex_count = 36 * blocks.size();

            std::vector<glm::vec3> vertices_vec = std::vector<glm::vec3>();

            std::vector<glm::vec2> texCoords_vec = std::vector<glm::vec2>();


            for(int i = 0; i < blocks.size(); i++)
            {
                float xOffset = blocks[i]->x;
                float yOffset = blocks[i]->y;
                float zOffset = blocks[i]->z;

                for(int j = 0; j < sizeof(vertices_left) / sizeof(float); j+=3)
                    vertices_vec.push_back(glm::vec3(vertices_left[j] + xOffset, vertices_left[j+1] + yOffset, vertices_left[j+2]) + zOffset);

                for(int j = 0; j < sizeof(texCoords_left) / sizeof(float); j+=2)
                    texCoords_vec.push_back(glm::vec2(texCoords_left[j], texCoords_left[j+1]));



                for(int j = 0; j < sizeof(vertices_left) / sizeof(float); j+=3)
                    vertices_vec.push_back(glm::vec3(vertices_right[j] + xOffset, vertices_right[j+1] + yOffset, vertices_right[j+2] + zOffset));

                for(int j = 0; j < sizeof(texCoords_left) / sizeof(float); j+=2)
                    texCoords_vec.push_back(glm::vec2(texCoords_right[j], texCoords_right[j+1]));



                for(int j = 0; j < sizeof(vertices_left) / sizeof(float); j+=3)
                    vertices_vec.push_back(glm::vec3(vertices_front[j] + xOffset, vertices_front[j+1] + yOffset, vertices_front[j+2] + zOffset));

                for(int j = 0; j < sizeof(texCoords_left) / sizeof(float); j+=2)
                    texCoords_vec.push_back(glm::vec2(texCoords_front[j], texCoords_front[j+1]));



                for(int j = 0; j < sizeof(vertices_left) / sizeof(float); j+=3)
                    vertices_vec.push_back(glm::vec3(vertices_back[j] + xOffset, vertices_back[j+1] + yOffset, vertices_back[j+2] + zOffset));

                for(int j = 0; j < sizeof(texCoords_left) / sizeof(float); j+=2)
                    texCoords_vec.push_back(glm::vec2(texCoords_back[j], texCoords_back[j+1]));



                for(int j = 0; j < sizeof(vertices_left) / sizeof(float); j+=3)
                    vertices_vec.push_back(glm::vec3(vertices_bottom[j] + xOffset, vertices_bottom[j+1] + yOffset, vertices_bottom[j+2] + zOffset));

                for(int j = 0; j < sizeof(texCoords_left) / sizeof(float); j+=2)
                    texCoords_vec.push_back(glm::vec2(texCoords_bottom[j], texCoords_bottom[j+1]));



                for(int j = 0; j < sizeof(vertices_left) / sizeof(float); j+=3)
                    vertices_vec.push_back(glm::vec3(vertices_top[j] + xOffset, vertices_top[j+1] + yOffset, vertices_top[j+2] + zOffset));

                for(int j = 0; j < sizeof(texCoords_left) / sizeof(float); j+=2)
                    texCoords_vec.push_back(glm::vec2(texCoords_top[j], texCoords_top[j+1]));
            }

            // top
            // back
            // left are fine

            float vertices[vertices_vec.size() * 3] = {};
            float texCoords[texCoords_vec.size() * 2] = {}; 

            int counter = 0;

            for(int i = 0; i < vertices_vec.size() * 3; i+=3)
            {
                vertices[i] = vertices_vec[counter].x;
                vertices[i+1] = vertices_vec[counter].y;
                vertices[i+2] = vertices_vec[counter].z;
                counter++;
            }

            counter = 0;

            for(int i = 0; i < texCoords_vec.size() * 2; i+=2)
            {
                texCoords[i] = texCoords_vec[counter].x;
                texCoords[i+1] = texCoords_vec[counter].y;
                counter++;
            }

            glGenVertexArrays(1, &VAO);
            glGenBuffers(1, &VBO);
            glGenBuffers(1, &TCBO);

            glBindVertexArray(VAO);

            glBindBuffer(GL_ARRAY_BUFFER, VBO);
            glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);

            glEnableVertexAttribArray(0);
            glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);

            glBindBuffer(GL_ARRAY_BUFFER, TCBO);
            glBufferData(GL_ARRAY_BUFFER, sizeof(texCoords), texCoords, GL_STATIC_DRAW);

            glEnableVertexAttribArray(1);
            glVertexAttribPointer(1, 2, GL_FLOAT, GL_FALSE, 2 * sizeof(float), (void*)0);

            glEnable(GL_TEXTURE_2D);
            glActiveTexture(GL_TEXTURE0);

            if(!texture.init())
                std::cerr << "Failed to load chunk texture." << std::endl;
        }
        
        void render(glm::mat4x4 view, glm::mat4x4 proj) 
        {
            shader.preRender(model, view, proj);
            texture.bind();
            glBindVertexArray(VAO);
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glDrawArrays(GL_TRIANGLES, 0, vertex_count);                

            shader.postRender();
        }

        void reloadShader()
        {
            shader = Shader("shaders/vertex", "shaders/frag");

            texture = Texture("textures/dirt.png");

            if(!shader.init() || !texture.init())
            {
                std::cerr << "Failed to reload chunk." << std::endl;
            }
        }
};

#endif