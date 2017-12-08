
public class Pair {
	private int first;
	private int second;
	
	public Pair(int first, int second) {
		setFirst(first);
		setSecond(second);
	}
	
	@Override
	public String toString() {
		return String.format("Pair(%s, %s)", Integer.toString(getFirst()), Integer.toString(getSecond()));
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Pair) {
			Pair otherPair = (Pair)other;
			return otherPair.getFirst() == getFirst() && otherPair.getSecond() == getFirst();
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return String.format("%d%d", getFirst(), getSecond()).hashCode();
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
}
