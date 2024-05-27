package imani.rijks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Flow;

public class RijksFrame extends JFrame {
    // need to create an object here which will be displayed?

    public RijksFrame() {
        setSize(800, 600);
        setTitle("Rijks Museum");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // is this the right layout?

        // have to create a display area which will display the pictures.

        // Create labels
        JButton prevButton = new JButton("Previous Page");
        JButton nextButton = new JButton("Next Page");

        // Create a search bar (JTextField)
        JTextField searchBar = new JTextField(30); // Set the preferred width

        // Add components to the content pane
        north.add(prevButton);
        north.add(searchBar);
        north.add(nextButton);

        // Add the panel to the frame
        add(north);

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

        RijksService service = new RijksServiceFactory().getService();

        // now i get if either there is no information in the search bar or there is
        // and display accordingly
        // i need to add actionlisteners to my search bar
        // and i need to add click listeners to my previous and next
    }

    public void update() {

    }

    public static void main(String[] args) {

        new RijksFrame().setVisible(true);
    }


    // need my display buttons

}
