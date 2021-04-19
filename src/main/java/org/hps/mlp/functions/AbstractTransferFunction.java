package org.hps.mlp.functions;


import org.hps.mlp.ann.MLPLayer;


/**
 * Created by xschen on 5/9/15.
 */
public abstract class AbstractTransferFunction implements TransferFunction {
    public abstract double calculate(MLPLayer layer, int j);
}
