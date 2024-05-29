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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.Flow;

public class RijksFrame extends JFrame {

    JButton prevButton = new JButton("Previous Page");
    JButton nextButton = new JButton("Next Page");
    JButton searchButton = new JButton("Search");
    JTextField searchBar = new JTextField(42); // Set the preferred width
    int pageNum = 1;

    JPanel north = new JPanel(new BorderLayout());
    JPanel center = new JPanel(new GridLayout(0, 5)); // why this?
    // create components for the pictures and add them to center

    ApiKey apiKey = new ApiKey();
    RijksService service = new RijksServiceFactory().getService();


    public RijksFrame() {
        setSize(800, 600);
        setTitle("Rijks Museum");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        north.add(prevButton, BorderLayout.WEST);
        north.add(searchBar, BorderLayout.CENTER);
        north.add(nextButton, BorderLayout.EAST);
        north.add(searchButton, BorderLayout.PAGE_END);



        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);


        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pageNum++;
                update();
            }
        });


        prevButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pageNum--;
                update();
            }
        });

        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                update();
            }
        });

}

    public void update() {
        if(searchBar.getText().isEmpty()) {
            fetchAndDisplayPage();
        } else {
            fetchAndDisplaySearch();
        }
    }

    public void fetchAndDisplayPage() {
        Disposable disposable = service.page(apiKey.get(), pageNum) // updates automatically according to the website
                // tells Rx to request the data on a background Thread
                .subscribeOn(Schedulers.io())
                // tells Rx to handle the response on Swing's main Thread
                .observeOn(SwingSchedulers.edt())
                //.observeOn(AndroidSchedulers.mainThread()) // Instead use this on Android only
                .subscribe(this::handleResponse,
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
                .subscribe(this::handleResponse,
                        Throwable::printStackTrace);
    }

    private void handleResponse(ArtObjects response) throws IOException {
        ArtObject[] artObjects = response.artObjects;

        center.setLayout(new GridLayout(2, 5));

        for (int i = 0; i < artObjects.length; i++) {

            ArtObject artObject = response.artObjects[i];
            String url = artObject.webImage.url;
            BufferedImage image = ImageIO.read(new File(url));
            Image scaledImage = image.getScaledInstance(200, -1, Image.SCALE_DEFAULT);
            JLabel label = new JLabel();
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            label.setIcon(imageIcon);
            center.add(label);
        }
    }



    public static void main(String[] args) {

        new RijksFrame().setVisible(true);
    }

}

// need a page count variable and i need to update that each time i hit prev/next page
// need an image panel for 10 images - 5 by 2
// then do a for loop and create a new artobject for every image
// then load the image in a for loop for all of this