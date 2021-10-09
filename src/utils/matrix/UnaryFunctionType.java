/*
 * SANNet Neural Network Framework
 * Copyright (C) 2018 - 2021 Simo Aaltonen
 */

package utils.matrix;

/**
 * Defines supported unary functions.
 *
 */
public enum UnaryFunctionType {

    /**
     * Absolute
     *
     */
    ABS,

    /**
     * Cosine
     *
     */
    COS,

    /**
     * Hyperbolic cosine
     *
     */
    COSH,

    /**
     * Exponential
     *
     */
    EXP,

    /**
     * Natural logarithm
     *
     */
    LOG,

    /**
     * Base 10 logarithm
     *
     */
    LOG10,

    /**
     * Sign
     *
     */
    SGN,

    /**
     * Sine
     *
     */
    SIN,

    /**
     * Hyperbolic sine
     *
     */
    SINH,

    /**
     * Square root
     *
     */
    SQRT,

    /**
     * Cubic root
     *
     */
    CBRT,

    /**
     * Inverse multiplication
     *
     */
    MULINV,

    /**
     * Tangent
     *
     */
    TAN,

    /**
     * Hyperbolic tangent
     *
     */
    TANH,

    /**
     * Linear
     *
     */
    LINEAR,

    /**
     * Sigmoid
     *
     */
    SIGMOID,

    /**
     * Swish
     *
     */
    SWISH,

    /**
     * Hard sigmoid
     *
     */
    HARDSIGMOID,

    /**
     * Bi-polar sigmoid
     *
     */
    BIPOLARSIGMOID,

    /**
     * Hyperbolic tangent sigmoid
     *
     */
    TANHSIG,

    /**
     * Approximated tangent.
     *
     */
    TANHAPPR,

    /**
     * Hard tangent
     *
     */
    HARDTANH,

    /**
     * Softplus
     *
     */
    SOFTPLUS,

    /**
     * Softsign
     *
     */
    SOFTSIGN,

    /**
     * Rectified Linear Unit (ReLU)
     *
     */
    RELU,

    /**
     * Cosine ReLU
     *
     */
    RELU_COS,

    /**
     * Sine ReLU
     *
     */
    RELU_SIN,

    /**
     * Exponential Linear Unit (ELU)
     *
     */
    ELU,

    /**
     * Scaled Exponential Linear Unit (SELU)
     *
     */
    SELU,

    /**
     * Gaussian Error Linear Units (GELU)
     *
     */
    GELU,

    /**
     * Softmax
     *
     */
    SOFTMAX,

    /**
     * Gumbel Softmax
     *
     */
    GUMBEL_SOFTMAX,

    /**
     * Gaussian
     *
     */
    GAUSSIAN,

    /**
     * Sine activation
     *
     */
    SINACT,

    /**
     * Logit
     *
     */
    LOGIT,

    /**
     * Custom (user definable) function
     *
     */
    CUSTOM

}
