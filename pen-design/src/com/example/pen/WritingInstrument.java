package com.example.pen;

/**
 * Contract for any writing instrument.
 * @author Aryan Jakhar
 */
public interface WritingInstrument {

    void uncap();

    void scribe(String text);

    void cap();

    void replaceCartridge(InkCartridge cartridge);

    boolean isReady();

    int getInkConsumptionRate();
}
