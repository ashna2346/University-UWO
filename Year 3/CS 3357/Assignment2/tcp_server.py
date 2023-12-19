# Author: Ashna Mittal
# Assignment: TCP Simple Chat Room - TCP Server Code Implementation

import threading
import socket

# Function to display messages to all connected clients
def display(clientSocket, message):
    # Iterate over all connected clients
    for client in clients:
        # Send message to all clients except the sender
        if client != clientSocket:
            try:
                client.send(message)
            # if the client connection is broken, remove the client
            except socket.error as e: # Catch exception if sending fails
                remove_client(client)

# Remove client data and notify others about the disconnection
def remove_client(clientSocket):
    # Check if clientSocket is still in the list
    if clientSocket in clients:
        index = clients.index(clientSocket)
        clients.remove(clientSocket)
        nickname = nicknames[index]
        nicknames.remove(nickname)
        display(clientSocket, f'{nickname} has left the chat!'.encode('utf-8'))

        # Check if all clients have disconnected
        if not clients:
            print("All clients have disconnected. Shutting down the server.")
            server_socket.close()
            exit(0)  # Exit the program


# Function to handle individual client's messages and disconnections
def handleClientmsj(clientAddress, clientSocket):
    while True:
        try:
            message = clientSocket.recv(1024).decode('utf-8')
            if not message:
                # Client has disconnected
                remove_client(clientSocket)
                break

            # Here we detect the special exit message from a client.
            # The message format would be "username: has left the chat."
            if 'exit' in message:
                print(f'{str(clientAddress)}: Disconnected')
                remove_client(clientSocket)
                break

            print(f'Message received from {str(clientAddress)}: {message}')
            display(clientSocket, message.encode('utf-8'))
        except:
            # Client has disconnected due to an error or socket closure
            remove_client(clientSocket)
            break

# Function to continuously accept new client connections
def receiveConnections(serverSocket):
    while True:
        try:
            clientSocket, clientAddress = serverSocket.accept()  # Accept a new client connection
            clientSocket.send('NICKNAME'.encode('utf-8')) # Request the client to send its nickname
            nickname = clientSocket.recv(1024).decode('utf-8') # Receive and store the nickname
            nicknames.append(nickname)
            clients.append(clientSocket)

            print(f'Message received from {str(clientAddress)}: joining:{nickname}')
            print(f'User {nickname} joined from address {clientAddress}')
            display(clientSocket, f'>>{nickname} has joined the chat!'.encode('utf-8'))
            clientSocket.send('Connected to server!'.encode('utf-8'))
            # Start a new thread to handle this client's messages and disconnections
            receiverThread = threading.Thread(target=handleClientmsj, args=(clientAddress, clientSocket,))
            receiverThread.start()
        except OSError:
            # This will catch the error that occurs when the socket is closed.
            break



def run(serverSocket, serverPort):
    # Main server function to start accepting new client connections
    receiveConnections(serverSocket)

# **Main Code**:
if __name__ == "__main__":
    server_port = 9301 # The server's port number
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)# Creating a TCP socket.
    server_socket.bind(('127.0.0.1', server_port))
    server_socket.listen(3) # size of the waiting_queue that stores the incoming requests to connect.
    print(f'This is the server side.\nI am ready to receive connections on port {server_port}')

    # Lists to store connected client sockets and their nicknames
    clients = []
    nicknames = []
    run(server_socket,server_port)# Calling the function to start the server.
