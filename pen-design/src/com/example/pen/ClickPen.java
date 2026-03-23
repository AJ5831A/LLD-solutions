package com.example.pen;

/**
 * Retractable ball-point pen – tip is toggled via a click mechanism.
 * @author Aryan Jakhar
 */
public class ClickPen implements WritingInstrument {

    private InkCartridge cartridge;
    private boolean tipOut;

    public ClickPen(InkCartridge cartridge) {
        this.cartridge = cartridge;
        this.tipOut = false;
    }

    @Override
    public int getInkConsumptionRate() {
        return 1;
    }

    @Override
    public boolean isReady() {
        return tipOut;
    }

    @Override
    public void uncap() {
        if (tipOut) {
            System.out.println("[Click] Tip is already out.");
            return;
        }
        tipOut = true;
        System.out.println("[Click] Tip pushed out. Writing with " + cartridge.getShade() + " ink.");
    }

    @Override
    public void scribe(String text) {
        if (!tipOut) {
            System.out.println("[Click] Tip is retracted – press uncap() first.");
            return;
        }
        if (cartridge.isDepleted()) {
            System.out.println("[Click] Ink exhausted. Cannot write.");
            return;
        }

        var required = text.length() * getInkConsumptionRate();
        var used = cartridge.useInk(required);

        var output = text.substring(0, used / getInkConsumptionRate());
        System.out.println("[Click] Wrote: \"" + output + "\" | " + cartridge);

        if (used < required) {
            System.out.println("[Click] Cartridge ran dry while writing!");
        }
    }

    @Override
    public void cap() {
        if (!tipOut) {
            System.out.println("[Click] Tip is already pushed in.");
            return;
        }
        tipOut = false;
        System.out.println("[Click] Tip pushed in.");
    }

    @Override
    public void replaceCartridge(InkCartridge cartridge) {
        this.cartridge = cartridge;
        System.out.println("[Click] Replaced cartridge: " + cartridge);
    }
}
