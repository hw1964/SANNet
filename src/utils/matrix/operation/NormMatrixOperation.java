/*
 * SANNet Neural Network Framework
 * Copyright (C) 2018 - 2023 Simo Aaltonen
 */

package utils.matrix.operation;

import utils.matrix.Matrix;
import utils.matrix.MatrixException;

/**
 * Implements norm matrix operation.
 *
 */
public class NormMatrixOperation extends AbstractMatrixOperation {

    /**
     * Input matrix.
     *
     */
    private transient Matrix input;

    /**
     * Power for norm operation.
     *
     */
    private final int p;

    /**
     * Cumulated norm value.
     *
     */
    private double value;

    /**
     * Constructor for norm matrix operation.
     *
     * @param rows number of rows for operation.
     * @param columns number of columns for operation.
     * @param depth depth for operation.
     * @param p power for norm operation.
     */
    public NormMatrixOperation(int rows, int columns, int depth, int p) {
        super(rows, columns, depth, true);
        this.p = p;
    }

    /**
     * Applies operation.
     *
     * @param input input matrix.
     * @return result matrix.
     * @throws MatrixException throws exception if matrix operation fails.
     */
    public double apply(Matrix input) throws MatrixException {
        this.input = input;
        value = 0;
        applyMatrixOperation();
        return Math.pow(value, 1 / (double)p);
    }

    /**
     * Returns target matrix.
     *
     * @return target matrix.
     */
    protected Matrix getTargetMatrix() {
        return input;
    }

    /**
     * Returns another matrix used in operation.
     *
     * @return another matrix used in operation.
     */
    public Matrix getOther() {
        return null;
    }

    /**
     * Applies operation.
     *
     * @param row current row.
     * @param column current column.
     * @param depth current depth.
     * @param value current value.
     */
    public void apply(int row, int column, int depth, double value) {
        this.value += Math.pow(Math.abs(value), p);
    }

}
