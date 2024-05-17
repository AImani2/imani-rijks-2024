package imani.rijks;

public class RijksServiceFactory {
    public RijksService getService() {

        // use retrofit to give us the service
        // creating a builder and a retrofit object - will always be the same
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://earthquake.usgs.gov/")
                // Configure Retrofit to use Gson to turn the Json into Objects
                .addConverterFactory(GsonConverterFactory.create())
                // Configure Retrofit to use Rx
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        return retrofit.create(EarthquakeService.class);
    }
}
