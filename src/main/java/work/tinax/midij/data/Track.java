package work.tinax.midij.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Track {
	private LinkedList<Event> events;
	private boolean sorted = true;
	
	public Track() {
		events = new LinkedList<Event>();
	}
	
	public void addEvent(Event event) {
		if (!sorted) sortAll();
		var iter = events.listIterator();
		while (iter.hasNext()) {
			var e = iter.next();
			if (e.getAbsTick() > event.getAbsTick()) {
				iter.previous();
				iter.add(event);
				return;
			}
		}
		// add to the last
		iter.add(event);
	}
	
	public void addEventNoSort(Event event) {
		events.add(event);
		sorted = false;
	}
	
	public void sortAll() {
		if (sorted) return;
		events.sort(new Comparator<Event>() {
			@Override
			public int compare(Event o1, Event o2) {
				return Integer.compare(o1.getAbsTick(), o2.getAbsTick());
			}
		});
	}
	
	public List<Event> unmodifiableEvents() {
		return Collections.unmodifiableList(events);
	}
	
	public int lastTick() {
		return events.isEmpty() ? 0 : events.getLast().getAbsTick();
	}
}
