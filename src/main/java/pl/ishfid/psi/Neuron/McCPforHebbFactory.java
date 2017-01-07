package pl.ishfid.psi.Neuron;

/**
 * Created by ishfi on 11.12.2016.
 */
public class McCPforHebbFactory implements NeuronFactory {
    @Override
    public Neuron createNeuron(int inputCount, boolean inInputLayer) {
        return new McCPforHebb(inputCount, inInputLayer);
    }

    @Override
    public Neuron createNeuron(int inputCount) {
        return new McCPforHebb(inputCount);
    }
}
