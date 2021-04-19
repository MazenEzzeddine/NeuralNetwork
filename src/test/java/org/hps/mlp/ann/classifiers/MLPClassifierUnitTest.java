package org.hps.mlp.ann.classifiers;


import com.github.chen0040.data.frame.DataFrame;
import com.github.chen0040.data.frame.DataQuery;
import com.github.chen0040.data.frame.DataRow;
import org.hps.mlp.enums.LearningMethod;
import org.hps.mlp.enums.WeightUpdateMode;
import org.hps.mlp.functions.Sigmoid;
import org.hps.mlp.utils.FileUtils;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * Created by xschen on 27/5/2017.
 */
public class MLPClassifierUnitTest {

   private static double atof(String s)
   {
      double d = Double.valueOf(s).doubleValue();
      if (Double.isNaN(d) || Double.isInfinite(d))
      {
         System.err.print("NaN or Infinity in input\n");
         System.exit(1);
      }
      return(d);
   }

   private static int atoi(String s)
   {
      return Integer.parseInt(s);
   }

   @Test
   public void test_heartScale() throws FileNotFoundException {
      InputStream inputStream = FileUtils.getResource("heart_scale");

      DataFrame dataFrame = DataQuery.libsvm().from(inputStream).build();

      dataFrame.unlock();
      for(int i=0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);
         row.setCategoricalTargetCell("category-label", "" + row.target());
      }
      dataFrame.lock();

      MLPClassifier mlpClassifier = new MLPClassifier();
      mlpClassifier.setHiddenLayers(6); // one hidden layer, to set two or more hidden layer call mlpClassifier.setHiddenLayer([layer1NeuronCunt], [layer2NeuronCunt], ...);
      mlpClassifier.fit(dataFrame);

      int correctnessCount = 0;
      for(int i = 0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);



         String predicted_label = mlpClassifier.classify(row);
         correctnessCount += (predicted_label.equals(row.categoricalTarget()) ? 1 : 0);

         if(i < 10) {
            System.out.println(row);
            System.out.println("predicted: " + predicted_label + "\tactual: " + row.categoricalTarget());
         }
      }

      System.out.println("Prediction Accuracy: "+(correctnessCount * 100 / dataFrame.rowCount()));
   }

   @Test
   public void test_heartScale_L2_regularization() throws FileNotFoundException {
      InputStream inputStream = FileUtils.getResource("heart_scale");

      DataFrame dataFrame = DataQuery.libsvm().from(inputStream).build();

      dataFrame.unlock();
      for(int i=0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);
         row.setCategoricalTargetCell("category-label", "" + row.target());
      }
      dataFrame.lock();

      MLPClassifier mlpClassifier = new MLPClassifier();
      mlpClassifier.setHiddenLayers(6); // one hidden layer, to set two or more hidden layer call mlpClassifier.setHiddenLayer([layer1NeuronCunt], [layer2NeuronCunt], ...);
      mlpClassifier.fit(dataFrame);
      mlpClassifier.setL2Penalty(0.01);

      int correctnessCount = 0;
      for(int i = 0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);



         String predicted_label = mlpClassifier.classify(row);
         correctnessCount += (predicted_label.equals(row.categoricalTarget()) ? 1 : 0);

         if(i < 10) {
            System.out.println(row);
            System.out.println("predicted: " + predicted_label + "\tactual: " + row.categoricalTarget());
         }
      }

      System.out.println("Prediction Accuracy: "+(correctnessCount * 100 / dataFrame.rowCount()));
   }

   @Test
   public void test_heartScale_weight_constraint() throws FileNotFoundException {
      InputStream inputStream = FileUtils.getResource("heart_scale");

      DataFrame dataFrame = DataQuery.libsvm().from(inputStream).build();

      dataFrame.unlock();
      for(int i=0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);
         row.setCategoricalTargetCell("category-label", "" + row.target());
      }
      dataFrame.lock();

      MLPClassifier mlpClassifier = new MLPClassifier();
      mlpClassifier.setHiddenLayers(6); // one hidden layer, to set two or more hidden layer call mlpClassifier.setHiddenLayer([layer1NeuronCunt], [layer2NeuronCunt], ...);
      mlpClassifier.fit(dataFrame);
      mlpClassifier.setWeightConstraint(80);

      int correctnessCount = 0;
      for(int i = 0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);



         String predicted_label = mlpClassifier.classify(row);
         correctnessCount += (predicted_label.equals(row.categoricalTarget()) ? 1 : 0);

         if(i < 10) {
            System.out.println(row);
            System.out.println("predicted: " + predicted_label + "\tactual: " + row.categoricalTarget());
         }
      }

      System.out.println("Prediction Accuracy: "+(correctnessCount * 100 / dataFrame.rowCount()));
   }

   @Test
   public void test_heartScale_steepest_descend() throws FileNotFoundException {
      InputStream inputStream = FileUtils.getResource("heart_scale");

      DataFrame dataFrame = DataQuery.libsvm().from(inputStream).build();

      dataFrame.unlock();
      for(int i=0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);
         row.setCategoricalTargetCell("category-label", "" + row.target());
      }
      dataFrame.lock();

      MLPClassifier mlpClassifier = new MLPClassifier();
      mlpClassifier.setWeightUpdateMode(WeightUpdateMode.SteepestGradientDescend);
      mlpClassifier.setHiddenLayers(6); // one hidden layer, to set two or more hidden layer call mlpClassifier.setHiddenLayer([layer1NeuronCunt], [layer2NeuronCunt], ...);
      mlpClassifier.fit(dataFrame);

      int correctnessCount = 0;
      for(int i = 0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);

         String predicted_label = mlpClassifier.classify(row);
         correctnessCount += (predicted_label.equals(row.categoricalTarget()) ? 1 : 0);

         if(i < 10) {
            System.out.println(row);
            System.out.println("predicted: " + predicted_label + "\tactual: " + row.categoricalTarget());
         }
      }

      System.out.println("Prediction Accuracy: "+(correctnessCount * 100 / dataFrame.rowCount()));
   }

   @Test
   public void test_heartScale_mini_batch_descend() throws FileNotFoundException {
      InputStream inputStream = FileUtils.getResource("heart_scale");

      DataFrame dataFrame = DataQuery.libsvm().from(inputStream).build();

      dataFrame.unlock();
      for(int i=0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);
         row.setCategoricalTargetCell("category-label", "" + row.target());
      }
      dataFrame.lock();

      MLPClassifier mlpClassifier = new MLPClassifier();
      mlpClassifier.setWeightUpdateMode(WeightUpdateMode.MiniBatchGradientDescend);
      mlpClassifier.setMiniBatchSize(20);
      mlpClassifier.setHiddenLayers(6); // one hidden layer, to set two or more hidden layer call mlpClassifier.setHiddenLayer([layer1NeuronCunt], [layer2NeuronCunt], ...);
      mlpClassifier.fit(dataFrame);

      int correctnessCount = 0;
      for(int i = 0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);

         String predicted_label = mlpClassifier.classify(row);
         correctnessCount += (predicted_label.equals(row.categoricalTarget()) ? 1 : 0);

         if(i < 10) {
            System.out.println(row);
            System.out.println("predicted: " + predicted_label + "\tactual: " + row.categoricalTarget());
         }
      }

      System.out.println("Prediction Accuracy: "+(correctnessCount * 100 / dataFrame.rowCount()));
   }

   @Test
   public void test_heartScale_mini_batch_descend_L2_regularization() throws FileNotFoundException {
      InputStream inputStream = FileUtils.getResource("heart_scale");

      DataFrame dataFrame = DataQuery.libsvm().from(inputStream).build();

      dataFrame.unlock();
      for(int i=0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);
         row.setCategoricalTargetCell("category-label", "" + row.target());
      }
      dataFrame.lock();

      MLPClassifier mlpClassifier = new MLPClassifier();
      mlpClassifier.setWeightUpdateMode(WeightUpdateMode.MiniBatchGradientDescend);
      mlpClassifier.setMiniBatchSize(20);
      mlpClassifier.setL2Penalty(0.001);
      mlpClassifier.setHiddenLayers(6); // one hidden layer, to set two or more hidden layer call mlpClassifier.setHiddenLayer([layer1NeuronCunt], [layer2NeuronCunt], ...);
      mlpClassifier.fit(dataFrame);

      int correctnessCount = 0;
      for(int i = 0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);

         String predicted_label = mlpClassifier.classify(row);
         correctnessCount += (predicted_label.equals(row.categoricalTarget()) ? 1 : 0);

         if(i < 10) {
            System.out.println(row);
            System.out.println("predicted: " + predicted_label + "\tactual: " + row.categoricalTarget());
         }
      }

      System.out.println("Prediction Accuracy: "+(correctnessCount * 100 / dataFrame.rowCount()));
   }

   @Test
   public void test_heartScale_mini_batch_descend_weight_constraint() throws FileNotFoundException {
      InputStream inputStream = FileUtils.getResource("heart_scale");

      DataFrame dataFrame = DataQuery.libsvm().from(inputStream).build();

      dataFrame.unlock();
      for(int i=0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);
         row.setCategoricalTargetCell("category-label", "" + row.target());
      }
      dataFrame.lock();

      MLPClassifier mlpClassifier = new MLPClassifier();
      mlpClassifier.setWeightUpdateMode(WeightUpdateMode.MiniBatchGradientDescend);
      mlpClassifier.setMiniBatchSize(20);
      mlpClassifier.setWeightConstraint(80);
      mlpClassifier.setHiddenLayers(6); // one hidden layer, to set two or more hidden layer call mlpClassifier.setHiddenLayer([layer1NeuronCunt], [layer2NeuronCunt], ...);
      mlpClassifier.fit(dataFrame);

      int correctnessCount = 0;
      for(int i = 0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);

         String predicted_label = mlpClassifier.classify(row);
         correctnessCount += (predicted_label.equals(row.categoricalTarget()) ? 1 : 0);

         if(i < 10) {
            System.out.println(row);
            System.out.println("predicted: " + predicted_label + "\tactual: " + row.categoricalTarget());
         }
      }

      System.out.println("Prediction Accuracy: "+(correctnessCount * 100 / dataFrame.rowCount()));
   }


   @Test
   public void test_heartScale_mini_batch_descend_adaptive_learning_rate() throws FileNotFoundException {
      InputStream inputStream = FileUtils.getResource("heart_scale");

      DataFrame dataFrame = DataQuery.libsvm().from(inputStream).build();

      dataFrame.unlock();
      for(int i=0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);
         row.setCategoricalTargetCell("category-label", "" + row.target());
      }
      dataFrame.lock();

      MLPClassifier mlpClassifier = new MLPClassifier();
      mlpClassifier.setWeightUpdateMode(WeightUpdateMode.MiniBatchGradientDescend);
      mlpClassifier.enabledAdaptiveLearningRate(true);
      mlpClassifier.setMiniBatchSize(20);
      mlpClassifier.setHiddenLayers(6); // one hidden layer, to set two or more hidden layer call mlpClassifier.setHiddenLayer([layer1NeuronCunt], [layer2NeuronCunt], ...);
      mlpClassifier.fit(dataFrame);

      int correctnessCount = 0;
      for(int i = 0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);

         String predicted_label = mlpClassifier.classify(row);
         correctnessCount += (predicted_label.equals(row.categoricalTarget()) ? 1 : 0);

         if(i < 10) {
            System.out.println(row);
            System.out.println("predicted: " + predicted_label + "\tactual: " + row.categoricalTarget());
         }
      }

      System.out.println("Prediction Accuracy: "+(correctnessCount * 100 / dataFrame.rowCount()));
   }

   @Test
   public void test_heartScale_steepest_descend_rprop() throws FileNotFoundException {
      InputStream inputStream = FileUtils.getResource("heart_scale");

      DataFrame dataFrame = DataQuery.libsvm().from(inputStream).build();

      dataFrame.unlock();
      for(int i=0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);
         row.setCategoricalTargetCell("category-label", "" + row.target());
      }
      dataFrame.lock();

      MLPClassifier mlpClassifier = new MLPClassifier();
      mlpClassifier.setWeightUpdateMode(WeightUpdateMode.SteepestGradientDescend);
      mlpClassifier.setLearningMethod(LearningMethod.ResilientBackPropagation);
      mlpClassifier.setMiniBatchSize(20);
      mlpClassifier.setHiddenLayers(6); // one hidden layer, to set two or more hidden layer call mlpClassifier.setHiddenLayer([layer1NeuronCunt], [layer2NeuronCunt], ...);
      mlpClassifier.fit(dataFrame);

      int correctnessCount = 0;
      for(int i = 0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);

         String predicted_label = mlpClassifier.classify(row);
         correctnessCount += (predicted_label.equals(row.categoricalTarget()) ? 1 : 0);

         if(i < 10) {
            System.out.println(row);
            System.out.println("predicted: " + predicted_label + "\tactual: " + row.categoricalTarget());
         }
      }

      System.out.println("Prediction Accuracy: "+(correctnessCount * 100 / dataFrame.rowCount()));
   }

   @Test
   public void test_heartScale_mini_batch_descend_sigmoid_output_activation() throws FileNotFoundException {
      InputStream inputStream = FileUtils.getResource("heart_scale");

      DataFrame dataFrame = DataQuery.libsvm().from(inputStream).build();

      dataFrame.unlock();
      for(int i=0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);
         row.setCategoricalTargetCell("category-label", "" + row.target());
      }
      dataFrame.lock();

      MLPClassifier mlpClassifier = new MLPClassifier();
      mlpClassifier.setWeightUpdateMode(WeightUpdateMode.MiniBatchGradientDescend);
      mlpClassifier.setMiniBatchSize(20);
      mlpClassifier.setHiddenLayerTransfer(new Sigmoid());
      mlpClassifier.setOutputLayerTransfer(new Sigmoid());
      mlpClassifier.setHiddenLayers(6); // one hidden layer, to set two or more hidden layer call mlpClassifier.setHiddenLayer([layer1NeuronCunt], [layer2NeuronCunt], ...);
      mlpClassifier.fit(dataFrame);

      int correctnessCount = 0;
      for(int i = 0; i < dataFrame.rowCount(); ++i){
         DataRow row = dataFrame.row(i);

         String predicted_label = mlpClassifier.classify(row);
         correctnessCount += (predicted_label.equals(row.categoricalTarget()) ? 1 : 0);

         if(i < 10) {
            System.out.println(row);
            System.out.println("predicted: " + predicted_label + "\tactual: " + row.categoricalTarget());
         }
      }

      System.out.println("Prediction Accuracy: "+(correctnessCount * 100 / dataFrame.rowCount()));
   }
}
