#ifndef PLIK_H
#define PLIK_H
#include "graf.h"

void wczytywanie(graf*g,const char *nazwa_pliku);

void wypisywanie_tekstowo(graf*g,const char*nazwa_pliku);

void wypisywanie_binarnie(graf*g,const char*nazwa_pliku);

#endif 