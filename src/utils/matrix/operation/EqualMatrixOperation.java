/*
 * SANNet Neural Network Framework
 * Copyright (C) 2018 - 2023 Simo Aaltonen
 */

package utils.matrix.operation;

import utils.matrix.Matrix;
import utils.matrix.MatrixException;

/**
 * Implements equal matrix operation.
 *
 */
public class EqualMatrixOperation extends AbstractMatrixOperation {

    /**
     * First matrix.
     *
     */
    private transient Matrix first;

    /**
     * Result matrix.
     *
     */
    private transient Matrix result;

    /**
     * Constructor for equal matrix operation.
     *
     * @param rows number of rows for operation.
     * @param columns number of columns for operation.
     * @param depth depth for operation.
     */
    public EqualMatrixOperation(int rows, int columns, int depth) {
        super(rows, columns, depth, true);
    }

    /**
     * Applies matrix operation.
     *
     * @param first first matrix.
     * @param result result matrix.
     * @throws MatrixException throws exception if matrix operation fails.
     */
    public void apply(Matrix first, Matrix result) throws MatrixException {
        this.first = first;
        this.result = result;
        applyMatrixOperation();
    }

    /**
     * Returns target matrix.
     *
     * @return target matrix.
     */
    protected Matrix getTargetMatrix() {
        return first;
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
     * Applies operation.<br>
     * Ignores masking of other matrix.<br>
     *
     * @param row current row.
     * @param column current column.
     * @param depth current depth.
     * @param value current value.
     */
    public void apply(int row, int column, int depth, double value) {
        result.setValue(row, column, depth, value);
    }

}
