
package invasion.game.test;

/**
 * @author milosav.grubovic
 *
 */
public class Border {
	private int rowIndex;
	private int landEnd;
	private int seaYellowEnd;
	
	
	/**
	 * @param landEnd
	 * @param seaYellowEnd
	 */
	public Border(int rowIndex, int landEnd, int seaYellowEnd) {
		super();
		this.rowIndex = rowIndex;
		this.landEnd = landEnd;
		this.seaYellowEnd = seaYellowEnd;
	}
	/**
	 * @return the landEnd
	 */
	public int getLandEnd() {
		return landEnd;
	}
	/**
	 * @param landEnd the landEnd to set
	 */
	public void setLandEnd(int landEnd) {
		this.landEnd = landEnd;
	}
	/**
	 * @return the seaYellowEnd
	 */
	public int getSeaYellowEnd() {
		return seaYellowEnd;
	}

	public void setSeaYellowEnd(int seaYellowEnd) {
		this.seaYellowEnd = seaYellowEnd;
	}
	/**
	 * @return the rowIndex
	 */
	public int getRowIndex() {
		return rowIndex;
	}
	/**
	 * @param rowIndex the rowIndex to set
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	
	

}
