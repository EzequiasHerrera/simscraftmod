package dev.naranjarra.needs;

//Constructor
public class PlayerNeeds {
    private float bladder;
    private float fun;
    private float social;
    private float energy;
    private float hygiene;
    private int hunger;

    //Initialization
    public PlayerNeeds(float bladder, float fun, float social, float energy, float hygiene, int hunger) {
        this.bladder = bladder;
        this.fun = fun;
        this.social = 20;
        this.energy = 20;
        this.hygiene = 20;
        this.hunger = 20;
    }

    // Getters
    public float bladder() { return bladder; }
    public float fun() { return fun; }
    public float social() { return social; }
    public float energy() { return energy; }
    public float hygiene() { return hygiene; }
    public int hunger() { return hunger; }

    // Actualización completa
    public void update(float bladder, float fun) {
        this.bladder = bladder;
        this.fun = fun;
        this.social = social;
        this.energy = energy;
        this.hygiene = hygiene;
        this.hunger = hunger;
    }
}
