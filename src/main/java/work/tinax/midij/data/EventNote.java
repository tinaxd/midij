package work.tinax.midij.data;

public class EventNote extends Event {
	private final int scale, duration, velocity;
	
	public EventNote(int absTick, int scale, int duration, int velocity) {
		super(EventType.NOTE, absTick);
		this.scale = scale;
		this.duration = duration;
		this.velocity = velocity;
	}
	
	@Override
	public <T> T visit(EventVisitor<T> visitor) {
		return visitor.handleNote(this, scale, duration, velocity);
	}
	
	@Override
	public String toString() {
		return String.format("<Note (%d) %d %d %d>", absTick, scale, duration, velocity);
	}

}
