/*
 * SANNet Neural Network Framework
 * Copyright (C) 2018 - 2023 Simo Aaltonen
 */

package core.layer.attention;

import core.activation.ActivationFunction;
import core.layer.AbstractExecutionLayer;
import core.layer.NeuralNetworkLayer;
import core.network.NeuralNetworkException;
import utils.configurable.DynamicParamException;
import utils.matrix.*;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implements abstract attention layer.<br>
 *
 * Reference: https://machinelearningmastery.com/adding-a-custom-attention-layer-to-recurrent-neural-network-in-keras/
 * Reference: https://www.analyticsvidhya.com/blog/2019/11/comprehensive-guide-attention-mechanism-deep-learning/
 * Reference: https://analyticsindiamag.com/a-beginners-guide-to-using-attention-layer-in-neural-networks/
 */
@SuppressWarnings("JavadocLinkAsPlainText")
public abstract class AbstractAttentionLayer extends AbstractExecutionLayer {

    /**
     * Softmax activation function.
     *
     */
    protected final ActivationFunction softmaxActivationFunction = new ActivationFunction(UnaryFunctionType.SOFTMAX);

    /**
     * Input matrices for procedure construction.
     *
     */
    protected TreeMap<Integer, Matrix> inputs;

    /**
     * Matrix to store previous output.
     *
     */
    protected Matrix previousOutput;

    /**
     * Constructor for abstract attention layer.
     *
     * @param layerIndex layer index
     * @param initialization initialization function for weight.
     * @param params parameters for abstract attention layer.
     * @throws NeuralNetworkException throws exception if setting of activation function fails.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     * @throws MatrixException throws exception if custom function is attempted to be created with this constructor.
     */
    public AbstractAttentionLayer(int layerIndex, Initialization initialization, String params) throws NeuralNetworkException, DynamicParamException, MatrixException {
        super (layerIndex, initialization, params);
    }

    /**
     * Initializes neural network layer dimensions.
     *
     * @throws NeuralNetworkException thrown if initialization of layer fails.
     */
    public void initializeDimensions() throws NeuralNetworkException {
        if (getLayerWidth() == -1) {
            if ((getDefaultPreviousLayer().getLayerWidth()) < 1) throw new NeuralNetworkException("Default previous layer width must be positive. Invalid value: " + (getDefaultPreviousLayer().getLayerWidth()));
            setLayerWidth(getDefaultPreviousLayer().getLayerWidth());
            setLayerHeight(1);
            setLayerDepth(1);
        }
    }

    /**
     * Checks if layer can have multiple previous layers.
     *
     * @return  if true layer can have multiple previous layers otherwise false.
     */
    public boolean canHaveMultiplePreviousLayers() {
        return true;
    }

    /**
     * Checks if layer is recurrent layer type.
     *
     * @return always false.
     */
    public boolean isRecurrentLayer() { return false; }

    /**
     * Checks if layer works with recurrent layers.
     *
     * @return if true layer works with recurrent layers otherwise false.
     */
    public boolean worksWithRecurrentLayer() {
        return true;
    }

    /**
     * Returns input matrices for procedure construction.
     *
     * @param resetPreviousInput if true resets also previous input.
     * @return input matrix for procedure construction.
     * @throws MatrixException throws exception if matrix operation fails.
     */
    public TreeMap<Integer, Matrix> getInputMatrices(boolean resetPreviousInput) throws MatrixException {
        inputs = new TreeMap<>();

        TreeMap<Integer, Matrix> inputMatrices = new TreeMap<>();
        int layerWidth = -1;
        for (Map.Entry<Integer, NeuralNetworkLayer> entry : getPreviousLayers().entrySet()) {
            if (layerWidth == -1) layerWidth = entry.getValue().getLayerWidth();
            else if (layerWidth != entry.getValue().getLayerWidth()) throw new MatrixException("All inputs must have same width.");
            Matrix input = new DMatrix(layerWidth, 1, 1, Initialization.ONE);
            input.setName("Input" + entry.getValue().getLayerIndex());
            inputMatrices.put(entry.getKey(), input);
        }

        for (int inputIndex = 0; inputIndex < inputMatrices.size(); inputIndex++) {
            inputs.put(inputIndex, inputMatrices.get(inputIndex));
        }

        if (resetPreviousInput) {
            previousOutput = new DMatrix(getLayerWidth(), 1, 1, Initialization.ONE);
        }

        return inputs;
    }

    /**
     * Builds forward procedure and implicitly builds backward procedure.
     *
     * @return output of forward procedure.
     * @throws MatrixException throws exception if matrix operation fails.
     */
    public Matrix getForwardProcedure() throws MatrixException {
        previousOutput.setName("PreviousOutput");

        Matrix totalScoreMatrix = null;
        for (Map.Entry<Integer, Matrix> entry : inputs.entrySet()) {
            Matrix scoreMatrix = getScoreMatrix(entry.getValue(), entry.getKey(), previousOutput);
            totalScoreMatrix = totalScoreMatrix == null ? scoreMatrix : totalScoreMatrix.join(scoreMatrix, true);
        }

        assert totalScoreMatrix != null;
        Matrix weightMatrix = totalScoreMatrix.apply(softmaxActivationFunction);
        weightMatrix.setName("Weights");

        Matrix contextMatrix = null;
        for (Map.Entry<Integer, Matrix> entry : inputs.descendingMap().entrySet()) {
            Matrix singleWeightMatrix =  weightMatrix.unjoin(entry.getKey(), 0, 0, 1, 1, 1);
            singleWeightMatrix.setName("Weight" + entry.getKey());
            contextMatrix = contextMatrix == null ? entry.getValue().multiply(singleWeightMatrix) : contextMatrix.add(entry.getValue().multiply(singleWeightMatrix));
        }

        previousOutput = contextMatrix;

        assert contextMatrix != null;
        contextMatrix.setName("Output");
        return contextMatrix;

    }

    /**
     * Return score matrix for attention.
     *
     * @param input input
     * @param inputIndex input index
     * @param previousOutput previous output
     * @return score matrix.
     * @throws MatrixException throws exception if matrix operation fails.
     */
    protected abstract Matrix getScoreMatrix(Matrix input, int inputIndex, Matrix previousOutput) throws MatrixException;

    /**
     * Returns matrices for which gradient is not calculated.
     *
     * @return matrices for which gradient is not calculated.
     */
    public HashSet<Matrix> getStopGradients() {
        return new HashSet<>();
    }

    /**
     * Returns constant matrices.
     *
     * @return constant matrices.
     */
    public HashSet<Matrix> getConstantMatrices() {
        return new HashSet<>();
    }

    /**
     * Returns number of truncated steps for gradient calculation. -1 means no truncation.
     *
     * @return number of truncated steps.
     */
    protected int getTruncateSteps() {
        return -1;
    }

    /**
     * Returns layer details as string.
     *
     * @return layer details as string.
     */
    protected String getLayerDetailsByName() {
        return "";
    }

}
