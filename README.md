# SpiroSimulator

SpiroSimulator is an app that draws Spirographs while also simulating evolution.  Starting from a set of randomly generated designs, the user can choose the ones that look the coolest, which are then used to create similar spirographs using techniques from genetic programming.

## Initial population
![Initial population](https://i.imgur.com/wRbPjhc.png)
This is an initial population of spirographs, with the gear sizes and rotation speeds selected randomly.  The user can then choose certain spirographs (highlighted in blue) to breed to create the next generation.

## Second generaton
![Second generation](https://i.imgur.com/FM4YIm5.png)
The next generaton contains spirographs that were created by performing an chromosome crossover algorithm on the initial spirographs selected by the user.  The spirographs are converted to a list of values representing their gear configuration, a crossover point is selected, and then all values after that point are swapped between the two designs, creating a new design with characterisitics from both of the originals.

## Mutations
![Mutations](https://i.imgur.com/Cze6eXZ.png)
The user can set a mutation chance, which causes some values to randomly change after breeding.  This usually creates spirographs that look similar to the originals, but in some cases, a small change can create a vastly different design, allowing the user to explore new types of spirographs.  This image shows the population after many more generatoins, and a new type of vertical spirogarph has emerged.

## Other stuff
There is currently no UI for designing custom spriographs, but the engine allows spirographs of any shape and size, with many options such as gear size, gear speed, number of gears, and position of the drawing point within a gear.  Here is a fractal spirograph created using a specific geometric progression of gear sizes.

![Fractal spirograph](https://i.imgur.com/P9Xd1CD.png)

