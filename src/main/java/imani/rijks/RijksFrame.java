package imani.rijks;

import com.andrewoid.ApiKey;
import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.http.Url;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.Flow;

public class RijksFrame extends JFrame {

    JButton prevButton = new JButton("Previous Page");
    JButton nextButton = new JButton("Next Page");
    JTextField searchBar = new JTextField(42); // Set the preferred width
    int pageNum = 1;

    JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
    // create components for the pictures and add them to center

    ApiKey apiKey = new ApiKey();
    RijksService service = new RijksServiceFactory().getService();


    public RijksFrame() {
        setSize(800, 600);
        setTitle("Rijks Museum");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        north.add(prevButton);
        north.add(searchBar);
        north.add(nextButton);

        add(north);
        add(center);

        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                update();
            }
        });


        prevButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                update();
            }
        });

        searchBar.addActionListener(e -> {
            update();
        });

    }

    public void update() {
        // have to create this method
        if(searchBar.getText() == null) {
            if(prevButton.isEnabled()) {
                fetchAndDisplayPreviousPage();
            } else if (nextButton.isEnabled()){
                fetchAndDisplayNextPage();
            }
        } else {
            // do i take the page number into account here too?
            // should i reset page number to be 1?
            pageNum = 1;
            fetchAndDisplaySearch();
        }

    }

    public void fetchAndDisplayPreviousPage() {
        pageNum--;
        Disposable disposable = service.page(apiKey.get(), pageNum) // updates automatically according to the website
                // tells Rx to request the data on a background Thread
                .subscribeOn(Schedulers.io())
                // tells Rx to handle the response on Swing's main Thread
                .observeOn(SwingSchedulers.edt())
                //.observeOn(AndroidSchedulers.mainThread()) // Instead use this on Android only
                .subscribe(response -> handleResponse(response),
                        Throwable::printStackTrace);
    }

    public void fetchAndDisplayNextPage() {
        pageNum++;
        Disposable disposable = service.page(apiKey.get(), pageNum) // updates automatically according to the website
                // tells Rx to request the data on a background Thread
                .subscribeOn(Schedulers.io())
                // tells Rx to handle the response on Swing's main Thread
                .observeOn(SwingSchedulers.edt())
                //.observeOn(AndroidSchedulers.mainThread()) // Instead use this on Android only
                .subscribe(response -> handleResponse(response),
                        Throwable::printStackTrace);
    }

    public void fetchAndDisplaySearch() {
        // how do i know if the user just put in an artist? Do I call artist also?
        Disposable disposable = service.query(apiKey.get(), pageNum, searchBar.getText()) // updates automatically according to the website
                // tells Rx to request the data on a background Thread
                .subscribeOn(Schedulers.io())
                // tells Rx to handle the response on Swing's main Thread
                .observeOn(SwingSchedulers.edt())
                //.observeOn(AndroidSchedulers.mainThread()) // Instead use this on Android only
                .subscribe(response -> handleResponse(response),
                        Throwable::printStackTrace);
    }

    private void handleResponse(ArtObjects response) throws IOException {
        String[] listData = new String[response.artObjects.length];

        for (int i = 0; i < response.artObjects.length; i++) {
            ArtObject artObject = response.artObjects[i];
            URL url = URI.create(artObject.webImage.url).toURL();
            listData[i] = url + "" + artObject.title;
            Image image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(200, -1, Image.SCALE_DEFAULT);
            JLabel label = new JLabel();
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            label.setIcon(imageIcon);
            center.add(label);
        }
        //ArtObjects.setListData(listData);
        // maybe add a panel where i can add this to each time
    }

    public static void main(String[] args) {

        new RijksFrame().setVisible(true);
    }

}

// need a page count variable and i need to update that each time i hit prev/next page
// need an image panel for 10 images - 5 by 2
// then do a for loop and create a new artobject for every image
// then load the image in a for loop for all of this