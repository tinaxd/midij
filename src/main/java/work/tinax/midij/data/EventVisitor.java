package work.tinax.midij.data;

public interface EventVisitor {
	void handleNote(Event event, int scale, int duration, int velocity);
	void handleGMReset(Event event);
}
