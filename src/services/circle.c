#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "circle.h"
#include "utils.h"

void circle_layout(Graph*g,double area){
    if(g==NULL) return;
    if(g->node_count==0) return;
    if(area<=0.0) return;

    const double pi = acos(-1.0);
    const double radius = sqrt(area/pi);

    for(int i=0;i<g->node_count;i++){
        g->nodes[i].x=radius*cos(2*pi*i/g->node_count);
        g->nodes[i].y=radius*sin(2*pi*i/g->node_count);
        g->nodes[i].dx=0.0;
        g->nodes[i].dy=0.0;
    }
}