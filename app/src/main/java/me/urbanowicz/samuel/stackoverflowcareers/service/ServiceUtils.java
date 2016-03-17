package me.urbanowicz.samuel.stackoverflowcareers.service;

public class ServiceUtils {

    public static String getApiKey() {
        return "a13becac060949f08d6eae26467ca0e595afcb544a7497c0f0ba97de1987897cec8d360b8e52f0738063ad9c7c8e1c180b8b2746e8a44f3a61185e532c4401a2c6798d70b2e0a0cdb663bf748db2f12d";
    }

    public static String getUrlWithSearchQuery(
            String jobTitle,
            String location,
            int distance,
            String distanceUnits,
            boolean allowsRemote,
            boolean providesRelocation,
            boolean providesVisaSponsorship
    ) {
        return new StringBuilder("webpage/url:http://careers.stackoverflow.com/jobs?")
                .append("searchTerm=").append(jobTitle)
                .append("&location=").append(location)
                .append("&range=").append(distance)
                .append("&distanceUnits=").append(distanceUnits)
                .append("&allowsremote=").append(allowsRemote)
                .append("&offersrelocation=").append(providesRelocation)
                .append("&offersvisasponsorship=").append(providesVisaSponsorship)
                .toString();
    }

    public static class SearchQueryUrlBuilder {
        private String jobTitle;
        private String location;
        private int distance;
        private String distanceUnits;
        private boolean allowsRemote;
        private boolean providesRelocation;
        private boolean providesVisaSponsorship;

        public SearchQueryUrlBuilder addJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public SearchQueryUrlBuilder addLocation(String location) {
            this.location = location;
            return this;
        }

        public SearchQueryUrlBuilder addDistance(int distance) {
            this.distance = distance;
            return this;
        }

        public SearchQueryUrlBuilder addDistanceUnits(String distanceUnits) {
            this.distanceUnits = distanceUnits;
            return this;
        }

        public SearchQueryUrlBuilder addAllowsRemote(boolean allowsRemote) {
            this.allowsRemote = allowsRemote;
            return this;
        }

        public SearchQueryUrlBuilder addProvidesRelocation(boolean providesRelocation) {
            this.providesRelocation = providesRelocation;
            return this;
        }

        public SearchQueryUrlBuilder addProvidesVisaSponsorship(boolean providesVisaSponsorship) {
            this.providesVisaSponsorship = providesVisaSponsorship;
            return this;
        }

        @Override
        public String toString() {
            return getUrlWithSearchQuery(
                    jobTitle,
                    location,
                    distance,
                    distanceUnits,
                    allowsRemote,
                    providesRelocation,
                    providesVisaSponsorship
            );
        }
    }

    private ServiceUtils() {
    }

}
