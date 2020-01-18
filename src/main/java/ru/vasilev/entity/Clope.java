package ru.vasilev.entity;

import ru.vasilev.IO.IReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents CLOPE clustering algorithm.
 */
public class Clope {
    /**
     * Coefficient of repulsion.
     */
    private final double repulsion;
    /**
     * List of clusters.
     */
    private List<Cluster> clusters = new ArrayList<>();

    /**
     * Creates and clusters data from reader with using repulsion.
     *
     * @param reader    Reader data from any data storage.
     * @param repulsion Coefficient of repulsion.
     * @throws IOException Throws by DaoReader.
     */
    public Clope(IReader reader, double repulsion) throws IOException {
        this.repulsion = repulsion;
        Transaction transaction;
        boolean moved = true;
        while (moved) {
            moved = false;
            reader.reset();
            while ((transaction = reader.next()) != null) {
                Cluster bestCluster = null;
                final Cluster clusterTransaction = findClusterByTransaction(transaction);
                final double removeDelta = clusterTransaction == null ? 0 : deltaRemove(clusterTransaction, transaction);
                double maxDelta = 0;
                double addDelta;
                for (Cluster cluster :
                        clusters) {
                    addDelta = deltaAdd(cluster, transaction);
                    if (addDelta + removeDelta > maxDelta) {
                        bestCluster = cluster;
                        maxDelta = addDelta;
                    }
                }
                addDelta = deltaAdd(null, transaction);
                if (addDelta + removeDelta > maxDelta) {
                    bestCluster = new Cluster();
                }
                if (bestCluster != clusterTransaction && bestCluster != null) {
                    if (clusterTransaction != null) {
                        clusterTransaction.removeTransaction(transaction);
                    }
                    if (bestCluster.getN() == 0)
                        clusters.add(bestCluster);
                    bestCluster.addTransaction(transaction);
                    moved = true;
                }
            }
            removeEmptyClusters();
        }
    }

    /**
     * Gets delta profit for adding transaction in cluster.
     *
     * @param cluster Cluster for which the delta profit is calculated.
     * @param t       Transaction, that could be added in cluster.
     * @return Delta profit if transaction will be added in cluster.
     */
    private double deltaAdd(Cluster cluster, Transaction t) {
        if (cluster == null) {
            return t.sizeTransaction() / Math.pow(t.sizeTransaction(), repulsion);
        }
        double result;
        final int sNew = cluster.getS() + t.sizeTransaction();
        int wNew = cluster.getW();
        for (String s :
                t.getCharacters()) {
            if (!cluster.getMap().containsKey(s)) {
                wNew++;
            }
        }
        result = sNew * (cluster.getN() + 1) / Math.pow(wNew, repulsion) -
                cluster.getS() * cluster.getN() / Math.pow(cluster.getW(), repulsion);
        return result;
    }

    /**
     * Gets delta profit for removing transaction in cluster.
     *
     * @param cluster Cluster for which the delta profit is calculated.
     * @param t       Transaction, that could be removed in cluster.
     * @return Delta profit if transaction will be removed in cluster.
     */
    private double deltaRemove(Cluster cluster, Transaction t) {
        double result;
        final int sNew = cluster.getS() - t.sizeTransaction();
        int wNew = cluster.getW();
        for (String s :
                t.getCharacters()) {
            if (!cluster.getMap().containsKey(s)) {
                wNew--;
            }
        }
        result = sNew * (cluster.getN() - 1) / Math.pow(wNew, repulsion) -
                cluster.getS() * cluster.getN() / Math.pow(cluster.getW(), repulsion);
        return result;
    }

    /**
     * Finds cluster that's contain transaction.
     *
     * @param transaction Transaction, that's found in clusters.
     * @return Cluster that's contain transaction.
     */
    private Cluster findClusterByTransaction(Transaction transaction) {
        for (Cluster cluster :
                clusters) {
            if (cluster.hasTransaction(transaction)) {
                return cluster;
            }
        }
        return null;
    }

    /**
     * Removes empty clusters.
     */
    private void removeEmptyClusters() {
        for (int i = 0; i < clusters.size(); i++) {
            if (clusters.get(i).getN() == 0) {
                clusters.remove(i);
                i--;
            }
        }
    }

    /**
     * Gets count clusters.
     *
     * @return count clusters
     */
    public int getCountClusters() {
        return clusters.size();
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return clusters.toString();
    }
}
