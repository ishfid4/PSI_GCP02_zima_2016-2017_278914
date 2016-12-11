package pl.ishfid.psi;

import java.util.ArrayList;

/**
 * Created by ishfi on 02.12.2016.
 */
public class Layer {
    protected ArrayList<Neuron> neurons = new ArrayList<Neuron>();
    private NeuronFactory factory;

    public Layer(NeuronFactory factory) {
        this.factory = factory;
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public void fillInputLayer(int amount){
        for (int i = 0; i < amount; ++i){
            neurons.add(factory.createNeuron(1, true));
        }
    }

    public void fillLayer(int amount, int eachNeuronEntries){
        for (int i = 0; i < amount; ++i){
            neurons.add(factory.createNeuron(eachNeuronEntries));
        }
    }

    public int getSize(){
        return neurons.size();
    }
}
