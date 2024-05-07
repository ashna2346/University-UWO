/*
Author : Ashna Mittal
ID : 251206758
 */
#include "worldcup_team.h"
#include<stdlib.h>
#include<string.h>
#include<stdio.h>

struct Team
{
    int tcode;
    char tname[25];
    char groupseeding[3];
    char teamKit;
    struct Team *next;

}* top;

void insert_team(int code, char *name, char *groupseeding, char *teamKit) // inserts a new team
{

    if (code < 0) // valid code check condition
    {
        printf("Error! Code cannot be less than 0.");
        return;
    }
    if (groupseeding[0]=='A' || groupseeding[0]=='B' || groupseeding[0]=='C' || groupseeding[0]=='D' || groupseeding[0]=='E' || groupseeding[0]=='F' || groupseeding[0]=='G' || groupseeding[0]=='H' )
    { // valid group seeding check condition
        if(groupseeding[1]=='1' || groupseeding[1]=='2' || groupseeding[1]=='3' || groupseeding[1]=='4') // valid group seeding check condition
            printf("");
        else
        {
            printf("Only A-H and 1-4 are acceptable");
            return;
        }
    }
    else
    {
        printf("Only A-H and  1-4 are acceptable");
        return;
    }
    if (teamKit != 'R' && teamKit != 'O' && teamKit != 'Y' && teamKit != 'G' && teamKit != 'B' && teamKit != 'I' && teamKit != 'V')
    { // valid colour check condition
        printf("The team primary Kit colour can only be R, O, Y, G, B, I or V.\n");
        return;
    }

    struct Team * team = (struct Team *) malloc(sizeof(struct Team));
    team->tcode = code;
    strcpy(team->tname, name);
    strcpy(team->groupseeding, groupseeding);
    team->teamKit = teamKit;
    team->next = NULL;
    if(top == NULL)
        top = team;
    else
    {
        team->next = top;
        top = team;
    }
}

int search_team(int code) // searches for a team in the database
{
    struct Team * temp = top;
    while(temp!=NULL)
    {
        if(temp->tcode == code) //  check condition for comparing each team with the search team value
        {
            printf("Team code: %d\n", temp->tcode);
            printf("Team Name: %s\n", temp->tname);
            printf("Group Seeding: %s\n", temp->groupseeding);
            printf("Primary Kit Colour: %d\n", temp->teamKit);
            return 1;
        }
        temp = temp->next; // pointer moves to next record
    }
    return 0;
}

void update_team(int code) // updates team records
{

    struct Team * temp = top;
    while(temp!=NULL)
    {

        if(temp->tcode == code) //  check condition for comparing each team's code with the update team code
        {
            // enter update values
            printf("Enter Team name: ");
            scanf(" %24[0-9a-zA-Z ]", temp->tname);
            printf("Group Seeding: ");
            scanf("%s", temp->groupseeding);
            printf("Primary Kit Colour: ");
            scanf(" %c",&temp->teamKit);
            printf("Update Successful!\n");
            return;
        }
        temp = temp->next; // pointer moves to next record

    }
    printf("Team code %d is not found. \n", code);
}

void display_team() // displays the team list
{
    struct Team * temp = top;
    while(temp!=NULL)
    {
        printf("Team code: %d\n", temp->tcode);
        printf("Team Name: %s\n", temp->tname);
        printf("Group seeding: %s\n", temp->groupseeding);
        printf("Primary Kit Colour: %c\n\n", temp->teamKit);
        temp = temp->next; // pointer moves to next record
    }
}

void Delete_team(int code) // method to delete a team from database
{
    struct Team * tmp1 = top;
    struct Team * tmp2 = top;
    while(tmp1 != NULL)
    {
        if(tmp1->tcode == code) // check condition to compare current team code with the to-be-deleted team code
        {
            printf("Team code %d found.\n", code);
            if(tmp1 == tmp2) // check condition if team is found
            {
                top = top->next;
                free(tmp1); // freeing the memory
            }
            else
            {
                tmp2->next = tmp1->next;
                free(tmp1); // freeing the memory
            }
            printf(" Team Record Deleted!\n");
            return;
        }
        tmp2 = tmp1;
        tmp1 = tmp1->next;
    }
    printf("Team code %d not found.\n", code); // Team code is not found in the Team database
}



