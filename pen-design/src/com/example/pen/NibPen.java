package com.example.pen;

/**
 * Fountain pen with a broad nib – uses 2x ink per character.
 * @author Aryan Jakhar
 */
public class NibPen implements WritingInstrument {

    private InkCartridge cartridge;
    private boolean capRemoved;

    public NibPen(InkCartridge cartridge) {
        this.cartridge = cartridge;
        this.capRemoved = false;
    }

    @Override
    public int getInkConsumptionRate() {
        return 2;
    }

    @Override
    public boolean isReady() {
        return capRemoved;
    }

    @Override
    public void uncap() {
        if (capRemoved) {
            System.out.println("[Nib] Cap is already off.");
            return;
        }
        capRemoved = true;
        System.out.println("[Nib] Cap removed. Nib ready with " + cartridge.getShade() + " ink.");
    }

    @Override
    public void scribe(String text) {
        if (!capRemoved) {
            System.out.println("[Nib] Cap is on. Call uncap() before writing.");
            return;
        }
        if (cartridge.isDepleted()) {
            System.out.println("[Nib] Ink reservoir is empty. Unable to write.");
            return;
        }

        // broader nib means higher ink usage per letter
        var rate = getInkConsumptionRate();
        var required = text.length() * rate;
        var used = cartridge.useInk(required);
        var lettersWritten = used / rate;

        var output = text.substring(0, Math.min(lettersWritten, text.length()));
        System.out.println("[Nib] Wrote: \"" + output + "\" | " + cartridge);

        if (lettersWritten < text.length()) {
            System.out.println("[Nib] Ran out of ink mid-sentence!");
        }
    }

    @Override
    public void cap() {
        if (!capRemoved) {
            System.out.println("[Nib] Cap is already on.");
            return;
        }
        capRemoved = false;
        System.out.println("[Nib] Capped back on.");
    }

    /** Refills the current reservoir from an ink bottle instead of swapping cartridges. */
    @Override
    public void replaceCartridge(InkCartridge cartridge) {
        this.cartridge.restore();
        System.out.println("[Nib] Ink reservoir refilled from bottle. " + this.cartridge);
    }
}
