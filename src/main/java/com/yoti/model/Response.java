package com.yoti.model;


import java.util.List;

public class Response {

    private List<Integer> coords;
    private int patches;

    public Response() {
    }

    public Response(List<Integer> coords, int patches) {
        this.coords = coords;
        this.patches = patches;
    }

    public List<Integer> getCoords() {
        return coords;
    }

    public void setCoords(List<Integer> coords) {
        this.coords = coords;
    }

    public int getPatches() {
        return patches;
    }

    public void setPatches(int patches) {
        this.patches = patches;
    }

}
