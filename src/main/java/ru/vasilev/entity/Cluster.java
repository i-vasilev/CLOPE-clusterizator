package ru.vasilev.entity;

import java.util.*;

/**
 * Represents cluster, using in Algorithm CLOPE.
 */
public class Cluster {

    /**
     * Map that represents clustered bar chart.
     */
    private Map<String, Integer> map = new HashMap<>();
    /**
     * List of transaction in cluster.
     */
    private List<Transaction> transactions = new ArrayList<>();

    /**
     * Gets map of objects.
     *
     * @return Map of objects.
     */
    public Map<String, Integer> getMap() {
        return map;
    }

    /**
     * Adds transaction to cluster.
     *
     * @param transaction Transaction, that's put to cluster.
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        int i = 0;
        for (String s :
                transaction.getCharacters()) {
            s = s.trim();
            if (map.containsKey(s)) {
                map.replace(s, map.get(s) + 1);
            } else {
                map.put(s, 1);
            }
        }
    }

    /**
     * Removes transaction from cluster.
     *
     * @param transaction Transaction, that's removed from cluster.
     */
    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        for (String s :
                transaction.getCharacters()) {
            int count = map.get(s);
            if (count == 1) {
                map.remove(s);
            } else {
                map.replace(s, count - 1);
            }
        }
    }

    /**
     * Gets total count of objects in cluster.
     *
     * @return S (count of objects).
     */
    public int getS() {
        int maxHeight = 0;
        final Collection<Integer> values = map.values();
        for (int countObjects :
                values) {
            if (maxHeight < countObjects)
                maxHeight = countObjects;
        }
        return maxHeight * map.size();
    }

    /**
     * Gets count of unique objects in cluster.
     *
     * @return W (count of unique objects in cluster).
     */
    public int getW() {
        return map.keySet().size();
    }

    /**
     * Gets count of transactions.
     *
     * @return count of transactions.
     */
    public int getN() {
        return transactions.size();
    }

    /**
     * Gets true, if cluster has transaction.
     *
     * @param transaction transaction, that's checks in cluster.
     * @return is cluster has transaction.
     */
    public boolean hasTransaction(Transaction transaction) {
        return transactions.lastIndexOf(transaction) != -1;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Count transactions: " + transactions.size() + ". Transactions: " + transactions + "\n";
    }
}
