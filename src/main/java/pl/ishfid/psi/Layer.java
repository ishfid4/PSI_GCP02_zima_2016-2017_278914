package pl.ishfid.psi;

import java.util.ArrayList;

/**
 * Created by ishfi on 02.12.2016.
 */
public class Layer {
    protected ArrayList<McCullohPitts> neurons = new ArrayList<McCullohPitts>();

    public ArrayList<McCullohPitts> getNeurons() {
        return neurons;
    }

    public void fillInputLayer(int amount){
        for (int i = 0; i < amount; ++i){
            neurons.add(new McCullohPitts(1, true));
        }
    }

    public void fillLayer(int amount, int eachNeuronEntries){
        for (int i = 0; i < amount; ++i){
            neurons.add(new McCullohPitts(eachNeuronEntries));
        }
    }

    public int getSize(){
        return neurons.size();
    }
}
