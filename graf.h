#ifndef GRAF_H
#define GRAF_H

#define MAX_WIERZCHOLKOW 1000
#define MAX_KRAWEDZI 5000

typedef struct {
    double x;
    double y;
    int id;

    double dx;
    double dy;
}wierzcholek;

typedef struct {
    int poczatek;
    int koniec;
    double wartosc;
}krawedz;

typedef struct{
    wierzcholek wierzcholki[MAX_WIERZCHOLKOW];
    int liczba_wierzcholkow;
    
    krawedz krawedzie[MAX_KRAWEDZI];
    int liczba_krawedzi;
}graf;

#endif 