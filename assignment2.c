/* 
Author: Ashna Mittal
CS 3305: Assignment 2
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <signal.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <sys/stat.h>

// Function that calculates the summation of numbers from start to end inclusive
int summation(int start, int end)
{
	int sum = 0;
	if (start < end)
	{
		sum = ((end * (end + 1)) - (start * (start - 1))) / 2;
	}
	return sum;
}

// Function that gets the starting value of the ith part
int ith_part_start(int i, int N, int M)
{
	int part_size = N / M;
	int start = i * part_size;
	return start;
}

// Function that gets the ending value of the ith part
int ith_part_end(int i, int N, int M)
{
	int part_size = N / M;
	int end = (i < M - 1) ? ((i + 1) * part_size - 1) : N;
	return end;
}

int main(int argc, char **argv)
{
	// Checking for correct number of arguments
	if (argc != 3)
	{
		printf("Usage: %s <N> <M>\n", argv[0]);
		exit(1);
	}

	// Declaring variables and arrays
	int N = atoi(argv[1]);
	int M = atoi(argv[2]);
	int pipes[M][2];
	pid_t child1_pid, child_pid;

	// Checking if the value of M entered by the user is valid and if not then printing the appropriate message
	if (M < 1 || M > 10)
	{
		printf("Input Error: M must be between 1 and 10\n");
		exit(1);
	}
	// Checking if the value of N entered by the user is valid and if not then printing the appropriate message
	if (N < 1 || N > 100)
	{
		printf("Input Error: N must be between 1 and 100\n");
		exit(1);
	}

	// Initial message sent from the parent process
	printf("parent(PID %d): process started\n", getpid());
	printf("\n");

	child1_pid = fork(); // Creating the first child : child_1

	if (child1_pid == 0)  // check condition for child_1 to see if its child_1 or the parent
	{
		printf("child_1(PID %d): process started from parent(PID %d)\n", getpid(), getppid());
		printf("child_1(PID %d): forking child_1.1....child_1.%d\n", getpid(), M);
		printf("\n");

		for (int i = 0; i < M; i++) // Loop for Forking M children within child_1
		{
			// Creating a pipe for communication between child1 and child1.i
			pipe(pipes[i]);
			child_pid = fork(); // Forking a new child (child1.i)
			if (child_pid == 0)  // check condition for child1.i
			{
				// Calculating the partial sum for this child
				int start = ith_part_start(i, N, M);
				int end = ith_part_end(i, N, M);
				int sum = summation(start, end);

				// Writing the partial sum to the pipe for child_1 to read
				close(pipes[i][0]);  // closing reading end
				write(pipes[i][1], &sum, sizeof(sum));
				close(pipes[i][1]);

				printf("child_1.%d(PID %d): fork() successful\n", i + 1, getpid());
				printf("child_1.%d(PID %d): partial sum: [%d - %d] = %d\n", i + 1, getpid(), start, end, sum);
				
				// Terminating this child
				exit(0);
			}
		}

		// Collecting all the partial sums from all of child_1's children
		int total_sum = 0;
		for (int i = 0; i < M; i++)
		{
			int partial_sum;
			wait(NULL);  // waiting for child_1.i to finish
			
			// Reading the partial sum from the pipe
			close(pipes[i][1]);  // close writing end
			read(pipes[i][0], &partial_sum, sizeof(partial_sum));
			close(pipes[i][0]);
			total_sum += partial_sum;
		}

		// Printing total_sum and terminating child_1
		printf("child_1(PID %d): total sum = %d\n", getpid(), total_sum);
		printf("child_1(PID %d): child_1 completed\n", getpid());
		printf("\n");
		exit(0);
	}
	else  // executing the parent
	{
		// creation of child_1
		printf("parent(PID %d): forking child_1\n", getpid());
		printf("parent(PID %d): fork successful for child_1(PID %d)\n", getpid(), child1_pid);
		printf("parent(PID %d): waiting for child_1(PID %d) to complete\n", getpid(), child1_pid);
		printf("\n");
		
		// Waiting for child_1 to finish
		wait(NULL);
		// Terminating parent
		printf("parent(PID %d): parent completed\n", getpid());
	}
	return 0;
}
