/*
This program accept integers separated by spaces, place the integers into an array, and work on the integers found in the array
 Author : Ashna Mittal
 Student ID: 251206758
 */
#include <stdio.h>

void readInputs(int* arr, int size)
{
    printf("\nPlease enter your integers separated by spaces: ");
    int i;
    for(i=0; i<size; i++)
        scanf("%d",&arr[i]);

    printf("\nPart 1:\n");
    printf("\tYour array is: ");
    for(i=0; i<size-1; i++)
        printf("[%d] = %d, ",i,arr[i]);
    printf("[%d] = %d",i,arr[i]);
    printf("\n");
}

void largest(int* arr, int size) // This method prints The largest value in the array
{
    int i, pos= 0;
    for(i=0; i<size; i++)
    {
        if(arr[i]>arr[pos])
            pos = i;
    }
    printf("\nPart 2:\n");
    printf("\tThe largest value in your array is: %d\n", arr[pos]);
}

void reverseAndPrint(int* arr, int size) // This method prints The elements of the array in reverse order
{
    int i;
    printf("\nPart 3:\n");
    printf("\tYour array in reverse is: ");
    for(i=size-1; i>=0; i--)
    {
        printf("%d ", arr[i]);
    }
    printf("\n");
}

void printSum(int* arr, int size) // This method prints The sum of all values in the array
{
    int i;
    int sum = 0;
    printf("\nPart 4:\n");
    for(i=size-1; i>=0; i--)
    {
        sum += arr[i];
    }
    printf("\tThe sum of all values in your array is: %d\n", sum);
}


void sortAndPrint(int* arr, int size) // This method prints The elements of the array from largest to smallest (descending order)
{
    int temp[12];
    int i,j;
    for(int i=0; i<size; i++)temp[i] = arr[i];
    for(i=0; i<size;i++)
    {
        for(j=0; j<size-i-1; j++)
        {
            if(temp[j]<temp[j+1])
            {
                int t = temp[j];
                temp[j] = temp[j+1];
                temp[j+1] = t;
            }
        }
    }
    printf("\nPart 5:\n");
    printf("\tYour array in sorted order is: ");
    for(i=0; i<size; i++)
    {
        printf("%d ", temp[i]);
    }
    printf("\n");
}

void swapFirstLast(int* arr, int size) // This method prints The elements of the array except the first and last element are swapped
{
    int t = arr[0];
    int i;
    arr[0] = arr[size-1];
    arr[size-1] = t;

    printf("\nPart 6:\n");
    printf("\tYour array with first and last element switched is: ");
    for(i=0; i<size; i++)
    {
        printf("%d ", arr[i]);
    }
    printf("\n");
}

int main()
{
    int size;
    int arr[12];
    printf("Please enter the number of integers to process: ");
    scanf("%d",&size);
    while(size<5 || size>12)
    {
        printf("Number not in the given range.\n");
        return 0;
    }
    int bytes = size*sizeof(int);
    printf("\tThere is enough room in your array for %d integers (%d bytes)\n", size, bytes);
    // calls the 6 methods
    readInputs(arr, size);
    largest(arr,size);
    reverseAndPrint(arr,size);
    printSum(arr,size);
    sortAndPrint(arr,size);
    swapFirstLast(arr,size);
}