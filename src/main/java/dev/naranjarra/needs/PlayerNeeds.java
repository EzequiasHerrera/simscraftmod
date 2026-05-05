package dev.naranjarra.needs;

import org.spongepowered.asm.mixin.Unique;

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
        this.social = social;
        this.energy = energy;
        this.hygiene = hygiene;
        this.hunger = hunger;
    }

    // Getters
    public float bladder() { return bladder; }
    public float fun() { return fun; }
    public float social() { return social; }
    public float energy() { return energy; }
    public float hygiene() { return hygiene; }
    public int hunger() { return hunger; }

    public void setBladder(float bladder) {
        this.bladder = bladder;
    }
    public void setFun(float fun) {
        this.fun = fun;
    }
    public void setSocial(float social) {
        this.social = social;
    }
    public void setEnergy(float energy) {
        this.energy = energy;
    }
    public void setHygiene(float hygiene) {
        this.hygiene = hygiene;
    }
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    // Actualización completa
    public void update(float bladder, float fun, float social, float energy, float hygiene, int hunger) {
        this.bladder = bladder;
        this.fun = fun;
        this.social = social;
        this.energy = energy;
        this.hygiene = hygiene;
        this.hunger = hunger;
    }
}
