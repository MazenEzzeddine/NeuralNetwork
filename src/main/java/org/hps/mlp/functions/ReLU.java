package org.hps.mlp.functions;


import org.hps.mlp.ann.MLPLayer;


/**
 * Created by xschen on 31/5/2017.
 */
public class ReLU extends AbstractTransferFunction {
   @Override public double gradient(MLPLayer layer, int j) {
      double z = layer.get(j).aggregate();
      if(z > 0) return 1;
      return 0;
   }


   @Override public double calculate(MLPLayer layer, int j) {
      double z = layer.get(j).aggregate();
      if(z > 0) return z;
      return 0;
   }
}
