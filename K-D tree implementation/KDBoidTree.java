package assignment10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.DuplicateFormatFlagsException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.TreeSet;

import assignment10.Crowd.Boid;



public class KDBoidTree extends TreeSet<Boid> {

	/**
	 * A Class for the boid tree
	 * @author Aaron Kc Hsu
	 *
	 */
	class BoidNode {
		BoidNode left;
		BoidNode right; // Left and Right Nodes for the KDTree
		Boid data; // Stores a boid
		boolean horizontal;

		/**
		 * A Node made specifically for a KD Tree
		 * @param data
		 * @param left
		 * @param right
		 */
		public BoidNode(BoidNode left, BoidNode right, Boid data, boolean horiztonal) {
			this.left = left;
			this.right = right;
			this.data = data;
			this.horizontal = horizontal;
		}

	}

	BoidNode root; // Keeps Track of the root

	/**
	 * Constructor for the KD Boid tree
	 */
	public KDBoidTree(ArrayList<Boid> boidList){
		Boid[] xSortList = new Boid[boidList.size()];
		Boid[] ySortList = new Boid[boidList.size()];
		// A loop to fill up two arrays
		for(int i = 0; i < boidList.size(); i++){
			//xSortList.set(i, boidList.get(i)); 
			xSortList[i] = boidList.get(i);
			//ySortList.set(i, boidList.get(i)); // Fill up both arrays
			ySortList[i] = boidList.get(i);
		}
		Arrays.sort(xSortList, new xSort()); // Sorts the x list
		Arrays.sort(ySortList, new ySort()); // Sorts the y list

		// Calls the first Recursion
		root = buildTreeX(xSortList, ySortList, 0, xSortList.length);
	}

	/**
	 * Builds a level of the tree based on x
	 * @param node The node to be added upon
	 * @param xList list sorted by x
	 * @param yList list sorted by y
	 * @param start the starting index of the array
	 * @param end the "length" of the array - One plus the last Index
	 * @return
	 */
	private BoidNode buildTreeX(Boid[] xList, Boid[] yList, int start, int end){
		if (start >= end) { // Ends Recursion
			return null;
		}
		Boid[] temp = new Boid[(end - start + 1)]; // Temp list to store stuff 
		int tempIndex = 0; // Keeps Track of temp Index
		int medianIndex = (start + end) / 2; // Median Index
		Boid median = xList[medianIndex]; // Gets the median from the x list
		BoidNode node = new BoidNode(null, null, median, true);
		// Loops through twice copying into the temp array
		for(int i = start; i < end; i++){
			if(median.posx > yList[i].posx && yList[i].posx != median.posx){ // Fills left side of array
				temp[tempIndex] = yList[i];
				tempIndex++;
			}
		}
		temp[tempIndex] = median; // copies the median into the middle of the array
		tempIndex++; 
		for(int i = start; i < end; i++){ // Copies the right half of tmp
			if(median.posx < yList[i].posx && yList[i].posx != median.posx){ // Fills up right side of the array
				temp[tempIndex] = yList[i]; 
				tempIndex++;
			}
		}
		for(int i = start; i < end; i++){
			yList[i] = temp[i - start]; 
		}
		node.left = buildTreeY(xList, yList, start, medianIndex);
		node.right = buildTreeY(xList, yList, medianIndex + 1, end);
		return node;
	}

	/**
	 * @param node The node to be added upon
	 * @param xList list sorted by x
	 * @param yList list sorted by y
	 * @param start the starting index of the array
	 * @param end the length of the array
	 * @return
	 */
	private BoidNode buildTreeY(Boid[] xList, Boid[] yList, int start, int end){
		if (start >= end) { // Ends Recursion
			return null;
		}
		Boid[] temp = new Boid[(end - start + 1)]; // Temp list to store stuff 
		int tempIndex = 0; // Keeps Track of temp Index
		int medianIndex = (start + end) / 2; // Median Index
		Boid median = xList[medianIndex]; // Gets the median from the x list
		BoidNode node = new BoidNode(null, null, median, false); // Creates a Boid node based on median
		// Loops through twice copying into the temp array
		for(int i = start; i < end; i++){
			if(median.posy > xList[i].posy && xList[i].posy != median.posy){ // Fills left side of array
				temp[tempIndex++] = xList[i];
			}
		}
		temp[tempIndex] = median; // copies the median into the middle of the array
		tempIndex++; 
		for(int i = start; i < end; i++){ // Copies the right half of tmp
			if(median.posy < xList[i].posy && xList[i].posy != median.posy){ // Fills up right side of the array
				temp[tempIndex++] = xList[i]; 
			}
		}
		node.left = buildTreeX(xList, yList, start, medianIndex); 
		node.right = buildTreeX(xList, yList, medianIndex + 1, end);
		return node;
	}


	// ############### Here is my first attempt ###############################
	//	/**
	//	 * This will return a list of all the nodes within a radius
	//	 * @param b The boid of the neighbors
	//	 * @param r The radius within the boid
	//	 * @param node The list of nodes in form of a tree
	//	 * @return A linked list of all the neighbors
	//	 */
	//	public LinkedList<Boid> neighbors(Boid b, double squaredDistance, BoidNode node){
	//		if(node == null) {
	//
	//		}
	//		LinkedList<Boid> neighborBoids = new LinkedList<Boid>(); // List to store some nodes of neighbors
	//		neighborBoids.add(b);
	//		double currentD = Math.pow(b.posx - node.data.posx, 2) + Math.pow(b.posy - node.data.posy, 2); // Get the distance Squared 
	//		// This is our base case 
	//		if(squaredDistance < currentD){
	//			neighborBoids.add(b);
	//		}
	//		if(node.horizontal && b.posx < node.data.posx || !node.horizontal && b.posy < node.data.posx) {
	//			neighbors(b, squaredDistance, node.left); // Change horizontal direction and go left
	//		} 
	//		else
	//			neighbors(b, squaredDistance, node.right); // Change horizontal direction and go right
	//		return neighborBoids;
	//	}

	// Find the SquareRt
	private static double sqr(double x){
		return x * x;
	}

	/**
	 * Find the Neighbours based on adams psydo code
	 * Takes a boid and uses boid poitns to make the place
	 * @param p
	 * @param sqrRad
	 * @param n
	 * @return
	 */
	public LinkedList<Boid> neighbors(Boid p, double squareRadius, BoidNode n)
	{
		LinkedList<Boid> neighbors = new LinkedList();
		if (n == null) {
			return neighbors;
		}
		if (sqr(p.posx - n.data.posx) + sqr(p.posy - n.data.posy) < squareRadius) {
			neighbors.add(n.data);
			neighbors.addAll(neighbors(p, squareRadius, n.left));
			neighbors.addAll(neighbors(p, squareRadius, n.right));
		}
		else if (((n.horizontal) && (sqr(p.posx - n.data.posx) < squareRadius)) || 
				(((!n.horizontal) && (sqr(p.posy - n.data.posy) < squareRadius)))) {
			neighbors.addAll(neighbors(p, squareRadius, n.left));
			neighbors.addAll(neighbors(p, squareRadius, n.right));
				}
		else if (((n.horizontal) && (p.posx < n.data.posx)) || (
				((!n.horizontal) && (p.posy < n.data.posy))))
		{
			neighbors.addAll(neighbors(p, squareRadius, n.left));
		}
		else
		{
			neighbors.addAll(neighbors(p, squareRadius, n.right));
		}
		return neighbors;
	}

	// Driver method
	public LinkedList<Boid> neighbors(Boid p, double sqrRad)
	{
		LinkedList<Boid> neighbors = new LinkedList<Boid>();
		neighbors.addAll(neighbors(p, sqrRad, this.root)); // Adds the good stuff
		return neighbors;
	}

	/**
	 * Comparator class that is used to sort the x list by position x
	 * 
	 * @author ahsu
	 *
	 */
	public class xSort implements Comparator<Boid> {
		@Override
		public int compare(Boid o1, Boid o2) {
			if(o1.posx < o2.posx){
				return -1;
			}
			else if(o1.posx > o2.posx){
				return 1;
			}
			return 0;
		}
	}

	/**
	 * Comparator class that is used to sort the y list by list y
	 * 
	 * @author ahsu
	 *
	 */
	public class ySort implements Comparator<Boid> {
		@Override
		public int compare(Boid o1, Boid o2) {
			if(o1.posy < o2.posy){
				return -1;
			}
			else if(o1.posy > o2.posy){
				return 1;
			}
			return 0;
		}

	}

}







