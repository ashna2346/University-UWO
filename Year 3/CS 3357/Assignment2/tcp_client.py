# Author: Ashna Mittal
# Assignment: TCP Simple Chat Room - TCP Client Code Implementation

import threading
import socket
import argparse

# Function to continuously receive messages from the server.
def receiveMessages(clientSocket, clientName):
    while True:
        try:
            # Try to receive a message from the server.
            message = clientSocket.recv(1024).decode('utf-8')
            if message == 'NICKNAME':
                clientSocket.send(clientName.encode('utf-8'))
            else:
                # Otherwise, just print the received message.
                print(message)
        except:
            # If there's any error in the process (e.g., the connection drops),
            # print an error message, close the socket, and break out of the loop.
            clientSocket.close()
            break

# Function to continuously read user input and send it to the server.
def read(clientSocket, clientName):
    while True:
        message = input("")
        if message == 'exit':
            print("Client CLosing...")
            exit_msg = 'exit'
            clientSocket.send(exit_msg.encode('utf-8'))
            clientSocket.close()
            break
        full_message = f'{clientName}: {message}'
        try:
            clientSocket.send(full_message.encode('utf-8'))
        except:
            break

# Main function to start the client's operations.
def run(clientSocket, clientName):
    # The main client function.
    receiverThread = threading.Thread(target=receiveMessages, args=(clientSocket, clientName,))
    receiverThread.start()

    writerThread = threading.Thread(target=read, args=(clientSocket, clientName,))
    writerThread.start()

# **Main Code**:
if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Argument Parser')
    parser.add_argument('name')  # to use: python tcp_client.py username
    args = parser.parse_args()
    client_name = args.name
     # Define the server's address and port number.
    server_addr = '127.0.0.1'
    server_port = 9301
    # Create a TCP socket and connect to the server.
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect((server_addr, server_port))
    # Start the client's main function.
    run(client_socket, client_name)

