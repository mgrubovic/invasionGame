package invasion.game.test;

import java.util.List;

public class DifferentWays {
	
	 MakePlayfield makePlayfield;
	 Hops hops;
	 
	 static final int UP=-1;
	 static final int DOWN=1;
	 
	 // the transition points to move from land to sea
	 List <Integer> optimalList;
	 
 	 static int pointCounter = 0;
	 
	/**
	 * @param makePlayfield
	 */
	public DifferentWays(MakePlayfield makePlayfield) {
		this.makePlayfield = makePlayfield;
		
		this.optimalList = makePlayfield.optimalList;
		hops = new Hops(makePlayfield);
	}
	
	public void otherWay(int nextForceCounter){

		Force forceItem = makePlayfield.listForces.get(nextForceCounter);

		if(nextForceCounter != makePlayfield.forceCounter || (makePlayfield.optimalList.size() ==0)){
			System.out.println("forceCounter " + makePlayfield.forceCounter + ", nextForceCounter " +nextForceCounter);
			makePlayfield.forceCounter = nextForceCounter;
			pointCounter = 0;
			makePlayfield.optimalList.clear();
			getOptimalPoints(forceItem);
		}

		System.out.println("optimalList is  " + optimalList   );

		
		if(pointCounter<optimalList.size()){
			System.out.println("optimal point in optimalList row is " +optimalList.get(pointCounter)/GameProperties.COLUMNS + 
					", column " + optimalList.get(pointCounter)%GameProperties.COLUMNS );
			tryOptimalPath(optimalList.get(pointCounter), forceItem);
			pointCounter++;
		}else{
			pointCounter =0;
		}
	}
	// making an ArrayList of optimal points (the transition points from land to sea) 
	private void getOptimalPoints(Force forceItem){
	
		int forceRow = forceItem.getForcePosition()/GameProperties.COLUMNS;
		int forceColumn = forceItem.getForcePosition()%GameProperties.COLUMNS;
		
		int targetColumn = forceColumn;
		int point =  forceRow*GameProperties.COLUMNS + makePlayfield.listBorders.get(forceRow).getLandEnd();
		optimalList.add(point);
		
		int down=findOptimalRow(forceRow, targetColumn, DOWN); 
		int up= findOptimalRow(forceRow, targetColumn, UP);
		System.out.println("targetColumn is " + targetColumn + " up-" +up + " down-"+ down);

		for(int i=0; i<9; i++ ){
			if(up != -10){
				point = up * GameProperties.COLUMNS + targetColumn;
				optimalList.add(point);
			}
			if(down != -10){
				point = down* GameProperties.COLUMNS + targetColumn;
				optimalList.add(point);
			}
						
			targetColumn--;
			up = findOptimalRow(forceRow, targetColumn, UP);
			down = findOptimalRow(forceRow, targetColumn, DOWN);
			
			System.out.println("targetColumn is " + targetColumn + " up-" +up + " down-"+ down);
		}
	}

	private void tryOptimalPath(int optimalPoint, Force forceItem) {
		// first to clear fileds of hop labeles
		hops.clearHopList();
		
		// start position
		int startId = forceItem.getTargetPosition();
		
		System.out.println("optimalPoint is  " + optimalPoint/GameProperties.COLUMNS + " "  + optimalPoint%GameProperties.COLUMNS );
		
		int hopFieldId = startId;
		hops.recordHop(hopFieldId);

		
		// stop position
		int forceRow = forceItem.getRowForce();
		int forceColumn = forceItem.getColumnForce();

		int simplePoint =  optimalPoint;
		int rowOffset = simplePoint/GameProperties.COLUMNS;

		// vertical hop from City to optimal row toward the Force 
		hopFieldId = hops.verticalHops(hopFieldId, rowOffset, GameProperties.LAND_HOP); 
		
		//	last land field before the transition to the sea
		int endLandColumn = hops.getEndLandColumn(hopFieldId, simplePoint, forceItem);
		//horizontal land hops
		hopFieldId = hops.horizontalHops(hopFieldId, endLandColumn, GameProperties.LAND_HOP);

		//transition from land to sea  may be horizontal or vertical
		hopFieldId = hops.transitionalHop(hopFieldId, forceRow);
		
		//vertical sea hops
		hopFieldId = hops.verticalHops(hopFieldId, forceRow, GameProperties.SEA_HOP);
		
		//horizontal sea hops
		hopFieldId = hops.horizontalHops(hopFieldId, forceColumn, GameProperties.SEA_HOP);
	}

	
	// finding optimal row for transition from land to sea looking for in 8 rows bellow or above form the force row
	private int findOptimalRow(int forceRow, int targetColumn, int direction){
		int noRow = -10;
		// going 8 rows up and down (depending on direction) from the force row
		for(int i = 1; i< 9; i++){
			int examineRow = forceRow + i* direction;
			// examination will stop when encounter the land field (column) in the row where the force is  
			int forceId = forceRow * GameProperties.COLUMNS + targetColumn;
			boolean isLand = makePlayfield.mapFields.get(forceId).getFieldType().indexOf("L")>-1;
			
			if(examineRow >= 0 && examineRow< GameProperties.ROWS && !isLand){
				int field = examineRow * GameProperties.COLUMNS + targetColumn;
				if(makePlayfield.mapFields.get(field).getFieldType().indexOf("L")>-1){
				//if(makePlayfield.mapFields.get(field).getFieldType().equals("L")){

					return examineRow;
				}
			}else{
				return noRow;
			}
		}
	 	return noRow;
	}


}


