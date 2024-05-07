/*
Author : Ashna Mittal
ID : 251206758
 */
#include<stdlib.h>
#include<string.h>
#include<stdio.h>
#include "worldcup_team.h"
#include "worldcup_player.h"


int main()
{
    printf("$ ./worldCupDB\n");
    printf("******************\n");
    printf("* 2211 World Cup *\n");
    printf("******************\n");
    int value =1;
    while (value != 0)
    {
        char choice;
        printf("\nEnter h to Print Help. \nEnter q to Quit. \nEnter t to Control Teams. \nEnter p to Control Players. \nEnter User's choice:");
        scanf(" %c", &choice);
        if (choice == 'p') // checking user's choice
        {
            int num = 1;
            char userchoice; // variable storing user's choice
            char pname[50]; // variable storing Player's name
            char club[50]; // variable storing player's professional club affiliation
            int pcode; // variable storing Players code
            int age; // variable storing Player's age
            int val;
            printf("\ni to insert a new Player. \ns to Search for a player in the database. \nd to Delete a player record. "
                   "\nu to Update a player in the database. \np to Print the entire list of Players.");
            while (num != 0)
            {
                printf("\nEnter Choice: ");
                scanf(" %c", &userchoice);
                switch (userchoice)
                {
                    case 'i': //inserts a new player
                        printf("\nEnter player code: ");
                        scanf("%d", &pcode);
                        val = search_player(pcode);
                        if (val == 1) // check condition for 2 players with same name
                        {
                            printf("Player code %d already exists.\n", pcode);
                            break;
                        }
                        printf("Enter name: ");
                        scanf(" %49[0-9a-zA-Z ]", pname);
                        printf("Enter Professional club: ");
                        scanf(" %49[0-9a-zA-Z ]", club);
                        fflush(stdin);
                        printf("Enter Age: ");
                        scanf("%d", &age);
                        if (age < 17 || age > 99) // Valid Age check condition
                        {
                            printf("Age is invalid.");
                            break;
                        }
                        insert_player(pcode, pname, club, age);
                        num = 0;
                        break;
                    case 's': // Search for a player in the database
                        printf("Enter Player's code to search: ");
                        scanf("%d", &pcode);
                        val = search_player(pcode);
                        if (val == 1)
                            printf("Player code %d is found.\n", pcode);
                        else
                            printf("Player code %d is not found. \n", pcode);
                        num=0;
                        break;
                    case 'd': // Delete player record
                        printf("Enter Player's code to delete: ");
                        scanf("%d", &pcode);
                        Delete_player(pcode);
                        num=0;
                        break;
                    case 'u':// Update a player in the database
                        printf("Enter Player's code to update: ");
                        scanf("%d", &pcode);
                        update_player(pcode);
                        num=0;
                        break;
                    case 'p': // Print the entire list of players
                        display_player();
                        num=0;
                        break;
                }
            }
        }
        if (choice == 't') // checking user's choice
        {
            int num = 1;
            char userchoice; // variable storing user's choice
            char tname[25]; // variable storing team name
            char groupseeding[3]; // variable storing group seeding
            int tcode; // variable storing team code
            char pkit; // variable storing team's primary kit colour
            int val;
            printf("\ni to insert a new Team. \ns to search for a Team in database. \nd to delete a Team from database. "
                   "\nu to update a Team in the database. \np to display the list of Teams.");
            while (num != 0)
            {
                fflush(stdin);
                printf("\nEnter Choice: ");
                scanf(" %c", &userchoice);
                switch (userchoice)
                {
                    case 'i': // inserts a new team
                        printf("Enter Team code: ");
                        scanf("%d", &tcode);
                        val = search_team(tcode);
                        if (val == 1) // check condition for 2 teams with same team code
                        {
                            printf("Team code %d already exists.\n", tcode);
                            break;
                        }
                        printf("Enter Team name: ");
                        scanf(" %24[0-9a-zA-Z ]", tname);
                        printf("Enter Group seeding: ");
                        scanf(" %2[1-4A-H ]", groupseeding);
                        fflush(stdin);
                        printf("Enter Primary Kit colour:");
                        scanf(" %c", &pkit);
                        insert_team(tcode, tname, groupseeding, pkit);
                        num=0;
                        break;
                    case 's': // searches for a team
                        printf("Enter Team code to search: ");
                        scanf("%d", &tcode);
                        val = search_team(tcode);
                        if (val == 1)
                            printf("Team code %d is found.\n", tcode);
                        else
                            printf("Team code %d not found.\n", tcode);
                        num=0;
                        break;
                    case 'd': // deletes a team
                        printf("Enter Team code to delete: ");
                        scanf("%d", &tcode); // user's choice for team deletion
                        Delete_team(tcode);
                        num=0;
                        break;
                    case 'u': // updates a team record
                        printf("Enter Team code to update: ");
                        scanf("%d", &tcode);
                        update_team(tcode);
                        num=0;
                        break;
                    case 'p': // displays list of teams entered
                        display_team();
                        num=0;
                        break;
                }
            }
        }
        if (choice == 'q') // checking user's choice
            exit(0); // quitting program
        if (choice == 'h') // checking user's choice
            printf("Enter instructed alphabets to execute the program.\n"); // description of how to use the program
    }
}
