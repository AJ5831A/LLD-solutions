package com.example.tickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable incident ticket created via Builder.
 * All fields are private final, no setters.
 * Tags list is defensively copied and returned as unmodifiable.
 */
public class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;
    private final String description;
    private final String priority;
    private final List<String> tags;
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;
    private final String source;

    private IncidentTicket(Builder builder) {
        this.id = builder.id;
        this.reporterEmail = builder.reporterEmail;
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.tags = Collections.unmodifiableList(new ArrayList<>(builder.tags));
        this.assigneeEmail = builder.assigneeEmail;
        this.customerVisible = builder.customerVisible;
        this.slaMinutes = builder.slaMinutes;
        this.source = builder.source;
    }

    public String getId() {
        return id;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public boolean isCustomerVisible() {
        return customerVisible;
    }

    public Integer getSlaMinutes() {
        return slaMinutes;
    }

    public String getSource() {
        return source;
    }

    public Builder toBuilder() {
        Builder b = new Builder(this.id, this.reporterEmail, this.title);
        b.description = this.description;
        b.priority = this.priority;
        b.tags = new ArrayList<>(this.tags);
        b.assigneeEmail = this.assigneeEmail;
        b.customerVisible = this.customerVisible;
        b.slaMinutes = this.slaMinutes;
        b.source = this.source;
        return b;
    }

    @Override
    public String toString() {
        return "IncidentTicket{"
                + "id='" + id + "'"
                + ", reporterEmail='" + reporterEmail + "'"
                + ", title='" + title + "'"
                + ", description='" + description + "'"
                + ", priority='" + priority + "'"
                + ", tags=" + tags
                + ", assigneeEmail='" + assigneeEmail + "'"
                + ", customerVisible=" + customerVisible
                + ", slaMinutes=" + slaMinutes
                + ", source='" + source + "'"
                + "}";
    }

    public static class Builder {
        private final String id;
        private final String reporterEmail;
        private final String title;

        String description;
        String priority;
        List<String> tags = new ArrayList<>();
        String assigneeEmail;
        boolean customerVisible;
        Integer slaMinutes;
        String source;

        public Builder(String id, String reporterEmail, String title) {
            this.id = id;
            this.reporterEmail = reporterEmail;
            this.title = title;
        }

        public Builder description(String val) {
            this.description = val;
            return this;
        }

        public Builder priority(String val) {
            this.priority = val;
            return this;
        }

        public Builder tags(List<String> val) {
            this.tags = new ArrayList<>(val);
            return this;
        }

        public Builder addTag(String val) {
            this.tags.add(val);
            return this;
        }

        public Builder assigneeEmail(String val) {
            this.assigneeEmail = val;
            return this;
        }

        public Builder customerVisible(boolean val) {
            this.customerVisible = val;
            return this;
        }

        public Builder slaMinutes(Integer val) {
            this.slaMinutes = val;
            return this;
        }

        public Builder source(String val) {
            this.source = val;
            return this;
        }

        public IncidentTicket build() {
            Validation.requireTicketId(id);
            Validation.requireEmail(reporterEmail, "reporterEmail");
            Validation.requireNonBlank(title, "title");
            Validation.requireMaxLen(title, 80, "title");
            Validation.requireOneOf(priority, "priority", "LOW", "MEDIUM", "HIGH", "CRITICAL");
            Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");
            if (assigneeEmail != null) {
                Validation.requireEmail(assigneeEmail, "assigneeEmail");
            }
            return new IncidentTicket(this);
        }
    }
}
