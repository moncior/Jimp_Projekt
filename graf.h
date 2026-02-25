#ifndef GRAF_H
#define GRAF_H

#define MAX_PUNKTOW 1000
#define MAX_WIERZCHOLKOW 5000

typedef struct {
    double x;
    double y;
    int id;

    double dx;
    double dy;
}punkt;

typedef struct {
    int poczatek;
    int koniec;
    double wartosc;
}krawedz;

typedef struct{
    punkt punkty[MAX_PUNKTOW];
    int liczba_punktow;
    
    krawedz krawedzie[MAX_WIERZCHOLKOW];
    int liczba_wierzcholkow;
}graf;

#endif 