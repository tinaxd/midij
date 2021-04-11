package work.tinax.midij.gui;

final class EditState {
	private boolean inEdit = false;
	private double mouseStartX, mouseStartY;
	private Double mouseEndX, mouseEndY;
	
	public void endEdit() {
		inEdit = false;
	}
	
	public void startEdit(double mouseStartX, double mouseStartY, Double mouseEndX, Double mouseEndY) {
		this.mouseStartX = mouseStartX;
		this.mouseStartY = mouseStartY;
		this.mouseEndX = mouseEndX;
		this.mouseEndY = mouseEndY;
		inEdit = true;
	}
	
	public void startEdit(double mouseStartX, double mouseStartY) {
		startEdit(mouseStartX, mouseStartY, null, null);
	}
	
	public void updateMouseEnd(double mouseEndX, double mouseEndY) {
		if (!inEdit) throw new IllegalStateException("not in edit mode");
		this.mouseEndX = mouseEndX;
		this.mouseEndY = mouseEndY;
	}
	
	public boolean hasEndPosition() {
		return mouseEndX != null && mouseEndY != null;
	}
	
	public boolean isInEdit() {
		return inEdit;
	}

	public double getMouseStartX() {
		return mouseStartX;
	}

	public double getMouseStartY() {
		return mouseStartY;
	}

	public Double getMouseEndX() {
		return mouseEndX;
	}

	public Double getMouseEndY() {
		return mouseEndY;
	}
}
