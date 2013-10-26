#include "game_interface.c"

#define LEFT 1
#define RIGHT 2
#define DOWN 3
#define UP 4

typedef struct coordinates {
	int x;
	int y;
}Cell;

Cell player_pos;
Cell set_point(Cell loc, int x, int y);
int check_neighbour(Cell loc, int dir);

int main(int argc, char *argv[])
{
	if(argc != 2) {
		printf("Please pass the gameid as argument\n");
		return FAILURE;
	}
	printf("starting with gameid: %s\n", argv[1]);
	int connection = connect_usergame();
	int i=0, j=0;
	if(connection==0) {	
		initialize(argv[1],1);
		
		//write your code here
		//example
		if(is_finished() == 0) {
			player_pos = set_point(player_pos, get_xPos(), get_yPos());
			while(check_neighbour(player_pos, DOWN) == 0)
				make_move("move", "down");
			
			while(check_neighbour(player_pos, LEFT) == 0)
				make_move("move", "down");
			
		}
		
	}
	return SUCCESS;
}

int check_neighbour(Cell loc, int dir) {
	if(dir == DOWN) {
		if(board_layout[loc.x+1][loc.y] == 'B' && loc.x < boardrows)
			return 1;
		else return 0;
	}
	if(dir == RIGHT) {
		if(board_layout[loc.x][loc.y+1] == 'B' && loc.y < boardcols)
			return 1;		
		else return 0;
	}
	if(dir == LEFT) {
		if(board_layout[loc.x][loc.y-1] == 'B' && loc.y > 0)
			return 1;
		else return 0;
	}	
	if(dir == UP) {
		if(board_layout[loc.x-1][loc.y] == 'B' && loc.x > 0)
			return 1;
		else return 0;
	}
	else return -1;
}

Cell set_point(Cell loc, int x, int y) {
	loc.x = x;
	loc.y = y;
	return loc;
}	 
