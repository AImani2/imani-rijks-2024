package imani.rijks;

import com.andrewoid.ApiKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RijksServiceTest {

    @Test
    public void page() {
        //given
        RijksService rijksService = new RijksServiceFactory().getService();
        ApiKey apiKey = new ApiKey();

        //When
        ArtObjects artObjects = rijksService.page(apiKey.get(), 1).blockingGet();

        //then
        ArtObject object = artObjects.artObjects[0];
        assertNotNull(object.title);
        assertNotNull(object.longTitle);
        assertNotNull(object.webImage);
        assertNotNull(object.webImage.url);
        assertNotNull(object.principalOrFirstMaker);
    }

    @Test
    public void query() {
        //given
        RijksService rijksService = new RijksServiceFactory().getService();
        ApiKey apiKey = new ApiKey();

        //When
        ArtObjects artObjects = rijksService.query(apiKey.get(), 1, "p").blockingGet();

        //then
        ArtObject object = artObjects.artObjects[0];
        assertNotNull(object.title);
        assertNotNull(object.longTitle);
        assertNotNull(object.webImage);
        assertNotNull(object.webImage.url);
        assertNotNull(object.principalOrFirstMaker);
    }

    @Test
    public void artist() {
        //given
        RijksService rijksService = new RijksServiceFactory().getService();
        ApiKey apiKey = new ApiKey();

        //When
        ArtObjects artObjects = rijksService.artist(apiKey.get(), 1, "Piero di Cosimo").blockingGet();

        //then
        ArtObject object = artObjects.artObjects[0];
        assertNotNull(object.title);
        assertNotNull(object.longTitle);
        assertNotNull(object.webImage);
        assertNotNull(object.webImage.url);
        assertNotNull(object.principalOrFirstMaker);
    }
}
