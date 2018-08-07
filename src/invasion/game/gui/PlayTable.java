package invasion.game.gui;

import java.io.*;
import java.util.*;

// GUI components and layouts
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import invasion.game.test.DifferentWays;
import invasion.game.test.GameProperties;
import invasion.game.test.MakePlayfield;
import invasion.game.test.OptimalWay;
import invasion.game.test.RectField;

import javax.swing.JTextArea;

// GUI support classes
import java.awt.*;
import java.awt.event.*;

// For creating borders
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;



public class PlayTable extends JFrame implements ActionListener //, TableModelListener
{
  
	private static final long serialVersionUID = 1L;

   JButton bNewGame, bNextForce, bNextWay;
   JTable tabela;
   //int brojacRedova = 0;

   JTextArea logArea ;


   String [] columnNames = new String[GameProperties.COLUMNS];
   String [] [] cellValues = new String[GameProperties.ROWS] [GameProperties.COLUMNS];

   MyRenderer myRenderer;
   MakePlayfield mPlay;



   public PlayTable()
   {
	super("Play Field");

	// Set up Menu 
	JMenuBar mb = new JMenuBar();
	
	JMenu mFile = new JMenu("File");
	JMenuItem mOpen = new JMenuItem("Open Game"); 
	mOpen.addActionListener(this);
	JMenuItem mSave = new JMenuItem("Save Game"); 
	mSave.addActionListener(this);
	
	JMenu mHelp = new JMenu("Help");
	JMenuItem mAbout = new JMenuItem("About");


	mFile.add(mOpen);
	mFile.add(mSave);
	
	mHelp.add(mAbout);
	
	mb.add(mFile);
	mb.add(mHelp);
	setJMenuBar(mb);		

	// Set up the table
	columnHeader();
	makeNewTable();

	tabela = new JTable(cellValues , columnNames );
	tabela.setFont(new Font("Arial", Font.BOLD, 8));
	myRenderer = new MyRenderer();
	tabela.setDefaultRenderer(Object.class, myRenderer);

	// Build table view
	JScrollPane tabelaView = new JScrollPane( tabela );


	// Make a nice border
	EmptyBorder eb = new EmptyBorder(5, 5, 5, 5);
	BevelBorder  bb = new BevelBorder( BevelBorder.LOWERED );
	CompoundBorder cb = new CompoundBorder ( eb, bb );
	tabelaView.setBorder( new CompoundBorder ( cb, eb ) );

	// Button panel
	bNewGame = new JButton( "New Game" ) ;
	bNewGame.addActionListener( this);

	bNextForce = new JButton( "Next Force" ) ;
	bNextForce .addActionListener( this );
	
	bNextWay = new JButton("Next Way");
	bNextWay.addActionListener(this);

	JPanel buttonPanel = new JPanel();
	buttonPanel.add( bNewGame );
	buttonPanel.add( bNextForce );
	buttonPanel.add( bNextWay );

      //********* Info area *****************
	 logArea = new JTextArea(5,10);
	 logArea.setLineWrap(true);
	 logArea.setWrapStyleWord(true);
	 logArea.setFont(new Font("Arial", Font.BOLD, 14));
	 JScrollPane logScroll = new JScrollPane(logArea);
	 logScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


	// Finalizing
	this.getContentPane().add( "North", buttonPanel);
	this.getContentPane().add( "Center", tabelaView);
	this.getContentPane().add( "South", logScroll);

	this.pack();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	this.setSize(screenSize.width, screenSize.height);
	this.setVisible( true );

   } // Constructor

   public static void main(String argv[] )
   {
	PlayTable pTable = new PlayTable();
	WindowListener l = new WindowAdapter(){
		public void windowClosing( WindowEvent e)	{   System.exit(0);   }
	};
	pTable.addWindowListener(l);

   } // main

   private void columnHeader(){
	   for(int i=0; i< GameProperties.COLUMNS ; i++){
		   columnNames[i] = "" + i ;
	   }
   }
   
   public void makeNewTable() {
	   
		mPlay = new MakePlayfield();
		mPlay.initGame();
		populateTable(mPlay.mapFields);// initializing table values with cellValues
		//populateTable();
   }
   

   private void populateTable(Map<Integer, RectField> mapFields){
		Set<Integer> keys= mapFields.keySet();
		for(Integer item: keys){
			RectField field= mapFields.get(item);
			cellValues [field.getRowIndex()][field.getColumnIndex()] = field.toString();
		}

  }
/*   
	private void populateTable(){	
		
		int i=1;
		int rowIndex=0;
		String line="";
		Set<Integer> keys= mPlay.mapFields.keySet();
		for(Integer item: keys){
			line = line + mPlay.mapFields.get(item).toString();
			if(i%GameProperties.COLUMNS==0){
				fillRow(line, rowIndex);
				rowIndex++;
				//System.out.println("**");
				line="";
			}
			i++;
		}
   }
    
   public void fillRow (String linija,int rowIndex){
		
		String [] data = linija.split(","); 
		for(int i = 0; i<GameProperties.COLUMNS; i++){

			vrednostiCelije[rowIndex] [i]= data[i];
		}
   }
*/ 
   
   public class MyRenderer extends DefaultTableCellRenderer  
   { 
	 	private static final long serialVersionUID = 1L;
	
		public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column) { 
	    	   Component c = super.getTableCellRendererComponent(table, value, false, false, row, column); 
	    	   //String elementNaziv = (String)table.getValueAt(row, row);
	    	   String elementNaziv = (String)value;
	
	    	   //System.out.println("all" + elementNaziv);
			   setToolTipText(elementNaziv) ;
	
	    	   if(elementNaziv.indexOf("L") >-1){
	    		   c.setBackground(Color.GREEN);
	    	   }else if(elementNaziv.indexOf("WS") >-1){
	    		   c.setBackground(Color.YELLOW);
	    	   }else if(elementNaziv.indexOf("BS") >-1){
	    		   c.setBackground(Color.BLUE);
	    	   }else if(elementNaziv.indexOf("C") >-1){
	    		   c.setBackground(Color.RED);
	    	   }
	    	   
	    	   if(elementNaziv.indexOf("f") >-1){
	    		   c.setBackground(new Color(240, 240, 255));
	    	   }
	    	   
	    	   if(elementNaziv.indexOf("Hop") >-1){   		  
	    		   //c.setForeground(Color.red);
	    		   c.setBackground(Color.gray);
	    		   
	    	   }else {
	    		   c.setForeground(Color.black);
	
	    	   }
	    	   
	    	   return c; 
	       } 
		public void setValue(Object value){
			super.setValue(value);
		}
   } 




   public void actionPerformed(ActionEvent e)
   {
		String cmd = e.getActionCommand();
		
		if(cmd.equals("New Game")) {
			System.out.println("New Game");
			makeNewTable();
			// ovako nije htelo da radi
			//DefaultTableModel tableModel = (DefaultTableModel)tabela.getModel();
			//tableModel.fireTableDataChanged();
			
			//moze i ovako
			//tabela.repaint();
			
			((AbstractTableModel) tabela.getModel()).fireTableDataChanged();
		}
		if(cmd.equals("Next Force")) {
			System.out.println("Next Force");
	
			OptimalWay optWay = new OptimalWay(mPlay);
			mPlay.nextForceCounter = optWay.nextForce(mPlay.nextForceCounter);
			populateTable(mPlay.mapFields);
			
			logArea.append(mPlay.listHops.toString() +" hops-" + mPlay.listHops.size() + System.lineSeparator() );

			tabela.repaint();
			((AbstractTableModel) tabela.getModel()).fireTableDataChanged();
/*			
			vrednostiCelije[0][0] = "A-BS";
			((AbstractTableModel) tabela.getModel()).fireTableCellUpdated(0, 0);
*/
		}if(cmd.equals("Next Way")) {
			System.out.println("Next Way");
			DifferentWays diffWays = new DifferentWays(mPlay);
			diffWays.otherWay(mPlay.nextForceCounter);
			populateTable(mPlay.mapFields);
			
			logArea.append(mPlay.listHops.toString() +" hops-" + mPlay.listHops.size()  + System.lineSeparator() );

			tabela.repaint();
			((AbstractTableModel) tabela.getModel()).fireTableDataChanged();

		}
		
		if(cmd.equals("Open Game")) {  
			openFile();
		}
   }
   public void openFile ()
   { 
		FileReader ulazniFajl = null;
		try{

		   FileDialog fd = new FileDialog(this, "Open Play Field file",FileDialog.LOAD);
		   fd.setVisible(true);
		   String fajl = fd.getFile();
		   String direktorijum = fd.getDirectory();
		   ulazniFajl = new FileReader(direktorijum + fajl);
		

		   BufferedReader br = new BufferedReader(ulazniFajl );
		   String  linija = br.readLine();
		   boolean prvaLinija = true;
		   while(linija != null)
		   {  
				if( prvaLinija ) {
				   prvaLinija = false;
				} else {
					//fillTable( linija );
					System.out.println(linija);
				}
		         linija = br.readLine();
		   }
	   	   ulazniFajl.close();

	}catch(IOException e){
		System.out.println("Error file opening" + e.toString());
	}
   } // openFile
   

} // end of class
