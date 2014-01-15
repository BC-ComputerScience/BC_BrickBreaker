package trigger.conditions;

import model.Model;
import trigger.Condition;
import trigger.Relation;
public class BlocksRemaining implements Condition {

	private Model model;
	private Relation rel;
	private int count;
	
	public BlocksRemaining(Model model, int count, Relation rel){
		this.model=model;
		this.rel=rel;
		this.count=count;
	}
	
	@Override
	public boolean isMet() {
		switch (rel){
		case GREATER:
			return model.getBrickCount()>count;
		case LESS:
			return model.getBrickCount()<count;
		case EQUAL:
			return model.getBrickCount()==count;
		case NOT_EQUAL:
			return model.getBrickCount()!=count;
		case GREATER_EQUAL:
			return model.getBrickCount()>=count;
		case LESS_EQUAL:
			return model.getBrickCount()<=count;
		}
		return false;
	}

}
