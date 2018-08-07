package invasion.game.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MakePlayfield {
	public static final int UP = -1;
	public static final int DOWN = 1;

	public Map <Integer, RectField> mapFields;// = new HashMap <>();
	List <Border> listBorders = new ArrayList<Border>();
	List <Force> listForces = new ArrayList<Force>();
	public List <RectField> listHops;// = new ArrayList<>();
	int [] positionOfCities;
	
	public int nextForceCounter=0;
	int forceCounter = 0;
	List <Integer> optimalList = new ArrayList<>();

	public void initGame(){
		// randomly creating borders land, shallow sea, deep sea 
		listBorders = makeBorders();
		mapFields = makeLandscape(listBorders); 
		
		// randomly creating cities 1-4 on the left border
		generateCityPositions();
		putCities();
		
		// randomly creating forces 18-22 and dividing into groups for each city
		listForces = makeForces();
		
		// for test purpose differnt strategies of deployment
		//deployHorizontal();// very simple deploy forces on horizontaly on the sea in the row where the city is
		//deployMinHorizontal();//deploy forces on horizontaly on the sea that is closest to the lannd
		
		deployVertical(UP);
		
		printArray(mapFields);
		
	}
	
	public List <Border> makeBorders() {
		int seaYellowStartCount = 0;
		int seaYellowEndCount = 0;
		
		Random rnd = new Random();
		
		for( int i = 0; i < GameProperties.ROWS; i++) {
			int randomLand= rnd.nextInt(GameProperties.RND_AREA);
			int landEndCount = randomLand;
			if(landEndCount<GameProperties.MIN_AREA){
				landEndCount = GameProperties.MIN_AREA;
			}
			
			int randomYellowSea= rnd.nextInt(GameProperties.RND_AREA-landEndCount);
			seaYellowStartCount =landEndCount+1 ;
			if(randomYellowSea<GameProperties.MIN_AREA){
				randomYellowSea = GameProperties.MIN_AREA;
			}
			seaYellowEndCount = seaYellowStartCount +  randomYellowSea;
			Border borderItem = new Border(i, landEndCount, seaYellowEndCount);
			listBorders.add(borderItem);
			
		}
		return listBorders;
	}
	
	public Map <Integer,RectField> makeLandscape(List<Border> listBorders) {
		int idField=0;
		Map <Integer, RectField> mFields = new HashMap <>();
		for(Border bord: listBorders){
			int landEndCount = bord.getLandEnd();
			int seaYellowEndCount = bord.getSeaYellowEnd();
			
			int numSeaYellow = seaYellowEndCount- landEndCount;
			int numSeaBlue = GameProperties.COLUMNS - seaYellowEndCount;
			listBorders.indexOf(bord);
			
			System.out.print("Row, " + listBorders.indexOf(bord) + " - "); 
			System.out.println("landEndCount " + landEndCount + ", numSeaYellow, " + numSeaYellow + ", numSeaBlue, " + numSeaBlue);
	
			
			while(landEndCount>0) {
				RectField rField = new RectField("L", idField);
				mFields.put(idField, rField);
				landEndCount--;
				idField++;
			}
			while(numSeaYellow>0) {
				RectField rField = new RectField("WS", idField);
				mFields.put(idField, rField);
				numSeaYellow--;
				idField++;
			}
			
			while(numSeaBlue>0) {
				RectField rField = new RectField("BS", idField);
				mFields.put(idField, rField);
				numSeaBlue--;
				idField++;
			}
		}
		//printArray(mFields);
		return mFields;
	}


	public void printArray(Map <Integer, RectField> mapFields){
		int i=1;
		Set<Integer> keys= mapFields.keySet();
		for(Integer item: keys){
			System.out.print(mapFields.get(item));
			if(i%GameProperties.COLUMNS==0){
				System.out.println("**");

			}
			i++;
		}
	}
	public void printArray(List <RectField> listHops){
		System.out.println("There were " + listHops.size() +"hops");

		for(RectField item: listHops){
			System.out.print(item);
		}
	}
	
	public void generateCityPositions(){
		Random rnd = new Random();
		// generating randomly 1-4 cities
		int numberOfCities = GameProperties.MIN_CITIES + rnd.nextInt(GameProperties.MAX_CITIES);
		positionOfCities = new int[numberOfCities];
		
		
 		for( int i = 0; i < positionOfCities.length; i++) {
 			
 			int position = rnd.nextInt(GameProperties.ROWS)*GameProperties.COLUMNS;
 			
 			// it must be checked if the field is free for new city
 			boolean addPosition=true;
 			for(int j=0; j<=i; j++){
				if(positionOfCities[j] == position){// city is already on that field, so try again 
					i--;// step back to try new position for the city
					addPosition = false;
					break;
				}
			}
			if(addPosition){ // the field is free to place the city
				positionOfCities[i] = position;
			}
			
		}
	}
	
	// marking fields where cities are
	public void putCities(){
		for(int i = 0; i < positionOfCities.length; i++) {
			RectField item = mapFields.get(positionOfCities[i]);
			System.out.println("item, "  + ", "+ item +  ", " + "positionOfCities, " + i + ", "+ positionOfCities[i] );

			item.setFieldType("C" + i);
		}
	}
	
	public List<Force> makeForces(){
		Random rnd = new Random();
		int sizeForces = GameProperties.MIN_FORCES + rnd.nextInt(GameProperties.SIZE_FORCES);

		//froming groupes
		int [] groupes = new int[positionOfCities.length];
		int div = sizeForces/positionOfCities.length;
		int mod = sizeForces%positionOfCities.length;
		for(int i=0; i<groupes.length; i++){
			groupes[i] = div;
		}
		
		switch(mod){
			case 1:
				groupes[0]++;
				break;
			case 2:
				groupes[0]++;// = groupes[0] + 1;
				groupes[1]++; //= groupes[1] + 1;
				break;
			case 3:
				groupes[0]++;// = groupes[0] + 1;
				groupes[1]++; //= groupes[1] + 1;
				groupes[2]++; // = groupes[2] + 1;
				break;
		}
		//end of forming groups
		for(int i=0; i<groupes.length; i++){
			System.out.println("sizeForces " + sizeForces +" into groupe " + i + " is "+ groupes[i]); 
		}
		
		// creating forces and making force list
		for(int targ=0; targ<groupes.length; targ++){
			int k = groupes[targ];
			int forceIndex=0;
			while(k>0){
				Force item = new Force("t" + targ +"-f" + forceIndex, "target" + targ);
				item.setTargetPosition(positionOfCities[targ]);
				item.setGroupSize(groupes[targ]);
				listForces.add(item);
				forceIndex++;
				k--;
			}
		}
		return listForces;
	}
	

	public void deployHorizontal() {
		int rowIndex;
		for(int i=0; i<listForces.size(); i++) {
			Force forceItem = listForces.get(i);

			rowIndex = forceItem.getTargetPosition()/GameProperties.COLUMNS;
			Border borderItem = listBorders.get(rowIndex);
			System.out.println("rowIndex " + rowIndex + " border " + listBorders.get(rowIndex).getRowIndex());
			int idField= (borderItem.getRowIndex()) * GameProperties.COLUMNS + borderItem.getSeaYellowEnd() ;
			boolean deployed=false;
			while(!deployed) {
				RectField fieldItem = mapFields.get(idField);
				if(!fieldItem.isOccupied()) {
					fieldItem.setOccupForce(forceItem);
					deployed=true;
				}else {
					idField++;
				}
			}// end while

		}//end for
		
	}
	
	public void deployMinHorizontal() {
		int rowIndex;
		int rowIndexTarget;

		for(int i=0; i<listForces.size(); i++) {
			Force forceItem = listForces.get(i);

			rowIndexTarget = forceItem.getTargetPosition()/GameProperties.COLUMNS;
			rowIndex = rowIndexTarget;
			
			int seaYellowEndIndexMin=GameProperties.COLUMNS;
			
			int maxUp=15;//rows up from row where is the target city

			if(rowIndexTarget + maxUp > GameProperties.ROWS){// but not out of  the playfield
				maxUp =  GameProperties.ROWS - rowIndexTarget;
			}
			int maxDown = -14; // rows down from row where is the target city
			if(rowIndexTarget + maxDown < 0){// but not out of the playfield
				maxDown = 0-rowIndexTarget;
			}
			
			// seven rows up and down from row where is the target city
			for(int j= maxDown; j<maxUp; j++){
				Border borderItemCurrent = listBorders.get(rowIndexTarget+j);
				if(seaYellowEndIndexMin > borderItemCurrent.getSeaYellowEnd()){
					seaYellowEndIndexMin = borderItemCurrent.getSeaYellowEnd();
					rowIndex = rowIndexTarget+j;
					System.out.println("seaYellowEndIndexMin " + seaYellowEndIndexMin + " rowIndex " + listBorders.get(rowIndex).getRowIndex());
				}
			}
			Border borderItem = listBorders.get(rowIndex);
			System.out.println("rowIndex " + rowIndex + " border " + listBorders.get(rowIndex).getRowIndex());
			int idField= (borderItem.getRowIndex()) * GameProperties.COLUMNS + borderItem.getSeaYellowEnd() ;
			boolean deployed=false;
			while(!deployed) {
				RectField fieldItem = mapFields.get(idField);
				if(!fieldItem.isOccupied()) {
					fieldItem.setOccupForce(forceItem);
					deployed=true;
				}else {
					idField++;
				}
			}// end while

		}//end for
		
	}

	public void deployVertical(int step) {
		int rowIndex, rowCity;
		for(int i=0; i<listForces.size(); i++) {
			Force forceItem = listForces.get(i);

			rowCity = forceItem.getTargetPosition()/GameProperties.COLUMNS;
			rowIndex = rowCity;

			boolean deployed=false;
			while(!deployed){ 	// && rowIndex<GameProperties.ROWS && rowIndex>-1) {
				if(rowIndex >= GameProperties.ROWS){
					step = step*(-1); // change direction
					rowIndex = rowCity;
					rowIndex=rowIndex+step;
				}else if(rowIndex < 0){
					step = step*(-1);
					rowIndex = rowCity;//rowIndex = rowCity +step*(-1); // change direction
					rowIndex=rowIndex+step;
				}
				
				Border borderItem = listBorders.get(rowIndex);
				int idField= rowIndex*GameProperties.COLUMNS + borderItem.getSeaYellowEnd() ;
				RectField fieldItem = mapFields.get(idField);
				if(!fieldItem.isOccupied()) {
					fieldItem.setOccupForce(forceItem);
					deployed=true;
				}else {
					rowIndex=rowIndex+step;
				}
			}// end while
			System.out.println("rowIndex " + rowIndex + " City row " + rowCity);
		}//end for
	}

}
