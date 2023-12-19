# Author : Ashna Mittal
# Assignment 4

import numpy as np
import pygame
import socket
import pickle
import rsa

width = 500
height = 500
rows = 20

rgb_colors = {
    "red": (255, 0, 0),
    "blue": (0, 0, 255),
    "yellow": (255, 255, 0),
    "purple": (255, 0, 255),
    "white": (255, 255, 255),
    "cyan": (0, 100, 100),
}
rgb_colors_list = list(rgb_colors.values())

def draw_grid(w, surface):
    global rows
    sizeBtwn = w // rows

    for l in range(rows):
        x = (l + 1) * sizeBtwn
        y = (l + 1) * sizeBtwn

        pygame.draw.line(surface, (255, 255, 255), (x, 0), (x, w))
        pygame.draw.line(surface, (255, 255, 255), (0, y), (w, y))

def draw_things(surface, positions, color=None, eye=False):
    global width, rgb_colors_list
    dis = width // rows
    if color is None:
        color = (np.random.randint(0, 255), np.random.randint(0, 255), np.random.randint(0, 255))
    for pos_id, pos in enumerate(positions):
        i, j = pos

        pygame.draw.rect(surface, color, (i * dis + 1, j * dis + 1, dis - 2, dis - 2))
        if eye and pos_id == 0:
            centre = dis // 2
            radius = 3
            circle_middle = (i * dis + centre - radius, j * dis + 8)
            circle_middle2 = (i * dis + dis - radius * 2, j * dis + 8)
            pygame.draw.circle(surface, (0, 0, 0), circle_middle, radius)
            pygame.draw.circle(surface, (0, 0, 0), circle_middle2, radius)


def draw(surface, players, snacks):
    global rgb_colors_list

    # Create a separate surface for drawing
    draw_surface = pygame.Surface((width, height))

    # Fill the drawing surface with a black background
    draw_surface.fill((0, 0, 0))

    # Draw the grid on the drawing surface
    draw_grid(width, draw_surface)

    # Draw the players on the drawing surface
    for i, player in enumerate(players):
        color = rgb_colors_list[i % len(rgb_colors_list)]
        draw_things(draw_surface, player, color=color, eye=True)

    # Draw the snacks on the drawing surface
    draw_things(draw_surface, snacks, (0, 255, 0))

    # Blit the drawing surface onto the main display surface
    surface.blit(draw_surface, (0, 0))

    # Update the display
    pygame.display.update()

class NetworkClient:
    def __init__(self):
        self.client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.server = "localhost"
        self.port = 5555
        self.addr = (self.server, self.port)
        self.connect()

    def connect(self):
        try:
            self.client.connect(self.addr)
        except:
            print("Unable to connect to server")

    def send(self, data):
        try:
            self.client.send(str.encode(data))
            return pickle.loads(self.client.recv(2048))
        except socket.error as e:
            print(e)

    def get_broadcast_message(self):
        try:
            self.client.send(str.encode("get_broadcast_message"))
            return pickle.loads(self.client.recv(2048))
        except socket.error as e:
            print(e)


def main():
    pygame.init()
    win = pygame.display.set_mode((width, height))
    clock = pygame.time.Clock()
    n = NetworkClient()
    flag = True

    color = n.send("get_color")
    while color is None:
        color = n.send("get_color")

    pos = None
    snacks, players = [], []

    while flag:
        events = pygame.event.get()
        snack_eaten = False

        if len(events) > 0:
            for event in events:
                if event.type == pygame.QUIT:
                    flag = False
                    n.send("quit")
                    pygame.quit()
                # Game controls sent by the user
                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_LEFT:
                        n.send("move:left")
                    elif event.key == pygame.K_RIGHT:
                        n.send("move:right")
                    elif event.key == pygame.K_UP:
                        n.send("move:up")
                    elif event.key == pygame.K_DOWN:
                        n.send("move:down")
                    elif event.key == pygame.K_SPACE:
                        n.send("reset")
                    elif event.key == pygame.K_z:
                        n.send("z")
                    elif event.key == pygame.K_x:
                        n.send("x")
                    elif event.key == pygame.K_c:
                        n.send("c")
        else:
            new_pos = n.send("get_state")

            if new_pos != pos:
                pos = new_pos
                snack_eaten = True

            if pos is not None:
                raw_players = pos.split("|")[0].split("**")
                raw_snacks = pos.split("|")[1].split("**")

                if raw_players:
                    players = [
                        [tuple(map(int, coord.strip('()').split(','))) for coord in raw_player.split('*') if coord]
                        for raw_player in raw_players
                    ]

                snacks = [tuple(map(int, coord.strip('()').split(','))) for coord in raw_snacks if coord]

        if snack_eaten or players or snacks:
            draw(win, players, snacks)

        response = n.get_broadcast_message()
        if response and "broadcast" in response:
            broadcast_message = response["broadcast"]
            print(broadcast_message)

        clock.tick(10)  # Control the frame rate

    pygame.quit()

if __name__ == "__main__":
    main()
