/**
 * Author: Ashna Mittal
 * CS3305: Assignment 5
*/


#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <string.h>

#define MAX_ACCOUNTS 100
#define MAX_CLIENTS 100
#define MAX_TRANSACTIONS 100

typedef struct
{
    char acctName[10]; // an array of characters with a size of 10 and is intended to store the name of the account
    int balance;
    pthread_mutex_t lock; // Mutex for each account
} Account;

typedef struct
{
    char type[10]; // deposit/withdraw
    char acctName[10];
    int amt; // Amount
} Transaction;

typedef struct
{
    char clientName[10];
    Transaction transactions[MAX_TRANSACTIONS];
    int transacCnt; // Count of Transactions
} Client;

Account accounts[MAX_ACCOUNTS];
int acctCnt = 0; // Account Count
Client clients[MAX_CLIENTS];
int clientCnt = 0; // Client Count

void parseInputFile()
{
    FILE *file = fopen("assignment_5_input.txt", "r");
    if (file == NULL)
    {
        perror("Error opening the file");
        exit(1);
    }

    char line[256];
    while (fgets(line, sizeof(line), file))
    {
        if (line[0] == 'A') // Account line
        { 
            sscanf(line, "%s balance %d", accounts[acctCnt].acctName, &accounts[acctCnt].balance);
            pthread_mutex_init(&accounts[acctCnt].lock, NULL); // Initializing mutex for account
            acctCnt++;
        } 
        else if (line[0] == 'C') { // Client line
            Client *client = &clients[clientCnt++];
            sscanf(line, "%s", client->clientName);
            char *token = strtok(line, " ");
            while (token != NULL)
            {
                token = strtok(NULL, " ");
                if (token != NULL && (strcmp(token, "deposit") == 0 || strcmp(token, "withdraw") == 0))
                {
                    strcpy(client->transactions[client->transacCnt].type, token);
                    token = strtok(NULL, " ");
                    strcpy(client->transactions[client->transacCnt].acctName, token);
                    token = strtok(NULL, " ");
                    client->transactions[client->transacCnt].amt = atoi(token);
                    client->transacCnt++;
                }
            }
        }
    }
    fclose(file);
}

int findAccount(char *name)
{
    for (int i = 0; i < acctCnt; i++)
    {
        if (strcmp(accounts[i].acctName, name) == 0)
        {
            return i;
        }
    }
    return -1; // Not found
}


void *processTransactions(void *arg)
{
    Client client = *(Client *)arg;
    for (int i = 0; i < client.transacCnt; i++)
    {
        Transaction t = client.transactions[i];
        int accountIndex = findAccount(t.acctName);

        pthread_mutex_lock(&accounts[accountIndex].lock); // Locking the account

        if (strcmp(t.type, "deposit") == 0)
        {
            accounts[accountIndex].balance += t.amt;
        } 
        else if (strcmp(t.type, "withdraw") == 0 && accounts[accountIndex].balance >= t.amt)
        {
            accounts[accountIndex].balance -= t.amt;
        }

        pthread_mutex_unlock(&accounts[accountIndex].lock); // Unlocking the account
    }
    return NULL;
}

int main()
{
    parseInputFile();

    printf("No. of Accounts: %d\n", acctCnt);
    printf("No. of Clients: %d\n", clientCnt);

    pthread_t threads[MAX_CLIENTS];
    for (int i = 0; i < clientCnt; i++)
    {
        if (pthread_create(&threads[i], NULL, processTransactions, (void *)&clients[i]))
        {
            perror("Failed to create thread");
            exit(1);
        }
    }

    for (int i = 0; i < clientCnt; i++)
    {
        pthread_join(threads[i], NULL);
    }

    for (int i = 0; i < acctCnt; i++)
    {
        printf("%s balance: %d\n", accounts[i].acctName, accounts[i].balance);
    }

    for (int i = 0; i < acctCnt; i++)
    {
        pthread_mutex_destroy(&accounts[i].lock); // Cleaning up the mutexes
    }

    return 0;
}

