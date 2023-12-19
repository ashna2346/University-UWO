/*
Author: Ashna Mittal
CS 3305: Assignment 3
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

// Declaration of Global variables
int input_array[4];
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t SumCompleted = PTHREAD_COND_INITIALIZER;
pthread_cond_t EvenOddCompleted = PTHREAD_COND_INITIALIZER;
pthread_cond_t MultiplicationCompleted = PTHREAD_COND_INITIALIZER;

// Flags to check the completion of the 3 thread operations
int sumCheck = 0, EvenOddCheck = 0, multiplicationCheck = 0;

// This function is executed by thread_1 to perform summation of X and Y
void *sum(void *thread_id) {
    pthread_mutex_lock(&mutex);
    printf("thread_1 (TID %lu) reads X = %d and Y = %d from input_array[]\n", pthread_self(), input_array[0], input_array[1]);
    input_array[2] = input_array[0] + input_array[1];
    printf("thread_1 (TID %lu) writes X + Y = %d to the input_array[2]\n", pthread_self(), input_array[2]);
    sumCheck = 1;
    pthread_cond_signal(&SumCompleted);
    pthread_mutex_unlock(&mutex);
    return NULL;
}

// This function is executed by thread_2 to check if the calculated sum is even or odd
void *even_odd(void *thread_id) {
    pthread_mutex_lock(&mutex);
    while(!sumCheck)
    {
        pthread_cond_wait(&SumCompleted, &mutex);
    }
    printf("thread_2 (TID %lu) reads %d from the input_array[2]\n", pthread_self(), input_array[2]);
    if(input_array[2] % 2 == 0) //check condition for even number
        printf("thread_2 (TID %lu) identified that %d is even\n", pthread_self(), input_array[2]);
    else // odd number condition
        printf("thread_2 (TID %lu) identified that %d is odd\n", pthread_self(), input_array[2]);
    
    EvenOddCheck = 1;
    pthread_cond_signal(&EvenOddCompleted);
    pthread_mutex_unlock(&mutex);
    return NULL;
}

// This function is executed by thread_2 to perform multiplication of X and Y
void *multiplication(void *thread_id)
{
    pthread_mutex_lock(&mutex);
    while(!EvenOddCheck)
    {
        pthread_cond_wait(&EvenOddCompleted, &mutex);
    }
    input_array[3] = input_array[0] * input_array[1];
    printf("thread_2 (TID %lu) reads X and Y from input_array[], writes X * Y = %d to input_array[3]\n", pthread_self(), input_array[3]);
    multiplicationCheck = 1;
    pthread_cond_signal(&MultiplicationCompleted);
    pthread_mutex_unlock(&mutex);
    return NULL;
}

// This function is executed by thread_3 to reverse the product of X and Y
void *reverse_num(void *thread_id)
{
    pthread_mutex_lock(&mutex);
    while(!multiplicationCheck)
    {
        pthread_cond_wait(&MultiplicationCompleted, &mutex);
    }
    int originalNum = input_array[3];
    int reverseNum = 0; // initializing reverse number
    while(originalNum != 0) // check condition
    {
        reverseNum = reverseNum * 10 + originalNum % 10; // formation of the reverse number
        originalNum /= 10;
    }
    printf("thread_3 (TID %lu) reverses the number %d  ->  %d\n", pthread_self(), input_array[3], reverseNum);
    pthread_mutex_unlock(&mutex);
    return NULL;
}

int main(int argc, char *argv[])
{
    //check for valid input
    if(argc != 3)
    {
        printf("Please provide exactly two arguments.\n");
        return 1;
    }

    pthread_t tSumNum, tEvenOddNum, tMultiplicationNum, tReverseNum;
    input_array[0] = atoi(argv[1]);
    input_array[1] = atoi(argv[2]);

    printf("parent (PID %d) receives X = %d and Y = %d from the user\n", getpid(), input_array[0], input_array[1]);
    printf("parent (PID %d) writes X = %d and Y = %d to input_array[]\n", getpid(), input_array[0], input_array[1]);

    // create a new thread
    pthread_create(&tSumNum, NULL, sum, NULL);
    pthread_create(&tEvenOddNum, NULL, even_odd, NULL);
    pthread_create(&tMultiplicationNum, NULL, multiplication, NULL);
    pthread_create(&tReverseNum, NULL, reverse_num, NULL);

    // join with a terminated thread
    pthread_join(tSumNum, NULL);
    pthread_join(tEvenOddNum, NULL);
    pthread_join(tMultiplicationNum, NULL);
    pthread_join(tReverseNum, NULL);

    // destroy the mutex object referenced by mutex
    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&SumCompleted);
    pthread_cond_destroy(&EvenOddCompleted);
    pthread_cond_destroy(&MultiplicationCompleted);

    return 0;
}
