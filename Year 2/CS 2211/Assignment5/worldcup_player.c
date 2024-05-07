/*
Author : Ashna Mittal
ID : 251206758
 */
#include<stdlib.h>
#include<string.h>
#include<stdio.h>
#include "worldcup_player.h"

struct Player
{
    int pcode;
    char pname[50];
    char club[50];
    int age;
    struct Player *next;
}* top;

void insert_player(int code, char *name, char *club, int age) // inserts a new player
{

    if (code < 0) // valid code check condition
    {
        printf("Error! Code cannot be less than 0. ");
        return;
    }
    struct Player * player = (struct Player *) malloc(sizeof(struct Player));
    player->pcode = code;
    strcpy(player->pname, name);
    strcpy(player->club, club);
    player->age = age;
    player->next = NULL;

    if(top == NULL)
        top = player;
    else
    {
        player->next = top;
        top = player;
    }
}

void update_player(int code) // updates player records
{

    struct Player * tmpvalue = top;
    while(tmpvalue != NULL)
    {

        if(tmpvalue->pcode == code) //  check condition for comparing each player's code with the update player code
        {
            // enter update values
            printf("Enter Player name: ");
            scanf("%s", tmpvalue->pname);
            printf("Enter Player's Professional Club Affiliation: ");
            scanf("%s", tmpvalue->club);
            printf("Enter Player age: ");
            scanf("%d",&tmpvalue->age);
            printf("Updated Successful.\n");
            return;
        }
        tmpvalue = tmpvalue->next; // pointer moves to next record
    }
    printf("Player code %d not found. \n", code);
}

int search_player(int code) // searches for a player in the database
{
    struct Player * tmpvalue = top;
    while(tmpvalue != NULL)
    {
        if(tmpvalue->pcode == code) //  check condition for comparing each player with the search player value
        {
            printf("Player code: %d\n", tmpvalue->pcode);
            printf("Player Name: %s\n", tmpvalue->pname);
            printf("Professional club Affiliation: %s\n", tmpvalue->club);
            printf("Player Age: %d\n", tmpvalue->age);
            return 1;
        }
        tmpvalue = tmpvalue->next; // pointer moves to next record
    }
    return 0;
}

void Delete_player(int code) // method to delete a player from database
{
    struct Player * tmp1 = top;
    struct Player * tmp2 = top;
    while(tmp1 != NULL)
    {
        if(tmp1->pcode == code) // check condition to compare current player code with the to-be-deleted player code
        {
            printf("Player code %d found. \n", code);
            if(tmp1 == tmp2) // check condition if player is found
            {
                top = top->next;
                free(tmp1); // freeing the memory
            }
            else
            {
                tmp2->next = tmp1->next;
                free(tmp1); // freeing the memory
            }
            printf("Player Record Deleted.\n");
            return;
        }
        tmp2 = tmp1;
        tmp1 = tmp1->next;
    }
    printf("Player code %d not found. \n", code);
}

void display_player() // displays the player list
{
    struct Player * tmpvalue = top;
    while(tmpvalue != NULL)
    {
        printf("Player code: %d\n", tmpvalue->pcode);
        printf("Player Name: %s\n", tmpvalue->pname);
        printf("Professional club Affiliation: %s\n", tmpvalue->club);
        printf("Player Age: %d\n\n", tmpvalue->age);
        tmpvalue = tmpvalue->next; // pointer moves to next record
    }
}
