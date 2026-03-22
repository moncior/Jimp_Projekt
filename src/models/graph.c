#include "graph.h"
#include <stdio.h>
#include <stdlib.h>
 
#define INIT_NODE_CAPACITY 16
#define INIT_EDGE_CAPACITY 32
 
int graph_init(Graph *g) {
    g->node_count = 0;
    g->node_capacity = INIT_NODE_CAPACITY;
 
    g->nodes = (Node *)malloc(g->node_capacity * sizeof(Node));
    if (g->nodes == NULL) {
        fprintf(stderr, "Błąd: Brak pamięci operacyjnej\n");
        return -1;
    }
 
    g->edge_count = 0;
    g->edge_capacity = INIT_EDGE_CAPACITY;
 
    g->edges = (Edge *)malloc(g->edge_capacity * sizeof(Edge));
    if (g->edges == NULL) {
        fprintf(stderr, "Błąd: Brak pamięci operacyjnej\n");
        free(g->nodes);
        g->nodes = NULL;
        return -1;
    }
 
    return 0;
}
 
void graph_free(Graph *g) {
    free(g->nodes);
    free(g->edges);
    g->nodes = NULL;
    g->edges = NULL;
    g->node_count    = 0;
    g->node_capacity = 0;
    g->edge_count    = 0;
    g->edge_capacity = 0;
}
 
int graph_find_node(const Graph *g, int id) {
    for (int i = 0; i < g->node_count; i++) {
        if (g->nodes[i].id == id)
            return i;
    }
    return -1;
}
 
int graph_grow_nodes(Graph *g) {
    int new_capacity = g->node_capacity * 2;
    Node *tmp = (Node *)realloc(g->nodes, new_capacity * sizeof(Node));
    if (tmp == NULL) {
        fprintf(stderr, "Błąd: Brak pamięci operacyjnej\n");
        return -1;
    }
    g->nodes         = tmp;
    g->node_capacity = new_capacity;
    return 0;
}
 
int graph_grow_edges(Graph *g) {
    int new_capacity = g->edge_capacity * 2;
    Edge *tmp = (Edge *)realloc(g->edges, new_capacity * sizeof(Edge));
    if (tmp == NULL) {
        fprintf(stderr, "Błąd: Brak pamięci operacyjnej\n");
        return -1;
    }
    g->edges = tmp;
    g->edge_capacity = new_capacity;
    return 0;
}
 
int graph_add_node(Graph *g, int id) {
    int idx = graph_find_node(g, id);
    if (idx != -1)
        return idx;
 
    if (g->node_count >= g->node_capacity) {
        if (graph_grow_nodes(g) != 0)
            return -1;
    }
 
    idx = g->node_count;
    g->nodes[idx].id = id;
    g->nodes[idx].x = 0.0;
    g->nodes[idx].y = 0.0;
    g->nodes[idx].dx = 0.0;
    g->nodes[idx].dy = 0.0;
    g->node_count++;
 
    return idx;
}
 
int graph_add_edge(Graph *g, int from, int to, double weight) {
    if (graph_add_node(g, from) == -1) return -1;
    if (graph_add_node(g, to) == -1) return -1;
 
    if (g->edge_count >= g->edge_capacity) {
        if (graph_grow_edges(g) != 0)
            return -1;
    }
 
    int idx = g->edge_count;
    g->edges[idx].from = from;
    g->edges[idx].to = to;
    g->edges[idx].weight = weight;
    g->edge_count++;
 
    return 0;
}