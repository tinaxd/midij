package work.tinax.midij.data;

public abstract class Event {
	public static final int TPQ = 960;
	
	protected final EventType type;
	protected final int absTick;
	
	public Event(EventType eventType, int absTick) {
		type = eventType;
		this.absTick = absTick;
	}
	
	public EventType getEventType() {
		return type;
	}
	
	public int getAbsTick() {
		return absTick;
	}
	
	public abstract void visit(EventVisitor visitor);
}
