package pl.ishfid.psi;

import pl.ishfid.psi.Neuron.Neuron;
import pl.ishfid.psi.Neuron.NeuronFactory;

import java.util.ArrayList;

/**
 * Created by ishfi on 02.12.2016.
 */
class Layer {
    private ArrayList<Neuron> neurons = new ArrayList<Neuron>();
    private NeuronFactory factory;

    Layer(NeuronFactory factory) {
        this.factory = factory;
    }

    ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    void fillInputLayer(int amount){
        for (int i = 0; i < amount; ++i){
            neurons.add(factory.createNeuron(1, true));
        }
    }

    void fillLayer(int amount, int eachNeuronEntries){
        for (int i = 0; i < amount; ++i){
            neurons.add(factory.createNeuron(eachNeuronEntries));
        }
    }

    int getSize(){
        return neurons.size();
    }
}
