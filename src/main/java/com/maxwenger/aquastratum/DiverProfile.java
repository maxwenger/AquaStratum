package com.maxwenger.aquastratum;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class DiverProfile {

    private Minecraft mc;
    private ZHL16A computer;
    private double selectedGasFO2 = 0.21;
    private double selectedGasFN2 = 1 - selectedGasFO2;

    private double gfLow = 0.2;
    private double gfHigh = 0.85;

    public DiverProfile(Minecraft mc) {
        this.mc = mc;
        computer = new ZHL16A(selectedGasFO2, gfLow, gfHigh);
    }

    public boolean isDiving(){
        return getPlayer().isInWater() && getDepth() > 0;
    }

    public void RecomputeTissues(double deltaTime) {
        computer.RecomputeN2Compartments(selectedGasFN2, deltaTime);
    }

    public double getPPO2(){
        return getPPOG(selectedGasFO2);
    }

    public int getAltitude(){
        int seaLevel = 63;
        int yCord = getPlayer().getPosition().getY();
        return yCord - seaLevel;
    }
    public int getDepth(){

        int alt = getAltitude();
        if (alt < 0 && getPlayer().isInWater()) {
            return getAltitude() * -1;
        }

        return 0;
    }

    private double getPPOG(double fog) {
        int depth = getDepth();
        double ppog = fog;

        if (depth != 0) {
            ppog *= 1 + (depth/10);
        }

        return ppog;
    }

    private EntityPlayerSP getPlayer() {
        return mc.player;
    }
}
