package view;

import java.util.List;

public interface View {

	public void updateScreen(List<Renderable> rendered);
	public int getGameWith();
	public int getGameHeight();

}
