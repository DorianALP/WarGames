README - WarGames Project
1. Notable Design Choices

1.1 Polymorphism
The GameAction abstract class implements polymorphism by allowing multiple action types (BuildNukeAction, LaunchNukeAction, etc.) to share the execute() method.
Each specific action overrides the execute() method.

1.2 Computer AI Logic
The AI logic in the Computer class selects actions based on the nation’s resources, health, and game state.
It prioritizes building nukes, strengthening shields, and attacking based on conditions, simulating strategic decision-making.

1.3 Separation of Concerns
The project uses the Model-View-Controller (MVC) pattern to separate:

Model: Nation, GameAction classes.
View: View class for UI handling.
Controller: Controller class for handling user input and game state transitions.

1.4 Game Simulation Loop
The WarSimulation class implements a game loop using JavaFX's Timeline to alternate turns between the player and computer.

2. Known Bugs/Limitations

Computer Actions:
The AI may sometimes over-prioritize building nukes instead of balancing resources or defenses.
No Save Feature:
The game does not have a save or resume option. Once exited, progress is lost.
No computer pause:
The computer should display "Thinking..." before making a move to simulate human-like decision-making.

3. Group Member Contributions

Danny Philbrook
Game UI, Controller Logic, User Interaction

Dorian Littlecook-Perez
Computer AI Logic, Action Classes

Cooper Nusbaum
Nation Class, Game Loop, Testing, Debugging


4. Referencing

Stack Overflow: Helped resolve a JavaFX button enabling/disabling issue.

Oracle Java Docs: Used for reference on JavaFX and Timeline.

ChatGPT: Helped cover different edge cases / possible outcomes