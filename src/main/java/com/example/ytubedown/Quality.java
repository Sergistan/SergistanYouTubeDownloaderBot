package com.example.ytubedown;

public enum Quality {
    DEFAULT(0), FULL_HD(1), HD(2), MEDIUM(3), TINY(4);

    private final int quality;

    Quality(int quality) {
        this.quality = quality;
    }

    public int getQuality() {
        return quality;
    }
}
