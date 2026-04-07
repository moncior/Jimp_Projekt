#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include "graph.h"
#include "utils.h"

static void seed_rng_once(void) {
    static int seeded = 0;
    if (!seeded) {
        srand((unsigned)time(NULL));
        seeded = 1;
    }
}

void fruchtermann_reingold(Graph *g, double area, double k, int i) {
    if (!g || g->node_count <= 0 || area <= 0.0 || k <= 0.0 || i <= 0) return;

    const int n = g->node_count;
    const int m = g->edge_count;
    const double side = sqrt(area);
    const double eps = 1e-9;
    double t = side / 10.0;

    int *edge_from_idx = (int *)malloc((size_t)m * sizeof(int));
    int *edge_to_idx   = (int *)malloc((size_t)m * sizeof(int));
    if ((m > 0) && (!edge_from_idx || !edge_to_idx)) {
        free(edge_from_idx);
        free(edge_to_idx);
        return;
    }

    for (int e = 0; e < m; e++) {
        edge_from_idx[e] = graph_find_node(g, g->edges[e].from);
        edge_to_idx[e]   = graph_find_node(g, g->edges[e].to);
    }

    seed_rng_once();

    for (int v = 0; v < n; v++) {
        g->nodes[v].x = random_range(0.0, side);
        g->nodes[v].y = random_range(0.0, side);
        g->nodes[v].dx = 0.0;
        g->nodes[v].dy = 0.0;
    }

    for (int iter = 0; iter < i; iter++) {
        for (int v = 0; v < n; v++) {
            g->nodes[v].dx = 0.0;
            g->nodes[v].dy = 0.0;
        }

        for (int v = 0; v < n; v++) {
            for (int u = v + 1; u < n; u++) {
                const double dx = g->nodes[v].x - g->nodes[u].x;
                const double dy = g->nodes[v].y - g->nodes[u].y;
                const double dist = sqrt(dx * dx + dy * dy) + eps;

                const double force = (k * k) / dist;
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

            const double dx = g->nodes[v].x - g->nodes[u].x;
            const double dy = g->nodes[v].y - g->nodes[u].y;
            const double dist = sqrt(dx * dx + dy * dy) + eps;

            const double force = (dist * dist) / k;
            const double fx = (dx / dist) * force;
            const double fy = (dy / dist) * force;

            g->nodes[v].dx -= fx;
            g->nodes[v].dy -= fy;
            g->nodes[u].dx += fx;
            g->nodes[u].dy += fy;
        }

        // Move + clamp
        for (int v = 0; v < n; v++) {
            const double disp = sqrt(g->nodes[v].dx * g->nodes[v].dx +
                                     g->nodes[v].dy * g->nodes[v].dy) + eps;
            const double step = min_val(disp, t);

            g->nodes[v].x += (g->nodes[v].dx / disp) * step;
            g->nodes[v].y += (g->nodes[v].dy / disp) * step;

            g->nodes[v].x = min_val(side, max_val(0.0, g->nodes[v].x));
            g->nodes[v].y = min_val(side, max_val(0.0, g->nodes[v].y));
        }

        t *= 0.90;
        if (t < 1e-4) break;
    }

    free(edge_from_idx);
    free(edge_to_idx);
}