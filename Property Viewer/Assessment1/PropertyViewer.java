import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

public class PropertyViewer {
    
    private PropertyViewerGUI gui;
    private Portfolio portfolio;
    private int index;
    private int propertiesViewed;
    private int totalPrice;
    private Property currentProperty;
    public PropertyViewer() {
        gui = new PropertyViewerGUI(this);
        portfolio = new Portfolio("airbnb-london.csv");
        currentProperty = null;
        index = 0;
        propertiesViewed = 0;
        totalPrice = 0;
    }

    public void nextProperty() {
        index++;
        if (index >= portfolio.numberOfProperties()) {
            index = 0;
        }
        displayCurrentProperty();
    }

    public void previousProperty() {
        index--;
        if (index < 0) {
            index = portfolio.numberOfProperties() - 1;
        }
        displayCurrentProperty();
    }

    public void toggleFavourite() {
        if (index >= 0 && index < portfolio.numberOfProperties()) {
            Property currentProperty = portfolio.getProperty(index);
            currentProperty.toggleFavourite();
            gui.showFavourite(currentProperty);
        }
    }
    
    private void displayCurrentProperty() {
    if (index >= 0 && index < portfolio.numberOfProperties()) {
        currentProperty = portfolio.getProperty(index); // Update currentProperty
        gui.showProperty(currentProperty);
        gui.showID(currentProperty);
        gui.showFavourite(currentProperty);
        propertiesViewed++;
        totalPrice += currentProperty.getPrice();
    }
    }


    public void viewMaps()
    {
        if (currentProperty != null) {
        double latitude = currentProperty.getLatitude();
        double longitude = currentProperty.getLongitude();
        String url = "https://www.google.com/maps?q=" + latitude + "," + longitude;
        
        try {
            URI uri = new URI(url);
            if (uri.isAbsolute()) {
                Desktop.getDesktop().browse(uri);
            } else {
                System.out.println("Invalid URL: " + uri);
            }
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error opening the map: " + e.getMessage());
        }
        } else {
        System.out.println("No property selected for map view."); 
        }
    }

    
   
    public int getNumberOfPropertiesViewed() {
        return propertiesViewed;
    }

    public int averagePropertyPrice() {
        if (propertiesViewed > 0) {
            return totalPrice / propertiesViewed;
        } else {
            return 0;
        }
    }

 
}