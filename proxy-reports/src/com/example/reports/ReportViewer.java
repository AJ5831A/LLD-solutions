package com.example.reports;

/**
 * Client that opens reports via the Report abstraction (proxy).
 */
public class ReportViewer {

    public void open(Report report, User user) {
        report.display(user);
    }
}
