package ru.vasilev.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents transaction objects.
 */
public class Transaction {
    /**
     * List of objects for algorithm.
     */
    private List<String> characters;
    /**
     * List of objects for output.
     */
    private List<String> characters_output;

    /**
     * Creates {@link Transaction}'s instance.
     *
     * @param str String of transaction.
     */
    public Transaction(String str) {
        final String[] strings = str.split(",");
        characters = new ArrayList<>(Arrays.asList(strings));
        characters_output = new ArrayList<>(characters);
        for (int j = 0; j < characters.size(); j++) {
            characters.set(j, characters.get(j) + " " + j);
        }
    }

    /**
     * Gets list of objects in transaction.
     *
     * @return List of objects in transaction.
     */
    public List<String> getCharacters() {
        return characters;
    }

    /**
     * Gets list the size of the list of objects.
     *
     * @return Size.
     */
    public int sizeTransaction() {
        return characters.size();
    }

    /**
     * Returns true, if obj is equal to this object.
     *
     * @param obj object, that's checks to equal.
     * @return is obj equal to this object.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Transaction) {
            return ((Transaction)obj).characters.equals(characters);
        }
        return false;
    }

    /**
     *  Returns a string representation of the object.
     *
     * @return  a string representation of the object.
     */
    @Override
    public String toString() {
        return characters_output.toString();
    }
}
