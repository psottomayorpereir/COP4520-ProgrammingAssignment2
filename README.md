# COP4520 - Programming Assignment 2

### _This program was written by: Pedro Henrique Sotto-Mayor Pereira da Silva_

<br />

### Compiling and running the Birthday Party code:

---

1. Open a windows terminal.

2. Go to the directory of **BirthdayParty.java** file by using `cd` command.

3. Type **javac BirthdayParty.java** and hit enter to compile the code.

4. Type **java BirthdayParty** and hit enter to run the code.

5. The code will run and ask for the number of guests.

<br />

### Compiling and running the Crystal Vase code:

---

1. Open a windows terminal.

2. Go to the directory of **CrystalVase.java** file by using `cd` command.

3. Type **javac CrystalVase.java** and hit enter to compile the code.

4. Type **java CrystalVase** and hit enter to run the code.

5. The code will run and ask for the number of guests.

<br />

### Program design:

---

**Birthday Party**:

This program was designed to simulate the winning strategy for Minotaur's Birthday Party, where each guest is represented by one thread. The guests are only allowed to communicate with each other before the game has started to build a winning protocol.

<br />

**Crystal Vase**:

This program was designed to implement the Minotaur's Crystal Vase Strategy #2, where each guest is represented by one thread. The guests are allowed to place a sign on the door to indicate when the showroom is available. Every guest should set the sign to "BUSY" whenever entering the room and back to "AVAILABLE" when exiting the room.

<br />

### Correctness, Efficiency, and Evaluation:

---

**Birthday Party**:

My winning strategy was to make the first guest be responsible for counting how many guests entered the labyrinth. For this, this guest is the only one that can ask the Minotaur to serve a cupcake in order to know how many guests have already entered the labyrinth. Also, this guest is allowed to eat the cupcake only 1 time.

Note that the guests are randomly chosen to fo into the labyrinth. Whenever other guests other than the counting one enters the labyrinth and there is no cupcake served, they do nothing. If they entered the labyrinth and the cupcake is served, they only eat it if they did not eat any cupcakes before (guests can only eat 1 cupcake).

After the counting guest counter has reached the total number of guests, the counting guest know that every guest visited the labyrinth at least once.

<br />

**Crystal Vase**:

My implementation is Strategy #2 of Minotaur's Crystal Vase. It simply allows the guests to check the sign on the door and see if the showroom is "BUSY" or "AVAILABLE" by testing if the lock is available or not.

If available, the guest goes see the vase and he gets added to a chosenGuest list so that he does not go see the vase more than once. If the room is not available, the guest does not bother on going inside the room and keep waiting until it is available.

The code is also efficient since we are not allowing guests to see the vase more than once.

In comparison with the other strategies, here are some of the advantages and disadvantages:

**Strategy 1**:

- Advantages:

  - Easiest of the 3 strategies to implement

- Disadvantages:

  - We cannot guarantee that guests will go in

**Strategy 2**:

- Advantages:

  - Somewhat easy to implement
  - We can guarantee that all guests will see the vase at some point

- Disadvantages:

  - We cannot guarantee any ordering for the guests. The guest that checks the room is available the quickest goes first

**Strategy 3**:

- Advantages:

  - We can guarantee that all guests will see the vase at some point
  - It is a FIFO implementation, where the first to arrive goes first

- Disadvantages:

  - Hardest of the 3 strategies to implement
