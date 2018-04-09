package fme.ibd;

import javax.servlet.annotation.WebServlet;

import com.exasol.jdbc.EXADriver;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TextField;
import org.apache.commons.beanutils.*;

import java.awt.*;


import java.sql.*;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    String SourceQuery;
    ResultSet rs;
    List<DynaBean> BeanList;
    Grid<DynaBean> grid;
    TextField filterText;
    CharSequence search;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        TextField filterText = new TextField();

        // DB Connection
        DBconnection con = new DBconnection();

        // Dynaclass Vorbereitung für Grid und Layout definieren
        RowSetDynaClass rsdc = null;
        final VerticalLayout layout = new VerticalLayout();
        MenuBar barmenu = new MenuBar();
        layout.addComponent(barmenu);

        layout.addComponent(filterText);


        // Menü erstellen
        layout.setComponentAlignment(barmenu, Alignment.TOP_CENTER);
        MenuBar.MenuItem management = barmenu.addItem("Management", null, null);
        MenuBar.MenuItem berichte = barmenu.addItem("Berichte", null, null);
        MenuBar.MenuItem administration = barmenu.addItem("Administration", null, null);

        // Menü Command
        // A feedback component
        final Label selection = new Label("-");
        layout.addComponent(selection);

        // Define a common menu command for all the menu items.
        MenuBar.Command mycommand = (MenuBar.Command) selectedItem -> selection.setValue("Ordered a " +
                selectedItem.getText() +
                " from menu.");


        // Untermenü erstellen
        management.addItem("Kunden", null, mycommand);
        management.addItem("Verbund", null, null);
        berichte.addItem("Generator", null, null);
        MenuBar.MenuItem statisch = berichte.addItem("Statische Berichte", null, null);
        statisch.addItem("nach Zeitraum", null, null);
        statisch.addItem("nach Status", null, null);
        statisch.addItem("nach Vertriebsleiter", null, null);
        statisch.addItem("nach Bundesland", null, null);
        statisch.addItem("nach Projektleiter", null, null);
        statisch.addItem("nach Aussendienst", null, null);
        statisch.addItem("nach Ansprechpatner", null, null);
        statisch.addItem("Wettbewerbsberichte", null, null);
        statisch.addItem("Wiedervorlagen", null, null);
        administration.addItem("neue Datenbank einlesen", null, null);
        administration.addItem("neuen Datensatz hinzufügen", null, null);
        MenuBar.MenuItem export = administration.addItem("Export", null, null);
        export.addItem("Tableau", null, null);

        // Create the query and add it to a result set
        SourceQuery = "SELECT * FROM \"Basis_Kunden\"";
        try {
            rs = con.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(SourceQuery);
            rsdc = new RowSetDynaClass(rs, false);
            rs.getStatement().close();
            rs.close();
        } catch (Exception e) {
            System.err.println("SQL ERROR");
            System.err.println(e.getMessage());
        }

        // TableView/Grid erstellen
        BeanList = rsdc.getRows();
        grid = new Grid("Kundendatenbank");
        grid.setSizeFull();
        if (BeanList.size() > 0)

        {
            DynaProperty[] propertys = BeanList.get(0).getDynaClass().getDynaProperties();
            for (DynaProperty property : propertys) {
                String col = property.getName();
                grid.addColumn((source) -> {
                    Object Value = (Object) source.get(col);
                    if (Value == null)
                        return "";
                    else
                        return Value;
                }).setCaption(col);
            }

            grid.setItems(BeanList);
            layout.addComponent(grid);


            // Suchfunktion
            /* filterText.setPlaceholder("filter by name...");
            filterText.addValueChangeListener(e -> updateList());
            filterText.setValueChangeMode(ValueChangeMode.LAZY);
            search = filterText.toString(); */

            setContent(layout);
        }
    }

    public void updateList() {

        if (BeanList.size() > 0)

        {
            DynaProperty[] propertys = BeanList.get(0).getDynaClass().getDynaProperties();
            for (DynaProperty property : propertys) {
                String col = property.getName();
                grid.addColumn((source) -> {
                    Object Value = (Object) source.get(col);
                    if (Value == null)
                        return "";
                    else {
                        if (Value.toString().contains(search))
                            return Value;
                        else return "";
                    }
                }).setCaption(col);
            }
        }
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
