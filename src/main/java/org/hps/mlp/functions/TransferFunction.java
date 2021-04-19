package org.hps.mlp.functions;


import org.hps.mlp.ann.MLPLayer;


/**
 * Created by xschen on 21/8/15.
 * Note that activation function can be used interchangeably as transfer function in this context
 * The term "transfer function" is more commonly used in the "signal and processing"
 */
public interface TransferFunction {
    double calculate(MLPLayer layer, int j);

    double gradient(MLPLayer layer, int j);
}
