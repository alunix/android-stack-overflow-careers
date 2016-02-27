package me.urbanowicz.samuel.stackoverflowcareers.service;

import me.urbanowicz.samuel.stackoverflowcareers.domain.JobPostsFeed;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import rx.Observable;

public class JobPostService {
    private static final String BASE_FEED_URL =
            "https://api.import.io/store/connector/6986fa32-b64c-47d5-a1f4-3035affdf45b/_query?input=webpage/url:http://careers.stackoverflow.com/jobs?searchTerm=&&_apikey=a13becac060949f08d6eae26467ca0e595afcb544a7497c0f0ba97de1987897cec8d360b8e52f0738063ad9c7c8e1c180b8b2746e8a44f3a61185e532c4401a2c6798d70b2e0a0cdb663bf748db2f12d";

    private JobPostFeedService jobPostFeedService;

    public JobPostService() {
        jobPostFeedService =
                new Retrofit.Builder().baseUrl(BASE_FEED_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(JobPostFeedService.class);
    }

    public Observable<JobPostsFeed> getJobPostsFeedObservable() {
        return jobPostFeedService.getJobPosts();
    }
}