/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		drawDecades();
		drawMargins();
		lineList = new ArrayList<NameSurferEntry>();
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		removeAll();
		lineList.clear();
		drawDecades();
		drawMargins();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		if(!lineList.contains(entry)) {
			lineList.add(entry);
		}
	}
	
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		drawDecades();
		drawMargins();
		if(!lineList.isEmpty()) {
			for(int i = 0; i < lineList.size(); i++) {
				NameSurferEntry currentEntry = lineList.get(i);
				drawEntry(currentEntry, i);
			}
		}
	}
	
	/** Method that deletes a name */
	public void deleteEntry(NameSurferEntry entry) {
		if(lineList.contains(entry)) {
			lineList.remove(entry);
		}
	}
	
	/** Method that draws the decade lines */
	private void drawDecades() {
		//Get the spacing between each line
		int space = getWidth()/ NDECADES;
		int topY = 0;
		int bottomY = getHeight();
		for(int i = 0; i < NDECADES; i++) {
			//Add each line times the spacing, so the first line is 0
			int x = i * space;
			GLine decade  = new GLine(x, topY, x, bottomY);
			add(decade);
			String decadeNum =  "" + (START_DECADE + (i * 10));
			GLabel label = new GLabel(decadeNum, x, getHeight() - 5);
			add(label);
		}
	}
	
	/** Method that draws the top and bottom margins */
	private void drawMargins() {
		int topMargin = GRAPH_MARGIN_SIZE;
		int bottomMargin = getHeight() - GRAPH_MARGIN_SIZE;
		int rightX = getWidth();
		//Create the top line
		GLine topLine, bottomLine;
		topLine = new GLine(0, topMargin, rightX, topMargin);
		add(topLine);
		//Create the bottom line
		bottomLine = new GLine(0, bottomMargin, rightX, bottomMargin);
		add(bottomLine);
	}
	
	/** Method that draws all the entries */
	private void drawEntry(NameSurferEntry entry, int entryNum) {
		//Calculate spacing between each rank number and decade
		int xSpace = getWidth() /NDECADES;
		double ySpace = (getHeight() -(GRAPH_MARGIN_SIZE * 2)) / (double) MAX_RANK;
		double x1, y1, x2, y2;
		for(int i = 0; i < NDECADES - 1; i++) {
			//Get two ranks next to eachother	
			int rank1 = entry.getRank(i);
				int rank2 = entry.getRank(i + 1);
				//If both are 0, both y's are set to bottom margin
				if(rank1 == 0 && rank2 == 0) {
					y1 = getHeight() - GRAPH_MARGIN_SIZE;
					y2 = getHeight() - GRAPH_MARGIN_SIZE;
				}
				//If rank1 is 0, only rank1 is at margin,
				else if(rank1 == 0) {
					y1 = getHeight() - GRAPH_MARGIN_SIZE;
					y2 = GRAPH_MARGIN_SIZE +(ySpace * rank2);
				}
				//If rank2 is only 0, rank 2 is at margin
				else if(rank2 == 0) {
					y1 = GRAPH_MARGIN_SIZE + (ySpace * rank1);
					y2 = getHeight() - GRAPH_MARGIN_SIZE;
				}
				//Calculate how far away a rank for will from the top margin
				else {
					y1 = GRAPH_MARGIN_SIZE + (ySpace * rank1);
					y2 = GRAPH_MARGIN_SIZE + (ySpace * rank2);
				}
				//Calculate the x coordinate for each rank
				x1 = xSpace * i;
				x2 = xSpace * (i + 1);
				//Make the line
				GLine line = new GLine(x1, y1 , x2, y2);
				line.setColor(pickColor(entryNum));
				add(line);
			}
		//Draw the name
		drawName(entry, entryNum);
	};
	
	/** Method that picks a color using numbers */
	private Color pickColor(int number) {
		int colorCase = number % 4;
		Color pickedColor = Color.BLACK;
		switch(colorCase) {
		case 1: pickedColor = Color.RED;
			break;
		case 2: pickedColor = Color.BLUE;
			break;
		case 3: pickedColor = Color.MAGENTA;
			break;
		}
		return pickedColor;
	}
	
	/**Method that adds a label for a given entry
	 * @ entry Entry to get label for
	 * @ entryNum The index of the entry
	 */
	
	private void drawName(NameSurferEntry entry, int entryNum) {
		double xSpace = getWidth() / NDECADES;
		double ySpace = (getHeight() - (GRAPH_MARGIN_SIZE * 2))/ (double) MAX_RANK;
		//For each decade, add a label
		for(int decade = 0; decade < NDECADES; decade++) {
			int rank = entry.getRank(decade);
			double x, y;
			String name = entry.getName();
			if(rank != 0) {
				name += " " + rank;
				y = GRAPH_MARGIN_SIZE + (ySpace * rank);
			}
			//If rank is zero, label's rank is *
			else {
				name += " *";
				y = getHeight() - GRAPH_MARGIN_SIZE;
			}
			x = xSpace * decade;
			GLabel label = new GLabel(name, x + 5, y - 5);
			label.setColor(pickColor(entryNum));
			add(label);
		}
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
	/*Instance variables */
	private ArrayList<NameSurferEntry> lineList;
	
}
