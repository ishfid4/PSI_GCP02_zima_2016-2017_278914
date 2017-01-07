package pl.ishfid.psi.Neuron;

import static java.lang.StrictMath.abs;

/**
 * Created by ishfi on 10.12.2016.
 */
public class McCPforHebb extends Neuron{
    public McCPforHebb(int inputCount) {
        super(inputCount);
    }

    public McCPforHebb(int inputCount, boolean inInputLayer) {
        super(inputCount, inInputLayer);
    }

    @Override
    public void calcOutput(){
        //This activation function only works with Hebbs rule,
        //Probably its due to large output values from calcSum()
        double out = 1 + abs(calcSum());
        this.outputVal = 0.1 * (calcSum() / out);
    }

    @Override
    public void updateWeights(double learningRate){
        for (NeuronInput input: this.getInputs()) {
            double deltaNewWeight = learningRate * input.getInputVal() * input.getOutputVal();
            input.setWeight(input.getWeight() + deltaNewWeight);
        }
    }
}
