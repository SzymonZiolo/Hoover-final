package com.yoti.calculator;


import com.yoti.model.Request;
import com.yoti.model.Response;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BasicHooverCalculator implements HooverCalculator {

    @Override
    public Response calculate(Request request) {
        VirtualHoover virtualHoover = createVirtualHoover(request);
        Set<Position> dirtyPatches = getAllDirtyPatches(request.getPatches());
        String instructions = request.getInstructions();
        int foundPatches = 0;
        for (char move : instructions.toCharArray()) {
            if (hasRemovedPatch(virtualHoover, dirtyPatches)) ++foundPatches;
            virtualHoover.moveAt(move);
        }
        if (hasRemovedPatch(virtualHoover, dirtyPatches)) ++foundPatches;
        return new Response(Arrays.asList(virtualHoover.getCurrentX(), virtualHoover.getCurrentY()), foundPatches);
    }

    private boolean hasRemovedPatch(VirtualHoover virtualHoover, Set<Position> dirtyPatches) {
        Position position = getCurrentPosition(virtualHoover);
        return dirtyPatches.remove(position);
    }


    private Set<Position> getAllDirtyPatches(List<List<Integer>> dirtyPatches) {
        return dirtyPatches.stream().map(list -> new Position(list.get(0), list.get(1))).collect(Collectors.toSet());
    }

    private VirtualHoover createVirtualHoover(Request request) {
        Integer width = request.getRoomSize().get(0);
        Integer height = request.getRoomSize().get(1);
        Integer initX = request.getCoords().get(0);
        Integer initY = request.getCoords().get(1);
        return new VirtualHoover(width, height, initX, initY);
    }

    private Position getCurrentPosition(VirtualHoover virtualHoover) {
        return new Position(virtualHoover.getCurrentX(), virtualHoover.getCurrentY());
    }
}
