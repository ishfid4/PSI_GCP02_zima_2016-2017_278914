package pl.ishfid.psi.Neuron;

/**
 * Created by ishfi on 10.12.2016.
 */
public class McCullohPittsFactory implements NeuronFactory {
    @Override
    public Neuron createNeuron(int inputCount, boolean inInputLayer) {
        return new McCullohPitts(inputCount, inInputLayer);
    }

    @Override
    public Neuron createNeuron(int inputCount) {
        return new McCullohPitts(inputCount);
    }
}
