package invasion.game.test;

public class RectField {
	
	private String fieldType;
	private int idField;
	private Force occupForce;
	
	private int rowIndex;
	private int columnIndex;

	

	
	/**
	 * @return the occupForce
	 */


	public RectField(String fieldType, int idField) {
		super();
		this.fieldType = fieldType;
		this.idField = idField;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	public void setFieldTypeAdditional(String fieldTypeAdditional) {
		this.fieldType += fieldTypeAdditional;
	}
	
	public void removeFieldTypeAdditional(String fieldTypeAdditional) {
		this.fieldType = fieldType.replace(fieldTypeAdditional, ""); //+= fieldTypeAdditional;
		
	}

	public int getIdField() {
		return idField;
	}

	public void setIdField(int idField) {
		this.idField = idField;
	}

	public int getRowIndex() {
		rowIndex = (idField)/GameProperties.COLUMNS;
		return rowIndex;
	}

	
	public int getColumnIndex() {
		columnIndex = (idField)%GameProperties.COLUMNS;

		return columnIndex;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldType == null) ? 0 : fieldType.hashCode());
		result = prime * result + idField;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RectField other = (RectField) obj;
		if (fieldType == null) {
			if (other.fieldType != null)
				return false;
		} else if (!fieldType.equals(other.fieldType))
			return false;
		if (idField != other.idField)
			return false;
		return true;
	}
	
	public Force getOccupForce() {
		return occupForce;
	}

	/**
	 * @param occupForce the occupForce to set
	 */
	public void setOccupForce(Force occupForce) {
		occupForce.setForcePosition(idField) ;
		this.occupForce = occupForce;
	}
	public void removeOccupForce(Force occupForce) {
		occupForce.setForcePosition(-1);
		this.occupForce = null;
	}

	public boolean isOccupied() {
		if(occupForce == null) {
			return false;
		}else {
			return true;
		}
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String fieldInfo =  getRowIndex()+ "."+ getColumnIndex() + "-"+ fieldType  ;
		if(occupForce != null){
			fieldInfo += occupForce.name ;
		}
		fieldInfo +=  ", ";
		return  fieldInfo ;
	}
	
}
