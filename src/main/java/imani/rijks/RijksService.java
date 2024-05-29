package imani.rijks;


import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RijksService {
    @GET("/api/en/collection")
    Single<ArtObjects> page(
                @Query("key") String key,
                @Query("p") int page
    );

    @GET("/api/en/collection")
    Single<ArtObjects> query(
            @Query("key") String key,
            @Query("p") int page,
            @Query("q") String query
    );

    @GET("/api/en/collection")
    Single<ArtObjects> artist(
            @Query("key") String key,
            @Query("p") int page,
            @Query("involvedMaker") String artist
    );

    // acessing the info tourgh artobects - gettnig page query
    // and using the chart to find the things that I need
    //then make a service factory and a web image class
    // doing the test for number query and artist
}
