#include "eades.h"
#include "utils.h"
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

#define EADES_C1  2.0
#define EADES_C2  1.0
#define EADES_C3  1.0
#define EADES_C4  0.1

static void seed_rng_once(void) {
    static int seeded = 0;
    if (!seeded) {
        srand((unsigned)time(NULL));
        seeded = 1;
    }
}

void eades(Graph *g, double area, int iterations) {
    if (!g || g->node_count <= 0 || area <= 0.0 || iterations <= 0) return;

    const int n = g->node_count;
    const int m = g->edge_count;
    const double side = sqrt(area);
    const double eps  = 1e-9;

    int *edge_from_idx = (int *)malloc((size_t)m * sizeof(int));
    int *edge_to_idx   = (int *)malloc((size_t)m * sizeof(int));
    if ((m > 0) && (!edge_from_idx || !edge_to_idx)) {
        free(edge_from_idx);
        free(edge_to_idx);
        fprintf(stderr, "Błąd: Brak pamięci operacyjnej\n");
        return;
    }

    for (int e = 0; e < m; e++) {
        edge_from_idx[e] = graph_find_node(g, g->edges[e].from);
        edge_to_idx[e]   = graph_find_node(g, g->edges[e].to);
    }

    seed_rng_once();

    for (int v = 0; v < n; v++) {
        g->nodes[v].x  = random_range(0.0, side);
        g->nodes[v].y  = random_range(0.0, side);
        g->nodes[v].dx = 0.0;
        g->nodes[v].dy = 0.0;
    }

    for (int iter = 0; iter < iterations; iter++) {

        for (int v = 0; v < n; v++) {
            g->nodes[v].dx = 0.0;
            g->nodes[v].dy = 0.0;
        }

        for (int v = 0; v < n; v++) {
            for (int u = v + 1; u < n; u++) {
                const double dx   = g->nodes[v].x - g->nodes[u].x;
                const double dy   = g->nodes[v].y - g->nodes[u].y;
                const double dist = sqrt(dx * dx + dy * dy) + eps;
                const double force = EADES_C3 / (dist * dist);
                const double fx = (dx / dist) * force;
                const double fy = (dy / dist) * force;
                g->nodes[v].dx += fx;
                g->nodes[v].dy += fy;
                g->nodes[u].dx -= fx;
                g->nodes[u].dy -= fy;
            }
        }

        for (int e = 0; e < m; e++) {
            const int v = edge_from_idx[e];
            const int u = edge_to_idx[e];
            if (v < 0 || u < 0 || v == u) continue;
            const double dx   = g->nodes[v].x - g->nodes[u].x;
            const double dy   = g->nodes[v].y - g->nodes[u].y;
            const double dist = sqrt(dx * dx + dy * dy) + eps;
            const double force = EADES_C1 * log(dist / EADES_C2);
            const double fx = (dx / dist) * force;
            const double fy = (dy / dist) * force;
            g->nodes[v].dx -= fx;
            g->nodes[v].dy -= fy;
            g->nodes[u].dx += fx;
            g->nodes[u].dy += fy;
        }

        for (int v = 0; v < n; v++) {
            g->nodes[v].x += EADES_C4 * g->nodes[v].dx;
            g->nodes[v].y += EADES_C4 * g->nodes[v].dy;
            g->nodes[v].x = min_val(side, max_val(0.0, g->nodes[v].x));
            g->nodes[v].y = min_val(side, max_val(0.0, g->nodes[v].y));
        }
    }

    free(edge_from_idx);
    free(edge_to_idx);
}