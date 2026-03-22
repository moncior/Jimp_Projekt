#include <stdio.h>
#include <stdlib.h>
#include "plik.h"

int wczytywanie(graf*g, const char *nazwa_pliku){
    FILE*plik=fopen(nazwa_pliku,"r");
    if(plik==NULL){
    printf("Nie można otworzyć pliku z danymi");
    return 0;
}
g->liczba_wierzcholkow = 0;
    g->liczba_krawedzi = 0;

    char nazwa_krawedzi[50];
    int wezel_a, wezel_b;
    double waga;

    printf("Rozpoczynam wczytywanie pliku: %s\n", nazwa_pliku);

    while (fscanf(plik, "%49s %d %d %lf", nazwa_krawedzi, &wezel_a, &wezel_b, &waga) == 4) {
        
        if (g->liczba_krawedzi >= MAX_KRAWEDZI) {
            printf("OSTRZEZENIE: Osiagnieto limit krawedzi (%d). Reszta zignorowana.\n", MAX_KRAWEDZI);
            break;
        }

        g->krawedzie[g->liczba_krawedzi].poczatek = wezel_a;
        g->krawedzie[g->liczba_krawedzi].koniec = wezel_b;
        g->krawedzie[g->liczba_krawedzi].waga = waga;
        g->liczba_krawedzi++;

        dodaj_punkt_jesli_brak(g, wezel_a);
        dodaj_punkt_jesli_brak(g, wezel_b);
    }

    fclose(plik);
    printf("Wczytano sukcesem! Liczba wierzcholkow: %d, Liczba krawedzi: %d\n", 
           g->liczba_punktow, g->liczba_krawedzi);
    
    return 1;
}

}
