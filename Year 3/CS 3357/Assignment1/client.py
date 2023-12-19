# Author: Ashna Mittal
# CS3357: Assignment 1
# Import required modules
from socket import socket, AF_INET, SOCK_STREAM
import sys

# Ensuring the acceptance of 3 maximum clients
if len(sys.argv) != 3:
    print("Usage: python client.py <ServerPort> <RequestedFileName>")
    sys.exit(1)

# Defining the server IP and port
serverIP = '127.0.0.1' # IP Address
serverPort = int(sys.argv[1]) # Port Number
requestedFile = sys.argv[2] # name of the requested file

clientSocket = socket(AF_INET, SOCK_STREAM) # Initializing a new socket for the client

try:
    # Attempting to establish a connection to the server
    clientSocket.connect((serverIP, serverPort))
    print("Connection to Server Established")

    # Building the HTTP GET request
    request = f"GET /{requestedFile} HTTP/1.1\r\nHost: {serverIP}:{serverPort}\r\n\r\n"
    clientSocket.send(request.encode()) # Sending the request to the server

    response = b''  # Buffer to collect the incoming data

    # Keep receiving data from the server until no more is sent
    while True:
        data = clientSocket.recv(1024)
        if not data:
            break
        response += data

    # Checking if the received data starts with a standard HTTP header
    if not response.startswith(b'HTTP/1.1'):
        print("Invalid response from server.")
    else:
        # Display the header part of the received data
        print(response.decode().split('\r\n\r\n', 1)[0])

        # If the response indicates success (HTTP 200 OK), saving the content to a New file
        if response.startswith(b'HTTP/1.1 200 OK'):
            file_data = response.split(b'\r\n\r\n', 1)[1]
            with open("new_file.html", 'wb') as file:
                file.write(file_data)
                print(f"File saved as new_file")
            # Displaying the saved content for verification in a new File
            with open("new_file.html", 'rb') as file:
                print(file.read())

# Handling any exceptions that arise during the above processes
except Exception as e:
    print(f"Error: {e}")

finally:
    # Ensuring the socket connection is closed
    clientSocket.close()
    print('Connection closed')
