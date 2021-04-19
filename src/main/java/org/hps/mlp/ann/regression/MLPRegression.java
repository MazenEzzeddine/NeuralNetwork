package org.hps.mlp.ann.regression;

import com.github.chen0040.data.frame.DataFrame;
import com.github.chen0040.data.frame.DataRow;
import com.github.chen0040.data.utils.transforms.Standardization;
import org.hps.mlp.enums.LearningMethod;
import org.hps.mlp.enums.WeightUpdateMode;
import org.hps.mlp.functions.Identity;
import org.hps.mlp.functions.Sigmoid;
import org.hps.mlp.functions.TransferFunction;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 21/8/15.
 */
public class MLPRegression {
    private MLPWithNumericOutput mlp;

    @Getter
    @Setter
    private int epoches = 1000;
    private List<Integer> hiddenLayers;

    @Setter
    private double L2Penalty = 0.0; // L2 regularization for limiting the size of the weights

    @Setter
    protected double weightConstraint = 0;

    @Setter
    private LearningMethod learningMethod = LearningMethod.BackPropagation;

    @Setter
    private int miniBatchSize = 50;

    @Setter
    private double maxLearningRate = 1.0;

    @Setter
    protected WeightUpdateMode weightUpdateMode = WeightUpdateMode.OnlineStochasticGradientDescend;

    private boolean adaptiveLearningRateEnabled = false;

    public void enabledAdaptiveLearningRate(boolean enabled){
        adaptiveLearningRateEnabled = enabled;
    }

    @Getter
    @Setter
    private TransferFunction hiddenLayerTransfer = new Sigmoid();

    @Getter
    @Setter
    private TransferFunction outputLayerTransfer = new Identity();

    @Getter
    @Setter
    private double learningRate = 0.2;

    @Getter
    @Setter
    private Standardization outputNormalization = new Standardization();

    public MLPRegression(){
        epoches = 1000;

        learningRate = 0.2;
        hiddenLayers = new ArrayList<>();
        hiddenLayers.add(6);
    }

    public List<Integer> getHiddenLayers() {
        return hiddenLayers;
    }

    public void setHiddenLayers(int... hiddenLayers) {
        this.hiddenLayers = new ArrayList<>();
        for(int hiddenLayerNeuronCount : hiddenLayers){
            this.hiddenLayers.add(hiddenLayerNeuronCount);
        }
    }

    public double transform(DataRow tuple) {
        double[] target = mlp.transform(tuple);
        return target[0];
    }

    public void fit(DataFrame batch) {

        mlp = new MLPWithNumericOutput();
        mlp.setNormalizeOutputs(true);
        mlp.setOutputNormalization(outputNormalization);
        mlp.setMiniBatchSize(miniBatchSize);
        mlp.setLearningMethod(learningMethod);
        mlp.setWeightUpdateMode(weightUpdateMode);
        mlp.setMaxLearningRate(maxLearningRate);
        mlp.setL2Penalty(L2Penalty);
        mlp.setWeightConstraint(weightConstraint);
        mlp.enabledAdaptiveLearningRate(adaptiveLearningRateEnabled);

        int dimension = batch.row(0).toArray().length;

        mlp.setLearningRate(learningRate);
        mlp.createInputLayer(dimension);
        for (int hiddenLayerNeuronCount : hiddenLayers){
            mlp.addHiddenLayer(hiddenLayerNeuronCount, hiddenLayerTransfer);
        }
        mlp.createOutputLayer(1);
        mlp.outputLayer.setTransfer(outputLayerTransfer);

        mlp.train(batch, epoches);
    }
}
