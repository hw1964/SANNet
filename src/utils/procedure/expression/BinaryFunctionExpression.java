/*
 * SANNet Neural Network Framework
 * Copyright (C) 2018 - 2022 Simo Aaltonen
 */

package utils.procedure.expression;

import utils.matrix.BinaryFunction;
import utils.matrix.BinaryFunctionType;
import utils.matrix.MatrixException;
import utils.matrix.operation.BinaryMatrixOperation;
import utils.procedure.node.Node;

import java.io.Serializable;

/**
 * Implements expression for binary function.<br>
 *
 */
public class BinaryFunctionExpression extends AbstractBinaryExpression implements Serializable {

    /**
     * Binary function type.
     *
     */
    private final BinaryFunctionType binaryFunctionType;

    /**
     * BinaryFunction used.
     *
     */
    private final BinaryFunction binaryFunction;

    /**
     * Binary matrix operation.
     *
     */
    private final BinaryMatrixOperation binaryMatrixOperation;

    /**
     * Constructor for binary function.
     *
     * @param expressionID unique ID for expression.
     * @param argument1 first argument.
     * @param argument2 second argument.
     * @param result result of expression.
     * @param binaryFunction BinaryFunction.
     * @throws MatrixException throws exception if expression arguments are not defined.
     */
    public BinaryFunctionExpression(int expressionID, Node argument1, Node argument2, Node result, BinaryFunction binaryFunction) throws MatrixException {
        super("BINARY_FUNCTION", String.valueOf(binaryFunction.getType()), expressionID, argument1, argument2, result);
        this.binaryFunctionType = binaryFunction.getType();
        this.binaryFunction = binaryFunction;

        // Checks if there is need to broadcast or un-broadcast due to scalar matrix.
        int rows = !argument1.isScalar() ? argument1.getRows() : argument2.getRows();
        int columns = !argument1.isScalar() ? argument1.getColumns() : argument2.getColumns();

        binaryMatrixOperation = new BinaryMatrixOperation(rows, columns, binaryFunction.getFunction());
    }

    /**
     * Returns true is expression is executed as single step otherwise false.
     *
     * @return true is expression is executed as single step otherwise false.
     */
    protected boolean executeAsSingleStep() {
        return false;
    }

    /**
     * Returns binary function type.
     *
     * @return binary function type.
     */
    public BinaryFunctionType getBinaryFunctionType() {
        return binaryFunctionType;
    }

    /**
     * Returns BinaryFunction of expression.
     *
     * @return BinaryFunction of expression.
     */
    public BinaryFunction getBinaryFunction() {
        return binaryFunction;
    }

    /**
     * Calculates expression.
     *
     */
    public void calculateExpression() {
    }

    /**
     * Calculates expression.
     *
     * @param sampleIndex sample index
     * @throws MatrixException throws exception if calculation fails.
     */
    public void calculateExpression(int sampleIndex) throws MatrixException {
        if (argument1.getMatrix(sampleIndex) == null || argument2.getMatrix(sampleIndex) == null) throw new MatrixException(getExpressionName() + ": Arguments for operation not defined");
        binaryMatrixOperation.applyFunction(argument1.getMatrix(sampleIndex), argument2.getMatrix(sampleIndex), result.getNewMatrix(sampleIndex));
    }

    /**
     * Calculates gradient of expression.
     *
     */
    public void calculateGradient() {
    }

    /**
     * Calculates gradient of expression.
     *
     * @param sampleIndex sample index
     * @throws MatrixException throws exception if calculation of gradient fails.
     */
    public void calculateGradient(int sampleIndex) throws MatrixException {
        if (result.getGradient(sampleIndex) == null) throw new MatrixException(getExpressionName() + ": Result gradient not defined.");
        if (!argument1.isStopGradient()) argument1.cumulateGradient(sampleIndex, binaryMatrixOperation.applyGradient(result.getMatrix(sampleIndex), argument2.getMatrix(sampleIndex), result.getGradient(sampleIndex)), false);
    }

    /**
     * Prints expression.
     *
     */
    public void printExpression() {
        printSpecificBinaryExpression();
    }

    /**
     * Prints gradient.
     *
     */
    public void printGradient() {
        printArgument1Gradient(true, " * " + binaryFunctionType + "_GRADIENT(" + result.getName() + ", " + argument2.getName() + ")");
    }

}
