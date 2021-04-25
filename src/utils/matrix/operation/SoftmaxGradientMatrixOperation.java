package utils.matrix.operation;

import utils.matrix.Matrix;

/**
 * Defines Softmax gradient matrix operation.
 *
 */
public class SoftmaxGradientMatrixOperation extends AbstractMatrixOperation {

    /**
     * First matrix.
     *
     */
    private Matrix first;

    /**
     * Result matrix.
     *
     */
    private Matrix result;

    /**
     * Constructor for Softmax gradient matrix operation.
     *
     * @param rows number of rows for operation.
     * @param columns number of columns for operation.
     */
    public SoftmaxGradientMatrixOperation(int rows, int columns) {
        super(rows, columns, false);
    }

    /**
     * Returns another matrix used in operation.
     *
     * @return another matrix used in operation.
     */
    public Matrix getAnother() {
        return null;
    }

    /**
     * Sets first matrix.
     *
     * @param first first matrix.
     */
    public void setFirst(Matrix first) {
        this.first = first;
    }

    /**
     * Returns first matrix.
     *
     * @return first matrix.
     */
    public Matrix getFirst() {
        return first;
    }

    /**
     * Sets result matrix.
     *
     * @param result result matrix.
     */
    public void setResult(Matrix result) {
        this.result = result;
    }

    /**
     * Returns result matrix.
     *
     * @return result matrix.
     */
    public Matrix getResult() {
        return result;
    }

    /**
     * Applies operation.
     *
     * @param row current row.
     * @param row1 current row1.
     * @param value current value.
     */
    public void apply(int row, int row1, double value) {
        result.setValue(row1, row, (row == row1 ? 1 : 0) - first.getValue(row1, 0));
    }

}
