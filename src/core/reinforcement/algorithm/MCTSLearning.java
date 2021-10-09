/*
 * SANNet Neural Network Framework
 * Copyright (C) 2018 - 2021 Simo Aaltonen
 */

package core.reinforcement.algorithm;

import core.network.NeuralNetworkException;
import core.reinforcement.agent.AgentException;
import core.reinforcement.agent.Environment;
import core.reinforcement.function.FunctionEstimator;
import core.reinforcement.policy.executablepolicy.MCTSPolicy;
import core.reinforcement.policy.updateablepolicy.UpdateableMCTSPolicy;
import core.reinforcement.value.StateValueFunctionEstimator;
import utils.DynamicParamException;
import utils.matrix.MatrixException;

import java.io.IOException;

/**
 * Class that defines MCTS learning algorithm.<br>
 *
 */
public class MCTSLearning extends AbstractPolicyGradient {

    /**
     * Constructor for MCTS learning
     *
     * @param environment reference to environment.
     * @param policyFunctionEstimator reference to policy function estimator.
     * @param valueFunctionEstimator reference to value function estimator.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     * @throws AgentException throws exception if state action value function is applied to non-updateable policy.
     */
    public MCTSLearning(Environment environment, FunctionEstimator policyFunctionEstimator, FunctionEstimator valueFunctionEstimator) throws DynamicParamException, AgentException {
        super(environment, new UpdateableMCTSPolicy(policyFunctionEstimator), new StateValueFunctionEstimator(valueFunctionEstimator), "gamma = 1, updateValuePerEpisode = true");
    }

    /**
     * Constructor for MCTS learning
     *
     * @param environment reference to environment.
     * @param policyFunctionEstimator reference to policy function estimator.
     * @param valueFunctionEstimator reference to value function estimator.
     * @param params parameters for agent.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     * @throws AgentException throws exception if state action value function is applied to non-updateable policy.
     */
    public MCTSLearning(Environment environment, FunctionEstimator policyFunctionEstimator, FunctionEstimator valueFunctionEstimator, String params) throws DynamicParamException, AgentException {
        super(environment, new UpdateableMCTSPolicy(policyFunctionEstimator), new StateValueFunctionEstimator(valueFunctionEstimator), "gamma = 1, updateValuePerEpisode = true" + (params.isEmpty() ? "" : ", " + params));
    }

    /**
     * Constructor for MCTS learning
     *
     * @param environment reference to environment.
     * @param policyFunctionEstimator reference to policy function estimator.
     * @param mctsPolicy reference to MCTS policy.
     * @param valueFunctionEstimator reference to value function estimator.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     * @throws AgentException throws exception if state action value function is applied to non-updateable policy.
     */
    public MCTSLearning(Environment environment, FunctionEstimator policyFunctionEstimator, MCTSPolicy mctsPolicy, FunctionEstimator valueFunctionEstimator) throws DynamicParamException, AgentException {
        super(environment, new UpdateableMCTSPolicy(policyFunctionEstimator, mctsPolicy), new StateValueFunctionEstimator(valueFunctionEstimator), "gamma = 1, updateValuePerEpisode = true");
    }

    /**
     * Constructor for MCTS learning
     *
     * @param environment reference to environment.
     * @param policyFunctionEstimator reference to policy function estimator.
     * @param mctsPolicy reference to MCTS policy.
     * @param valueFunctionEstimator reference to value function estimator.
     * @param params parameters for agent.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     * @throws AgentException throws exception if state action value function is applied to non-updateable policy.
     */
    public MCTSLearning(Environment environment, FunctionEstimator policyFunctionEstimator, MCTSPolicy mctsPolicy, FunctionEstimator valueFunctionEstimator, String params) throws DynamicParamException, AgentException {
        super(environment, new UpdateableMCTSPolicy(policyFunctionEstimator, mctsPolicy), new StateValueFunctionEstimator(valueFunctionEstimator), "gamma = 1, updateValuePerEpisode = true" + (params.isEmpty() ? "" : ", " + params));
    }

    /**
     * Returns reference to algorithm.
     *
     * @return reference to algorithm.
     * @throws IOException throws exception if creation of target value FunctionEstimator fails.
     * @throws ClassNotFoundException throws exception if creation of target value FunctionEstimator fails.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     * @throws MatrixException throws exception if neural network has less output than actions.
     * @throws NeuralNetworkException throws exception if optimizer is of an unknown type.
     * @throws AgentException throws exception if state action value function is applied to non-updateable policy.
     */
    public MCTSLearning reference() throws MatrixException, NeuralNetworkException, IOException, DynamicParamException, ClassNotFoundException, AgentException {
        return new MCTSLearning(getEnvironment(), policy.reference().getFunctionEstimator(), valueFunction.reference().getFunctionEstimator(), getParams());
    }

    /**
     * Returns reference to algorithm.
     *
     * @param sharedPolicyFunctionEstimator if true shared policy function estimator is used otherwise new policy function estimator is created.
     * @param sharedValueFunctionEstimator if true shared value function estimator is used between value functions otherwise separate value function estimator is used.
     * @param sharedMemory if true shared memory is used between estimators.
     * @return reference to algorithm.
     * @throws IOException throws exception if creation of target value FunctionEstimator fails.
     * @throws ClassNotFoundException throws exception if creation of target value FunctionEstimator fails.
     * @throws DynamicParamException throws exception if parameter (params) setting fails.
     * @throws MatrixException throws exception if neural network has less output than actions.
     * @throws NeuralNetworkException throws exception if optimizer is of an unknown type.
     * @throws AgentException throws exception if state action value function is applied to non-updateable policy.
     */
    public MCTSLearning reference(boolean sharedPolicyFunctionEstimator, boolean sharedValueFunctionEstimator, boolean sharedMemory) throws MatrixException, NeuralNetworkException, IOException, DynamicParamException, ClassNotFoundException, AgentException {
        return new MCTSLearning(getEnvironment(), policy.reference(sharedPolicyFunctionEstimator, sharedMemory).getFunctionEstimator(), valueFunction.reference(sharedValueFunctionEstimator, sharedMemory).getFunctionEstimator(), getParams());
    }

}
