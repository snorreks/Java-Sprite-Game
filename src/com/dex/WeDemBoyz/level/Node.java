package com.dex.WeDemBoyz.level;

import com.dex.WeDemBoyz.util.Vector2i;

public class Node { //som tile

	public Vector2i tile;
	public Node parent;
	public double fCost, gCost, hCost;
	//cost er som en distance
	//finner kjappeste veien, gjennom tiles som slower ogsaa
	//gCost er summen av hele veien, nodes to nodes.
	//hCost er direkte linjen til veien, skipper mellom alle nodes.
	//fCost er kombonisjonen

	public Node(Vector2i tile, Node parent, double gCost, double hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost; //distance from the start
		this.hCost = hCost; //ditance from finish?
		this.fCost = this.gCost + this.hCost;
	}
}
