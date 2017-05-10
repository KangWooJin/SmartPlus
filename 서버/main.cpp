


#include <fcntl.h>
#include <string.h>
#include <stdlib.h>
#include <errno.h>
#include <stdio.h>
#include <netinet/in.h>
#include <resolv.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <pthread.h>

#include<iostream>
#include<string>
#include<list>
#include <sstream>
#include<vector>

#include <cstdlib>
#include <unistd.h>
#include<time.h>

#include <chrono>
#include <ctime>


#include "Tag.h"
#include "Airc.h"

using namespace std;

void* SocketHandler(void*);

void myServerInit();

void serving();

int host_port = 1111;

struct sockaddr_in my_addr;

int hsock;
int * p_int;
int err;

socklen_t addr_size = 0;
int* csock;
sockaddr_in sadr;
pthread_t thread_id = 0;

int before;
int after;
time_t  timev;
double  result;

bool isLightOff = true;
int lightTime=0;

std::chrono::time_point<std::chrono::system_clock> startt, endd;
std::chrono::time_point<std::chrono::system_clock> startt2, endd2;

Airc airc;
Tag TAG;

void myServerInit()
{
	airc.id = 0;


	hsock = socket(AF_INET, SOCK_STREAM, 0);
	if (hsock == -1){
		printf("Error initializing socket %d\n", errno);
		exit(-1);
	}

	p_int = (int*)malloc(sizeof(int));
	*p_int = 1;

	if ((setsockopt(hsock, SOL_SOCKET, SO_REUSEADDR, (char*)p_int, sizeof(int)) == -1) ||
		(setsockopt(hsock, SOL_SOCKET, SO_KEEPALIVE, (char*)p_int, sizeof(int)) == -1)){
		printf("Error setting options %d\n", errno);
		free(p_int);
		exit(-1);
	}
	free(p_int);
	
	

	my_addr.sin_family = AF_INET;
	my_addr.sin_port = htons(host_port);

	memset(&(my_addr.sin_zero), 0, 8);
	my_addr.sin_addr.s_addr = INADDR_ANY;

	if (bind(hsock, (sockaddr*)&my_addr, sizeof(my_addr)) == -1){
		fprintf(stderr, "Error binding to socket, make sure nothing else is listening on this port %d\n", errno);
		exit(-1);

	}
	if (listen(hsock, 10) == -1){
		fprintf(stderr, "Error listening %d\n", errno);
		exit(-1);

	}

	addr_size = sizeof(sockaddr_in);

}

void serving()
{
	while (true){
		printf("waiting for a connection\n");
		csock = (int*)malloc(sizeof(int));
		if ((*csock = accept(hsock, (sockaddr*)&sadr, &addr_size)) != -1){
			printf("---------------------\nReceived connection from %s\n", inet_ntoa(sadr.sin_addr));
			pthread_create(&thread_id, 0, &SocketHandler, (void*)csock);
			pthread_detach(thread_id);
		}
		else{
			fprintf(stderr, "Error accepting %d\n", errno);
		}
	}
}

int main(int argv, char** argc){

	myServerInit();

	serving();


FINISH:
	;
}


void* SocketHandler(void* lp){
	int *csock = (int*)lp;

	char buffer[1024];
	int buffer_len = 1024;
	int bytecount;

	memset(buffer, 0, buffer_len);
	if ((bytecount = recv(*csock, buffer, buffer_len, 0)) == -1){
		fprintf(stderr, "Error receiving data %d\n", errno);
		//return 
		exit(-1);
	}

	printf("Received bytes %d\nReceived string >> %s\n", bytecount, buffer);

	string str = buffer;
	cout << "Buffer : " << buffer << endl;
	cout << "THIS : " << str << endl;
	cout << "0000000 - > " << str.length()<<endl;
	 

	// SPLIT //
 
	std::istringstream ss(str);
	std::string token;

	vector<string> splitString;
	while (std::getline(ss, token, '@')) {
		cout <<"token : " << token << endl;
		splitString.push_back(token); 
	}

	//nowSend = true;

	string TAGS = splitString.at(0);
 
	cout << " TAGS : " << TAGS << endl;

	if (atoi(TAGS.c_str()) == TAG.SET_SETTING)
	{
		cout << "Tag::SET_SETTING" << endl;

		airc.minTe = atoi(splitString.at(1).c_str());
		airc.maxTe = atoi(splitString.at(2).c_str());
		airc.curTe = (airc.minTe + airc.maxTe) / 2;
		 airc.minPo = atoi(splitString.at(3).c_str());
		 airc.maxPo = atoi(splitString.at(4).c_str());
		 airc.curPo = (airc.minPo + airc.maxPo) / 2;
		airc.time = 0;
		
		airc.isPower = false;
		airc.id++;
		//airc.id = 


		
	}
	else if (atoi(TAGS.c_str()) == TAG.POWER_ON)
	{
		if (airc.id != 0)
		{ 
			if (airc.isPower == true)
			{
				system("sudo python myPy.py 1");
				airc.isPower = false;

				//time end
				endd = std::chrono::system_clock::now();

				cout << "endd end" << endl;


				std::chrono::duration<double> elapsed_seconds = endd - startt;
				 

				elapsed_seconds.count();

				airc.time += elapsed_seconds.count();

				cout << "airc.time : " << airc.time << endl;
				//cout <<"## "<< result << "add     " << airc.time << " all ##" <<endl;

			}
			else
			{
				system("sudo python myPy.py 1");
				airc.isPower = true;

				//time start
				startt = std::chrono::system_clock::now(); 

				//cout << "before : " << startt << endl;

			}
			 
		} 
	}
	else if (atoi(TAGS.c_str()) == TAG.TEMPER_UP)
	{
		if (airc.curTe < airc.maxTe)
		{
			airc.curTe++;
			system("sudo python myPy.py 2");
		}
		 
	}
	else if (atoi(TAGS.c_str()) == TAG.TEMPER_DOWN)
	{

		if (airc.curTe > airc.minTe)
		{
			airc.curTe--;
			system("sudo python myPy.py 3");
		}
		 
	}
	else if (atoi(TAGS.c_str()) == TAG.POWER_UP)
	{
		if (airc.curPo < airc.maxPo)
		{
			airc.curPo++;
			system("sudo python myPy.py 4");
		}
		 
	}
	else if (atoi(TAGS.c_str()) == TAG.POWER_DOWN)
	{
		if (airc.curPo > airc.minPo)
		{
			airc.curPo--;
			system("sudo python myPy.py 5");
		}

	}
	else if (atoi(TAGS.c_str()) == TAG.DELETE_AIRC)
	{
		if (airc.id > 0)
			airc.id--;

	}
	else if (atoi(TAGS.c_str()) == TAG.GET_SETTING)
	{
		string tmpStr = to_string(airc.minTe) + "@" + to_string(airc.maxTe) + "@" + to_string(airc.curTe) + "@" + to_string(airc.minPo) + "@" + to_string(airc.maxPo) + "@" + to_string(airc.curPo);

		strcpy(buffer, tmpStr.c_str());
		strcat(buffer, "\r\n");

	}
	else if (atoi(TAGS.c_str()) == TAG.GET_SETTING)
	{
		string tmpStr = to_string(airc.minTe) + "@" + to_string(airc.maxTe) + "@" + to_string(airc.curTe) + "@" + to_string(airc.minPo) + "@" + to_string(airc.maxPo) + "@" + to_string(airc.curPo);

		strcpy(buffer, tmpStr.c_str());
		strcat(buffer, "\r\n");

	}
	else if (atoi(TAGS.c_str()) == TAG.GET_IS_ON)
	{

		if (airc.isPower == true)
			strcpy(buffer, "1");
		else
			strcpy(buffer, "2");
		strcat(buffer, "\r\n");

	}
	else if (atoi(TAGS.c_str()) == TAG.GET_AIRC_USE_TIME)
	{


		strcpy(buffer, std::to_string(airc.time).c_str());
		strcat(buffer, "\r\n");

	}
	else if (atoi(TAGS.c_str()) == TAG.TURN_LIGHT)
	{
		if (isLightOff == true)
		{
			isLightOff = false;
			startt2 = std::chrono::system_clock::now();
		}
		else
		{
			isLightOff = true;
			//time end
			endd2 = std::chrono::system_clock::now();

			cout << "endd2 end" << endl;


			std::chrono::duration<double> elapsed_seconds = endd2 - startt2;


			elapsed_seconds.count();

			lightTime += elapsed_seconds.count();

			cout << "lightTime : " << lightTime << endl;


		}


		system("sudo python myPy.py 6");

	}
	else if (atoi(TAGS.c_str()) == TAG.GET_LIGHT_TIME)
	{


		strcpy(buffer, std::to_string(lightTime).c_str());
		strcat(buffer, "\r\n");

	}

	cout << "Buffer : >>" << buffer << "<<" << endl;

	if ((bytecount = send(*csock, buffer, strlen(buffer), 0)) == -1){
			fprintf(stderr, "Error sending data %d\n", errno);
			//goto FINISH;
			exit(-1);

	}

	printf("Sent bytes %d\n", bytecount);


//FINISH:
	free(csock);

	return 0;
}