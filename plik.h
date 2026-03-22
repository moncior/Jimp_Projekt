#ifndef PLIK_H
#define PLIK_H
#include "graph.h"

int load_file(Graph*g,const char *filename);

void write_text(Graph*g,const char*filename);

void write_binary(Graph*g,const char*filename);

#endif 