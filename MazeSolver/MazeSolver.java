package mazeSolver;

import java.util.ArrayList;

public class MazeSolver {
    public static void main(String[] args){
        World world = new World("open.txt"); //Create a new maze
        world.draw(); // Draw the maze
        ArrayList<Cell> list = new ArrayList<Cell>(); // Create a list that holds the Cell
        Cell start = world.getStart(); // Return the cell to start
        start.mark(); // Mark down the start cell
        list.add(start); // Add the start cell to the list
        boolean foundEnd = false;
        while(list.size()!=0 && !foundEnd){ // If size is not 0 and had not found the end
            //Cell current = list.remove(list.size() - 1); //Depth first search - faster 
            Cell current = list.remove(0); // Breadth first search - slower but more accurate
            
            foundEnd = current.equals(world.getEnd());

            Cell[] neighbors = current.neighbors(); // Create a new array for neighbor cells
            for(Cell cell : neighbors){ //For each cell in the neighbors array
                if(!cell.isMarked()){ //If cell is not marked
                    cell.setPrevious(current); // Set the back path to current
                    list.add(cell); // Add cell to list
                    cell.mark(); // Mark the cell that just added
                }
            }
            //Window.mouse.waitForClick();
            //Window.mouse.waitForRelease();
            world.draw();
        }
        Cell current = world.getEnd();
        while(!current.equals(world.getStart())){ // When it doesn't reach the end
        	current = current.getPrevious(); 
        	current.markAsPath();
        	world.draw();
        }
        world.draw();
    }
}