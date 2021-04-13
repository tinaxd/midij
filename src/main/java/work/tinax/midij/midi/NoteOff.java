package work.tinax.midij.midi;

public class NoteOff extends MidiEvent {
	private int scale;
	
	public NoteOff(int scale) {
		this.scale = scale;
	}
	
	public int getScale() {
		return scale;
	}
}
