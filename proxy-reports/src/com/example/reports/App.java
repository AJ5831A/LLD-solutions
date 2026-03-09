package com.example.reports;

/**
 * CampusVault demo — uses ReportProxy instead of direct ReportFile access.
 */
public class App {

    public static void main(String[] args) {
        User student = new User("Jasleen", "STUDENT");
        User faculty = new User("Prof. Noor", "FACULTY");
        User admin = new User("Kshitij", "ADMIN");

        Report publicReport = new ReportProxy("R-101", "Orientation Plan", "PUBLIC");
        Report facultyReport = new ReportProxy("R-202", "Midterm Review", "FACULTY");
        Report adminReport = new ReportProxy("R-303", "Budget Audit", "ADMIN");

        ReportViewer viewer = new ReportViewer();

        System.out.println("=== CampusVault Demo ===");

        viewer.open(publicReport, student); // Allowed (PUBLIC)
        System.out.println();

        viewer.open(facultyReport, student); // Denied (student can't view FACULTY)
        System.out.println();

        viewer.open(facultyReport, faculty); // Allowed (FACULTY)
        System.out.println();

        viewer.open(adminReport, admin); // Allowed (ADMIN) — first load
        System.out.println();

        viewer.open(adminReport, admin); // Allowed (ADMIN) — cached, no reload
    }
}
