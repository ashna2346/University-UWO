# Author : Ashna Mittal
# Assignment 4

import math
import random
import pygame
import tkinter as tk
from tkinter import messagebox

class Cube:
    rows = 20
    w = 500

    def __init__(self, start, dirnx=1, dirny=0, color=(255, 0, 0)):
        self.pos = start
        self.dirnx = dirnx
        self.dirny = dirny
        self.color = color

    def move(self, dirnx, dirny):
        self.dirnx = dirnx
        self.dirny = dirny
        self.pos = (self.pos[0] + self.dirnx, self.pos[1] + self.dirny)

    def draw(self, surface, eyes=False):
        dis = self.w // self.rows
        i, j = self.pos
        pygame.draw.rect(surface, self.color, (i * dis + 1, j * dis + 1, dis - 2, dis - 2))
        if eyes:
            centre = dis // 2
            radius = 3
            circle_middle = (i * dis + centre - radius, j * dis + 8)
            circle_middle2 = (i * dis + dis - radius * 2, j * dis + 8)
            pygame.draw.circle(surface, (0, 0, 0), circle_middle, radius)
            pygame.draw.circle(surface, (0, 0, 0), circle_middle2, radius)

class Snake:
    body = []
    turns = {}

    def __init__(self, color, pos):
        self.color = color
        self.head = Cube(pos)
        self.body.append(self.head)
        self.dirnx = 0
        self.dirny = 1

    def move(self, key=None):
        if key in ['left', 'right', 'up', 'down']:
            moves = {'left': (-1, 0), 'right': (1, 0), 'up': (0, -1), 'down': (0, 1)}
            self.dirnx, self.dirny = moves[key]
            self.turns[self.head.pos[:]] = [self.dirnx, self.dirny]

        for i, c in enumerate(self.body):
            p = c.pos[:]
            if p in self.turns:
                turn = self.turns[p]
                c.move(turn[0], turn[1])
                if i == len(self.body) - 1:
                    self.turns.pop(p)
            else:
                c.move(c.dirnx, c.dirny)

    def reset(self, pos):
        self.head = Cube(pos)
        self.body = [self.head]
        self.turns = {}
        self.dirnx = 0
        self.dirny = 1

    def add_cube(self):
        tail = self.body[-1]
        dx, dy = tail.dirnx, tail.dirny
        new_cube_pos = (tail.pos[0] - dx, tail.pos[1] - dy)
        self.body.append(Cube(new_cube_pos))
        self.body[-1].dirnx = dx
        self.body[-1].dirny = dy

    def draw(self, surface):
        for i, c in enumerate(self.body):
            c.draw(surface, eyes=(i == 0))

    def get_positions(self):
        return [p.pos for p in self.body]

class SnakeGame:
    def __init__(self, rows):
        self.rows = rows
        self.players = {}
        self.snacks = [Cube(self.random_snack()) for _ in range(5)]

    def add_player(self, user_id, color):
        self.players[user_id] = Snake(color, (10, 10))

    def remove_player(self, user_id):
        self.players.pop(user_id)

    def move(self, moves):
        moves_ids = set(m[0] for m in moves)
        still_ids = set(self.players.keys()) - moves_ids

        for move in moves:
            self.move_player(move[0], move[1])

        for still_id in still_ids:
            self.move_player(still_id, None)

        for p_id in self.players.keys():
            if self.check_collision(p_id):
                self.reset_player(p_id)

    def move_player(self, user_id, key=None):
        self.players[user_id].move(key)

    def reset_player(self, user_id):
        x_start, y_start = random.randrange(1, self.rows - 1), random.randrange(1, self.rows - 1)
        self.players[user_id].reset((x_start, y_start))

    def get_player(self, user_id):
        return self.players[user_id].head.pos

    def check_collision(self, user_id):
        for snack in self.snacks:
            if self.players[user_id].head.pos == snack.pos:
                self.snacks.remove(snack)
                self.snacks.append(Cube(self.random_snack()))
                self.players[user_id].add_cube()

        head_pos = self.players[user_id].head.pos
        body_positions = [p.pos for p in self.players[user_id].body[1:]]
        if head_pos in body_positions or any(coord < 0 or coord >= self.rows for coord in head_pos):
            return True

        return False

    def get_state(self):
        players_pos = [p.get_positions() for p in self.players.values()]
        players_pos_str = "**".join("*".join(str(coord) for coord in pos) for pos in players_pos)
        snacks_pos = "**".join(str(s.pos) for s in self.snacks)
        return players_pos_str + "|" + snacks_pos

    def random_snack(self):
        x, y = random.randrange(1, self.rows - 1), random.randrange(1, self.rows - 1)
        return x, y




if __name__ == "__main__":
    pass
