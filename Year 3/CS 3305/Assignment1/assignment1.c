/* Author: Ashna Mittal*/
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>

int main(int argc, char *argv[]){
    pid_t ch1,ch2, ch1_1, ch1_2,parent; // Declaration of all the parent and child variables
    parent = getpid();
    printf("parent (PID %d): process started\n", parent);
    printf("parent (PID %d): forking child_1\n", parent);
    ch1 = fork(); //Creating child_1
    if (ch1 == 0) // successful check condition for child_1
    {   
        printf("parent (PID %d): fork successful for child_1 (PID %d)\n",parent,getpid());
        printf("parent (PID %d): waiting for child_1 (PID %d) to complete\n",parent,getpid());
        printf("child_1 (PID %d): process started from parent (PID %d)\n",getpid(),parent);
        printf("\n");
        printf("child_1 (PID %d): forking child_1.1\n", getpid());
        ch1_1 = fork(); //Creating child1_1
        if(ch1_1 == 0 ) // successful check condition for child1_1
        {
            printf("child_1.1 (PID %d): process started from child_1 (PID %d)\n",getpid(),getppid());
            printf("child_1.1 (PID %d):calling an external program [./external_program1.out]\n",getpid());
            execl("external_program1.out",argv[1],NULL);
        }
        else
        {
            wait(NULL); // Wait for the child to finish
            printf("child_1 (PID %d): completed child_1.1\n", getpid());
            printf("\n");
            printf("child_1 (PID %d): forking child_1.2\n", getpid());
            ch1_2 = fork(); //Creating child1_2
            if(ch1_2 == 0) //successful check condition for child1_2
            {
                printf("child_1.2 (PID %d): process started from child_1 (PID %d)\n",getpid(), getppid());
                printf("child_1.2 (PID %d): calling an external program [./external_program1.out]\n",getpid());
                execl("external_program1.out",argv[2],NULL);
            }
            else
            {
                wait(NULL); // Wait for the child to finish
                printf("child_1 (PID %d): completed child_1.2\n",getppid());
                printf("\n");
            }
        }

    }
    else
    {  
        if (ch1> 0)
        {
        wait(NULL);   // Wait for the  child to finish
        printf("parent (PID %d): forking child_2\n",parent);
        ch2 = fork(); //Creating child_2
        if(ch2 == 0) // successful check condition for child_2
        {
            printf("parent (PID %d): fork successful for child_2 (PID %d)\n", parent, getpid());
            printf("child_2 (PID %d): process started from parent (PID %d)\n", getpid(), parent);
            printf("child_2 (PID %d): calling an external program [./external_program2.out]\n",getpid());
            printf("\n");
            execl("./external_program2.out",argv[3],NULL);
        
        }
                else
            {
                wait(NULL); // Wait for the child to finish
                printf("parent (PID %d): completed parent\n",parent);
            }
        }
    }
    return 0;
}
