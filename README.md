# invasionGame
A graphic representation of a part of the invasion game
There is a playfield made of land and sea(shallow and deep). 
Forces are in the deep sea and they should conquer (reach) the cities which are placed on the land, as fast as possible.
The algorithm should find the path that has the minimum possible number of moves (hops), that is the fastest path.

Settings

- Map(playfield) is created in the memory
- Playfield is made of 60x80 fields
- Playfield is made of three types of fields:
	land (green color)
	shallow sea (yellow color)
	deep sea (blue color)
-Playfield is divided into three parts of random shape from top to bottom, for every new game shape of the playfield is made randomly again.

- There are 1 to 4 cities placed randomly on the left edge on land. 

- Forces initially could be placed only in deep sea.
- There are 18 to 22 forces. For each city there is a group of forces.
- Only one force can occupy a single field.


Moving 
- Forces move 4 fields in the sea (using boats) and 7 fields on the land
- One move for the transition from sea to land
- There is no diagonal moves.


Objective
- Cities  placed on the land are the objectives that forces need to arrive.
- Depending on number of cities and forces it should be created one group of forces for each city, and find the shortest path (whit minimal hops) to the city.
- Forces are placed around the city in the deep sea as near as possible one above or below another (not one beside another).
- Determine where is the transition point to move from sea to land.
- Show path from city to force and numbers of moves (hops).

