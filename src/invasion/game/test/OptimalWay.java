

package invasion.game.test;

/**
 * @author milosav.grubovic
 *
 */
public class OptimalWay {
	 MakePlayfield makePlayfield;
	 Hops hops;
	 
	 static final int UP=-1;
	 static final int DOWN=1;
	 
	 
	 
	 int forceCounter;
	/**
	 * @param makePlayfield
	 */
	public OptimalWay(MakePlayfield makePlayfield) {
		this.makePlayfield = makePlayfield;
		hops = new Hops(makePlayfield);
	}
	
	public int nextForce(int nextForceCounter){
		Force forceItem = makePlayfield.listForces.get(nextForceCounter);
	
		findOptimalPath(forceItem.getTargetPosition(),forceItem );
		
		nextForceCounter++;
		if(	nextForceCounter >= makePlayfield.listForces.size()){
			nextForceCounter = 0;
		}
		return nextForceCounter;
	}
	//1. declaring start and stop place (field) for moving (start is the city, stop is the force) 
	//2. finding optimal point where is the best to make tranisiton form sea to land
	//3. going up/down to the optimal row (it is possible that there is no move)
	//4. going horizontaly on land along the optimal row to the optimal point
	//5. tranisiton form sea to land (it could be horizontal or vertical move)
	//6. going verticaly on the sea to the force row
	//7. going horizontaly on sea along the force point
	
	public void findOptimalPath(int startId, Force forceItem) {
		// first to clear fileds of hop labeles
		hops.clearHopList();
		
		// start position
		RectField start = makePlayfield.mapFields.get(startId);
		int startRow = start.getRowIndex();
		System.out.println("startRow is  " + startRow   );
		
		int hopFieldId = startId;
		hops.recordHop(hopFieldId);
		
		// stop position
		int forceRow = forceItem.getRowForce();
		int forceColumn = forceItem.getColumnForce();
		
		//optimal position for  transition form land  to sea 
		//changing from vertical to horizontal hops
		int optimalPoint = getOptimalPoint(startRow, forceRow, forceColumn);
		int rowOffset = optimalPoint/GameProperties.COLUMNS;
		
		/* Moving hops*/

		// vertical hop from City to optimal row toward the Force 
		hopFieldId = hops.verticalHops(hopFieldId, rowOffset, GameProperties.LAND_HOP); 
		
		//	last land field before the transition to the sea
		int endLandColumn = hops.getEndLandColumn(hopFieldId, optimalPoint, forceItem);
		//horizontal land hops
		hopFieldId = hops.horizontalHops(hopFieldId, endLandColumn, GameProperties.LAND_HOP);

		//transition from land to sea  may be horizontal or vertical
		hopFieldId = hops.transitionalHop(hopFieldId, forceRow);
		
		//vertical sea hops
		hopFieldId = hops.verticalHops(hopFieldId, forceRow, GameProperties.SEA_HOP);
		
		//horizontal sea hops
		hopFieldId = hops.horizontalHops(hopFieldId, forceColumn, GameProperties.SEA_HOP);
	}
	
	// finding optimal row where is the best to make tranisiton form sea to land
	// 1. checking rows above from the stop row - calling method findRowUpDown with UP argument
	// 2. checking rows bellow from the stop row - calling method findRowUpDown with DOWN argument
	// 3. calling method getOptimalRow for the best row for making transition 
	// 4. returnig optimal point (field) for transition from land to sea
	private int getOptimalPoint(int startRow, int forceRow, int forceColumn){

		int targetColumn = forceColumn;
		 
		int up=findRowUpDown(forceRow, targetColumn, UP);
		int down=findRowUpDown(forceRow, targetColumn, DOWN);
		
		for(int i=1; i<9; i++ ){
			if(up!=0 || down !=0){
				break;
			}else{
				targetColumn--;
				up=findRowUpDown(forceRow, targetColumn, UP);
				down=findRowUpDown(forceRow, targetColumn, DOWN);
			}
		}
		System.out.println("targetColumn is " + targetColumn);
		int targetRow =  getOptimalRow(startRow, forceRow, up, down);
		int idField = targetRow * GameProperties.COLUMNS + targetColumn; 
		return idField; 
	}
	
	private int findRowUpDown(int forceRow, int forceColumn, int direction){
		int offset=0;
		for(int i = 1; i< 8; i++){
			int exemineRow = forceRow + i*direction;
			int forceId = forceRow * GameProperties.COLUMNS + forceColumn;
			boolean isLand = makePlayfield.mapFields.get(forceId).getFieldType().indexOf("L")>-1;
			if(exemineRow >= 0 && exemineRow< GameProperties.ROWS && !isLand){//!makePlayfield.mapFields.get(forceId).getFieldType().equals("L")){
			//if(exemineRow >= 0 && exemineRow< GameProperties.ROWS){
				int field = exemineRow * GameProperties.COLUMNS + forceColumn;
				if(makePlayfield.mapFields.get(field).getFieldType().indexOf("L")>-1){
				//if(makePlayfield.mapFields.get(field).getFieldType().equals("L")){

					offset=i;
					return offset;
				}
			}else{
				return offset;
			}
		}
	 	return offset;
	}
	private int getOptimalRow(int startRow, int forceRow, int up, int down){
		int rowOffsetUp=forceRow-up;
		int rowOffsetDown=forceRow+down;
	
		System.out.println("offsets Land is above in " + rowOffsetUp +  ",forceRow " + forceRow + ", up " + up );
		System.out.println("offsets Land is down in " + rowOffsetDown +   ",forceRow " + forceRow + ", down " + down );
		
		int fromSourceDown = Math.abs(startRow-rowOffsetDown);
		int fromSourceUp = Math.abs(startRow-rowOffsetUp);
		
		if(up==0){
			if(down==0){
				return forceRow;
			}else{
				return rowOffsetDown;
			}
		}else if(down==0){
			return rowOffsetUp;
		}
		
		if(up==1){
			if(down==1){
				if(fromSourceDown>fromSourceUp){
					return rowOffsetUp;
				}else{
					return  rowOffsetDown;
				}
			}else{
				return rowOffsetUp;
			}
		}else if(down==1){
			return  rowOffsetDown;
		}
		
		if(up != down){
			if(up < down){
				return  rowOffsetUp;
			}else{
				return rowOffsetDown;
			}
		}else{
			if(fromSourceDown>fromSourceUp){
				return  rowOffsetUp;
			}else{
				return rowOffsetDown;
			}
		}

	}
	
}
