/*
 * SANNet Neural Network Framework
 * Copyright (C) 2018 - 2021 Simo Aaltonen
 */

package core.optimization;

import utils.configurable.DynamicParam;
import utils.configurable.DynamicParamException;
import utils.matrix.DMatrix;
import utils.matrix.Matrix;
import utils.matrix.MatrixException;
import utils.matrix.UnaryFunctionType;

import java.util.HashMap;

/**
 * Class that implements Adam optimizer.<br>
 * <br>
 * Reference: http://ruder.io/optimizing-gradient-descent/ <br>
 *
 */
public class Adam extends AbstractOptimizer {

    /**
     * Parameter name types for Adam.
     *     - learningRate: learning rate for optimizer. Default value 0.001.<br>
     *     - beta1: beta1 value for optimizer. Default value 0.9.<br>
     *     - beta2: beta2 value for optimizer. Default value 0.999.<br>
     *
     */
    private final static String paramNameTypes = "(learningRate:DOUBLE), " +
            "(beta1:DOUBLE), " +
            "(beta2:DOUBLE)";

    /**
     * Learning rate for Adam. Default value 0.001.
     *
     */
    private double learningRate;

    /**
     * Beta1 term for Adam. Default value 0.9.
     *
     */
    private double beta1;

    /**
     * Beta2 term for Adam. Default value 0.999.
     *
     */
    private double beta2;

    /**
     * Hash map to store iteration counts.
     *
     */
    private transient HashMap<Matrix, Integer> iterations;

    /**
     * Hash map to store first moments (means).
     *
     */
    private transient HashMap<Matrix, Matrix> m;

    /**
     * Hash map to store second moments (uncentered variances).
     *
     */
    private transient HashMap<Matrix, Matrix> v;

    /**
     * Default constructor for Adam.
     *
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     */
    public Adam() throws DynamicParamException {
        super(OptimizationType.ADAM, Adam.paramNameTypes);
    }

    /**
     * Constructor for Adam.
     *
     * @param params parameters for Adam.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     */
    public Adam(String params) throws DynamicParamException {
        super(OptimizationType.ADAM, Adam.paramNameTypes, params);
    }

    /**
     * Initializes default params.
     *
     */
    public void initializeDefaultParams() {
        learningRate = 0.001;
        beta1 = 0.9;
        beta2 = 0.999;
    }

    /**
     * Sets parameters used for Adam.<br>
     * <br>
     * Supported parameters are:<br>
     *     - learningRate: learning rate for optimizer. Default value 0.001.<br>
     *     - beta1: beta1 value for optimizer. Default value 0.9.<br>
     *     - beta2: beta2 value for optimizer. Default value 0.999.<br>
     *
     * @param params parameters used for Adam.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     */
    public void setParams(DynamicParam params) throws DynamicParamException {
        if (params.hasParam("learningRate")) learningRate = params.getValueAsDouble("learningRate");
        if (params.hasParam("beta1")) beta1 = params.getValueAsDouble("beta1");
        if (params.hasParam("beta2")) beta2 = params.getValueAsDouble("beta2");
    }

    /**
     * Resets optimizer state.
     *
     */
    public void reset() {
        iterations = new HashMap<>();
        m = new HashMap<>();
        v = new HashMap<>();
    }

    /**
     * Optimizes single matrix (M) using calculated matrix gradient (dM).<br>
     * Matrix can be for example weight or bias matrix with gradient.<br>
     *
     * @param matrix matrix to be optimized.
     * @param matrixGradient matrix gradients for optimization step.
     * @throws MatrixException throws exception if matrix operation fails.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     */
    public void optimize(Matrix matrix, Matrix matrixGradient) throws MatrixException, DynamicParamException {
        if (iterations == null) iterations = new HashMap<>();
        if (m == null) m = new HashMap<>();
        if (v == null) v = new HashMap<>();

        int iteration;
        if (iterations.containsKey(matrix)) iterations.put(matrix, iteration = iterations.get(matrix) + 1);
        else iterations.put(matrix, iteration = 1);

        Matrix mM;
        if (m.containsKey(matrix)) mM = m.get(matrix);
        else m.put(matrix, mM = new DMatrix(matrix.getRows(), matrix.getColumns()));

        Matrix vM;
        if (v.containsKey(matrix)) vM = v.get(matrix);
        else v.put(matrix, vM = new DMatrix(matrix.getRows(), matrix.getColumns()));

        // mt = β1*mt − 1 + (1 − β1)*gt
        mM.multiply(beta1).add(matrixGradient.multiply(1 - beta1), mM);

        // vt = β2*vt − 1 + (1 − β2)*g2t
        vM.multiply(beta2).add(matrixGradient.power(2).multiply(1 - beta2), vM);

        // mt = mt / (1 − βt1)
        Matrix mM_hat = mM.divide(1 - Math.pow(beta1, iteration));

        // vt = vt / (1 − βt2)
        Matrix vM_hat = vM.divide(1 - Math.pow(beta2, iteration));

        // θt+1 = θt − η / (√^vt + ϵ) * mt
        double epsilon = 10E-8;
        matrix.subtract(mM_hat.divide(vM_hat.add(epsilon).apply(UnaryFunctionType.SQRT)).multiply(learningRate), matrix);
    }

}

