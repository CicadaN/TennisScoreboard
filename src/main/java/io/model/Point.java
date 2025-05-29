package io.model;

public enum Point {
    LOVE(0),
    FIFTEEN(15),
    THIRTY(30),
    FORTY(40),
    ADVANTAGE(-1);

    public final int value;

    Point(int v) {
        this.value = v;
    }

    public Point next() {
        return switch (this) {
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            case FORTY, ADVANTAGE -> this;
        };
    }

    // TODO проверить использование и удалить
    public boolean isAdvantage() {return this == ADVANTAGE; }
    public boolean isForty() { return  this == FORTY; }
}
