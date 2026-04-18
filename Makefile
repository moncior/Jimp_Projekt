CC = gcc
CFLAGS = -I./src/models -I./src/repositories -I./src/services -I./src/utils -I./src -Wall -g
LDFLAGS = -lm

SRCS = src/main.c \
       src/models/graph.c \
       src/repositories/file.c \
       src/services/circle.c \
       src/services/eades.c \
       src/services/Fruchtermann.c \
       src/utils/utils.c

TARGET = program

all: $(TARGET)

$(TARGET): $(SRCS)
	$(CC) $(CFLAGS) $(SRCS) -o $(TARGET) $(LDFLAGS)

clean:
	rm -f $(TARGET)
	rm -f results.txt

check: all
	valgrind --leak-check=full \
             --show-leak-kinds=all \
             --track-origins=yes \
             ./$(TARGET) -i data.txt -o results.txt -a fr -f txt