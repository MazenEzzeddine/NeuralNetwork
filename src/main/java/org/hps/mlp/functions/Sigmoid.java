package org.hps.mlp.functions;


import org.hps.mlp.ann.MLPLayer;
import org.hps.mlp.ann.MLPNeuron;

/**
 * Created by xschen on 6/6/2017.
 * Sigmoid function
 */
public class Sigmoid extends AbstractTransferFunction
{
	@Override
	public double calculate(MLPLayer layer, int j)
	{
		MLPNeuron neuron = layer.get(j);
		double z = neuron.aggregate();
		return 1/(Math.exp(-z)+1);
	}


	@Override public double gradient(MLPLayer layer, int j) {
		double y = calculate(layer, j);
		return y * (1-y);
	}
}
