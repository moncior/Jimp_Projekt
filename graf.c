#include "graf.h"
#include<stdio.h>
#include<stdlib.h>

void inicjuj_graf(graf *g){
    g->liczba_wierzcholkow = 0;
    g->liczba_krawedzi = 0;
}

void dodaj_wierzcholek(graf *g, int id){
    for(int i=0;i<g->liczba_wierzcholkow;i++){
        if(g->wierzcholki[i].id == id){
            return;
        }
    }

    if(g->liczba_wierzcholkow < MAX_WIERZCHOLKOW){
        int idx = g->liczba_wierzcholkow;
        g->wierzcholki[idx].id = id;

        g->wierzcholki[idx].x = 0.0;
        g->wierzcholki[idx].y = 0.0;
        g->wierzcholki[idx].dx = 0.0;
        g->wierzcholki[idx].dy = 0.0;

        g->liczba_wierzcholkow++;
    }
}

void dodaj_krawedz(graf *g ,int id_a, int id_b, double waga ){
    if(g->liczba_krawedzi < MAX_KRAWEDZI){
        int idx = g->liczba_krawedzi;

        dodaj_wierzcholek(g,id_a);
        dodaj_wierzcholek(g,id_b);

        g->krawedzie[idx].poczatek = id_a;
        g->krawedzie[idx].koniec = id_b;
        g->krawedzie[idx].wartosc = waga;

        g->liczba_krawedzi++;

    }
}




