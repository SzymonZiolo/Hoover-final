package com.yoti.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class Request {

    @NotNull
    @Size(min = 2,max = 2)
    private List<Integer> roomSize;
    @Size(min = 2,max = 2)
    private List<Integer> coords;
    @NotNull
    private List<List<Integer>> patches;
    @Pattern(regexp = "[NESW]*")
    private String instructions;

    public Request() {
    }

    public List<Integer> getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(List<Integer> roomSize) {
        this.roomSize = roomSize;
    }

    public List<Integer> getCoords() {
        return coords;
    }

    public void setCoords(List<Integer> coords) {
        this.coords = coords;
    }

    public List<List<Integer>> getPatches() {
        return patches;
    }

    public void setPatches(List<List<Integer>> patches) {
        this.patches = patches;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
