package imani.rijks;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class RijksFrame extends JFrame {
    // need to create an object here which will be displayed?

    public RijksFrame() {
        setSize(800, 600);
        setTitle("Rijks Museum");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create labels
        JLabel leftLabel = new JLabel("Previous Page");
        JLabel rightLabel = new JLabel("Next Page");

        // Create a search bar (JTextField)
        JTextField searchBar = new JTextField(20); // Set the preferred width

        // Add components to the content pane
        north.add(leftLabel);
        north.add(searchBar);
        north.add(rightLabel);

        // Add the panel to the frame
        add(north);


        RijksService service = new RijksServiceFactory().getService();
    }

    public static void main(String[] args) {

        new RijksFrame().setVisible(true);
    }


    // need my display buttons

}
