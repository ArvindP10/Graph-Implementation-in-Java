import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {

        int graphChoice, choice;
        char ch;

        Scanner input = new Scanner(System.in);
        Graph graph = new Graph();

        System.out.println("GRAPHS");
        System.out.println("------\n");
        System.out.println("1. Undirected graph");
        System.out.println("2. Directed graph");
        System.out.print("Select choice: ");
        graphChoice = input.nextInt();

        if (graphChoice != 1 && graphChoice != 2) {
            
            System.out.println("Invalid graph choice! Exiting...");
            input.close();
            return;
        }

        System.out.println("OPERATIONS");
        System.out.println("----------");
        System.out.println("1. Add New Edge");
        System.out.println("2. Display Adjacency Matrix");
        System.out.println("3. Perform BFS Traversal");
        System.out.println("4. Perform DFS Traversal");
        System.out.println("5. Perform Dijkstra's Shortest Path Algorithm");
        System.out.println("6. Perform Kahn's Topological Sorting Algorithm");

        do {
            System.out.print("Select choice: ");
            choice = input.nextInt();
            System.out.print("\n\n");

            switch (choice) {

                case 1: graph.addEdge(graphChoice);
                        break;
                
                case 2: graph.dispAdjMatrix();
                        break;

                case 3: graph.bfs();
                        break;

                case 4: graph.dfs();
                        break;

                case 5: graph.dijkstra();
                        break;

                case 6: graph.kahn();
                        break;

                default: System.out.println("Invalid option number!");
            }

            System.out.print("Another operation? (Y/N): ");
            ch = input.next().charAt(0);
            
        } while (ch == 'Y' || ch == 'y');

        input.close();
    }
}
