import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.net.URI;

import java.io.File;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * PropertyViewerGUI provides the GUI for the project. It displays the property
 * and strings and listens to button clicks.
 *
 * @author Michael Kölling, David J Barnes, and Josh Murphy
 * @version 3.0
 */
public class PropertyViewerGUI {
    private JFrame frame;
    private JPanel propertyPanel;
    private JLabel idLabel;
    private JLabel favouriteLabel;
    private JPanel toolbar;

    private JTextField hostIDLabel;
    private JTextField hostNameLabel;
    private JTextField neighbourhoodLabel;
    private JTextField roomTypeLabel;
    private JTextField priceLabel;
    private JTextField minNightsLabel;
    private JTextArea descriptionLabel;

    
    private PropertyViewer viewer;
    private boolean fixedSize;

    /**
     * Create a PropertyViewerGUI and display its GUI on the screen.
     */
    public PropertyViewerGUI(PropertyViewer viewer) {
        
        this.viewer = viewer;
        fixedSize = false;
        makeFrame();
        this.setPropertyViewSize(400, 250);
    }

    /**
     * Display a given property
     */
    public void showProperty(Property property) {
        hostIDLabel.setText(property.getHostID());
        hostNameLabel.setText(property.getHostName());
        neighbourhoodLabel.setText(property.getNeighbourhood());
        roomTypeLabel.setText(property.getRoomType());
        priceLabel.setText("£" + property.getPrice());
        minNightsLabel.setText(property.getMinNights());
        // descriptionLabel.setText(property.getDescription());
    }

    /**
     * Set a fixed size for the property display. If set, this size will be used for all properties.
     * If not set, the GUI will resize for each property.
     */
    public void setPropertyViewSize(int width, int height) {
        propertyPanel.setPreferredSize(new Dimension(width, height));
        frame.pack();
        fixedSize = true;
    }

    /**
     * Show a message in the status bar at the bottom of the screen.
     */
    public void showFavourite(Property property) {
        String favouriteText = " ";
        if (property.isFavourite()) {
            favouriteText += "This is one of your favourite properties!";
        }
        favouriteLabel.setText(favouriteText);
    }

    /**
     * Show the ID at the top of the screen.
     */
    public void showID(Property property) {
        idLabel.setText("Current Property ID: " + property.getID());
    }

    /**
     * Called when the 'Next' button is clicked.
     */
    private void nextButton() {
        viewer.nextProperty();
    }

    /**
     * Called when the 'Previous' button is clicked.
     */
    private void previousButton() {
        viewer.previousProperty();
    }
    
    /**
     * Called when the 'View on Maps' button is clicked.
     */
    
    /**
    * Called when the 'Toggle Favourite' button is clicked.
    */
    private void toggleFavouriteButton() {
        viewer.toggleFavourite();
    }

        public void viewOnMapsButton() {
        try{
         viewer.viewMaps();
        }
        catch(Exception e){
            System.out.println("URL INVALID");
        }
    }
    
    /**
     * Create and display the statistics dialog.
     */
    private void displayStatistics() {
        int numberOfPropertiesViewed = viewer.getNumberOfPropertiesViewed();
        int averagePropertyPrice = viewer.averagePropertyPrice();

        String message = "Number of Properties Viewed: " + numberOfPropertiesViewed + "\n"
                + "Average Property Price: £" + averagePropertyPrice;

        JOptionPane.showMessageDialog(frame, message, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame() {
        frame = new JFrame("Portfolio Viewer Application");
        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));

        contentPane.setLayout(new BorderLayout(6, 6));

        toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(0, 1));

        propertyPanel = new JPanel();
        propertyPanel.setLayout(new GridLayout(6, 2));

        propertyPanel.add(new JLabel("HostID: "));
        hostIDLabel = new JTextField("default");
        hostIDLabel.setEditable(false);
        propertyPanel.add(hostIDLabel);

        propertyPanel.add(new JLabel("Host Name: "));
        hostNameLabel = new JTextField("default");
        hostNameLabel.setEditable(false);
        propertyPanel.add(hostNameLabel);

        propertyPanel.add(new JLabel("Neighbourhood: "));
        neighbourhoodLabel = new JTextField("default");
        neighbourhoodLabel.setEditable(false);
        propertyPanel.add(neighbourhoodLabel);

        propertyPanel.add(new JLabel("Room type: "));
        roomTypeLabel = new JTextField("default");
        roomTypeLabel.setEditable(false);
        propertyPanel.add(roomTypeLabel);

        propertyPanel.add(new JLabel("Price: "));
        priceLabel = new JTextField("default");
        priceLabel.setEditable(false);
        propertyPanel.add(priceLabel);

        propertyPanel.add(new JLabel("Minimum nights: "));
        minNightsLabel = new JTextField("default");
        minNightsLabel.setEditable(false);
        propertyPanel.add(minNightsLabel);

        propertyPanel.setBorder(new EtchedBorder());
        contentPane.add(propertyPanel, BorderLayout.CENTER);

        idLabel = new JLabel("default");
        contentPane.add(idLabel, BorderLayout.NORTH);

        favouriteLabel = new JLabel(" ");
        contentPane.add(favouriteLabel, BorderLayout.SOUTH);

        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(0, 1));

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> nextButton());
        toolbar.add(nextButton);

        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(e -> previousButton());
        toolbar.add(previousButton);

        JButton mapButton = new JButton("View Property on Map");
        mapButton.addActionListener(e -> viewOnMapsButton());
        toolbar.add(mapButton);

        JButton favouriteButton = new JButton("Toggle Favourite");
        favouriteButton.addActionListener(e -> toggleFavouriteButton());
        toolbar.add(favouriteButton);

        JButton statisticsButton = new JButton("Statistics");
        statisticsButton.addActionListener(e -> displayStatistics());
        toolbar.add(statisticsButton);

        JPanel flow = new JPanel();
        flow.add(toolbar);

        contentPane.add(flow, BorderLayout.WEST);

        frame.pack();

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width / 2 - frame.getWidth() / 2, d.height / 2 - frame.getHeight() / 2);
        frame.setVisible(true);
    }
}