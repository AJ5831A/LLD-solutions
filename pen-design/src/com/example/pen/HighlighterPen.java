package com.example.pen;

/**
 * Thick-tipped highlighter – consumes 3x ink per character and
 * does not support cartridge replacement (disposable body).
 * @author Aryan Jakhar
 */
public class HighlighterPen implements WritingInstrument {

    private final InkCartridge cartridge;
    private boolean uncapped;

    public HighlighterPen(InkCartridge cartridge) {
        this.cartridge = cartridge;
        this.uncapped = false;
    }

    @Override
    public int getInkConsumptionRate() {
        return 3;
    }

    @Override
    public boolean isReady() {
        return uncapped;
    }

    @Override
    public void uncap() {
        if (uncapped) {
            System.out.println("[Highlighter] Cap is already off.");
            return;
        }
        uncapped = true;
        System.out.println("[Highlighter] Cap removed. Highlighting with " + cartridge.getShade() + " ink.");
    }

    @Override
    public void scribe(String text) {
        if (!uncapped) {
            System.out.println("[Highlighter] Still capped – call uncap() first.");
            return;
        }
        if (cartridge.isDepleted()) {
            System.out.println("[Highlighter] Ink has run out. Cannot highlight.");
            return;
        }

        // highlighters have the broadest tip and therefore use the most ink
        var rate = getInkConsumptionRate();
        var required = text.length() * rate;
        var used = cartridge.useInk(required);
        var lettersHighlighted = used / rate;

        var output = text.substring(0, Math.min(lettersHighlighted, text.length()));
        System.out.println("[Highlighter] Wrote: \"" + output + "\" | " + cartridge);

        if (lettersHighlighted < text.length()) {
            System.out.println("[Highlighter] Ran dry while highlighting!");
        }
    }

    @Override
    public void cap() {
        if (!uncapped) {
            System.out.println("[Highlighter] Already capped.");
            return;
        }
        uncapped = false;
        System.out.println("[Highlighter] Cap put back on.");
    }

    /** Highlighters are disposable – cartridge swap is not allowed. */
    @Override
    public void replaceCartridge(InkCartridge cartridge) {
        throw new UnsupportedOperationException(
                "Highlighter pens are disposable; cartridge replacement is not supported.");
    }
}
