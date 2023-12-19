# Author: Ashna Mittal
# Assignment: UDP Simple Chat Room - UDP Client Code Implementation

import socket
import argparse
import threading

stop_thread = False  # Global flag to control the receiverThread

# Function to listen for and print messages received from the server.
def receiveMessages(clientSocket):
    global stop_thread
     # Keep listening until the stop_thread flag is set.
    while not stop_thread:
        try:
            data, _ = clientSocket.recvfrom(1024)
            message = data.decode('utf-8')
            print(message)
        except:
             # If there's any error (e.g., the socket is closed), exit the loop.
            break
    clientSocket.close()

def run(clientSocket, clientname, serverAddr, serverPort):
    # Send the client's username to the server.
    clientSocket.sendto(clientname.encode('utf-8'), (serverAddr, serverPort))
    # Start a new thread to continuously listen for messages from the server.
    receiverThread = threading.Thread(target=receiveMessages, args=(clientSocket,))
    receiverThread.daemon = True
    receiverThread.start()

    while True: # Main loop to get input from the user and send messages to the server.
        message = input()
        clientSocket.sendto(message.encode('utf-8'), (serverAddr, serverPort))
        # If the user types "exit", set the stop_thread flag to stop the receiver thread and exit the loop.
        if message == "exit":
            print(f'Client Closing....')
            stop_thread = True
            break

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='UDP Chat Client')
    parser.add_argument('name', help='Your username')
    args = parser.parse_args()
    clientname = args.name
    # Define the server's address and port number.
    serverAddr = '127.0.0.1'
    serverPort = 9301
    # Create a UDP socket for the client.
    clientSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    # Start the client.
    run(clientSocket, clientname, serverAddr, serverPort)
