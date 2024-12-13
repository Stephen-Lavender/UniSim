package com.backlogged.univercity;

/**
 * Represents a coordinate with a row and column. Provides functionality for
 * equality checking,
 * hashing, and string representation.
 */
public class Coord {

    private final int row;
    private final int column;

    /**
     * Constructs a {@code Coord} instance with the specified row and column.
     *
     * @param row    The row index of the coordinate.
     * @param column The column index of the coordinate.
     */
    public Coord(int column, int row) {
        this.column = column;
        this.row = row;

    }
    public Coord translate(Coord translation){
        return new Coord(this.column + translation.getColumn(), this.row + translation.getRow());
    }

    /**
     * Computes a hash code for this {@code Coord} based on its row and column
     * values.
     *
     * @return An integer hash code based on the row and column values.
     */
    @Override
    public int hashCode() {
        int result = row ^ (row >>> 32);
        result = 31 * result + Integer.hashCode(column);
        return result;
    }

    /**
     * Compares this {@code Coord} with another object for equality. Two
     * {@code Coord} objects are
     * considered equal if they have the same row and column values.
     *
     * @param other The object to compare with this {@code Coord} for equality.
     * @return {@code true} if the specified object is also a {@code Coord} with the
     * same row and
     * column values; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || !(other instanceof Coord o)) {
            return false;
        }
        return o.getColumn() == this.column && o.getRow() == this.row;
    }

    /**
     * Returns a string representation of this {@code Coord} in the format "(row,
     * column)".
     *
     * @return A string representation of this coordinate.
     */
    @Override
    public String toString() {
        return String.format("(%d, %d)", row, row);
    }

    /**
     * Retrieves the row index of this {@code Coord}.
     *
     * @return The row index.
     */
    public int getRow() {
        return row;
    }

    /**
     * Retrieves the column index of this {@code Coord}.
     *
     * @return The column index.
     */
    public int getColumn() {
        return column;
    }
}
