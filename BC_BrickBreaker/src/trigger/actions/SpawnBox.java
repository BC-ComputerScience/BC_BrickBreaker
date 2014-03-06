package trigger.actions;

import trigger.Action;
import model.*;
import model.gameObjects.Brick2;
public class SpawnBox implements Action {
	private Model model;
	private Brick2 b;
	
	public SpawnBox(Model model,Brick2 b){
		this.model=model;
		this.b=b;
	}
	
	public void performAction() {
		model.addGameObject(b);
	}

}
