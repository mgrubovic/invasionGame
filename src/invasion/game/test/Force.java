package invasion.game.test;

/**
 * @author milosav.grubovic
 *
 */
public class Force {
	String name;
	String target;
	private int forcePosition;
	private int targetPosition;
	private int groupSize;
	
	private int rowForce;
	private int columnForce;
	
	/**
	 * @param name
	 * @param target
	 */
	public Force(String name, String target) {
		super();
		this.name = name;
		this.target = target;
	}
	public int getForcePosition() {
		return forcePosition;
	}
	public void setForcePosition(int forcePosition) {
		this.forcePosition = forcePosition;
	}
	

	public int getRowForce() {
		rowForce = (forcePosition)/GameProperties.COLUMNS;
		return rowForce;
	}

	public int getColumnForce() {
		columnForce = (forcePosition)%GameProperties.COLUMNS;

		return columnForce;
	}

	/**
	 * @param position the position to set
	 */

	
	public int getTargetPosition() {
		return targetPosition;
	}
	public void setTargetPosition(int targetPosition) {
		this.targetPosition = targetPosition;
	}
	public int getGroupSize() {
		return groupSize;
	}
	public void setGroupSize(int groupSize) {
		this.groupSize = groupSize;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Force [name=" + name + ", target=" + target + "] target-" + targetPosition +",  forcePosition "+ forcePosition;
	}

}
