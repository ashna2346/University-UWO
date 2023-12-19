# Author: Ashna Mittal
# Assignment 3: Server-driven snake game

import socket
import pygame
import sys
import pickle
import time

# Pygame Initialization
pygame.init()
width, height = 500, 500
win = pygame.display.set_mode((width, height))
pygame.display.set_caption("Snake Client")

# Network Initialization
server = "localhost"
port = 5555
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Try to connect to the server
try:
    client_socket.connect((server, port))
except socket.error as e:
    print(f"Error connecting to server: {e}") # Display error while connecting to the server
    sys.exit()

# Function to handle sending and receiving data from the server
def networkCommunication(data):
    try:
        client_socket.sendall(data.encode())
        received_data = client_socket.recv(4096).decode()
        return received_data if received_data else None
    except Exception as e:
        print(f"Error during network communication: {e}")
        handleDisconnection()


# Function to draw the grid lines
def drawGrid(surface, grid_size, window_width, window_height):
    for x in range(0, window_width, grid_size):  # Draw vertical grid lines
        pygame.draw.line(surface, (128, 128, 128), (x, 0), (x, window_height))
    for y in range(0, window_height, grid_size):  # Draw horizontal grid lines
        pygame.draw.line(surface, (128, 128, 128), (0, y), (window_width, y))


# Function to draw the game state
def drawGameState(game_state):
    if game_state is None:
        handleDisconnection()

    win.fill((0, 0, 0))
    # Draw the grid
    drawGrid(win, 25, width, height)  # Assuming each grid cell is 20x20 pixels (500/25 =20)

    # Parse game state into player and snack positions
    try:
        player_data, snack_data = game_state.split("|")
        player_positions = [eval(pos) for pos in player_data.split("*") if pos]
        snack_positions = [eval(pos) for pos in snack_data.split("**") if pos]

        # Draw snake (Player)
        for pos in player_positions:
            if isinstance(pos, tuple) and len(pos) == 2:
                pygame.draw.rect(win, (255, 0, 0), (pos[0]*25, pos[1]*25, 25, 25))

        # Draw snacks
        for pos in snack_positions:
            if isinstance(pos, tuple) and len(pos) == 2:
                pygame.draw.rect(win, (0, 255, 0), (pos[0]*25, pos[1]*25, 25, 25))

        pygame.display.update()

    except SyntaxError as e:
        print(f"Error parsing game state: {e}")
    except TypeError as e:
        print(f"Error drawing game state: {e}")

# Function to handle disconnections
def handleDisconnection():
    print("Disconnected from server")
    pygame.quit()
    sys.exit()

# Main loop
running = True
clock = pygame.time.Clock()

while running:
    clock.tick(10)
    try:
        # Handle Pygame events
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
                networkCommunication("quit")
                break

            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_UP:
                    networkCommunication("up")
                elif event.key == pygame.K_DOWN:
                    networkCommunication("down")
                elif event.key == pygame.K_LEFT:
                    networkCommunication("left")
                elif event.key == pygame.K_RIGHT:
                    networkCommunication("right")
                elif event.key == pygame.K_r:  # Type 'r' for reset
                    networkCommunication("reset")
                elif event.key == pygame.K_q:  # Type 'q' for quit
                    running = False
                    networkCommunication("quit")
                    break

        # Request game state
        game_state = networkCommunication("get")
        drawGameState(game_state)

    except Exception as e:
        print(f"An error occurred: {e}")
        handleDisconnection()

networkCommunication("quit")
pygame.quit()
