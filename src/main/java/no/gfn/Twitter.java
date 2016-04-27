package no.gfn;

import rx.Observable;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.io.IOException;

import static java.util.Arrays.asList;

public class Twitter {

    public void fetchTweets() throws IOException {

        twitterObservable()
                .map(s -> asList(s.getHashtagEntities()))
                .subscribe(s -> s.forEach(v -> System.out.println(v.getText())));
    }

    private Observable<Status> twitterObservable() {
        return Observable.create(subscriber -> {
            final TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
            twitterStream.addListener(new StatusAdapter() {
                public void onStatus(Status status) {
                    subscriber.onNext(status);
                }

                public void onException(Exception ex) {
                    subscriber.onError(ex);
                }
            });
            // Starts listening on random sample of all public statuses. The default access level provides a small proportion of the Firehose.
            // The "Gardenhose" access level provides a proportion more suitable for data mining and research applications
            // that desire a larger proportion to be statistically significant sample.
            twitterStream.sample();
        });
    }
}