/*
This program descriptively prompts the user for the input value, descriptively displays
the result by performing some simple conversions, and returns to the main menu.
Author Name: Ashna Mittal
Student ID: 251206758
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// function for conversion between Kilograms and Pounds
void K_and_P()
{
    char ch;
    //print statements
    printf("K for conversion from Kilogram to Pounds\n");
    printf("P for conversion from Pounds to Kilograms\n\n");
    printf("Enter choice for conversion : ");
    scanf("%s",&ch);

    while(!(ch=='K' || ch == 'P'))
    {
        printf("Wrong input! please try again : \n\n");
        printf("K for conversion from Kilogram to Pounds\n");
        printf("P for conversion from Pounds to Kilograms\n\n");
        printf("Enter choice for conversion : ");
        scanf("%s",&ch);
    }

    if(ch=='K')
    {
        double kg,pd;
        printf("Enter Kilograms : ");
        scanf("%lf",&kg);

        pd = kg*2.20462; // conversion calculation
        printf("Kilograms : %.2lf\n",kg);
        printf("Pounds : %.2lf\n",pd);
    }
    else if(ch=='P')
    {
        double kg,pd;
        printf("Enter Pounds : ");
        scanf("%lf",&pd);

        kg = pd/2.20462; // conversion calculation
        printf("Pounds : %.2lf\n",pd);
        printf("Kilograms : %.2lf\n",kg);

    }
}

// function for conversion between Hectares and Acres
void H_and_A()
{
    char ch;
    //print statements
    printf("H for conversion from Hectares to Acres\n");
    printf("A for conversion from Acres to Hectares\n\n");
    printf("Enter choice for conversion : ");
    scanf("%s",&ch);

    while(!(ch=='H' || ch == 'A'))
    {
        printf("Wrong input! please try again : \n\n");
        printf("H for conversion from Hectares to Acres\n");
        printf("A for conversion from Acres to Hectares\n\n");
        printf("Enter choice for conversion : ");
        scanf("%s",&ch);
    }

    if(ch=='H')
    {
        double h,a;
        printf("Enter Hectares : ");
        scanf("%lf",&h);

        a = h*2.47105; // conversion calculation
        printf("Hectares : %.2lf\n", h);
        printf("Acres : %.2lf\n", a);
    }
    else if(ch=='A')
    {
        double h,a;
        printf("Enter Acres : ");
        scanf("%lf",&a);

        h = a/2.47105; // conversion calculation
        printf("Acres : %.2lf\n", a);
        printf("Hectares : %.2lf\n", h);

    }
}

// function for conversion between Liter and Gallon
void L_and_G()
{
    char ch;
    //print statements
    printf("L for conversion from Liter to Gallon\n");
    printf("G for conversion from Gallon to Liter\n\n");
    printf("Enter choice for conversion : ");
    scanf("%s",&ch);

    while(!(ch=='L' || ch == 'G'))
    {
        printf("Wrong input! please try again : \n\n");
        printf("L for conversion from Liter to Gallon\n");
        printf("G for conversion from Gallon to Liter\n\n");
        printf("Enter choice for conversion : ");
        scanf("%s",&ch);
    }

    if(ch=='L')
    {
        double lt,gal;
        printf("Enter Liter : ");
        scanf("%lf",&lt);

        gal = lt*0.264172; // conversion calculation
        printf("Liter : %.2lf\n",lt);
        printf("Gallon : %.2lf\n",gal);
    }
    else if(ch=='G')
    {
        double lt,gal;
        printf("Enter Gallon : ");
        scanf("%lf",&gal);
        lt = gal/0.264172; // conversion calculation
        printf("Gallon : %.2lf\n",gal);
        printf("Liter : %.2lf\n",lt);

    }
}

// function for conversion between kilometer and Mile
void K_and_M()
{
    char ch;
    //print statements
    printf("K for conversion from Kilometer to Mile\n");
    printf("M for conversion from Mile to Kilometer\n\n");
    printf("Enter choice for conversion : ");
    scanf("%s",&ch);

    while(!(ch=='K' || ch == 'M'))
    {
        printf("Wrong input! please try again : \n\n");
        printf("K for conversion from Kilometer to Mile\n");
        printf("M for conversion from Mile to Kilometer\n\n");
        printf("Enter choice for conversion : ");
        scanf("%s",&ch);
    }

    if(ch=='K')
    {
        double km,mile;
        printf("Enter Kilometer : ");
        scanf("%lf",&km);
        mile = km*0.621371; // conversion calculation
        printf(" Kilometer : %.2lf\n",km);
        printf(" Mile : %.2lf\n",mile);
    }
    else if(ch=='M')
    {
        double km,mile;
        printf("Enter Mile : ");
        scanf("%lf",&mile);
        km = mile/0.621371; // conversion calculation
        printf("Mile : %.2lf\n",mile);
        printf(" Kilometer : %.2lf\n",km);

    }
}

int main()
{
    int choice;
    // Print statements
    printf("1 for conversion between Kilograms and Pounds\n");
    printf("2 for conversion between Hectares and Acres\n");
    printf("3 for conversion between Liter and Gallon\n");
    printf("4 for conversion between Kilometer and Mile\n");
    printf("5 for quit\n\n");
    printf("Enter choice : ");
    scanf("%d",&choice); // choice entered by the user

    while(choice!=5)
    {
        if(choice==1)
            K_and_P(); // function calling
        else if(choice==2)
            H_and_A(); // function calling
        else if(choice ==3)
            L_and_G(); // function calling
        else if(choice ==4)
            K_and_M(); // function calling
        else if(choice==5)
            return 0;
        else {
            printf("Wrong input! please try again : \n\n");
        }
        printf("\n1 for conversion between Kilograms and Pounds\n");
        printf("2 for conversion between Hectares and Acres\n");
        printf("3 for conversion between Liter and Gallon\n");
        printf("4 for conversion between Kilometer and Mile\n");
        printf("5 for quit\n");
        printf("Enter choice : ");
        scanf("%d",&choice);
    } // while loop closes
    return 0;
}