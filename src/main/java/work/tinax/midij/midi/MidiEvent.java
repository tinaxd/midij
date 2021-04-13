package work.tinax.midij.midi;

public class MidiEvent {
	protected int deltaTime;
	
	public MidiEvent(int deltaTime) {
		this.deltaTime = deltaTime;
	}
	
	public int getDeltaTime() {
		return deltaTime;
	}
}
