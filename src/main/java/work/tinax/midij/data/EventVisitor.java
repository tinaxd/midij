package work.tinax.midij.data;

public interface EventVisitor<T> {
	T handleNote(Event event, int scale, int duration, int velocity);
	T handleGMReset(Event event);
}
