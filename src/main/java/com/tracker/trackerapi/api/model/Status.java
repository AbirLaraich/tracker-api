package com.tracker.trackerapi.api.model;


public enum Status {
        PENDING("Pending"),
        PROCESSED("Processed"),
        CANCELED("Canceled");

        private final String displayName;

        Status(String displayName) {
                this.displayName = displayName;
        }

        public String getDisplayName() {
                return displayName;
        }
}
