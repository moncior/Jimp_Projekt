#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "plik.h"

int load_file(Graph *g, const char *filename) {
    if (g == NULL || filename == NULL) {
        fprintf(stderr, "Blad: nieprawidlowy graf lub nazwa pliku.\n");
        return 0;
    }

    FILE *file = fopen(filename, "r");
    if (file == NULL) {
        fprintf(stderr, "Blad: nie mozna otworzyc pliku wejsciowego: %s\n", filename);
        return 0;
    }

    g->node_count = 0;
    g->edge_count = 0;

    char edge_name[50];
    int from;
    int to;
    double weight;

    while (fscanf(file, "%49s %d %d %lf", edge_name, &from, &to, &weight) == 4) {
        if (graph_add_edge(g, from, to, weight) != 0) {
            fprintf(stderr, "Blad: nie udalo sie dodac krawedzi (%d -> %d).\n", from, to);
            fclose(file);
            return 0;
        }
    }

    fclose(file);
    return 1;
}

void write_text(Graph *g, const char *filename) {
    if (g == NULL || filename == NULL) {
        fprintf(stderr, "Blad: nieprawidlowy graf lub nazwa pliku.\n");
        return;
    }

    FILE *file = fopen(filename, "w");
    if (file == NULL) {
        fprintf(stderr, "Blad: nie mozna otworzyc wyjsciowego pliku tekstowego: %s\n", filename);
        return;
    }

    for (int i = 0; i < g->edge_count; i++) {
        fprintf(file, "edge_%d %d %d %.6f\n", i, g->edges[i].from, g->edges[i].to, g->edges[i].weight);
    }

    fclose(file);
}

void write_binary(Graph *g, const char *filename) {
    if (g == NULL || filename == NULL) {
        fprintf(stderr, "Blad: nieprawidlowy graf lub nazwa pliku.\n");
        return;
    }

    FILE *file = fopen(filename, "wb");
    if (file == NULL) {
        fprintf(stderr, "Blad: nie mozna otworzyc wyjsciowego pliku binarnego: %s\n", filename);
        return;
    }

    fwrite(&g->node_count, sizeof(int), 1, file);
    fwrite(&g->edge_count, sizeof(int), 1, file);
    fwrite(g->nodes, sizeof(Node), g->node_count, file);
    fwrite(g->edges, sizeof(Edge), g->edge_count, file);

    fclose(file);
}
