# PSI (The basics of artificial intelligence)

### This program contains:

1. Digit Recognition using ENCOG library
    1. This problem uses ADALINE network (example)
2. Resolving XOR and OR problem using ENCOG library
    1. OR is processed using single layer network
    2. XOR uses multi layer network
3. Implementation of Neural Network, it contains:
    1. Data set creation from file
    2. McCullohPitts neuron model
    4. Perceptron model
    5. Implementation of single neuron
    6. Implementation of layer of neurons
    7. Implementation of network
        1. Input layer
        2. Hidden layers
        4. Output layers
        5. Feeding all layers with input informations
        6. Updating weights of all neurons with calculated signal error
    8. Implementation of various network learning 
    9. Data used for data recognition comes from:
        https://archive.ics.uci.edu/ml/datasets/Semeion+Handwritten+Digit
    
    
  
        
        
### Harmonogram:
1. Opracowanie zbioru danych do uczenia oraz walidacji
2. Implementacja pojedynczego neuronu
3. Implementacja Perceptronu
4. Implementacja modelu McCullohaPittsa
5. Implementacja warstw neuronów
6. Implementacja sieci neuronowej
7. Propagacja i obliczenia sygnału błędu nuronów
8. Aktualizowanie wag neuronów w całej sieci neuronowej
9. Implementacja reguły uczenia Hebba dla sieci
    1. Z nauczycielem 
    2. Bez nauczyciela (coś nie działa ¯\\\_(ツ)_/¯)
10. Implementacja reguły Oja dla sieci
    1. Z nauczycielem (podobnie jak u Hebb'a?)
    2. Bez nauczyciela (nie działa ¯\\\_(ツ)_/¯)
11. Stworzenie możliwości uruchamiania jednowarstwowych sieci (kompatybilne ze wszystkimi zaimplementowanymi regułami)
12. Uczenie WTA - coś słabo działa (spróbować uczyć sieć danymi odnośnie tylko danej liczby i zobaczyć jak się zachowa)
13. Próba rozpoznawania liczb sieciami SOM (Zauważalne zależności pomiędzy danymi liczbami)
99. Dalsze rozwijanie sieci neuronowej