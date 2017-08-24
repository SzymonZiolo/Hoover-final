package com.yoti.calculator;


final class VirtualHoover {

    private int roomWidth;
    private int roomHeight;
    private int currentX;
    private int currentY;

    public VirtualHoover(int roomWidth, int roomHeight, int currentX, int currentY) {
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        this.currentX = currentX;
        this.currentY = currentY;
    }


    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void moveAt(Character move) {
        switch (move) {
            case 'N':
                ++currentY;
                break;
            case 'E':
                ++currentX;
                break;
            case 'S':
                --currentY;
                break;
            case 'W':
                --currentX;
                break;
        }
        updateIfIsOverWall();
    }

    private void updateIfIsOverWall() {
        if (currentX >= roomWidth) {
            --currentX;
        } else if (currentX < 0) {
            ++currentX;
        } else if (currentY >= roomHeight) {
            --currentY;
        } else if (currentY < 0) {
            ++currentY;
        }
    }

}
