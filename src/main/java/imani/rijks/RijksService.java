package imani.rijks;

public interface RijksService {
    @GET("/api/en/collection")

    // acessing the info tourgh artobects - gettnig page query
    // and using the chart to find the things that I need
    @Query("name") Obect name,

    //then make a service factory and a web image class
    // doing the test for number query and artist
}
