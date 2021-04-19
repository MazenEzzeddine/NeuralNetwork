package org.hps.mlp.ann.regression;


import com.github.chen0040.data.frame.DataRow;
import org.hps.mlp.ann.MLP;


/**
 * Created by xschen on 5/9/15.
 */
public class MLPWithNumericOutput extends MLP {

    @Override
    protected boolean isValidTrainingSample(DataRow tuple){
        return !tuple.getTargetColumnNames().isEmpty();
    }

    @Override
    public double[] getTarget(DataRow tuple) {
        double[] target = new double[1];
        target[0] = tuple.target();
        return target;
    }
}
