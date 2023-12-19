#Author: Ashna Mittal
#CS3357: Assignment 1
import socket
import threading
import time
import sys

activeConnections = 0 # Global variables for tracking the number of active connections
lock = threading.Lock() # Lock to ensure thread-safe updates to the active_connections variable
server_shutdown_event = threading.Event() # Event to signal when the server should shut down

# Function to serve a requested file to the client
def serverFile_process(clientSocket, filename):
    try:
        # Opening and reading the requested file
        with open(filename, 'rb') as file:
            response_data = file.read()
            # Construct the HTTP response header
            fileHeader = f"HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: {len(response_data)}\r\n\r\n"
            # Send the response header and file data to the client
            clientSocket.send(fileHeader.encode() + response_data)
    except FileNotFoundError:
        # If the file is not found, a 404 Not Found response is sent to the client
        ret_response = b'HTTP/1.1 404 Not Found\r\n\r\nFile Not Found'
        clientSocket.send(ret_response)
        print(f"File {filename} not found!")

# Function to handle an individual client connection
def handleClientConnection(clientSocket):
    global activeConnections

    # Safely update the global count of active connections
    with lock:
        activeConnections += 1

    client_address = clientSocket.getpeername()
    print(f"Client connected from {client_address}")
    # Receive the client's HTTP request
    request = clientSocket.recv(1024).decode()
    request_lines = request.split('\r\n')

    # Process the request if it's not empty
    if len(request_lines) > 0:
        # Extract the request method and requested file path
        request_method, request_path, _ = request_lines[0].split()
        # If the request is an HTTP GET, serve the requested file
        if request_method == 'GET':
            filename = request_path[1:]
            print(f"Requested file: {filename}")
            serverFile_process(clientSocket, filename)

    # Close the client socket once the request is processed
    clientSocket.close()
    print(f"Connection from {client_address} closed")

    # Updating the global count of active connections
    with lock:
        activeConnections -= 1

    # If the active connections are 0, Shutting down the server and printing the appropriate message
    if activeConnections == 0:
        print("\nNo active connections. Signaling the server to shut down...")
        server_shutdown_event.set()

# Main function to initialize and run the server
def main():
    # Ensuring the acceptance of 3 maximum clients
    if len(sys.argv) != 3:
        print("Please input this format: python server.py <port> <maximumConnections>")
        return

    host = '127.0.0.1' # local host
    serverPort = int(sys.argv[1]) # Port Number
    maximumConnections = int(sys.argv[2]) # Maximum Connections

    # Initializing the Server Socket
    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serverSocket.bind((host, serverPort)) # Creation of the socket
    serverSocket.listen(maximumConnections)

    print(f"Server listening on {host}:{serverPort}")

    try:
        # While loop to accept client connections and respond until the connection is called off
        while not server_shutdown_event.is_set(): # Beginning the while loop
            if threading.active_count() - 1 < maximumConnections: # check if the active connections are within the maximum connection limit
                connectionSocket, addr = serverSocket.accept() # if the 'if' condition is successful then the connection is accepted
                print(f"Accepted new connection from {addr[0]}:{addr[1]}")
                client_thread = threading.Thread(target=handleClientConnection, args=(connectionSocket,))
                client_thread.start()
            else:
                print("Maximum clients have been reached. Now Waiting for a connection to be closed.")
            time.sleep(0.1) # Sleep to prevent the loop from taking up too much CPU
    except KeyboardInterrupt: # Handling manual interruptions to shut down the server
        print("\nClosing the server...")
    finally:
        serverSocket.close() # Closing the server socket
        print("Server has been Closed.") # printing the closing process

# When the script is run, begin the main function.
if __name__ == "__main__":
    main()
