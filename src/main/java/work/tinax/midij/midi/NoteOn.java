package work.tinax.midij.midi;

public class NoteOn extends MidiEvent {
	private int scale, velocity;
	
	public NoteOn(int deltaTime, int scale, int velocity) {
		super(deltaTime);
		this.scale = scale;
		this.velocity = velocity;
	}

	public int getScale() {
		return scale;
	}

	public int getVelocity() {
		return velocity;
	}
}
