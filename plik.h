#ifndef PLIK_H
#define PLIK_H
#include "graf.h"

int load_file(graf*g,const char *filename);

void write_text(graf*g,const char*filename);

void write_binary(graf*g,const char*filename);

#endif 