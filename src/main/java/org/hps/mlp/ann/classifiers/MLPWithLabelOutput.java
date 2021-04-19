package org.hps.mlp.ann.classifiers;


import com.github.chen0040.data.frame.DataRow;
import org.hps.mlp.ann.MLP;

import java.util.List;
import java.util.function.Supplier;


/**
 * Created by xschen on 5/9/15.
 */
public class MLPWithLabelOutput extends MLP {
    public Supplier<List<String>> classLabelsModel;

    @Override
    public boolean isValidTrainingSample(DataRow tuple){
        return !tuple.getCategoricalTargetColumnNames().isEmpty();
    }

    @Override
    public double[] getTarget(DataRow tuple) {
        List<String> labels = classLabelsModel.get();
        double[] target = new double[labels.size()];
        for (int i = 0; i < labels.size(); ++i) {
            target[i] = labels.get(i).equals(tuple.categoricalTarget()) ? 1 : 0;
        }
        return target;
    }

}
