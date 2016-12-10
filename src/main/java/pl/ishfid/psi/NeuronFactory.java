package pl.ishfid.psi;

/**
 * Created by ishfi on 10.12.2016.
 */
public interface NeuronFactory {
    Neuron createNeuron(int inputCount, boolean inInputLayer);
    Neuron createNeuron(int inputCount);
}
