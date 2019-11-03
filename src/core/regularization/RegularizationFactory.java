/********************************************************
 * SANNet Neural Network Framework
 * Copyright (C) 2018 - 2019 Simo Aaltonen
 *
 ********************************************************/

package core.regularization;

import core.NeuralNetworkException;
import utils.DynamicParamException;

import java.io.Serializable;

/**
 * Factory class that creates regularizer instances.
 *
 */
public class RegularizationFactory implements Serializable {

    /**
     * Creates regularization instance with given type with defined parameters.
     *
     * @param regularizationType type of regularizer.
     * @param params parameters for regularization.
     * @return constructed regularizer.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     * @throws NeuralNetworkException throws exception if creation of regularizer fails.
     */
    public static Regularization create(RegularizationType regularizationType, String params) throws DynamicParamException, NeuralNetworkException {
        switch (regularizationType) {
            case DROPOUT:
                return (params == null) ? new DropOut() : new DropOut(params);
            case GRADIENT_CLIPPING:
                return (params == null) ? new GradientClipping() : new GradientClipping(params);
            case L1_REGULARIZATION:
                return (params == null) ? new L1_Regularization() : new L1_Regularization(params);
            case L2_REGULARIZATION:
                return (params == null) ? new L2_Regularization() : new L2_Regularization(params);
            case LP_REGULARIZATION:
                return (params == null) ? new Lp_Regularization() : new Lp_Regularization(params);
        }
        throw new NeuralNetworkException("Creation of regularizer failed.");
    }

    /**
     * Creates regularization instance with given type with defined parameters.
     *
     * @param regularizationType type of regularizer.
     * @return constructed regularizer.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     * @throws NeuralNetworkException throws exception if creation of regularizer fails.
     */
    public static Regularization create(RegularizationType regularizationType) throws DynamicParamException, NeuralNetworkException {
        return create(regularizationType, null);
    }

    /**
     * Returns type of regularizer.
     *
     * @param regularization regularizer.
     * @return type of regularizer.
     * @throws NeuralNetworkException throws exception is regularizer is of unknown type.
     */
    public static RegularizationType getRegularizationType(Regularization regularization) throws NeuralNetworkException {
        if (regularization instanceof DropOut) return RegularizationType.DROPOUT;
        if (regularization instanceof GradientClipping) return RegularizationType.GRADIENT_CLIPPING;
        if (regularization instanceof L1_Regularization) return RegularizationType.L1_REGULARIZATION;
        if (regularization instanceof L2_Regularization) return RegularizationType.L2_REGULARIZATION;
        if (regularization instanceof Lp_Regularization) return RegularizationType.LP_REGULARIZATION;
        throw new NeuralNetworkException("Unknown regularization type");
    }

}
