package trigger.actions;

import trigger.Action;
import model.*;
public class SpawnBox implements Action {
	private Model model;
	private Brick b;
	
	public SpawnBox(Model model,Brick b){
		this.model=model;
		this.b=b;
	}
	
	public void performAction() {
		model.addGameObject(b);
	}

}
