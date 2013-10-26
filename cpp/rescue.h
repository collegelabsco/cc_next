#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <errno.h>

#define SUCCESS 0
#define FAILURE -1

typedef struct game_info
{ 
    int command; 
    char msg[20];  
    char gameid[20];
    char type[10];
    char board[10][10];
    int game_steps;
    int bonus;
    int level;    
    int cols;
    int rows;
    int x,y;
    int ex,ey;
}ServerData;

int sockfd;
const int STOP = 1;
const int NOP = 2;
const int INIT = 3;
const int BOARD = 4; 



