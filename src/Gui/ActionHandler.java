package Gui;
import game.arena.ArenaFactory;
import game.arena.IArena;
import game.arena.WinterArena;
import game.competition.*;
import game.entities.sportsman.Skier;
import game.entities.sportsman.Snowboarder;
import game.entities.sportsman.WinterSportsman;
import game.enumm.*;
import utilities.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ActionHandler implements ActionListener, PropertyChangeListener {
    private BPanel panel;
    private int competitorCount = 0;
    private double currentAcceleration = 5; // Default acceleration
    private Color selectedColor = null; // Default color


    public ActionHandler(BPanel p) {
        panel = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
            switch (command) {
                case "Build Arena":
                    restarting_competition();
                    handleBuildArena();
                    break;
                case "Create Competition":
                    handleCreate_Competition();
                    break;
                case "Add Competitor":
                    handle_addcompetitor();
                    break;
                case "Start Competition":
                    handle_StratCompetition();
                    break;
                case "Show Info":
                    handle_ShowInfo();
                    break;
                case "Copy competitor":
                    handle_copycompetitor();
                    break;
                case "Choose color":
                    handle_choosen_color();
                    break;
                case "Default SkiCompetition":
                    handle_default_skicompetition();
                    break;
                case "Costume Competitor":
                    handle_costumecompetitor();
                    break;
            }
        } catch (IllegalArgumentException ex) {

        }

    }
    /**
     * Handles the "Build Arena" action.
     * Creates a new WinterArena based on user input and updates the UI accordingly.
     * Throws IllegalArgumentException if input values are invalid.
     */
    public void handleBuildArena() {
        try {
            double arenalength = Double.parseDouble(panel.getTf_arealength().getText());
            if (arenalength < 700 || arenalength > 900) {
                JOptionPane.showMessageDialog(null, "Invalid input values! please try again.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Invalid input values! please try again.");
            }
            String session=(String)panel.getSession_choice().getSelectedItem();
            if(session=="Summer"){
                JOptionPane.showMessageDialog(null, "We does not have summer competitors! please try again..", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("We does not have summer competitors! please try again.");
            }
            else {
                panel.setArenalength(arenalength);
                String snowsurface = (String) panel.getSurface_choices().getSelectedItem();
                String weather = (String) panel.getWeather_choices().getSelectedItem();
                ArenaFactory arena=new ArenaFactory();
                build_arena_panel();
                panel.processArena(arena.get_arena(session,arenalength, SnowSurface.valueOf(snowsurface), WeatherCondition.valueOf(weather)));
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace(System.out);
        }

    }
    /**
     * Handles the "Create Competition" action.
     * Creates a new WinterCompetition based on user input.
     * Throws IllegalArgumentException if arena is not built or input values are invalid.
     */
    public void handleCreate_Competition() {
        try {
            if (panel.getArena() == null) {
                JOptionPane.showMessageDialog(null, "please build arena,before creating competition .", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("please build arena,before creating competition ");
            }
            int maxCompetitorNum = Integer.parseInt(panel.getTx_maxcompetitorNum().getText());
            if (maxCompetitorNum < 1 || maxCompetitorNum > 20) {
                JOptionPane.showMessageDialog(null, "Invalid input values! please try again.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Invalid input values! please try again.");
            } else {
                if(panel.isComp_finished()|| !(panel.isComp_start())){
                        restarting_competition();
                        handleBuildArena();
                }
                panel.getComp_to_copy().removeAllItems();
                panel.setMaxcompetitors(maxCompetitorNum);
                String competitionType = (String) panel.getCompetition_choices().getSelectedItem();
                panel.setCompType(competitionType);
                String className = "game.competition." + competitionType + "Competition";
                String discipline = (String) panel.getDisciple_choices().getSelectedItem();
                panel.setSelected_disciple(discipline);
                String league = (String) panel.getLeague_choices().getSelectedItem();
                panel.setSelected_league(league);
                String gender = (String) panel.getGender_choices().getSelectedItem();
                panel.setSelected_gender(gender);

                // Load the class dynamically
                Class<?> clazz = Class.forName(className);
                // Get the constructor with parameters
                Constructor<?> constructor = clazz.getConstructor(WinterArena.class, int.class, Discipline.class, League.class, Gender.class);
                // Create a new instance using the constructor
                WinterCompetition competition = (WinterCompetition) constructor.newInstance(
                        panel.getArena(),
                        maxCompetitorNum,
                        Discipline.valueOf(discipline),
                        League.valueOf(league),
                        Gender.valueOf(gender)
                );

                // Process the competition
                if (competitionType.equals("Ski")) {
                    panel.processSkiCompetition((SkiCompetition) competition);
                } else if (competitionType.equals("Snowboard")) {
                    panel.processSnowboardCompetition((SnowboardCompetition) competition);
                }
                System.out.println(competition);
                //updateFieldSize();

            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace(System.out);
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public void handle_default_skicompetition(){
        restarting_competition();
        panel.getComp_to_copy().removeAllItems();
        SkiCompetitionBuilder skiCompetitionBuilder=new SkiCompetitionBuilder(Integer.parseInt(panel.getTf_defaultmaxcomp().getText()));
        skiCompetitionBuilder.BuildWinterArena();
        skiCompetitionBuilder.BuildLeague();
        skiCompetitionBuilder.BuildGender();
        skiCompetitionBuilder.BuildDiscipline();
        skiCompetitionBuilder.BuildMaxCompetitors();
        skiCompetitionBuilder.BuildCompetitorsArry();
        SkiCompetition skiComp=skiCompetitionBuilder.getSkicopm();
        panel.setSelected_gender(skiComp.getGender().name());
        panel.setMaxcompetitors(skiComp.getMaxCompetitors());
        panel.setSelected_disciple(skiComp.getDiscipline().name());
        panel.setSelected_league(skiComp.getLeague().name());
        panel.setCompType("Ski");
        panel.setArenalength(skiComp.getArena().getLength());
        panel.processArena(skiComp.getArena());
        panel.processSkiCompetition(skiComp);
        build_arena_panel();
        for(Competitor c:skiComp.getAllCompetitors()){
            c.addPropertyChangeListener(this);
            panel.setComp_to_copy(c.getName());
            add_comp_icon(null,skiComp.getAllCompetitors().indexOf(c));
            updateFieldSize();
        }
        panel.setComparr(skiComp.getAllCompetitors());
    }



    /**
     * Handles the "Add Competitor" action.
     * Adds a new competitor to the competition based on user input.
     * Throws IllegalArgumentException if competition is not created, input is invalid,
     * or if the competition has already started.
     */
    public void handle_addcompetitor() {
        try {
            if (!(panel.check_for_competition()) || panel.getArena() == null) {
                JOptionPane.showMessageDialog(null, "please build arena,create competition before adding competitors.", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("please build arena,create competition before adding competitors");
            }

            double comp_age = (double) Double.parseDouble(panel.getTx_age().getText());
            if (!(League.valueOf(panel.getSelected_league()).isInLeague(comp_age))) {
                JOptionPane.showMessageDialog(null, "Competitor does not fit to competition! Choose another competitor.", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Competitor does not fit to competition! Choose another competitor");
            }
            String comp_name = (String) panel.getTx_name().getText();
            if(panel.getTx_name().getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Competitor must have name!", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Competitor must have name!");
            }
            Double comp_maxspeed = (double) Double.parseDouble(panel.getTx_maxspeed().getText());
            Double comp_accelarition = (double) Double.parseDouble(panel.getTx_acceleration().getText());
            if(comp_accelarition<=0 || comp_maxspeed<=0){
                JOptionPane.showMessageDialog(null, "Acceleration and max speed must be over 0 !", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Acceleration and max speed must be over 0 !");
            }
            if (panel.getMaxcompetitors() <= panel.getCompetitorLabels().size()) {
                JOptionPane.showMessageDialog(null, "The competition is in full max!! .", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("The competition is in full max!! .");
            }
            if(panel.isComp_start()){
                JOptionPane.showMessageDialog(null, "The competition has already started.", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("The competition has already started!");
            }
            else {
                String competitionType = panel.getCompType();
                String className = "game.entities.sportsman." + (competitionType.equals("Ski") ? "Skier" : "Snowboarder");

                // Load the class dynamically
                Class<?> clazz = Class.forName(className);

                // Get the constructor with parameters
                Constructor<?> constructor = clazz.getConstructor(
                        String.class,
                        double.class,
                        Gender.class,
                        double.class,
                        double.class,
                        Discipline.class,
                        IArena.class
                );

                // Create a new instance using the constructor
                WinterSportsman competitor = (WinterSportsman) constructor.newInstance(
                        comp_name,
                        comp_age,
                        Gender.valueOf(panel.getSelected_gender()),
                        comp_accelarition,
                        comp_maxspeed,
                        Discipline.valueOf(panel.getSelected_disciple()),
                        panel.getArena()
                );

                // Add the competitor to the competition
                if (competitionType.equals("Ski")) {
                    panel.getSkiComp().addCompetitor((Skier) competitor);
                    panel.setComparr(panel.getSkiComp().getAllCompetitors());
                    panel.setComp_to_copy(competitor.getName());
                    competitor.addPropertyChangeListener(this);
                    add_comp_icon(panel.getChosen_icon_color(),competitorCount);
                    competitor.changecolor(panel.getChosen_icon_color());
                    System.out.println(competitor);
                } else if (competitionType.equals("Snowboard")) {
                    panel.getSnowboardComp().addCompetitor((Snowboarder) competitor);
                    panel.setComparr(panel.getSnowboardComp().getAllCompetitors());
                    panel.setComp_to_copy(competitor.getName());
                    competitor.addPropertyChangeListener(this);
                    add_comp_icon(panel.getChosen_icon_color(),competitorCount);
                    competitor.changecolor(panel.getChosen_icon_color());
                    System.out.println(panel.getSnowboardComp());
                    System.out.println(competitor);
                }
                updateFieldSize();
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace(System.out);
        }

    }
    public void handle_copycompetitor(){
        try {
            if (!(panel.check_for_competition()) || panel.getArena() == null) {
                JOptionPane.showMessageDialog(null, "please build arena,create competition before adding competitors.", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("please build arena,create competition before adding competitors");
            }
            if (panel.getMaxcompetitors() <= panel.getCompetitorLabels().size()) {
                JOptionPane.showMessageDialog(null, "The competition is in full max!! .", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("The competition is in full max!! .");
            }
            if(panel.isComp_start()){
                JOptionPane.showMessageDialog(null, "The competition has already started.", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("The competition has already started!");
            }
            if (panel.getComp_to_copy().getSelectedItem() != null) {
                switch (panel.getCompType()) {
                    case "Ski":
                        Skier orginalcomp = (Skier) panel.getSkiComp().getActiveCompetitors().get(panel.getComp_to_copy().getSelectedIndex());
                        Skier clonecomp = orginalcomp.clone();
                        panel.getSkiComp().addCompetitor(clonecomp);
                        panel.setComparr(panel.getSkiComp().getAllCompetitors());
                        clonecomp.addPropertyChangeListener(this);
                        add_comp_icon(panel.getChosen_icon_color(),competitorCount);
                        System.out.println(clonecomp);
                        updateFieldSize();
                        break;
                    case "Snowboard":
                        Snowboarder originalsnoowbordcomp=(Snowboarder)panel.getSnowboardComp().getAllCompetitors().get(panel.getComp_to_copy().getSelectedIndex());
                        Snowboarder clonecompsnowbord=originalsnoowbordcomp.clone();
                        panel.getSnowboardComp().addCompetitor(clonecompsnowbord);
                        panel.setComparr(panel.getSnowboardComp().getAllCompetitors());
                        clonecompsnowbord.addPropertyChangeListener(this);
                        add_comp_icon(panel.getChosen_icon_color(),competitorCount);
                        System.out.println(clonecompsnowbord);
                        updateFieldSize();
                        break;
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace(System.out);
        }
    }

    public void handle_costumecompetitor(){
       JButton changeAccelerationButton = new JButton("Change Acceleration");
       JButton chooseColorButton = new JButton("Choose Color");
       JComboBox<String> competitorBox = panel.getComp_to_copy();

       // Print the items in the JComboBox to verify their order
        for (int i = 0; i < competitorBox.getItemCount(); i++) {
            System.out.println("Item " + i + ": " + competitorBox.getItemAt(i));
        }

       // Add ActionListeners directly to buttons
        changeAccelerationButton.addActionListener(e -> {
            currentAcceleration = (currentAcceleration + 10); // Example logic to change acceleration
            System.out.println("acceleration set");
            JOptionPane.showMessageDialog(panel.getFrame(), "Acceleration set to " + currentAcceleration);
        });

        chooseColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(panel.getFrame(), "Choose Color", selectedColor);
            if (newColor != null) {
                selectedColor = newColor;
                System.out.println("color set");
                JOptionPane.showMessageDialog(panel.getFrame(), "Color selected: " + String.format("#%02x%02x%02x", selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue()));
            }
        });

       // Display the dialog with OK and Cancel buttons
        Object[] options = {"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(
                panel.getFrame(),
                new Object[]{
                        "Select Competitor:", competitorBox,
                        changeAccelerationButton,
                        chooseColorButton
                },
                "Customize Competitor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0] // Default to "OK"
        );
        if (option == JOptionPane.OK_OPTION) {
            int indexCompetitor = competitorBox.getSelectedIndex();
            IWinterSportsman winterSportsman = (WinterSportsman) panel.getComparr().get(indexCompetitor);

            // Apply the chosen decorations
            winterSportsman = new SpeedySportsman(winterSportsman); // Decorate with speed
            winterSportsman.change_Acceleration(currentAcceleration);

            winterSportsman = new ColoredSportsman(winterSportsman); // Decorate with color
            winterSportsman.changecolor(selectedColor);

            // Unwrap decorators to retrieve the original WinterSportsman
            IWinterSportsman originalSportsman = winterSportsman;
            while (originalSportsman instanceof WSDecorator) {
                originalSportsman = (IWinterSportsman) ((WSDecorator) originalSportsman).getDecoWinterSportsman();
            }

            // Set the decorated competitor back to the list
            panel.getComparr().set(indexCompetitor, (WinterSportsman) originalSportsman);
            update_customCompIcon(((WinterSportsman) originalSportsman).getColor(), indexCompetitor);
            System.out.println((WinterSportsman) originalSportsman);
            currentAcceleration=5;
            JOptionPane.showMessageDialog(panel.getFrame(), "Customization applied.");
        } else if (option == JOptionPane.CANCEL_OPTION) {
            // Handle cancel action
            JOptionPane.showMessageDialog(panel.getFrame(), "Customization canceled.");
        }
    }


    /**
     * Handles the "Start Competition" action.
     * Begins the competition if all conditions are met.
     * Throws IllegalArgumentException if competition is not set up properly or has already started/finished.
     */
    public void handle_StratCompetition() {
        try {
            if (!(panel.check_for_competition()) || panel.getArena() == null) {
                JOptionPane.showMessageDialog(null, "please build arena,create competition before adding competitors.", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("please build arena,create competition before starting competition");
            }
            if(panel.isComp_start()){
                JOptionPane.showMessageDialog(null, "The competition has already started.", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("The competition has already started!");
            }
            if(panel.isComp_finished()){
                JOptionPane.showMessageDialog(null, "The competition has already finished.", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("The competition has already finished!");
            }
            else {
                panel.setComp_start(true);
                switch (panel.getCompType()) {
                    case "Ski":
                        panel.setComparr(panel.getSkiComp().getAllCompetitors());
                        panel.getSkiComp().destiny();
                        for (Competitor c : panel.getSkiComp().getActiveCompetitors()) {
                            Thread thread_competitors = new Thread((Skier) c);
                            thread_competitors.start();
                        }
                        break;

                    case "Snowboard":
                        panel.setComparr(panel.getSnowboardComp().getAllCompetitors());
                        panel.getSnowboardComp().destiny();
                        for (Competitor c : panel.getSnowboardComp().getActiveCompetitors()) {
                            Thread thread_competitors = new Thread((Snowboarder) c);
                            thread_competitors.start();
                        }
                        break;
                }
                panel.setComp_finished(true);

            }
        }
        catch (IllegalArgumentException e){
            e.printStackTrace(System.out);
        }
    }

    /**
     * Handles the "Show Info" action.
     * Displays information about all competitors in a scrollable table.
     * Throws IllegalArgumentException if competition is not created.
     */
    public void handle_ShowInfo(){
        try {
            if (!(panel.check_for_competition())) {
                JOptionPane.showMessageDialog(null, "please create competition before pressing ShowInfo!.", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("please create competition before pressing ShowInfo!");
            }
            panel.updateCompetitors_tableInfo();
            JScrollPane scrollPane = new JScrollPane(panel.getInfoTable());
            JOptionPane.showMessageDialog(panel.getFrame(), scrollPane, "Competitors Information", JOptionPane.INFORMATION_MESSAGE);
        }catch (IllegalArgumentException e){
            e.printStackTrace(System.out);
        }
    }
    public void handle_choosen_color(){
        try {
            if (!(panel.check_for_competition()) || panel.getArena() == null) {
                JOptionPane.showMessageDialog(null, "please build arena,create competition before choosing color.", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("please build arena,create competition before choosing color");
            }
            if (panel.getMaxcompetitors() <= panel.getCompetitorLabels().size()) {
                JOptionPane.showMessageDialog(null, "The competition is in full max!! .", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("The competition is in full max!! .");
            }
            if(panel.isComp_start()){
                JOptionPane.showMessageDialog(null, "The competition has already started.", "ERROR", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("The competition has already started!");
            }
            else {
                // Open the color chooser dialog when the button is clicked
                Color color = JColorChooser.showDialog(null, "Choose a Color", Color.RED);
                if (color != null) {
                    panel.setChosen_icon_color(color);
                }
            }
        }catch (IllegalArgumentException e) {
            e.printStackTrace(System.out);
        }
    }

    public void add_comp_icon(Color color,int compCount){
        String iconPath = "C:/Users/Sapir Shenkor/IdeaProjects/HW1/src/Gui/icons/";
        // Select the correct icon based on competition type and gender
        if (panel.getCompType().equalsIgnoreCase("Ski")) {
            iconPath += panel.getSelected_gender().equalsIgnoreCase("Male") ? "SkiMale.png" : "SkiFemale.png";
        } else if (panel.getCompType().equalsIgnoreCase("Snowboard")) {
            iconPath += panel.getSelected_gender().equalsIgnoreCase("Male") ? "SnowboardMale.png" : "SnowboardFemale.png";

        }
        ImageIcon competitorIcon = new ImageIcon(iconPath);
        Image img = competitorIcon.getImage();
        if(color!=null) {
            // Convert the image to a BufferedImage for manipulation
            BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(img, 0, 0, null);
            g2d.dispose();

            // Paint the entire image with the chosen color
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    int rgba = bufferedImage.getRGB(x, y);
                    Color new_color = new Color(rgba, true);
                    // Only change non-transparent pixels
                    if (new_color.getAlpha() > 0) {
                        bufferedImage.setRGB(x, y, color.getRGB());
                    }
                }
            }

            // Resize the painted image
            Image resizedImg = bufferedImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            competitorIcon = new ImageIcon(resizedImg);
        }
        else{
           // Resize the painted image
            Image resizedImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            competitorIcon = new ImageIcon(resizedImg);
        }
        // Add the icon to the JLabel and place it on the panel
        JLabel competitorLabel = new JLabel(competitorIcon);
        competitorLabel.setBounds(10 + (compCount * 60), 10, 50, 50);

        panel.getLayerimg().add(competitorLabel, Integer.valueOf(1));
        panel.getLayerimg().revalidate();
        panel.getLayerimg().repaint();
        competitorCount++;
        panel.addCompetitorIcon(competitorLabel);
    }
    public void update_customCompIcon(Color color,int compCount){
        String iconPath = "C:/Users/Sapir Shenkor/IdeaProjects/HW1/src/Gui/icons/";
        // Select the correct icon based on competition type and gender
        if (panel.getCompType().equalsIgnoreCase("Ski")) {
            iconPath += panel.getSelected_gender().equalsIgnoreCase("Male") ? "SkiMale.png" : "SkiFemale.png";
        } else if (panel.getCompType().equalsIgnoreCase("Snowboard")) {
            iconPath += panel.getSelected_gender().equalsIgnoreCase("Male") ? "SnowboardMale.png" : "SnowboardFemale.png";

        }
        ImageIcon competitorIcon = new ImageIcon(iconPath);
        Image img = competitorIcon.getImage();
        if(color!=null) {
            // Convert the image to a BufferedImage for manipulation
            BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(img, 0, 0, null);
            g2d.dispose();

            // Paint the entire image with the chosen color
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    int rgba = bufferedImage.getRGB(x, y);
                    Color new_color = new Color(rgba, true);
                    // Only change non-transparent pixels
                    if (new_color.getAlpha() > 0) {
                        bufferedImage.setRGB(x, y, color.getRGB());
                    }
                }
            }

            // Resize the painted image
            Image resizedImg = bufferedImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            competitorIcon = new ImageIcon(resizedImg);
        }
        else{
           // Resize the painted image
            Image resizedImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            competitorIcon = new ImageIcon(resizedImg);
        }
        // Add the icon to the JLabel and place it on the panel
        JLabel competitorLabel = new JLabel(competitorIcon);
        competitorLabel.setBounds(10 + (compCount * 60), 10, 50, 50);

        panel.changecompetitorIcon(compCount,competitorLabel);
    }

    /**
     * Updates the size of the competition field based on the number of competitors.
     * Resizes the frame and adjusts the background image accordingly.
     */
    public void updateFieldSize() {
        int newWidth = panel.getFrame().getWidth();
        if (competitorCount == 12 || competitorCount==17) { // Adjust this threshold as needed
            newWidth += (panel.getMaxcompetitors()-10) * 60; // Increase width by 10 pixels for each extra competitor
            panel.getFrame().pack();
        }
        ImageIcon icon = panel.getFieldIcon();
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(newWidth, panel.getFrame().getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(resizedImg);
        panel.getFieldLabel().setIcon(icon);
        panel.getFieldLabel().setBounds(0, 0, newWidth, panel.getFrame().getHeight());
        panel.getLayerimg().setPreferredSize(new Dimension(newWidth, panel.getFrame().getHeight()));
        panel.getLayerimg().revalidate();
        panel.getLayerimg().repaint();
    }

    public void build_arena_panel(){
        panel.getFrame().setSize(1000,(int)panel.getArenalength());
        panel.getFrame().revalidate();
        panel.getFrame().repaint();
        String s[] = {"SUNNY", "CLOUDY", "STORMY"};
        JLabel imageLabel = new JLabel();
        JLayeredPane layerimg = new JLayeredPane();
        if (panel.getWeather_choices().getSelectedItem() == "SUNNY") {
            ImageIcon icon = new ImageIcon("C:/Users/Sapir Shenkor/IdeaProjects/HW1/src/Gui/icons/Sunny.jpg");
            panel.setFieldIcon(icon);
            Image img = icon.getImage();
            panel.setFieldImage(img);
            Image resizedImg = img.getScaledInstance(panel.getFrame().getWidth(), panel.getFrame().getHeight(), Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImg);
            layerimg.setPreferredSize(new Dimension(panel.getFrame().getWidth(), panel.getFrame().getHeight()));
            imageLabel = new JLabel(icon);
            imageLabel.setBounds(0, 0, panel.getFrame().getWidth(), panel.getFrame().getHeight());
            panel.setFieldLabel(imageLabel);
            layerimg.add(imageLabel, Integer.valueOf(0));
            panel.add_layer_to_frame(layerimg);
            System.out.println("SUNNY");
        }
        if (panel.getWeather_choices().getSelectedItem() == "CLOUDY") {
            ImageIcon icon = new ImageIcon("C:/Users/Sapir Shenkor/IdeaProjects/HW1/src/Gui/icons/Cloudy.jpg");
            panel.setFieldIcon(icon);
            Image img = icon.getImage();
            panel.setFieldImage(img);
            Image resizedImg = img.getScaledInstance(panel.getFrame().getWidth(), panel.getFrame().getHeight(), Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImg);
            layerimg.setPreferredSize(new Dimension(panel.getFrame().getWidth(), panel.getFrame().getHeight()));
            imageLabel = new JLabel(icon);
            imageLabel.setBounds(0, 0, panel.getFrame().getWidth(), panel.getFrame().getHeight());
            panel.setFieldLabel(imageLabel);
            layerimg.add(imageLabel, Integer.valueOf(0));
            panel.add_layer_to_frame(layerimg);
            System.out.println("CLOUDY");
        }
        if (panel.getWeather_choices().getSelectedItem() == "STORMY") {
            ImageIcon icon = new ImageIcon("C:/Users/Sapir Shenkor/IdeaProjects/HW1/src/Gui/icons/Stormy.jpg");
            panel.setFieldIcon(icon);
            Image img = icon.getImage();
            panel.setFieldImage(img);
            Image resizedImg = img.getScaledInstance(panel.getFrame().getWidth(), panel.getFrame().getHeight(), Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImg);
            layerimg.setPreferredSize(new Dimension(panel.getFrame().getWidth(), panel.getFrame().getHeight()));
            imageLabel = new JLabel(icon);
            imageLabel.setBounds(0, 0, panel.getFrame().getWidth(), panel.getFrame().getHeight());
            panel.setFieldLabel(imageLabel);
            layerimg.add(imageLabel, Integer.valueOf(0));
            panel.add_layer_to_frame(layerimg);
            System.out.println("STORMY");
        }
    }

    /**
     * Resets the competition state.
     * Clears competitor count and resets competition flags.
     */
    public void restarting_competition(){
        competitorCount=0;
        panel.setComp_start(false);
        panel.setComp_finished(false);
        panel.restart_comp();

    }

    /**
     * Handles property change events for competitor locations.
     * Updates the UI to reflect changes in competitor positions.
     *
     * @param evt The PropertyChangeEvent containing the updated location information.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(("location").equals(evt.getPropertyName())){
            if(evt.getOldValue()!=evt.getNewValue()) {
                for (int i = 0; i < panel.getComparr().size(); i++) {
                    if (panel.getComparr().get(i) == evt.getSource()) {
                        Point p = (Point) evt.getNewValue();
                        int labelWidth = 50; // Width of the competitor label/icon
                        int labelHeight = 50; // Height of the competitor label/icon
                        // Calculate x position based on competitor's location
                        int y = (int) (p.getX() * (panel.getLayerimg().getHeight() - labelHeight) / panel.getArena().getLength());

                        // Calculate y position (fixed for each competitor)
                        int x = (i * (labelWidth + 10)); // 10 pixels gap between competitors
                        panel.getCompetitorLabels().get(i).setLocation(x, y);
                        break;
                    }
                }
            }
        }
    }
}
