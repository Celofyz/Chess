package pl.everfree.celofyz;

/* */
public class Field {
	private ID id;
	private int x;
	private int y;
	
	public Field(int x, int y, ID id){
		this.x = x;
		this.y = y;
		this.id = id;
	}

	/*Getters and setters*/
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
