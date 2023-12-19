/*
Author: Ashna Mittal
CS 3305: Assignment 4
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>


#define MAX_PROCESSES 50
#define MAX_LINE_LENGTH 1000

typedef struct
{
    char name[10];
    int arT; // Arrival Time
    int brT; // Burst Time
    int wT; // Waiting Time
    int cT; // Completion Time
    int rT; // Remaining Time
    int taT; // Turnaround Time
    int isComplete;
    int arPrinted;
} Process;

int readFile(const char* fName, Process processes[], int maxProcessesPerLine, char lines[][MAX_LINE_LENGTH], int maxLines);
void SJFexecution(Process processes[], int n);
void printProcessInfo(Process p);
float AverageCalc(int values[], int n);

int main()
{
    Process processes[MAX_PROCESSES];
    char lines[100][MAX_LINE_LENGTH]; // An estimate of 100 Test cases is taken
    int nTestCase = readFile("sjf_input.txt", processes, MAX_PROCESSES, lines, 100);

    for (int i = 0; i < nTestCase; ++i) // loop to iterate each test case
    {
        printf("Test case #%d: %s\n", i + 1, lines[i]);
        int ct = 0;
        char *tkn = strtok(lines[i], " ");
        while (tkn != NULL)
        {
            strcpy(processes[ct].name, tkn);
            processes[ct].arT = atoi(strtok(NULL, " "));
            processes[ct].brT = atoi(strtok(NULL, " "));
            processes[ct].rT = processes[ct].brT;
            processes[ct].isComplete = 0;
            ct++;
            tkn = strtok(NULL, " ");
        }
        printf("Number of Processes: %d\n", ct);
        SJFexecution(processes, ct);
    }
    return 0;
}

int readFile(const char* fName, Process processes[], int maxProcessesPerLine, char lines[][MAX_LINE_LENGTH], int maxLines)
{
    FILE* file = fopen(fName, "r"); // opening file in read mode
    if (file == NULL) // checking if the file is unable to open successfully
    {
        perror("Error opening file"); // printing error message if the file is unable to open successfully
        return -1;
    }

    int i = 0;
    while (fgets(lines[i], MAX_LINE_LENGTH, file) != NULL && i < maxLines) // parsing through the input file
    {
        lines[i][strcspn(lines[i], "\n")] = 0; // Elilimate newline character at the end of each line in the input file
        i++;
    }
    fclose(file); // closing the file
    return i;
}


void SJFexecution(Process processes[], int n) // Function to apply the Shortest Job First CPU Scheduling Algorithm
{
    int curT = 0, completed = 0;
    int waitT[n], taTs[n];

    printf("Process Scheduling Started:\n");

    for (int i = 0; i < n; i++)
    {
    processes[i].arPrinted = 0;
    }

    while (completed != n)
    {
        int index = -1;
        int lim = INT_MAX;

        // Printing of the arrival of processes
        for (int i = 0; i < n; i++) 
        {
            if (processes[i].arT <= curT && !processes[i].isComplete && !processes[i].arPrinted)
            {
                printf("CPU Time %d: [%s Arrived]\n", processes[i].arT, processes[i].name);
                processes[i].arPrinted = 1;
                }
            }

        // loop so that the process to be executed is selected
        for (int i = 0; i < n; i++) {
            if (processes[i].arT <= curT && processes[i].isComplete == 0)
            {
                if (processes[i].brT< lim)
                {
                    lim = processes[i].brT;
                    index = i;
                }
                // If two processes have the same burst time, the one that arrived first executes first
                if (processes[i].brT == lim)
                {
                    if (index == -1 || processes[i].arT < processes[index].arT)
                    {
                        index = i;
                    }
                }
            }
        }

        // If no process is ready, increment the time and continue
        if (index == -1) 
        {
            printf("CPU Time %d: None\n", curT);
            curT++;
            continue;
        }

        // Execute selected process
        printf("CPU Time %d: %s [%d/%d]\n", curT, processes[index].name, 0, processes[index].brT);
        processes[index].cT = curT + processes[index].brT;
        processes[index].taT = processes[index].cT - processes[index].arT;
        processes[index].wT = processes[index].taT - processes[index].brT;
        curT += processes[index].brT;

        // Print the process execution at each time unit
        for (int j = 1; j <= processes[index].brT; j++) {
            printf("CPU Time %d: %s [%d/%d]\n", curT - processes[index].brT+ j, processes[index].name, j, processes[index].brT);
        }

        // Print the completion message for the current process
        printProcessInfo(processes[index]);

        waitT[completed] = processes[index].wT;
        taTs[completed] = processes[index].taT;
        completed++;
        processes[index].isComplete = 1;
    }

    float avgtaT = AverageCalc(taTs, n);
    float avgwT = AverageCalc(waitT, n);
    printf("Process scheduling completed with Avg Turnaround Time: %.2f, Avg Waiting Time: %.2f\n\n", avgtaT, avgwT);
}


void printProcessInfo(Process p) // function to print the Turnaround and Waiting Time of the test cases
{
    printf("Process %s completed with Turnaround Time: %d, Waiting Time: %d\n", p.name, p.taT, p.wT);
}

float AverageCalc(int values[], int n) // function to calculate the Average urnaround and Waiting Time of the test cases
{
    float sum = 0; // variable for storing the sum
    for (int i = 0; i < n; i++)
    {
        sum += values[i]; // Sum calculation
    }
    return sum / n; // returning the average
}
