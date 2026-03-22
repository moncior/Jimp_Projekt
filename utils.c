
#include "utils.h"
#include <math.h>
#include <stdlib.h>

double distance(double x1, double y1, double x2, double y2) {
    double dx = x2 - x1;
    double dy = y2 - y1;
    return sqrt(dx * dx + dy * dy);
}
 
double max_val(double a, double b) {
    return (a > b) ? a : b;
}
 
double min_val(double a, double b) {
    return (a < b) ? a : b;
}
 
double random_double(void) {
    return (double)rand() / ((double)RAND_MAX + 1.0);
}
 
double random_range(double a, double b) {
    return a + random_double() * (b - a);
}
 
