/*
This program has task of designing and implementing a database of all teams (nations) which will be used
throughout the World Cup.
Author: Ashna Mittal
Student ID: 251206758
 */

#include <stdio.h>
#include <string.h>

// up to 32 possible teams
#define MAX_TEAMS 32

// struct to represent team details
struct Team
{
    int code;
    char name[25];
    char group_seeding[3];
    char Pkit_colour;
};


// Prompt for operation code and return user's choice
char getOperationCode()
{
    printf("Enter operation code: ");
    char choice;
    scanf(" %c", &choice); // Take user's choice
    return choice; // return the entered choice
}

int getTeamCode(struct Team teams[], int count, int code)
{
    for(int i = 0; i < count; i++)
    {
        if(teams[i].code == code)
            return i;
    }
    return -1;
}



// Takes a references of a Team structure
// then take data from the user into the structure
int get_team(struct Team teams[], int count)
{
    int tcode;
    printf("\t\tEnter team code: ");
    scanf("%d", &tcode); // Take user's input for team code
    if(tcode < 0 || tcode > 31) // check condition for a valid Team Code
    {
        printf("Not a valid Team code. \n");
        return 0;
    }
    else if (getTeamCode(teams, count, tcode) != -1) // check for duplicate Team Code
    {
        printf("Team already exists.\n");
        return 0;
    }

    teams[count].code = tcode;

    printf("\t\tEnter team name: ");
    scanf("%s", teams[count].name); // Take user's input for team name
    for(int i=0; i<count; i++)
    {
        if(strcmp(teams[count].name, teams[i].name) == 0) // check for unique Team name
        {
            printf("Team Name already exists.\n");
            return 0;
        }
    }


    printf("\t\tEnter group seeding: ");
    scanf(" %s", teams[count].group_seeding); // Take user's input for group seeding
    if(teams[count].group_seeding[0] < 'A' || teams[count].group_seeding[0] > 'H' || teams[count].group_seeding[1] < 49
       || teams[count].group_seeding[1] > 52) // check condition for a valid Group seeding
    {
        printf("Not a valid group seeding.\n");
        return 0;
    }
    for(int i=0; i<count; i++)
    {
        if(strcmp(teams[count].group_seeding, teams[i].group_seeding) == 0) // check for unique Group seeding
        {
            printf("Group Seeding already exists.\n");
            return 0;
        }
    }


    char Pkit_colour;
    printf("\t\tEnter the kit colour: ");
    scanf(" %c", &Pkit_colour); // Take user's input for Primary Kit colour

    if(Pkit_colour != 'R' && Pkit_colour != 'O' && Pkit_colour != 'Y'
       && Pkit_colour != 'G' && Pkit_colour != 'B' && Pkit_colour != 'I'
       && Pkit_colour != 'V') // check condition for a valid Kit colour
    {
        printf("Not a valid colour.\n");
        return 0;
    }
    teams[count].Pkit_colour = Pkit_colour;
    return 1;
}


void update(struct Team teams[], int count)
{
    int tcode;
    printf("\t\tEnter team code: ");
    scanf("%d", &tcode); // Take user's input for team code
    int index = getTeamCode(teams, count, tcode);
    if (index == -1)
        printf("Team does not exist.\n");
    else
    {
        // get other details like Team name, group seeding and kit colour
        char ns[25];
        printf("\t\tEnter team name: ");
        scanf("%s", ns); // Take user's input for Team name

        for(int i=0; i < index; i++) // check with values before the current index
        {
            if(strcmp(ns, teams[i].name) == 0) // check for unique Team name
            {
                printf("Team Name already exists.\n");
                return;
            }
        }

        for(int i= index + 1; i < count; i++)// check with values after the current index
        {
            if(strcmp(ns, teams[i].name) == 0) // check for unique Team name
            {
                printf("Team Name already exists.\n");
                return;
            }
        }
        strcpy(teams[index].name, ns ); // make copy of the string

        char gs[25];
        printf("\t\tEnter group seeding: ");
        scanf("%s", gs); // Take user's input for group seeding
        if(gs[0] < 'A' || gs[0] > 'H'|| gs[1] < 49 || gs[1] > 52) // check condition for valid Group seeding range
        {
            printf("Not a valid group seeding.\n");
            return;
        }
        for(int i=0; i < index; i++) // check with values before the current index
        {
            if(strcmp(gs, teams[i].group_seeding) == 0) // check for unique Group seeding
            {
                printf("Group Seeding already exists.\n");
                return;
            }
        }
        strcpy(teams[index].group_seeding, gs ); // make copy of the string

        for(int i= index + 1; i < count; i++)// check with values after the current index
        {
            if(strcmp(gs, teams[i].group_seeding) == 0) // // check for unique Group seeding
            {
                printf("Group seeding already exists.\n");
                return;
            }
        }

        char Pkit_colour;
        printf("\t\tEnter the kit colour: ");
        scanf(" %c", &Pkit_colour); // Take user's input for kit colour
        if(Pkit_colour != 'R' && Pkit_colour != 'O' && Pkit_colour != 'Y'&& Pkit_colour != 'G'
           && Pkit_colour != 'B' && Pkit_colour != 'I' && Pkit_colour != 'V') // valid Primary Kit Colours checking
        {
            printf("Not a valid colour.\n");
            return;
        }
        teams[index].Pkit_colour = Pkit_colour;
    }
}

char* getColourDetail(char PK_colour) // fetches the respective colour as per the First letter entered by the user
{
    if(PK_colour == 'R')
        return "Red";
    else if(PK_colour == 'O')
        return "Orange";
    else if(PK_colour == 'Y')
        return "Yellow";
    else if(PK_colour == 'G')
        return "Green";
    else if(PK_colour == 'B')
        return "Blue";
    else if(PK_colour == 'I')
        return "Indigo";
    else
        return "Violet";
}


void search(struct Team teams[], int count)
{
    int tcode;
    printf("\t\tEnter team code: ");
    scanf("%d", &tcode); // Take user's input for Team code
    int index = getTeamCode(teams, count, tcode);
    if (index == -1)
        printf("Team does not exist.\n");
    else
    {
        // prints team's information
        printf("%-15s %-25s %-25s %-25s\n", "Team Code", "Team Name", "Group Seeding", "Primary Kit Colour");
        printf("%-15d %-25s %-25s %-25s\n", teams[index].code,
               teams[index].name, teams[index].group_seeding, getColourDetail(teams[index].Pkit_colour));
    }
}

void print(struct Team teams[MAX_TEAMS], int count) // prints each team's information
{
    printf("%-15s %-25s %-25s %-25s\n", "Team Code", "Team Name", "Group Seeding", "Primary Kit Colour");
    for(int i = 0; i < count; i++)
    {
        printf("%-15d %-25s %-25s %-25s\n", teams[i].code,
               teams[i].name, teams[i].group_seeding, getColourDetail(teams[i].Pkit_colour));
    }
}

int main()
{
    // Array of structure that stores upto 32 teams
    struct Team teams[MAX_TEAMS];
    int c = 0; // counter that keeps a count of the number of teams
    // printing heading
    printf("$ ./worldCupDB\n");
    printf("******************\n");
    printf("* 2211 World Cup *\n");
    printf("******************\n");

    char choice;
    while (choice != 'q')
    {
        choice = getOperationCode(); // user's choice
        switch(choice) // Performs tasks based on user input
        {
            case 'i': // Insert a new team
                if (get_team(teams, c))
                    c++;
                break;
            case 's': // Search for a team in the database and print it out
                search(teams, c);
                break;
            case 'u': // Update a team in the database
                update(teams, c);
                break;
            case 'p': // Print the entire list of teams
                print(teams, c);
                break;
            case 'q': // Quit the program
                printf("$");
                break;
            default:
                printf("Invalid choice, please try again.\n");
                break;
        }
        printf("\n");
    }
    return 0;
}