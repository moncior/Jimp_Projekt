#ifndef GRAPH_H
#define GRAPH_H
 
typedef struct {
    int    id;
    double x;
    double y;
    double dx;
    double dy;
} Node;
 
typedef struct {
    int    from;
    int    to;
    double weight;
} Edge;
 
typedef struct {
    Node  *nodes;
    int    node_count;
    int    node_capacity;
 
    Edge  *edges;
    int    edge_count;
    int    edge_capacity;
} Graph;
 
int  graph_init(Graph *g);
void graph_free(Graph *g);
int  graph_find_node(const Graph *g, int id);
int  graph_add_node(Graph *g, int id);
int  graph_add_edge(Graph *g, int from, int to, double weight);
int  graph_grow_nodes(Graph *g);
int  graph_grow_edges(Graph *g);
 
#endif