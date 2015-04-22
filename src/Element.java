import processing.core.*;

public class Element {
	
	private int posX;
	private int posY;
	private float roll;
	private float scale;
	private int width;
	private int height;
	private PImage image;
	
	/**
	 * 
	 */
	public Element() {
		posX = 0;
		posY = 0;
		roll = 0;
		scale = 1;
		width = 0;
		height = 0;
		image = null;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 
	 * @return
	 */
	public PImage getImage() {
		return image;
	}

	/**
	 * 
	 * @return
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * 
	 * @return
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * 
	 * @return
	 */
	public float getRoll() {
		return roll;
	}

	/**
	 * 
	 * @return
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 
	 * @param inc
	 */
	public void incrementRoll(float inc) {
		roll = (PApplet.abs(roll) < PApplet.TWO_PI) ? roll + inc : 0;
	}
	
	/**
	 * 
	 * @param inc
	 */
	public void incrementScale(float inc) {
		scale += inc;
	}

	/**
	 * 
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * 
	 * @param image
	 */
	public void setImage(PImage image) {
		this.image = image;
		this.width = image.width;
		this.height = image.height;
	}

	/**
	 * 
	 * @param posX
	 * @param posY
	 */
	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	/**
	 * 
	 * @param posX
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * 
	 * @param posY
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	/**
	 * 
	 * @param roll
	 */
	public void setRoll(float roll) {
		this.roll = roll;
	}

	/**
	 * 
	 * @param scale
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * 
	 * @param vertex
	 * @return
	 */
	public PVector getVertex(int vertex) {
		int x = 0;
		int y = 0;
		
		//Se obtienen las coordenadas con respecto al centroide y las dimensiones del elemento
		switch(vertex) {
		case 0: //Superior derecho
			x = posX + width / 2;
			y = posY - height / 2;
			break;
		case 1: //Inferior derecho
			x = posX + width / 2;
			y = posY + height / 2;
			break;
		case 2: //Inferior izquierdo
			x = posX - width / 2;
			y = posY + height / 2;
			break;
		case 3: //Superior izquierdo
			x = posX - width / 2;
			y = posY - height / 2;
			break;
		}
		
		//Se actualiza la posicion con respecto al angulo de rotacion que tenga el elemento
		float vX = posX + (x - posX) * PApplet.cos(roll) - (y - posY) * PApplet.sin(roll);
		float vY = posY + (x - posX) * PApplet.sin(roll) + (y - posY) * PApplet.cos(roll);
		
		//La posicion del vertice solicitado se envia en un PVector
		return new PVector(vX, vY);
	}
	
	/**
	 * 
	 * @param vertex
	 * @return
	 */
	public PVector getFaceDir(int vertex) {
		PVector vA = new PVector();
		PVector vB = new PVector();
		switch(vertex) {
		case 0://Superior
			vA = getVertex(3);
			vB = getVertex(0);
			break;
		case 1://Derecha
			vA = getVertex(0);
			vB = getVertex(1);
			break;
		case 2://Inferior
			vA = getVertex(1);
			vB = getVertex(2);
			break;
		case 3://Izquierda
			vA = getVertex(2);
			vB = getVertex(3);
			break;
		}
		return new PVector(vB.x - vA.x, vB.y - vA.y);
	}
	
	/**
	 * 
	 * @param axis
	 * @return
	 */
	public PVector getInterval(PVector axis) {
		float min = 0;
		float max = 0;
		min = max = axis.dot(getVertex(0));
		for(int i = 1;i <= 3;i += 1) {
			float value = axis.dot(getVertex(i));
			min = PApplet.min(min, value);
			max = PApplet.max(max, value);
		}
		return new PVector(min, max);
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	public boolean isCollition(Element e) {
		boolean collition = true;	//De acuerdo a la logica del algoritmo, se inicia con sospecha de colision hasta que se demuestre lo contrario
		float min1, max1, min2, max2;
		min1 = max1 = min2 = max2 = 0;
		for(int i = 0;i < 4;i += 1) {
			PVector interval1 = getInterval(getFaceDir(i));
			PVector interval2 = e.getInterval(getFaceDir(i));
			min1 = interval1.x;
			max1 = interval1.y;
			min2 = interval2.x;
			max2 = interval2.y;
			if(max1 < min2 || max2 < min1) {
				collition = false;
			}
		}
		if(collition) {
			for(int i = 0;i < 4;i += 1) {
				PVector interval1 = getInterval(e.getFaceDir(i));
				PVector interval2 = e.getInterval(e.getFaceDir(i));
				min1 = interval1.x;
				max1 = interval1.y;
				min2 = interval2.x;
				max2 = interval2.y;
				if(max1 < min2 || max2 < min1) {
					collition = false;
				}
			}
			
		}
		return collition;
	}

}
