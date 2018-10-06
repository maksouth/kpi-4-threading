package lab3;

class Pair<T, V> {
    private final T first;
    private final V second;

    Pair(T first, V second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return first;
    }

    public V second() {
        return second;
    }
}
