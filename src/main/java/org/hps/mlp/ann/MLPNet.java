package org.hps.mlp.ann;

import org.hps.mlp.enums.LearningMethod;
import org.hps.mlp.enums.WeightUpdateMode;
import org.hps.mlp.functions.TransferFunction;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


//default network assumes input and output are in the range of [0, 1]
public class MLPNet {
	protected MLPLayer inputLayer =null;
	public MLPLayer outputLayer =null;

	protected List<MLPLayer> hiddenLayers;

	@Setter
	protected double maxLearningRate = 1.0;

	@Setter
	protected double L2Penalty = 0.0; // L2 regularization for limiting the size of the weights

	@Setter
	protected double weightConstraint = 0;

	@Setter
	protected LearningMethod learningMethod = LearningMethod.BackPropagation;

	private static final Logger logger = LoggerFactory.getLogger(MLPNet.class);

	@Getter
	@Setter
	protected double learningRate =0.25; //learning rate

	@Setter
	protected WeightUpdateMode weightUpdateMode = WeightUpdateMode.OnlineStochasticGradientDescend;

	@Setter
	protected int miniBatchSize = 50;



	public MLPLayer createInputLayer(int size){
		inputLayer = new MLPLayer(size, 1);
		return inputLayer;
	}

	public MLPLayer createOutputLayer(int size){
		outputLayer = new MLPLayer(size, hiddenLayers.get(hiddenLayers.size()-1).size());
		return outputLayer;
	}



	
	public MLPNet()
	{
		hiddenLayers = new ArrayList<>();
	}



	public void addHiddenLayer(int neuron_count)
	{
		MLPLayer layer;
		if(hiddenLayers.isEmpty()){
			layer = new MLPLayer(neuron_count, inputLayer.size());
		} else {
			layer = new MLPLayer(neuron_count, hiddenLayers.get(hiddenLayers.size() - 1).size());
		}
		hiddenLayers.add(layer);
	}
	
	public void addHiddenLayer(int neuron_count, TransferFunction transfer_function)
	{
		MLPLayer layer;
		if(hiddenLayers.isEmpty()) {
			layer = new MLPLayer(neuron_count, inputLayer.size());
		} else {
			layer = new MLPLayer(neuron_count, hiddenLayers.get(hiddenLayers.size() - 1).size());
		}
		layer.setTransfer(transfer_function);
		hiddenLayers.add(layer);
	}
	
	public double stochasticGradientDescend(double[] input, double[] target)
	{
		//forward propagate
		double[] propagated_output = inputLayer.setOutput(input);
		for(int i=0; i < hiddenLayers.size(); ++i) {
			propagated_output = hiddenLayers.get(i).forward_propagate(propagated_output);
		}
		propagated_output = outputLayer.forward_propagate(propagated_output);


		double error = get_target_error(target);

		
		//backward propagate
		double[] propagated_error = outputLayer.back_propagate(minus(propagated_output, target));
		for(int i = hiddenLayers.size()-1; i >= 0; --i){
			propagated_error = hiddenLayers.get(i).back_propagate(propagated_error);
		}

		//adjust weights
		for(int i = 0; i < hiddenLayers.size(); ++i){
			hiddenLayers.get(i).adjust_weights(getLearningRate(), L2Penalty, weightConstraint);
		}
		outputLayer.adjust_weights(getLearningRate(), L2Penalty, weightConstraint);

		
		return error; 
	}

	public double[] minus(double[] a, double[] b){
		double[] c = new double[a.length];
		for(int i=0; i < a.length; ++i){
			c[i] = a[i] - b[i];
		}
		return c;
	}

	
	protected double get_target_error(double[] target)
	{
		double t_error=0;
		double error=0;
		double[] output = outputLayer.output();
		for(int i=0; i< output.length; i++)
		{
			error = target[i] - output[i];
			t_error+=(0.5 * error * error);
		}
		
		return t_error;
	}
	
	public double[] transform(double[] input)
	{
		double[] propagated_output = inputLayer.setOutput(input);
		for(int i=0; i < hiddenLayers.size(); ++i) {
			propagated_output = hiddenLayers.get(i).forward_propagate(propagated_output);
		}
		propagated_output = outputLayer.forward_propagate(propagated_output);

		return propagated_output;
	}
	
}
