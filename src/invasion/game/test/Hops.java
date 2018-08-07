
package invasion.game.test;

import java.util.ArrayList;

/**
 * @author milosav.grubovic
 *
 */
public class Hops {
	 static final int UP=-1;
	 static final int DOWN=1;
	
	MakePlayfield makePlayfield;

	/**
	 * @param makePlayfield
	 */
	public Hops(MakePlayfield makePlayfield) {
		this.makePlayfield = makePlayfield;
	}
	
	 public int horizontalHops(int idField, int end, int hopSize){
			
		int hopFieldId = idField;
		int start = hopFieldId%GameProperties.COLUMNS;
		
		if((end-start)==0){
			return 0;
		}
		
		int fullHops= (end-start)/hopSize;
		int lastHop = (end-start)%hopSize;
		
		//full hops
		for(int i=0; i<fullHops;i++) {
			hopFieldId += hopSize;
			recordHop(hopFieldId);
		}
		
		//last hop
		if(lastHop !=0){
			hopFieldId += lastHop;
			recordHop(hopFieldId);
		}
		System.out.println("last horizontal hop " + lastHop + ", in row " + hopFieldId/GameProperties.COLUMNS );
		return hopFieldId;
	}
	 
	public int verticalHops(int idField, int end, int hopSize){

		int hopFieldId = idField;
		int start = idField/GameProperties.COLUMNS;
		
		int distance = end-start;
		
		int fullHops=distance/hopSize;
		int lastHop = distance%hopSize;
		
		int verticalDirection;
		
		if(distance == 0){
			return hopFieldId;
		}else if(distance<0){
			verticalDirection=UP;
		}else{
			verticalDirection=DOWN;
		}
		
		//full hops
		for(int i=0; i<fullHops*verticalDirection; i++) {
			hopFieldId += hopSize*GameProperties.COLUMNS * verticalDirection;
			recordHop(hopFieldId);
		}
		
		//last hop
		if(lastHop !=0){
			hopFieldId += lastHop*GameProperties.COLUMNS;
			recordHop(hopFieldId);
		}
		System.out.println("vertical start " + start + "vertical end " + end + "last v hop " + lastHop);

		
		return hopFieldId;
	}
	
	public int transitionalHop(int hopFieldId, int forceRow){
		int startRow = hopFieldId/GameProperties.COLUMNS;
		
		int direction = (int) Math.signum(forceRow - startRow);
		if(direction==0){
			hopFieldId++;// horizontal hop
		}else{
			hopFieldId += direction*GameProperties.COLUMNS; //vertical hop
		}
		
		recordHop(hopFieldId);
		return hopFieldId;
	
	}
	
	public int getEndLandColumn(int hopFieldId, int optimalPoint, Force forceItem){
		
		int rowOffset = optimalPoint/GameProperties.COLUMNS;
		int columnOffset = optimalPoint%GameProperties.COLUMNS;
		
		int forceRow =  forceItem.getRowForce();
		int forceColumn = forceItem.getColumnForce();
		
		if(forceRow == rowOffset){
			int forceRowLandEnd = makePlayfield.listBorders.get(forceRow).getLandEnd()-1;
			return forceRowLandEnd;
	
		}else{
			if(columnOffset != forceColumn){
				return columnOffset;
			}else{
				return forceColumn;
			}
		}
	}

	public void recordHop(int hopFieldId){
		RectField nextHopField = makePlayfield.mapFields.get(hopFieldId);
		nextHopField.setFieldTypeAdditional("Hop");
		makePlayfield.listHops.add(nextHopField);
	}
	
	public void clearHopList(){
		if(makePlayfield.listHops != null){
			for(RectField item: makePlayfield.listHops){
				item.removeFieldTypeAdditional("Hop");
				System.out.println(item);
			}
		}
		makePlayfield.listHops = new ArrayList<>();
	}
}
	

