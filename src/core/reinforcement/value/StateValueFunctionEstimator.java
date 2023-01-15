/*
 * SANNet Neural Network Framework
 * Copyright (C) 2018 - 2023 Simo Aaltonen
 */

package core.reinforcement.value;

import core.reinforcement.memory.Memory;
import core.reinforcement.agent.StateTransition;
import core.reinforcement.function.FunctionEstimator;
import utils.configurable.DynamicParamException;
import utils.matrix.MatrixException;

import java.io.IOException;

/**
 * Implements state value function estimator (state value function with function estimator).<br>
 *
 */
public class StateValueFunctionEstimator extends AbstractValueFunctionEstimator {

    /**
     * Constructor for state value function estimator
     *
     * @param functionEstimator reference to function estimator.
     */
    public StateValueFunctionEstimator(FunctionEstimator functionEstimator) {
        super(1, functionEstimator);
    }

    /**
     * Constructor for state value function estimator
     *
     * @param functionEstimator reference to function estimator.
     * @param params parameters for value function.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     */
    public StateValueFunctionEstimator(FunctionEstimator functionEstimator, String params) throws DynamicParamException {
        super(1, functionEstimator, params);
    }

    /**
     * Returns reference to value function.
     *
     * @return reference to value function.
     * @throws IOException throws exception if copying of neural network fails.
     * @throws ClassNotFoundException throws exception if copying of neural network fails.
     * @throws MatrixException throws exception if matrix operation fails.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     */
    public ValueFunction reference() throws DynamicParamException, MatrixException, IOException, ClassNotFoundException {
        return new StateValueFunctionEstimator(getFunctionEstimator().reference(), getParams());
    }

    /**
     * Returns reference to value function.
     *
     * @param sharedValueFunctionEstimator if true shared value function estimator is used between value functions otherwise separate value function estimator is used.
     * @param sharedMemory if true shared memory is used between estimators.
     * @return reference to value function.
     * @throws IOException throws exception if copying of neural network fails.
     * @throws ClassNotFoundException throws exception if copying of neural network fails.
     * @throws MatrixException throws exception if matrix operation fails.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     */
    public ValueFunction reference(boolean sharedValueFunctionEstimator, boolean sharedMemory) throws DynamicParamException, MatrixException, IOException, ClassNotFoundException {
        return new StateValueFunctionEstimator(sharedValueFunctionEstimator ? getFunctionEstimator() : getFunctionEstimator().reference(sharedMemory), getParams());
    }

    /**
     * Returns reference to value function.
     *
     * @param sharedValueFunctionEstimator if true shared value function estimator is used between value functions otherwise separate value function estimator is used.
     * @param memory reference to memory.
     * @return reference to value function.
     * @throws IOException throws exception if creation of target value function estimator fails.
     * @throws ClassNotFoundException throws exception if creation of target value function estimator fails.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     * @throws MatrixException throws exception if neural network has less output than actions.
     */
    public ValueFunction reference(boolean sharedValueFunctionEstimator, Memory memory) throws DynamicParamException, MatrixException, IOException, ClassNotFoundException {
        return new StateValueFunctionEstimator(sharedValueFunctionEstimator ? getFunctionEstimator() : getFunctionEstimator().reference(memory), getParams());
    }

    /**
     * Returns function index. Always 0.
     *
     * @param stateTransition state transition.
     * @return updated action.
     */
    protected int getValueFunctionIndex(StateTransition stateTransition) {
        return 0;
    }

    /**
     * Returns target value based on next state.
     *
     * @param nextStateTransition next state transition.
     * @return target value based on next state
     */
    public double getTargetValue(StateTransition nextStateTransition) {
        return nextStateTransition.tdTarget;
    }

}
