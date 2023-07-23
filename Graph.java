import java.util.Queue;
import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.Deque;

public class Graph {
    
    int noOfVertices;

    Node[] head;
    static Scanner input = new Scanner(System.in);

    //***********************************************************//
    //------------------------CONSTRUCTOR------------------------//
    //***********************************************************//

    public Graph() {

        System.out.println("Constructing the graph...");

        System.out.print("How many vertices? : ");
        noOfVertices = input.nextInt();

        head = new Node[noOfVertices];
        for (int i=0; i<noOfVertices; i++) {

            head[i] = new Node();
            head[i].val = i;

        }
    }

    //***********************************************************//
    //-----------------METHODS FOR ADDING EDGES------------------//
    //***********************************************************//

    void addAdjVertex(Node start, int dest, int weight) {

        Node newVertex = new Node(dest, weight);
        int startVal = start.val;

        while (start.next != null) {
            start = start.next;
            if (start.val == dest) {

                System.out.println("Edge already exists!");
                return;
            }
        }

        start.next = newVertex;
        System.out.println("Edge " + startVal + " - " + dest + " added successfully!");
    }

    void addEdge(int dg) {

        int ver1, ver2, weight;

        System.out.print("Vertex 1 for the edge: ");
        ver1 = input.nextInt();
        
        if (ver1 < 0 || ver1 >= noOfVertices) {

            System.out.println("ver1 is out of range!");
            return;
        }

        System.out.print("Vertex 2 for the edge: ");
        ver2 = input.nextInt();

        if (ver2 < 0 || ver2 >= noOfVertices) {

            System.out.println("ver2 is out of range!");
            return;
        }

        System.out.print("Weight for the edge: ");
        weight = input.nextInt();

        if (weight < 0) {

            System.out.println("Weights of edges are not supposed to be negative!");
            return;
        }

        addAdjVertex(head[ver1], ver2, weight);
        if (dg == 1 && ver1 != ver2)
            addAdjVertex(head[ver2], ver1, weight);
    }

    //***********************************************************//
    //-------------METHODS FOR DISPLAYING ADJ MATRIX-------------//
    //***********************************************************//

    boolean checkAdjVertex(Node start, int dest) {

        while (start.next != null) {

            start = start.next;
            if (start.val == dest) {
                return true;
            }
        }

        return false;
    }

    void dispAdjMatrix() {

        System.out.print("  ");
        for (int i=0; i<noOfVertices; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int i=0; i<noOfVertices; i++) {

            System.out.print(i + " ");
            for (int j=0; j<noOfVertices; j++) {

                if (checkAdjVertex(head[i], j)) {
                    System.out.print(" " + "1");
                }
                else {
                    System.out.print(" " + "0");
                }
            }

            System.out.print("\n");
        }
    }

    //***********************************************************//
    //-------------------TRAVERSAL ALGORITHMS--------------------//
    //***********************************************************//

    // BREADTH-FIRST SEARCH
    void bfs() {

        int sourceVertex;
        System.out.print("Enter source vertex: ");
        sourceVertex = input.nextInt();

        if (sourceVertex < 0 || sourceVertex >= noOfVertices) {

            System.out.println("Source vertex out of range!");
            return;
        }

        Node[] tfTable = new Node[noOfVertices];
        for (int i=0; i<noOfVertices; i++) {

            tfTable[i] = new Node();
            tfTable[i].val = i;
            tfTable[i].next = new Node();
            tfTable[i].next.val = 'F';
        }

        Queue<Integer> queue = new ArrayDeque<Integer>();

        int index = 0;
        int[] bfsTraversalArray = new int[noOfVertices];

        queue.offer(sourceVertex);
        tfTable[sourceVertex].next.val = 'T';

        while (!queue.isEmpty()) {

            sourceVertex = queue.poll();
            bfsTraversalArray[index] = sourceVertex;
            index++;

            Node adjVertex = head[sourceVertex];
            while (adjVertex.next != null) {

                if (tfTable[adjVertex.next.val].next.val == 'F') {

                    queue.add(adjVertex.next.val);
                    tfTable[adjVertex.next.val].next.val = 'T';
                }

                adjVertex = adjVertex.next;
            }
        }

        System.out.println("BFS Traversal: ");
        for (int val : bfsTraversalArray) {

            System.out.print(val + " ");
        }
        System.out.println();
    }

    // DEPTH-FIRST SEARCH
    void dfs() {

        int sourceVertex;
        System.out.print("Enter source vertex: ");
        sourceVertex = input.nextInt();

        if (sourceVertex < 0 || sourceVertex >= noOfVertices) {

            System.out.println("Source vertex out of range!");
            return;
        }

        Node[] tfTable = new Node[noOfVertices];
        for (int i=0; i<noOfVertices; i++) {

            tfTable[i] = new Node();
            tfTable[i].val = i;
            tfTable[i].next = new Node();
            tfTable[i].next.val = 'F';
        }

        Deque<Integer> stack = new ArrayDeque<Integer>();

        int index = 0;
        boolean shift = false;
        int[] dfsTraversalArray = new int[noOfVertices];
        Node adjVertex;

        stack.push(sourceVertex);
        tfTable[sourceVertex].next.val = 'T';
        adjVertex = head[sourceVertex];
        dfsTraversalArray[index] = sourceVertex;
        index++;

        while (!stack.isEmpty()) {

            if (shift)
                adjVertex = head[sourceVertex];
            
            if (adjVertex.next != null && tfTable[adjVertex.next.val].next.val == 'F') {

                tfTable[adjVertex.next.val].next.val = 'T';
                dfsTraversalArray[index] = adjVertex.next.val;
                index++;
                stack.push(adjVertex.next.val);
                sourceVertex = adjVertex.next.val;
                shift = true;
            }
            else {
                adjVertex = adjVertex.next;
                shift = false;
            }

            if (adjVertex == null) {
                stack.pop();
                if (!stack.isEmpty()) {
                    sourceVertex = stack.peek();
                    shift = true;
                } 
            }
        }

        System.out.println("DFS Traversal: ");
        for (int val : dfsTraversalArray) {

            System.out.print(val + " ");
        }
        System.out.println();
    }

    //***********************************************************//
    //------------DIJKSTRA'S SHORTEST PATH ALGORITHM-------------//
    //***********************************************************//

    void dijkstra() {

        int sourceVertex;
        System.out.print("Enter source vertex: ");
        sourceVertex = input.nextInt();

        if (sourceVertex < 0 || sourceVertex >= noOfVertices) {

            System.out.println("Source vertex out of range!");
            return;
        }

        Node[] tfTable = new Node[noOfVertices];
        Node[] dvTable = new Node[noOfVertices];
        Node[] pvTable = new Node[noOfVertices];
        for (int i=0; i<noOfVertices; i++) {

            tfTable[i] = new Node();
            tfTable[i].val = i;
            tfTable[i].next = new Node();
            tfTable[i].next.val = 'F';

            dvTable[i] = new Node();
            dvTable[i].val = i;
            dvTable[i].next = new Node();
            dvTable[i].next.val = Integer.MAX_VALUE;

            pvTable[i] = new Node();
            pvTable[i].val = i;
            pvTable[i].next = new Node();
            pvTable[i].next.val = -1;
        }

        tfTable[sourceVertex].next.val = 'T';
        dvTable[sourceVertex].next.val = 0;

        int counter = 0, minDist = Integer.MAX_VALUE;
        int vertex = sourceVertex, addDist = 0;

        while (counter < noOfVertices) {

            // SELECTING VERTEX WITH SMALLEST UNKNOWN (F) DISTANCE
            for (int i=0; i<noOfVertices; i++) {

                if (tfTable[i].next.val == 'F' && minDist > dvTable[i].next.val) {

                    minDist = dvTable[i].next.val;
                    vertex = i;
                }
            }

            // SHORTEST PATH CALCULATIONS
            tfTable[vertex].next.val = 'T';
            counter++;

            Node node = head[vertex];
            while (node.next != null) {

                if (pvTable[head[vertex].val].next.val == -1)
                    addDist = 0;
                else
                    addDist = dvTable[head[vertex].val].next.val;

                if (tfTable[node.next.val].next.val == 'F' && node.next.weight + addDist < dvTable[node.next.val].next.val) {

                    dvTable[node.next.val].next.val = node.next.weight + addDist;
                    pvTable[node.next.val].next.val = vertex;
                }

                node = node.next;
            }

            minDist = Integer.MAX_VALUE;
        }

        for (int i=0; i<noOfVertices; i++)
            System.out.println("Shortest path from '" + sourceVertex
                + "' to '" + i + "' is " + dvTable[i].next.val + " unit(s).");
    }

    //***********************************************************//
    //-----------TOPOLOGICAL SORTING (KAHN'S ALGORITHM)----------//
    //***********************************************************//

    void kahn() {

        Node[] indegree = new Node[noOfVertices];
        for (int i=0; i<noOfVertices; i++) {

            indegree[i] = new Node();
            indegree[i].val = i;
            indegree[i].next = new Node();
            indegree[i].next.val = 0;
        }

        // CALCULATING INDEGREES OF ALL THE NODES
        for (int i=0; i<noOfVertices; i++) {

            Node node = head[i];
            while (node.next != null) {
                node = node.next;
                indegree[node.val].next.val += 1;
            }
        }

        Queue<Integer> queue = new ArrayDeque<Integer>();
        for (int i=0; i<noOfVertices; i++) {

            if (indegree[i].next.val == 0)
                queue.offer(i);
        }

        if (queue.isEmpty()) {

            System.out.println("The graph contains cycles. Topological sort is not possible.");
            return;
        }

        // PERFORMING TOPOLOGICAL ORDERING
        int index = 0;
        int[] topologicalSortArray = new int[noOfVertices];
        while (!queue.isEmpty()) {

            int vertex = queue.poll();
            topologicalSortArray[index] = vertex;
            index++;

            Node node = head[vertex];
            while (node.next != null) {

                node = node.next;
                indegree[node.val].next.val -= 1;

                if (indegree[node.val].next.val == 0)
                    queue.offer(node.val);
            }
        }

        System.out.println("Topological Ordering:");
        for (int i=0; i<noOfVertices; i++) {

            System.out.print(topologicalSortArray[i] + " ");
        }
        System.out.println();
    }
}