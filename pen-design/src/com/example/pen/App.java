package com.example.pen;

/**
 * Driver class demonstrating the three pen types.
 * @author Aryan Jakhar
 */
public class App {

    public static void main(String[] args) {

        // --- Retractable Click Pen ---
        System.out.println("=== Click Pen ===");
        var blueInk = new InkCartridge(Shade.BLUE, 20);
        WritingInstrument clickPen = new ClickPen(blueInk);

        clickPen.scribe("test");                    // tip still retracted
        clickPen.uncap();
        clickPen.scribe("Hello World");             // uses 11 ink
        clickPen.scribe("More text");               // uses 9 ink – depletes cartridge
        clickPen.scribe("nothing");                 // should report depletion

        // install a fresh cartridge and continue
        clickPen.replaceCartridge(new InkCartridge(Shade.BLACK, 20));
        clickPen.scribe("Back in action");
        clickPen.cap();

        // --- Fountain / Nib Pen ---
        System.out.println("\n=== Nib Pen ===");
        var darkInk = new InkCartridge(Shade.BLACK, 30);
        WritingInstrument nibPen = new NibPen(darkInk);

        nibPen.uncap();
        nibPen.scribe("Elegant");                   // 7 letters × 2 = 14 ink
        nibPen.scribe("Writing");                   // 7 letters × 2 = 14 ink
        nibPen.scribe("More");                      // 4 letters × 2 = 8 ink, only 2 left

        // refill from ink bottle (restores the existing reservoir)
        nibPen.replaceCartridge(null);
        nibPen.scribe("Refilled");
        nibPen.cap();

        // --- Disposable Highlighter ---
        System.out.println("\n=== Highlighter Pen ===");
        var redInk = new InkCartridge(Shade.RED, 30);
        WritingInstrument marker = new HighlighterPen(redInk);

        marker.uncap();
        marker.scribe("BOLD");                      // 4 letters × 3 = 12 ink
        marker.scribe("HEADING");                   // 7 letters × 3 = 21 ink, only 18 left

        // replacing a highlighter cartridge is not supported
        try {
            marker.replaceCartridge(new InkCartridge(Shade.RED, 30));
        } catch (UnsupportedOperationException ex) {
            System.out.println("[Highlighter] Swap failed: " + ex.getMessage());
        }

        marker.cap();
    }
}
