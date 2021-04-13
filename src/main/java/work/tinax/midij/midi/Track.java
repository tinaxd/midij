package work.tinax.midij.midi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Track {
	private List<MidiEvent> events = new ArrayList<>();
	
	public void addEvent(MidiEvent newEvent) {
		events.add(newEvent);
	}
	
	public List<MidiEvent> getUnmodifiableEvents() {
		return Collections.unmodifiableList(events);
	}
}
