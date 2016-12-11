package pl.ishfid.psi;

import java.util.ArrayList;

/**
 * Created by ishfi on 02.12.2016.
 */
public class Neuron {
    protected double outputVal;
    protected double signalError;
    protected double adalineSignalError;
    protected double inputsSumVal;
    protected double targetVal;
    protected ArrayList<NeuronInput> inputs = new ArrayList<NeuronInput>();

    public Neuron(int inputCount) {
        for (int i = 0; i < inputCount; ++i) {
            this.inputs.add(new NeuronInput());
        }
    }

    public Neuron(int inputCount, boolean inInputLayer) {
        for (int i = 0; i < inputCount; ++i) {
            if (inInputLayer) {
                this.inputs.add(new NeuronInput(true));
            }else{
                this.inputs.add(new NeuronInput());
            }
        }
    }

    public void calcSignalError() {
        this.signalError = this.targetVal - this.outputVal;
    }

    public void calcAdalineSignalError(){
//        this.adalineSignalError = this.targetVal - this.calcSum();
        this.adalineSignalError = Math.pow(this.targetVal - this.outputVal, 2); //is it correct?
    }

    protected double derivativeFunction(){
        return 1;
    }

    public double calcSum() {
        this.inputsSumVal = 0.0;
        for (NeuronInput input: inputs) {
            this.inputsSumVal += input.getOutputVal();
        }
        return this.inputsSumVal;
    }

    public void calcOutput(){}

    public void updateWeights(){}

    public void updateWeights(double learningRate){}

    public double getOutputVal() {
        return outputVal;
    }

    public double getSignalError() {
        return signalError;
    }

    public double getInputsSumVal() {
        return inputsSumVal;
    }

    public double getTargetVal() {
        return targetVal;
    }

    public ArrayList<NeuronInput> getInputs() {
        return inputs;
    }

    public void setOutputVal(double outputVal) {
        this.outputVal = outputVal;
    }

    public void setSignalError(double signalError) {
        this.signalError = signalError;
    }

    public void setTargetVal(double targetVal) {
        this.targetVal = targetVal;
    }

    public double getAdalineSignalError() {
        return adalineSignalError;
    }

    public void setAdalineSignalError(double adalineSignalError) {
        this.adalineSignalError = adalineSignalError;
    }
}