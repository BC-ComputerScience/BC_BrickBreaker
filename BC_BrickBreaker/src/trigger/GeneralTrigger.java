package trigger;

import java.util.ArrayList;

public class GeneralTrigger implements Trigger {
	ArrayList<Action> actions=new ArrayList<Action>();
	ArrayList<Condition> conditions=new ArrayList<Condition>();
	boolean hasProcced=false;
	
	public boolean checkConditions() {
		boolean ret=true;
		for(Condition c: conditions){
			ret=ret&&c.isMet();
		}
		return ret;
	}
	
	public void proc() {
		for(Action a: actions){
			a.performAction();
		}
		hasProcced=true;
	}
	public void addCondition(Condition c){
		this.conditions.add(c);
	}
	public void addAction(Action a){
		this.actions.add(a);
	}
	@Override
	public boolean stillExists() {
		return !hasProcced;
	}
}
