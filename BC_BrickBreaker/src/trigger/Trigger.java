package trigger;

public interface Trigger {
	public boolean checkConditions();
	public void proc();
	public boolean stillExists();
}
