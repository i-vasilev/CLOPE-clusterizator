package ru.vasilev.dao;

import ru.vasilev.entity.Transaction;

import java.io.IOException;

/**
 * Reader of transactions from any data storage.
 * Could be implemented for using databases.
 */
public interface IReader {
    /**
     * Gets next transaction from data storage.
     *
     * @return Ready transaction.
     * @throws IOException Throws when I/O error occurs.
     */
    Transaction next() throws IOException;

    /**
     * Resets reader to start file.
     *
     * @throws IOException Throws when I/O error occurs.
     */
    void reset() throws IOException;
}
