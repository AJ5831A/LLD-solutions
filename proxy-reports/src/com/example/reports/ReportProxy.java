package com.example.reports;

/**
 * Proxy that wraps a RealReport and adds:
 * - Access control (checks user role vs report classification)
 * - Lazy loading (RealReport is only created on first authorized display)
 * - Caching (same RealReport reused for repeated views through this proxy)
 */
public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl accessControl = new AccessControl();

    private RealReport cachedReport; // lazy-loaded and cached

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        // Access control check
        if (!accessControl.canAccess(user, classification)) {
            System.out.println("ACCESS DENIED: " + user.getName()
                    + " (" + user.getRole() + ") cannot view "
                    + classification + " report [" + reportId + "]");
            return;
        }

        // Lazy load + cache the real report
        if (cachedReport == null) {
            cachedReport = new RealReport(reportId, title, classification);
        } else {
            System.out.println("[cache] reusing already-loaded report " + reportId);
        }

        cachedReport.display(user);
    }
}
