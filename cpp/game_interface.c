#include "rescue.h"

char board_layout[10][10];
char usermsg[256];
char* status;
short int stop_flag = 0;
short int debug = 0;
int boardcols = 10;
int boardrows = 10;
int game_step = 0;
int game_bonus = 0;
int boardcols;
int xPos;
int yPos;
int exPos;
int eyPos;
int game_level;
char gameid[15];
ServerData *game_data;

int make_connection(char* hostname, int portno)
{  
   struct sockaddr_in server_addr;
   struct hostent *server;
   if(hostname == NULL)
      return FAILURE;
   sockfd = socket(AF_INET, SOCK_STREAM, 0);
   if(sockfd < 0)
      return FAILURE;
   server = gethostbyname(hostname);
   bzero((char*) &server_addr, sizeof(server_addr));
   server_addr.sin_family = AF_INET;
   bcopy((char*)server->h_addr, 
   	(char*)&server_addr.sin_addr.s_addr, 
   	server->h_length);
   server_addr.sin_port = htons(portno);
   if(connect(sockfd,&server_addr,sizeof(server_addr))<0)
   {
      printf("ERROR, connection refused");
      return FAILURE;
   }
   else
      return SUCCESS;
}

char* get_user_msg()
{
	return usermsg;
}
int get_xPos()
{
	return xPos;
}
int get_yPos()
{
	return yPos;
}
void set_xPos(int x)
{
	xPos = x;
}
void set_yPos(int y)
{
	yPos = y;
}
int connect_usergame()
{	
	int connection = make_connection("202.138.101.231", 443);
	if(connection<0)
		return FAILURE;
	
	return SUCCESS;
}

void get_info_from_msg(char* msg)
{
   char delim1[] = ";=";
   char delim2[] = "=";
   char *result = NULL;   
   
   game_data = (ServerData*) malloc(sizeof(ServerData));
   game_data->x = -1;
   game_data->y = -1;
   game_data->ex = -1;
   game_data->ey = -1;
   game_data->bonus = 0;
   game_data->level = 1; 
   game_data->cols = 10;
   game_data->rows = 10;  
  
   short int enemy_pos = 0;
   short int user_pos = 0;
   short int rowfound = 0;
   char *server_data[20];
   int i=0,j=0;
   for(i=0;i<20;i++)
   	server_data[i] = malloc(150*sizeof(char));
   int counter = 0;
   
   result = strtok(msg, delim1);     
   while( result != NULL ) 
   { 	 	
		for(i=0;i<strlen(result);i++)
			*(server_data[counter]+i) = *(result+i);            
      //printf( "result is %s\n", result);      
      result = strtok( NULL, delim1 );           
      counter++;    		 
   }
   for(i=0,j=i+1;i<=counter,j<=counter-1;i+=2,j+=2) {
   	if(strcmp(server_data[i],"gameid")==0)   		
   		strcpy(game_data->gameid,server_data[j]);
   	if(strcmp(server_data[i],"step")==0)
   		game_data->game_steps = atoi(server_data[j]);
   	if(strcmp(server_data[i],"bonus")==0)
   		game_data->bonus = atoi(server_data[j]);
   	if(strcmp(server_data[i],"command")==0)
   	{
   		if(strcmp(server_data[j],"init")==0) {
  				game_data->command = INIT;
  			}
  			else if(strcmp(server_data[j],"stop")==0) {
  				game_data->command = STOP;
  			}
  			else if(strcmp(server_data[j],"nop")==0) {
  				game_data->command = NOP;
  				user_pos=1;
  				enemy_pos=1;
  			}
	}
   	if(strcmp(server_data[i],"source")==0)
   		strcpy(game_data->type,server_data[j]);
   	if(strcmp(server_data[i],"level")==0)
   		game_data->level = atoi(server_data[j]);
   	if(strcmp(server_data[i],"msg")==0)
   		strcpy(game_data->msg, server_data[j]);
   	if(strcmp(server_data[i],"board")==0 && strlen(server_data[j])!=0)
   	{
   		game_data->command = BOARD;
   		int k=0;
   		int l=0;
   		int m=0;
   		char *boardchars = (char*)malloc(sizeof(char)*100);
   		strcpy(boardchars,server_data[j]);
   		for(k=0;k<strlen(boardchars);k++)
   		{
   			l=k/game_data->cols;
   			m=k%game_data->cols;
   			if(boardchars[k] == 'P') 
			{
   				game_data->x = l;
   				game_data->y = m;
   			}
   			game_data->board[l][m] = boardchars[k];
   		}   		
   		
   	}
   	if(strcmp(server_data[i],"x")==0)
   		game_data->x = atoi(server_data[j]);
   	if(strcmp(server_data[i],"y")==0)
   		game_data->y = atoi(server_data[j]);
   }
   //printf("One Extracted Msg: %d\n",game_data->command);    
}

void update_game()
{
	//printf("line no 144: %d\n", game_data->command);
	if(game_data->command==BOARD && game_data->cols!=-1)
	{
		status = "Board Initialized";
		set_xPos(game_data->x);
		set_yPos(game_data->y);
		int i=0,j=0;
		for(i=0;i<10;i++) {
			for(j=0;j<10;j++) {
				board_layout[i][j] = game_data->board[i][j];				
			}
		}
		game_level = game_data->level;
	}
	else if(game_data->command == STOP)
	{
		status = game_data->msg;
		stop_flag = 1;		
	}
	else if(game_data->command == INIT)
	{
		status = "Connected to Server";			
	}
	else if(game_data->command == NOP)
	{		
		//int n = sprintf(status,"player position changed. x=%d,y=%d\n",game_data->x,game_data->y);
		printf("line no 170: %d\n", game_data->command);				
		board_layout[get_xPos()][get_yPos()] = 'O';
		set_xPos(game_data->x);
		set_yPos(game_data->y);
		board_layout[get_xPos()][get_yPos()] = 'P';	
	}
	printf("status = %s\n", status);
	if(stop_flag)
		exit (1);
}

ssize_t Readline(int sockd, void *vptr, size_t maxlen) {
    ssize_t n, rc;
    char    c, *buffer;

    buffer = vptr;

    for ( n = 1; n < maxlen; n++ ) {
	
	if ( (rc = read(sockd, &c, 1)) == 1 ) {
	    *buffer++ = c;
	    if ( c == '\n' )
		break;
	}
	else if ( rc == 0 ) {
	    if ( n == 1 )
		return 0;
	    else
		break;
	}
	else {
	    if ( errno == EINTR )
		continue;
	    return -1;
	}
    }

    *buffer = 0;
    return n;
}

ssize_t Writeline(int sockd, const void *vptr, size_t n) {
    size_t      nleft;
    ssize_t     nwritten;
    const char *buffer;

    buffer = vptr;
    nleft  = n;

    while ( nleft > 0 ) {
	if ( (nwritten = write(sockd, buffer, nleft)) <= 0 ) {
	    if ( errno == EINTR )
		nwritten = 0;
	    else
		return -1;
	}
	nleft  -= nwritten;
	buffer += nwritten;
    }

    return n;
}
void initialize(char* uid, int level) 
{
	//int connection = connect_usergame(gameid,level);
	//printf("connection status: %d\n",connection);
	int i;
	int n;
	game_level = level; 
	for(i=0; i<strlen(uid); i++)
	{
		gameid[i] = uid[i];
	}
	game_step = 0;
	game_bonus = 0; 
	char* usermsg_prt1 = "gameid";
	char* usermsg_prt2 = ";step=0;bonus=0;command=init;source=user;level=";
	n = sprintf(usermsg,"%s=%s%s%d\n",usermsg_prt1,gameid,usermsg_prt2,game_level);
	//printf("value of n = %d\n",n);	
	ssize_t con;	
	printf("Writing to Server..."); 
	con = Writeline(sockfd,usermsg,strlen(usermsg));	
	printf("%s\n", usermsg);
	if(con<0)
		bzero(usermsg,256);
	printf("Reading from the server...");	
	con = Readline(sockfd,usermsg,255); 
    printf("%s\n",usermsg);
    if (con < 0) 
       error("ERROR reading from socket\n");
    //printf("%s\n",usermsg);
   
    if(usermsg != NULL) 
    {
		get_info_from_msg(usermsg);
		if(game_data != NULL)
			update_game();
   	else 
   	{
   		status = "invalid message format from server";
   		printf("%s\n", status);
   		stop_flag = 1;
   	}  	
   }
}

void make_move(char* command, char* type)
{
	char message_to_server[256];
	int n = sprintf(message_to_server,"gameid=%s;step=%d;source=user;command=%s;type=%s;bonus=%d;level=%d\n",gameid,game_step,command,type,game_bonus,game_level);
	ssize_t con;
	//printf("%s\n",message_to_server);	
	printf("writing to server: %s\n", message_to_server);	
	con = Writeline(sockfd,message_to_server,strlen(message_to_server));
	printf("%d\n",con);
	if(con<0)
		printf("Error, reading from socket\n");
	game_step++; 
	printf("waiting to read....\n");	
	bzero(message_to_server,256);
	con = Readline(sockfd,message_to_server,255);
	printf("read data as: %s\n",message_to_server);	
	if(message_to_server!=NULL)
	{
		get_info_from_msg(message_to_server);
		printf("x=%d;y=%d\n",game_data->x,game_data->y);
		if(game_data!=NULL)
		{
			printf("Check in line no 307\n");
			update_game();
		}		
		else
		{
			status = "Invalid message format from server";
			printf("%s\n",status);
			stop_flag = 1;
		}
	}	
} 
int get_current_step()
{
	return game_step;
} 
char **get_board()
{
	return (char**)board_layout;
}
char *get_status()
{ 
	return status;
}
int get_column_count()
{
	return boardcols;
}
int get_row_count()
{
	return boardrows;
}
int is_finished()
{
	return stop_flag;
}

/*int main()
{
	initialize();
	return 0;
}*/

