/**
 * 
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import util.GraphLoader;

/**
 * @author Your name here.
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {

	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	private int numVertices;
	private int numEdges;
	private HashMap<Integer, HashSet<Integer>> adjListsMap;
	
	/** 
	 * Create a new empty Graph
	 */
	public CapGraph()
	{
		adjListsMap = new HashMap<Integer, HashSet<Integer>>();
		//numVertices = 0;
		//numEdges = 0;
	
	}
	
	
	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		implementAddVertex(num);
		numVertices ++;
		//return (numVertices-1);
		//System.out.println("add vert");
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		numEdges ++;
		//System.out.println("no of vertices:"+numVertices);
		//System.out.println("from:"+ from+ "to:"+ to);
		//if (from < numVertices ) {
			implementAddEdge(from , to);			
		//}
		//else {
		//	throw new IndexOutOfBoundsException();
		//}
	}
	
	/** 
	 * Implement the method for adding a vertex. 
	 */
	public void implementAddVertex(int num) {
		//int v = getNumVertices();
		HashSet<Integer> neighbors = new HashSet<Integer>();
		adjListsMap.put(num,  neighbors);
	//System.out.println("HER"+ adjListsMap);
	}
	
	/** 
	 * Implement the abstract method for adding an edge.
	 * @param v the index of the start point for the edge.
	 * @param w the index of the end point for the edge.  
	 */
	public void implementAddEdge(int v, int w) {
		(adjListsMap.get(v)).add(w);
		//System.out.println("HER"+ adjListsMap);
	}
	/**
	 * Report size of vertex set
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		return numVertices;
	}
	
	
	/**
	 * Report size of edge set
	 * @return The number of edges in the graph.
	 */	
	public int getNumEdges() {
		return numEdges;
	}
	
	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		Graph graph= new CapGraph();
		//System.out.println("CENTER:"+ center);
		if( center>=0)
		{
			graph.addVertex(center);
			List<Integer> edgeList= getNeighbors(center);
			//System.out.println("here:"+edgeList);
			for(int edge: edgeList)
			{
				graph.addVertex(edge);
				graph.addEdge(center,edge);
				List<Integer> friendList= getNeighbors(edge);
				for(int frnd: friendList)
				{
					
					if(edgeList.contains(frnd))
					{
						
						graph.addEdge(edge,frnd);
					}
				}
			}
			return graph;
		}
	
		return null;
	}
	public List<Integer> getNeighbors(int v) {
		//System.out.println("NEigh:"+ v);
		return new ArrayList<Integer>(adjListsMap.get(v));
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		CapGraph graph = new CapGraph();
		
		//create graph from map
		for(int vertex:adjListsMap.keySet() )
		{
			graph.addVertex(vertex);
			for(int edge: adjListsMap.get(vertex))
			{
				graph.addEdge(vertex, edge);
			}
		}
		System.out.println("check graph:"+ graph.exportGraph());
		
		Stack<Integer> vertices=new Stack<Integer>();
		  TreeMap<Integer, HashSet<Integer>> sorted = new TreeMap<>(); 
		  
	       // Copy all data from hashMap into TreeMap 
	        sorted.putAll(adjListsMap); 
		vertices.addAll(sorted.keySet());
		System.out.println("ver"+ vertices);
		
		//step :1 DFS(G);
		//List<Graph> graphs= new ArrayList<Graph>();
		Stack <Integer> finished = DFS(graph,vertices);
		System.out.println("fin--1:"+ finished);
		
		
		//step :2 Compute transpose of G
		//HashMap <Integer,HashSet<Integer>> transposeMap= new HashMap<Integer,HashSet<Integer>>();
		CapGraph transposeGraph = new CapGraph();
		Set<Integer> keys= adjListsMap.keySet();
		for( int i1: keys)
		{
			transposeGraph.addVertex(i1);
		}
		for( int i: keys)
		{
			//System.out.println("vert check :"+ i);
			//transposeGraph.addVertex(i);
			//transposeGraph.addVertex(i);
			HashSet<Integer> set=adjListsMap.get(i);
			//System.out.println(set+"SET");
			for(int j: set)
			{
			//	transposeGraph.addVertex(j);
			
				transposeGraph.addEdge(j,i);
				//System.out.println("Adding edge:"+ j +"::"+i);
			
			}
			
		}
		
		
		
		
		System.out.println("oldmap"+ graph.adjListsMap);
		System.out.println("new map"+ transposeGraph.adjListsMap);
		//step :3 DFS(Gt)
		List<Graph> graphs= new ArrayList<Graph>();
		//graphs= DFS1(transposeGraph,finished,graphs);
		//System.out.println("TT"+graphs);
		for( Graph g: graphs)
		{
			 HashMap<Integer, HashSet<Integer>> curr=g.exportGraph();
			//System.out.println("try:"+ curr);
		}
		return graphs;
	
	}
	public List<Graph> DFS1(CapGraph graph,Stack<Integer> vertices,List<Graph> graphs)
	{
		Set <Integer> visited= new HashSet<Integer>();
		Stack <Integer> finished = new Stack<Integer>();
		
		while(vertices.size()>0)
		{
			int v= vertices.pop();
			//System.out.println("tee 1:"+ v);
			if(!visited.contains(v))
			{
				Graph subgraph = new CapGraph();
				finished=DFS_VISIT1(graph,v,visited,finished,subgraph);
				//System.out.println("tee 2:"+ finished);
				graphs.add(subgraph);
			}
			
		}
		return graphs;
		
	}
	public Stack<Integer> DFS(CapGraph graph,Stack<Integer> vertices)
	{
		Set <Integer> visited= new HashSet<Integer>();
		Stack <Integer> finished = new Stack<Integer>();
		
		while(vertices.size()>0)
		{
			int v= vertices.pop();
			if(!visited.contains(v))
			{
				//Graph subgraph = new CapGraph();
				finished=DFS_VISIT(graph,v,visited,finished);
				//System.out.println("FIMISHE:"+finished);
				//graphs.add(subgraph);
			}
			
		}
		return finished;
		
	}
	public Stack<Integer> DFS_VISIT(Graph graph,int v,Set <Integer>visited,Stack<Integer>finished)
	{
		//Stack<Integer>finished= finished1;
		visited.add(v);
		
		for(int n: getNeighbors(v))
		{
			if(!visited.contains(n))
			{
				DFS_VISIT(graph,n,visited,finished);
				System.out.println("TEST GG"+ n);
			}
		}
		
		finished.push(v);
		System.out.println("DONE"+ finished);
		return finished;
	}
	public Stack<Integer> DFS_VISIT1(Graph graph,int v,Set <Integer>visited,Stack<Integer>finished,Graph subgrah)
	{
		//Stack<Integer>finished= finished1;
		visited.add(v);
		finished.push(v);
		for(int n: getNeighbors(v))
		{
			if(!visited.contains(n))
			{
				DFS_VISIT1(graph,n,visited,finished,new CapGraph());
				//System.out.println("TEST GG"+ n);
			}
		}
		
		//finished.push(v);
		System.out.println("DONE"+ finished);
		return finished;
	}
	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public  HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		return adjListsMap;
	}

}
