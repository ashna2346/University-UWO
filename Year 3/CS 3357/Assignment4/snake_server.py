# Author : Ashna Mittal
# Assignment 4

import socket
from _thread import *
import pickle
from snake import SnakeGame
import uuid
import time
import numpy as np
import rsa

server = "localhost"
port = 5555
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

counter = 0
rows = 20

try:
    s.bind((server, port))
except socket.error as e:
    str(e)

s.listen()
print("Waiting for a connection, Server Started")

game = SnakeGame(rows)
game_state = ""
last_move_timestamp = time.time()
interval = 0.2
moves_queue = set()

def game_thread():
    global game, moves_queue, game_state
    while True:
        last_move_timestamp = time.time()
        game.move(moves_queue)
        moves_queue = set()
        game_state = game.get_state()
        while time.time() - last_move_timestamp < interval:
            time.sleep(0.1)

rgb_colors = {
    "red": (255, 0, 0),
    "blue": (0, 0, 255),
    "yellow": (255, 255, 0),
    "purple": (255, 0, 255),
    "white": (255, 255, 255),
    "cyan": (0, 100, 100),
}
rgb_colors_list = list(rgb_colors.values())


# Function to broadcast messages to all clients
def broadcast_message(message):
    global clients
    for conn in clients:
        try:
            conn.sendall(pickle.dumps({"broadcast": message}))
        except Exception as e:
            print("Error broadcasting message:", e)

clients = [] # List of clients



# function to communicate with the client
def client_thread(conn, user_id, color):
    global game, moves_queue, game_state
    conn.sendall(pickle.dumps(color))
    game.add_player(user_id, color)
    clients.append(conn)
    while True:
        try:
            data = conn.recv(2048).decode()

            if not data:
                print("Disconnected")
                game.remove_player(user_id)
                conn.close()
                break

            if data.startswith("move"):
                move = data.split(":")[1]
                moves_queue.add((user_id, move))
            elif data == "reset":
                game.reset_player(user_id)
            elif data == "quit":
                game.remove_player(user_id)
                conn.close()
                break
            elif data in ["z", "x", "c"]: # Custom messages apart from the previous 7 messages
                message_dict = {"z": "Congratulations!", "x": "It works!", "c": "Ready?"}
                broadcast_message(message_dict[data]) # call function broadcast_message
                print(f"Command received from Client : {message_dict[data]}")

            else:
                pass

            conn.sendall(pickle.dumps(game_state))

        except Exception as e:
            print(e)
            break

def main():
    start_new_thread(game_thread, ())

    while True:
        conn, addr = s.accept()
        print("Connected to:", addr)

        unique_id = str(uuid.uuid4())
        color = rgb_colors_list[np.random.randint(0, len(rgb_colors_list))]

        start_new_thread(client_thread, (conn, unique_id, color))

if __name__ == "__main__":
    main()
