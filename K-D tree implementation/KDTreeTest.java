package assignment10;

import java.util.ArrayList;
import java.util.Iterator;

import assignment10.Crowd.Boid;

public class KDTreeTest {

	public static void main(String[] args) {
		ArrayList<Boid> boidy = new ArrayList<Boid>();
		Crowd crowd = new Crowd();
		for (int i=0; i<20; i++) {
            double posx = i;
            double posy = i;
            boidy.add(crowd.new Boid(posx, posy, 0, 0));
        }
		KDBoidTree boidling = new KDBoidTree(boidy);
		System.out.println(boidling.root.data.posx);
		System.out.println(boidling.root.left.data.posy);
		System.out.println(boidling.root.right.data.posy);
		System.out.println(boidling.root.right.data.posx);
		System.out.println(boidling.root.right.right.data.posx);
		System.out.print(boidling.neighbors(boidling.root.right.data, 50).contains(boidling.root.right.data));
	}
}