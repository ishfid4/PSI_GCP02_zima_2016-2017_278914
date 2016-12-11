package pl.ishfid.psi;

import static java.lang.StrictMath.*;

/**
 * Created by ishfi on 02.12.2016.
 */
public class McCullohPitts extends Neuron{
    private double learningRate = 0.005;     // TODO: make this dependant on  variable in Network class

    public McCullohPitts(int inputCount) {
        super(inputCount);
    }

    public McCullohPitts(int inputCount, boolean inInputLayer) {
        super(inputCount, inInputLayer);
    }

    @Override
    protected double derivativeFunction(){
        return (1 - this.getOutputVal()) * this.getOutputVal();
    }

    // Calculate output including activation function
    @Override
    public void calcOutput(){
//        double out = 1 + exp(-calcSum());
//        this.outputVal = (1 / out);

        //This activation function only works with Hebbs rule,
        //Probably its due to large output values from calcSum()
        double out = 1 + abs(calcSum());
        this.outputVal = 0.1 * (calcSum() / out);
    }

    @Override
    public void updateWeights(){
        for (NeuronInput input: this.getInputs()) {
            double newWeight = input.getWeight() + learningRate *
                    (this.targetVal - this.outputVal * derivativeFunction() * input.getInputVal());
            input.setWeight(newWeight);
        }
    }
}
