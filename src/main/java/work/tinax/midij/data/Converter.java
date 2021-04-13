package work.tinax.midij.data;

import java.util.ArrayList;
import java.util.function.Function;

import work.tinax.midij.midi.MidiEvent;
import work.tinax.midij.midi.NoteOff;
import work.tinax.midij.midi.NoteOn;

public class Converter {
	public work.tinax.midij.midi.Track convertTrack(Track track) {
		var absEvents = new ArrayList<AbsTickEvent<MidiEvent>>();
		for (var event : track.unmodifiableEvents()) {
			event.visit(new EventVisitor<Void>() {

				@Override
				public Void handleNote(Event event, int scale, int duration, int velocity) {
					final var noteOnAbsTick = event.getAbsTick();
					Function<Integer, MidiEvent> noteOn = (delta) -> new NoteOn(delta, scale, velocity);
					absEvents.add(new AbsTickEvent<MidiEvent>(noteOnAbsTick, noteOn));
					
					final var noteOffAbsTick = event.getAbsTick() + duration;
					Function<Integer, MidiEvent> noteOff = (delta) -> new NoteOff(delta, scale);
					absEvents.add(new AbsTickEvent<MidiEvent>(noteOffAbsTick, noteOff));
					return null;
				}

				@Override
				public Void handleGMReset(Event event) {
					// TODO
					return null;
				}
				
			});
		}
		
		absEvents.sort((o1, o2) -> Integer.compare(o1.absTick(), o2.absTick()));
		var events = new ArrayList<MidiEvent>();
		int lastAbsTick = 0;
		for (var absEvent : absEvents) {
			final var currentAbsTick = absEvent.absTick();
			final var delta = currentAbsTick - lastAbsTick;
			events.add(absEvent.event().apply(delta));
			lastAbsTick = currentAbsTick;
		}
		return work.tinax.midij.midi.Track.withSortedEvents(events);
	}
}

record AbsTickEvent<T>(int absTick, Function<Integer, T> event) {}
