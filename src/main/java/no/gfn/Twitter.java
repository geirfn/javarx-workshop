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


        System.out.println('b' +  66);


        //.map(Status::getText)
        //.filter(text -> text.contains("#java"))
        //.subscribe(s -> System.out.println(a));
    }

    public Observable<Status> twitterObservable() {
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
            twitterStream.sample();
        });
    }
}
