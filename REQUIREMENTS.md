# Game of Life Requirements

## Implemented

### Cell

- A cell has two states, ALIVE and DEAD

### Board

- A board has a width and a height
- Any cell can be retrieved from the board by specifying an x and y coordinate
    - The top left cell of the board is x=0,y=0. x increases horizontally, y increases vertically
- Attempting to access a cell outside of the board throws an exception
- A board has an iteration count
- The iteration count starts at 0
- The iteration count can be incremented
- When the iteration count is incremented, rules are applied to each cell at once
- A board's iteration count can be reset to 0
- Board state can be saved using RLE format: http://conwaylife.com/wiki/Run_Length_Encoded
- Board state can be saved using plaintext format: http://conwaylife.com/wiki/Plaintext
- Board state can be initialized to a random pattern
- Board state can be loaded from an RLE string: http://conwaylife.com/wiki/Run_Length_Encoded
- Board rules can be saved as a rulestring: http://conwaylife.com/wiki/Rulestring#Rules
- Board rules can be loaded from a rulestring: http://conwaylife.com/wiki/Rulestring#Rules

## TODO

### Board

- A board must be able to, for any cell, determine the number of living neighbors
    - A neighbor is a cell with an adjoining edge or corner
- A board has a set of rules:
  - For a cell that is ALIVE:
      - Each cell with one or no living neighbors dies, as if by solitude.
      - Each cell with four or more living neighbors dies, as if by overpopulation.
      - Any cell that touches one or more board edges dies
  - For a cell that is DEAD:
      - Each cell with exactly three living neighbors becomes populated.
