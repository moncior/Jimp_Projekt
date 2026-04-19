#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#include "graph.h"
#include "circle.h"
#include "Fruchtermann.h"
#include "eades.h"

static void print_usage(const char *program_name) {
	fprintf(stderr,
			"Uzycie:\n"
			"  %s -i <input.txt> -o <output.txt> -a <algorithm> -f <format>\n\n"
			"Algorytmy: circle | FR | eades\n"
			"Format: txt | bin\n",
			program_name);
}

static int equals_ignore_case(const char *left, const char *right) {
	if (left == NULL || right == NULL) {
		return 0;
	}

	while (*left != '\0' && *right != '\0') {
		char a = *left;
		char b = *right;

		if (a >= 'A' && a <= 'Z') {
			a = (char)(a - 'A' + 'a');
		}
		if (b >= 'A' && b <= 'Z') {
			b = (char)(b - 'A' + 'a');
		}

		if (a != b) {
			return 0;
		}

		left++;
		right++;
	}

	return *left == '\0' && *right == '\0';
}

static int write_nodes_text(const Graph *g, const char *filename) {
	FILE *file = fopen(filename, "w");
	if (file == NULL) {
		fprintf(stderr, "Blad: Nie mozna otworzyc pliku z danymi\n");
		return 0;
	}

	for (int i = 0; i < g->node_count; i++) {
		if (fprintf(file, "%d %.6f %.6f\n", g->nodes[i].id, g->nodes[i].x, g->nodes[i].y) < 0) {
			fclose(file);
			fprintf(stderr, "Blad: Brak pamieci operacyjnej\n");
			return 0;
		}
	}

	fclose(file);
	return 1;
}

static int write_nodes_binary(const Graph *g, const char *filename) {
	FILE *file = fopen(filename, "wb");
	if (file == NULL) {
		fprintf(stderr, "Blad: Nie mozna otworzyc pliku z danymi\n");
		return 0;
	}

	if (fwrite(&g->node_count, sizeof(int), 1, file) != 1) {
		fclose(file);
		fprintf(stderr, "Blad: Brak pamieci operacyjnej\n");
		return 0;
	}

	for (int i = 0; i < g->node_count; i++) {
		if (fwrite(&g->nodes[i].id, sizeof(int), 1, file) != 1 ||
			fwrite(&g->nodes[i].x, sizeof(double), 1, file) != 1 ||
			fwrite(&g->nodes[i].y, sizeof(double), 1, file) != 1) {
			fclose(file);
			fprintf(stderr, "Blad: Brak pamieci operacyjnej\n");
			return 0;
		}
	}

	fclose(file);
	return 1;
}

static int load_graph_from_text(Graph *g, const char *filename) {
	FILE *file = fopen(filename, "r");
	if (file == NULL) {
		fprintf(stderr, "Blad: Nie mozna otworzyc pliku z danymi\n");
		return 0;
	}

	char line[1024];
	int loaded_any_edge = 0;

	while (fgets(line, sizeof(line), file) != NULL) {
		char edge_name[64];
		int from = 0;
		int to = 0;
		double weight = 0.0;
		char extra = '\0';
		int matched = sscanf(line, " %63s %d %d %lf %c", edge_name, &from, &to, &weight, &extra);

		if (matched != 4) {
			fclose(file);
			fprintf(stderr, "Blad: Nieprawidlowy format danych w pliku\n");
			return 0;
		}

		if (graph_add_edge(g, from, to, weight) != 0) {
			fclose(file);
			fprintf(stderr, "Blad: Brak pamieci operacyjnej\n");
			return 0;
		}

		loaded_any_edge = 1;
	}

	if (ferror(file)) {
		fclose(file);
		fprintf(stderr, "Blad: Nieprawidlowy format danych w pliku\n");
		return 0;
	}

	fclose(file);

	if (!loaded_any_edge) {
		fprintf(stderr, "Blad: Nieprawidlowy format danych w pliku\n");
		return 0;
	}

	return 1;
}

int main(int argc, char **argv) {
	if (argc != 9) {
		fprintf(stderr, "Blad: Nieprawidlowe parametry wywolania.\n");
		print_usage(argv[0]);
		return 1;
	}

	const char *input_file = NULL;
	const char *output_file = NULL;
	const char *algorithm = NULL;
	const char *format = NULL;
	int seen_i = 0;
	int seen_o = 0;
	int seen_a = 0;
	int seen_f = 0;

	for (int i = 1; i < argc; i += 2) {
		if (i + 1 >= argc) {
			fprintf(stderr, "Blad: Nieprawidlowe parametry wywolania.\n");
			print_usage(argv[0]);
			return 1;
		}

		if (strcmp(argv[i], "-i") == 0) {
			if (seen_i) {
				fprintf(stderr, "Blad: Nieprawidlowe parametry wywolania.\n");
				print_usage(argv[0]);
				return 1;
			}
			input_file = argv[i + 1];
			seen_i = 1;
		} else if (strcmp(argv[i], "-o") == 0) {
			if (seen_o) {
				fprintf(stderr, "Blad: Nieprawidlowe parametry wywolania.\n");
				print_usage(argv[0]);
				return 1;
			}
			output_file = argv[i + 1];
			seen_o = 1;
		} else if (strcmp(argv[i], "-a") == 0) {
			if (seen_a) {
				fprintf(stderr, "Blad: Nieprawidlowe parametry wywolania.\n");
				print_usage(argv[0]);
				return 1;
			}
			algorithm = argv[i + 1];
			seen_a = 1;
		} else if (strcmp(argv[i], "-f") == 0) {
			if (seen_f) {
				fprintf(stderr, "Blad: Nieprawidlowe parametry wywolania.\n");
				print_usage(argv[0]);
				return 1;
			}
			format = argv[i + 1];
			seen_f = 1;
		} else {
			fprintf(stderr, "Blad: Nieprawidlowe parametry wywolania.\n");
			print_usage(argv[0]);
			return 1;
		}
	}

	if (!seen_i || !seen_o || !seen_a || !seen_f) {
		fprintf(stderr, "Blad: Nieprawidlowe parametry wywolania.\n");
		print_usage(argv[0]);
		return 1;
	}

	Graph graph;
	if (graph_init(&graph) != 0) {
		fprintf(stderr, "Blad: Brak pamieci operacyjnej\n");
		return 1;
	}

	if (!load_graph_from_text(&graph, input_file)) {
		graph_free(&graph);
		return 1;
	}

	if (equals_ignore_case(algorithm, "circle")) {
		circle_layout(&graph, 10000.0);
	} else if (equals_ignore_case(algorithm, "fr")) {
		double area = 10000.0;
		double k = sqrt(area / (double)graph.node_count);
		fruchtermann_reingold(&graph, area, k, 200);
	} else if (equals_ignore_case(algorithm, "eades")) {
		eades(&graph, 10000.0, 250);
	} else {
		fprintf(stderr, "Blad: Nieprawidlowe parametry wywolania.\n");
		print_usage(argv[0]);
		graph_free(&graph);
		return 1;
	}

	if (equals_ignore_case(format, "txt")) {
		if (!write_nodes_text(&graph, output_file)) {
			graph_free(&graph);
			return 1;
		}
	} else if (equals_ignore_case(format, "bin")) {
		if (!write_nodes_binary(&graph, output_file)) {
			graph_free(&graph);
			return 1;
		}
	} else {
		fprintf(stderr, "Blad: Nieprawidlowe parametry wywolania.\n");
		print_usage(argv[0]);
		graph_free(&graph);
		return 1;
	}

	graph_free(&graph);
	return 0;
}
