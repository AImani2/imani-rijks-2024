package imani.rijks;

import com.andrewoid.ApiKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Flow;

public class RijksFrame extends JFrame {

    JButton prevButton = new JButton("Previous Page");
    JButton nextButton = new JButton("Next Page");
    JTextField searchBar = new JTextField(42); // Set the preferred width

    // create components for the pictures and add them to center

    ApiKey apiKey = new ApiKey();
    RijksService service = new RijksServiceFactory().getService();


    public RijksFrame() {
        setSize(800, 600);
        setTitle("Rijks Museum");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));

        north.add(prevButton);
        north.add(searchBar);
        north.add(nextButton);

        add(north);
        add(center);

        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                update();
                // code here that i want to happen
                // how do i go to the next page?
            }
        });


        prevButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                update();
                // code here that i want to happen
                // how do i go to the next page
            }
        });

        searchBar.addActionListener(e -> {
            update();
        });


        // now i get if either there is no information in the search bar or there is
        // and display accordingly
        // i need to add actionlisteners to my search bar
        // and i need to add click listeners to my previous and next
    }

    public void update() {
        // have to create this method
        if(searchBar.getText() == null) {
            // what am i calling each time?

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