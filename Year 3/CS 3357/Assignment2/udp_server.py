# Author: Ashna Mittal
# Assignment: UDP Simple Chat Room - UDP Server Code Implementation

import socket

# A dictionary to store client addresses (as keys) and their usernames (as values).
clients = {}

def run(serverSocket, serverPort):
    # Display server status on Screen.
    print(f'This is the server side.\nI am ready to receive connections on port {serverPort}')

    # Infinite loop to continuously listen for messages.
    while True:
        # Receive data from clients, up to 1024 bytes.
        data, clientAddress = serverSocket.recvfrom(1024)  # Receive data from clients
        message = data.decode('utf-8')

        # Check if clientAddress is not recognized, i.e., it's a new client.
        if clientAddress not in clients:
            # Store the client's username
            nickname = message
            clients[clientAddress] = nickname
            # Display on Screen the new client's details.
            print(f'Message received from {str(clientAddress)}: joining:{nickname}')
            print(f'User {nickname} joined from address {clientAddress}')
            # Notify all other clients about this new client.
            for address, _ in clients.items():
                serverSocket.sendto(f'>>{nickname} has joined the chat!'.encode('utf-8'), address)

        else:
            # Fetch the stored nickname of the client if the client is recognized
            nickname = clients[clientAddress]
            # If the received message is "exit", handle client disconnection.
            if message == "exit":
                print(f'Message received from {str(clientAddress)}: leaving:{nickname}')
                print(f'User {nickname} left from address {clientAddress}')
                # Remove the client from the clients dictionary.
                del clients[clientAddress]
                for address, _ in clients.items():
                    serverSocket.sendto(f'{nickname} has left the chat!'.encode('utf-8'), address)

                # If no clients remain, shut down the server.
                if not clients:
                    print("All clients have disconnected. Shutting down the server.")
                    serverSocket.close()
                    break
            # If it's a regular chat message.
            else:
                print(f'Message received from {str(clientAddress)}: {nickname}:{message}')
                display(clientAddress, serverSocket, nickname, message)

def display(clientAddress, serverSocket, nickname, message):
    # Broadcast the message to all connected clients
    data = f"{nickname}: {message}".encode('utf-8')
    # Loop through all clients and send them the message.
    for address, _ in clients.items():
        serverSocket.sendto(data, address)


if __name__ == "__main__":
    serverPort = 9301  # Set the `serverPort` to the desired port number (e.g., 9301).
    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)  # Creating a UDP socket.
    serverSocket.bind(('127.0.0.1', serverPort))  # Binding the server socket to the desired address.

    run(serverSocket, serverPort)  # Start the server to listen for connections and handle messages.
