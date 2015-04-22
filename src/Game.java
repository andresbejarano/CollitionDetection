/**
 * Ejemplo del uso de las operaciones de traslacion, rotacion y redimension
 */

import processing.core.PApplet;
import processing.core.PVector;

public class Game extends PApplet {
	
	//Linea generada automaticamente por Eclipse (no es necesaria)
	private static final long serialVersionUID = 1L;
	
	private Element e1;
	private Element e2;
	
	/**
	 * 
	 */
	@Override
	public void setup() {
		size(800, 600, P3D);
		
		//Creacion del elemento 1
		e1 = new Element();
		e1.setPos(width / 4,  height / 4);
		e1.setImage(loadImage("alien.png"));
		
		//Creacion del elemento 2
		e2 = new Element();
		e2.setPos(width / 2,  height / 2);
		e2.setImage(loadImage("rocket.png"));
		
		//Creacion del elemento 3
		/*
		e3 = new Element();
		e3.setPos(width - (width / 4), height - (height / 4));
		e3.setImage(loadImage("saturn.png"));
		e3ScaleRad = 0;
		*/
		
		stroke(0xffffffff);
	}
	
	/**
	 * 
	 */
	@Override
	public void draw() {
		background(0);
		
		//Actualizacion del estado del elemento 1 (rotacion)
		e1.incrementRoll(PI / 400);
		e1.setPos(mouseX,  mouseY);
		
		e2.incrementRoll(-PI / 50);
		
		//Actualizacion del estado del elemento 2 (redimension)
		/*
		if(e2.getScale() < 3) {
			e2.incrementScale(0.01f);
		}
		else {
			e2.setScale(1);
		}
		*/
		
		//Actualizacion del estado del elemento 3 (rotacion y redimension)
		/*
		e3.incrementRoll(-PI / 300);
		e3ScaleRad = (e3ScaleRad < TWO_PI) ? e3ScaleRad + (PI / 30) : 0;
		float scaleFactor = map(sin(e3ScaleRad), 1, -1, 1.5f, 0.5f);
		e3.setScale(scaleFactor);
		*/
		
		//Dibujo de los elementos
		
		if(e1.isCollition(e2)) {
			tint(255,0,0);
		}
		else {
			tint(0xffffffff);
		}
		
		drawElement(e1);
		drawElement(e2);
		//drawElement(e3);
	}
	
	/**
	 * 
	 */
	public void drawElement(Element e) {
		pushMatrix();							//Duplica la matriz actual
		translate(e.getPosX(), e.getPosY());	//Traslacion
		rotate(e.getRoll());					//Rotacion
		scale(e.getScale());					//Redimension
		beginShape();
		texture(e.getImage());
		vertex(-e.getWidth() / 2, -e.getHeight() / 2, 0, 0);
		vertex( e.getWidth() / 2, -e.getHeight() / 2, e.getWidth(), 0);
		vertex( e.getWidth() / 2,  e.getHeight() / 2, e.getWidth(), e.getHeight());
		vertex(-e.getWidth() / 2,  e.getHeight() / 2, 0, e.getHeight());
		endShape();
		popMatrix();							//Elimina la matriz duplicada
		
		
		PVector vertex = e.getVertex(0);
		fill(255,0,0);
		ellipse(vertex.x, vertex.y, 10, 10);
		
		vertex = e.getVertex(1);
		fill(0,255,0);
		ellipse(vertex.x, vertex.y, 10, 10);
		
		vertex = e.getVertex(2);
		fill(0,0,255);
		ellipse(vertex.x, vertex.y, 10, 10);
		
		vertex = e.getVertex(3);
		fill(255,255,0);
		ellipse(vertex.x, vertex.y, 10, 10);
	}

}
