package pl.ishfid.psi;

/**
 * Created by ishfi on 11.12.2016.
 */
public class McCPforOjaFactory implements NeuronFactory {
    @Override
    public Neuron createNeuron(int inputCount, boolean inInputLayer) {
        return new McCPforOja(inputCount, inInputLayer);
    }

    @Override
    public Neuron createNeuron(int inputCount) {
        return new McCPforOja(inputCount);
    }
}
