/*
This program descriptively prompts the user for the input value in the range 0-999, descriptively displays
the result by converting the integer into the English word (in lower case), and returns to the main menu.
Author Name: Ashna Mittal
Student ID: 251206758
 */
#include <stdio.h>
#include <string.h>

void getWord(char* number)
{
    int length = strlen(number);
    char* nums[] = { "zero", "one", "two",   "three", "four","five", "six",
                     "seven", "eight", "nine" }; // contains the english words for 10 single digits : 0-9
    char* two_digs[] = { "",          "ten",      "eleven",  "twelve","thirteen",
                         "fourteen", "fifteen", "sixteen","seventeen", "eighteen",
                         "nineteen" }; // contains the english words for two digits: 10-19
    char* multiples[] = { "",       "",        "twenty","thirty", "forty",   "fifty",
                          "sixty",  "seventy", "eighty","ninety" };
    // contains the english words for multiples of 10 from 20 to 90
    char* powers[] = { "hundred and" };
    printf("\nYou entered the number "); // descriptively displays the entered number by the user
    if (length == 1) // check condition to find if the number entered is a single digit
    {
        printf("%s\n", nums[*number - '0']);
        return;
    }
    while (*number != '\0')
    {
        if (length == 3) // check condition to find if the number entered is a three digit number
        {
            if (*number - '0' != 0)
            {
                printf("%s ", nums[*number - '0']);
                printf("%s ", powers[length - 3]);
            }
            --length;
        }
        else
        {
            if (*number == '1')
            {
                int sum = *number - '0' + *(number + 1) - '0';
                printf("%s\n", two_digs[sum]);
                return;
            }
            else if (*number == '2' && *(number + 1) == '0')
            {
                printf("twenty\n");
                return;
            }
            else
            {
                int i = *number - '0';
                printf("%s ", i ? multiples[i] : "");
                ++number;
                if (*number != '0')
                    printf("%s ",nums[*number - '0']);
            }
        }
        ++number;
    }
    printf("\n");
}

int main(void)
{
    int n;
    char str[3];
    while(1)
    {
        printf("\nPlease enter a value (1-999, 0 to quit): ");
        scanf("%s",str);
        if(str[0]=='0') break; // if the user enters 0, the program terminates
        else
        {
            getWord(str); // if the number entered is between 1-999, it is converted to english words
        }
    } // while loop closes
    return 0;
}