/*/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */
 
import acm.program.*;
import java.awt.event.*;
import javax.swing.*;
 
public class NameSurfer extends Program implements NameSurferConstants {
 
/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		//Add the name label
		JLabel name = new JLabel("Name");
		add(name, SOUTH);
		//Add the name textfield
		nameField = new JTextField(20);
		add(nameField, SOUTH);
		nameField.addActionListener(this);
		//Add both buttons
		initButtons();
		nameGraph = new NameSurferGraph();
		add(nameGraph);
		//Create the database
		nameDB = new NameSurferDataBase(NAMES_DATA_FILE);
		//Add the action listeners
		addActionListeners();
	}
 
/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nameField || e.getSource() == graph) {
			//get the name typed
			String name = nameField.getText();
			//Get the NameSurferEntry for the name
			NameSurferEntry entry = nameDB.findEntry(name);
			//If entry exists in database, print it's toString
			if(entry != null) {
				nameGraph.addEntry(entry);
				nameGraph.update();
			}
		}
		else if(e.getSource() == clear) {
			nameGraph.clear();
		}
		else if(e.getSource() == delete) {
			String name = nameField.getText();
			NameSurferEntry entry = nameDB.findEntry(name);
			if(entry != null) {
			nameGraph.deleteEntry(entry);
			nameGraph.update();
			}
		}
	}
	
	/** This method inits both buttons */
	public void initButtons() {
		graph = new JButton("Graph");
		add(graph, SOUTH);
		delete = new JButton("Delete");
		add(delete, SOUTH);
		clear = new JButton("Clear");
		add(clear, SOUTH);
	}
	private JTextField nameField;
	private JButton graph;
	private JButton clear;
	private JButton delete;
	private NameSurferGraph nameGraph;
	private NameSurferDataBase nameDB;
} 