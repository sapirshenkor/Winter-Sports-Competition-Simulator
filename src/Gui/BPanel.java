package Gui;
import com.sun.org.apache.xerces.internal.xs.StringList;
import game.arena.IArena;
import game.arena.WinterArena;
import game.competition.Competitor;
import game.competition.SkiCompetition;
import game.competition.SnowboardCompetition;
import game.competition.WinterCompetition;

import javax.swing .*;
import java.awt .*;
import java.awt.event .*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.awt.image.BufferedImage;



public class BPanel {
    public final static String FRAME_TEXT = "Competition";
    private JFrame frame;
    private ActionHandler actionHandler;
    private JPanel mainPanel;
    private JTextField tf_arealength;
    private double arenalength;
    private JTextField tf_defaultmaxcomp;
    private JComboBox<String> surface_choices;
    private JComboBox<String> weather_choices;
    private JComboBox<String> competition_choices;
    private String compType;
    private JTextField tx_maxcompetitorNum;
    private int maxcompetitors;
    private JComboBox<String> session_choice;
    private JComboBox<String> disciple_choices;
    private JComboBox<String> league_choices;
    private JComboBox<String> gender_choices;
    private JComboBox<String> comp_to_copy;
    private JTextField tx_name;
    private JTextField tx_age;
    private JTextField tx_maxspeed;
    private JTextField tx_acceleration;
    private String selected_disciple;
    private String selected_league;
    private String selected_gender;
    private IArena arena;
    private SkiCompetition skiComp;
    private SnowboardCompetition snowboardComp;
    private JLayeredPane layerimg;
    private ImageIcon fieldIcon=null ;
    private Image fieldImage=null;
    private JLabel fieldLabel=null;
    private ArrayList<JLabel> competitorLabels;
    private boolean comp_start=false;
    private boolean comp_finished=false;
    private JTable infoTable;
    private DefaultTableModel tableModel;
    private ArrayList<Competitor> comparr;
    private Color chosen_icon_color=null;



    public BPanel(){
        frame = new JFrame(FRAME_TEXT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());
        actionHandler = new ActionHandler(this);
        arena=null;
        skiComp=null;
        snowboardComp=null;
        mainPanel=new JPanel();
        layerimg=new JLayeredPane();
        competitorLabels=new ArrayList<>();
        createInfoTable();
        comparr=new ArrayList<>();
        comp_to_copy=new JComboBox<>();
    }

    /**
     * Creates a panel for building an arena.
     *
     * This method initializes and arranges components for building an arena.
     * The panel includes fields for area length, snow surface type, and weather condition,
     * along with a button to build the arena. The components are added to a JPanel
     * with a GridLayout.
     *
     * @return A JPanel containing the components for building an arena.
     */
    public Component CreateBuildArena(){
        JLabel l_buildarena= new JLabel("BUILD ARENA");
        JLabel l_session=new JLabel("choose session");
        session_choice=new JComboBox<>(new String[]{"Winter","Summer"});
        JLabel l_arealength=new JLabel("Area length");
        tf_arealength=new JTextField(String.valueOf(700));
        JLabel l_snowsurface=new JLabel("Snow surface");
        surface_choices= new JComboBox<>(new String[]{"POWDER", "CRUD", "ICE"});
        JLabel l_weather_condition=new JLabel("Weather condition");
        weather_choices=new JComboBox<>(new String[]{"SUNNY","CLOUDY","STORMY"});
        JButton b_buildarena=new JButton("Build Arena");
        //פונקציה עבור לחיצת הכפתור "בניית זירה"
        b_buildarena.addActionListener(actionHandler);

        JPanel pane=new JPanel();
        //pane.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        pane.setLayout(new GridLayout(0,1));
        pane.add(l_buildarena);
        pane.add(l_session);
        pane.add(session_choice);
        pane.add(l_arealength);
        pane.add(tf_arealength);
        pane.add(l_snowsurface);
        pane.add(surface_choices);
        pane.add(l_weather_condition);
        pane.add(weather_choices);
        pane.add(b_buildarena);
        return pane;
    }

    /**
     * Creates a panel for creating a competition.
     *
     * This method initializes and arranges components for creating a competition.
     * The panel includes fields for competition type, maximum competitor number,
     * discipline, league, and gender, along with a button to create the competition.
     * The components are added to a JPanel with a GridLayout.
     *
     * @return A JPanel containing the components for creating a competition.
     */
    public Component CreateCompetition(){
        JLabel l_createcompetition=new JLabel("CREATE COMPETITION");
        JLabel l_choosecompetition=new JLabel("Choose competition");
        competition_choices=new JComboBox<>(new String[]{"Ski","Snowboard"});
        JLabel l_maxcompetitorNum=new JLabel("max competitor number");
        tx_maxcompetitorNum=new JTextField(String.valueOf(10));
        JLabel l_discipline=new JLabel("Discipine");
        disciple_choices=new JComboBox<>(new String[]{"SLALOM","GIANT_SLALOM","DOWNHILL","FREESTYLE"});
        JLabel l_league=new JLabel("League");
        league_choices=new JComboBox<>(new String[]{"JUNIOR","ADULT","SENIOR"});
        JLabel l_gender=new JLabel("Gender");
        gender_choices=new JComboBox<>(new String[]{"MALE","FEMALE"});
        JButton b_createcompetition=new JButton("Create Competition");
        b_createcompetition.addActionListener(actionHandler);
        JLabel l_defaultmaxcomp=new JLabel("max competitiors default");
        tf_defaultmaxcomp=new JTextField(String.valueOf(10));
        JButton b_defaultcompetition=new JButton("Default SkiCompetition");
        b_defaultcompetition.addActionListener(actionHandler);
        //פונקציית עבור לחיצת כפתור "יצירת תחרות"
        JPanel pane=new JPanel();
        //pane.setBorder(BorderFactory.createEmptyBorder(1,30,1,30));
        pane.setLayout(new GridLayout(0,1));
        pane.add(l_createcompetition);
        pane.add(l_choosecompetition);
        pane.add(competition_choices);
        pane.add(l_maxcompetitorNum);
        pane.add(tx_maxcompetitorNum);
        pane.add(l_discipline);
        pane.add(disciple_choices);
        pane.add(l_league);
        pane.add(league_choices);
        pane.add(l_gender);
        pane.add(gender_choices);
        pane.add(b_createcompetition);
        pane.add(l_defaultmaxcomp);
        pane.add(tf_defaultmaxcomp);
        pane.add(b_defaultcompetition);
        return pane;
    }

    /**
     * Creates a panel for adding a competitor.
     * This method initializes and arranges components for adding a competitor.
     * The panel includes fields for the competitor's name, age, max speed,
     * and acceleration, along with a button to add the competitor. The components
     * are added to a JPanel with a GridLayout.
     * @return A JPanel containing the components for adding a competitor.
     */
    public Component CreateAddCompetitor(){
        JLabel l_addcompetitor=new JLabel("ADD COMPETITOR");
        JLabel l_name=new JLabel("Name");
        tx_name=new JTextField();
        JLabel l_age=new JLabel("Age");
        tx_age=new JTextField();
        JLabel l_maxspeed=new JLabel("Max speed");
        tx_maxspeed=new JTextField();
        JLabel l_accelaration=new JLabel("Accelaration");
        tx_acceleration=new JTextField();
        JButton b_addcompetitor=new JButton("Add Competitor");
        b_addcompetitor.addActionListener(actionHandler);
        JLabel l_allcompetitors=new JLabel("All active competitors");
        JButton b_copycompetitor=new JButton("Copy competitor");
        b_copycompetitor.addActionListener(actionHandler);
        JButton b_choosencolor=new JButton("Choose color");
        b_choosencolor.addActionListener(actionHandler);
        JButton b_decocompetitor=new JButton("Costume Competitor");
        b_decocompetitor.addActionListener(actionHandler);
        JPanel pane=new JPanel();
        //pane.setBorder(BorderFactory.createEmptyBorder(1,30,1,30));
        pane.setLayout(new GridLayout(0,1));
        pane.add(l_addcompetitor);
        pane.add(l_name);
        pane.add(tx_name);
        pane.add(l_age);
        pane.add(tx_age);
        pane.add(l_maxspeed);
        pane.add(tx_maxspeed);
        pane.add(l_accelaration);
        pane.add(tx_acceleration);
        pane.add(l_allcompetitors);
        pane.add(comp_to_copy);
        pane.add(b_choosencolor);
        pane.add(b_addcompetitor);
        pane.add(b_copycompetitor);
        pane.add(b_decocompetitor);
        return pane;
    }

    /**
     * Creates a panel for starting a competition and showing information.
     * This method initializes and arranges components for starting a competition
     * and showing information. The panel includes buttons for starting the competition
     * and showing information. The components are added to a JPanel with a GridLayout.
     * @return A JPanel containing the components for starting a competition and showing information.
     */
    public Component StartAndShowInfo(){
        JButton b_startcompetition=new JButton("Start Competition");
        JButton b_showinfo=new JButton("Show Info");
        b_startcompetition.addActionListener(actionHandler);
        b_showinfo.addActionListener(actionHandler);
        JPanel pane=new JPanel();
        //pane.setBorder(BorderFactory.createEmptyBorder(1,30,1,30));
        pane.setLayout(new GridLayout(0,1));
        pane.add(b_startcompetition);
        pane.add(b_showinfo);
        return pane;
    }


    /**
     * Runs the GUI for the competition.
     * This method initializes and arranges the main components of the GUI for the competition.
     * It includes panels for building an arena, creating a competition, adding a competitor,
     * and starting the competition. The components are added to a main panel with separators
     * and then to a JScrollPane. The frame is configured and made visible.
     * @return The JFrame containing the main GUI components for the competition.
     */
    public JFrame RunGuiCompetition(){
        Component buildArenaPanel = this.CreateBuildArena();
        Component createCompetitionPanel = this.CreateCompetition();
        Component addCompetitorPanel = this.CreateAddCompetitor();
        Component startandinfo=this.StartAndShowInfo();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(buildArenaPanel);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(createCompetitionPanel);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(addCompetitorPanel);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(startandinfo);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.add(scrollPane, BorderLayout.EAST);
        frame.pack();
        frame.setMinimumSize(new Dimension(1000, 700));
        frame.setLocationRelativeTo(null);  // Center the frame on screen
        frame.setVisible(true);
        return frame;
    }

    /**
     * Adds a layered pane to the frame.
     * This method removes all existing layers from the layered pane and adds
     * a new one, revalidating and repainting the frame afterwards.
     * @param l The JLayeredPane to be added to the frame.
     */
    public void add_layer_to_frame(JLayeredPane l){
        layerimg.removeAll();
        layerimg=l;
        layerimg.revalidate();
        layerimg.repaint();
        frame.add(layerimg,BorderLayout.CENTER);
        frame.setVisible(true);
    }

    /**
     * Restarts the competition.
     * This method resets the competition by nullifying the ski and snowboard
     * competition objects and clearing the list of competitor labels.
     */
    public void restart_comp(){
        skiComp=null;
        snowboardComp=null;
        comparr=null;
        for(JLabel l:competitorLabels){
            layerimg.remove(l);
            layerimg.revalidate();
            layerimg.repaint();
        }
        competitorLabels=new ArrayList<>();
    }
    /**
     * Creates the information table.
     * This method initializes the table model and the JTable for displaying
     * competitor information. The table is made non-editable.
     */
    private void createInfoTable() {
        String[] columnNames = {"Name", "Speed", "Max speed", "Location", "Finished","State"};
        tableModel = new DefaultTableModel(columnNames, 0);
        infoTable = new JTable(tableModel);
        infoTable.setEnabled(false); // Make the table non-editable
    }

    /**
     * Updates the competitors' information table.
     * This method clears the existing rows in the table and adds new rows
     * for finished and active competitors based on the current competition type.
     */
    public void updateCompetitors_tableInfo(){
        tableModel.setRowCount(0); // Clear existing rows
        ArrayList<Competitor> finishedCompetitors = new ArrayList<>();
        ArrayList<Competitor> activeCompetitors = new ArrayList<>();
        switch (compType){
            case "Ski":
                activeCompetitors=skiComp.getActiveCompetitors();
                finishedCompetitors=skiComp.getFinishedCompetitors();
                break;
            case "Snowboard":
                activeCompetitors=snowboardComp.getActiveCompetitors();
                finishedCompetitors=snowboardComp.getFinishedCompetitors();
                break;
        }
        Collections.sort(finishedCompetitors, Comparator.comparing(Competitor::getlocation,Comparator.reverseOrder()));
        // Add finished competitors first
        for (Competitor competitor : finishedCompetitors) {
            addCompetitorToTable(competitor);
        }
        Collections.sort(activeCompetitors, Comparator.comparing(Competitor::getlocation,Comparator.reverseOrder()));
        // Add active competitors
        for (Competitor competitor : activeCompetitors) {
            addCompetitorToTable(competitor);
        }
    }

    /**
     * Adds a competitor's information to the table.
     * This method takes a Competitor object and extracts its name, speed, max speed,
     * location (X coordinate), and finished status. The extracted data is formatted
     * and added as a new row to the table model.
     * @param competitor The Competitor object containing the information to be added to the table.
     */
    private void addCompetitorToTable(Competitor competitor) {
        Object[] rowData = {
            competitor.getName(),
            String.format("%.1f", competitor.getSpeed()),
            String.format("%.1f", competitor.getMaxSpeed()),
            String.format("%.1f", competitor.getLocation().getX()),
            competitor.isIsfinished() ? "Yes" : "No",
            competitor.alert()+"  "+competitor.getDestinyTime()
        };
        tableModel.addRow(rowData);
    }
    public void processArena(IArena a){
        arena=a;
    }
    public void processSkiCompetition(SkiCompetition s){
        skiComp=s;
    }
    public void processSnowboardCompetition(SnowboardCompetition s){
        snowboardComp=s;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTextField getTf_arealength() {
        return tf_arealength;
    }

    public JComboBox<String> getSurface_choices() {
        return surface_choices;
    }

    public JComboBox<String> getWeather_choices() {
        return weather_choices;
    }

    public JComboBox<String> getCompetition_choices() {
        return competition_choices;
    }

    public JTextField getTx_maxcompetitorNum() {
        return tx_maxcompetitorNum;
    }

    public JComboBox<String> getDisciple_choices() {
        return disciple_choices;
    }

    public JComboBox<String> getLeague_choices() {
        return league_choices;
    }

    public JComboBox<String> getGender_choices() {
        return gender_choices;
    }

    public JTextField getTx_name() {
        return tx_name;
    }

    public JTextField getTx_age() {
        return tx_age;
    }

    public JTextField getTx_acceleration() {
        return tx_acceleration;
    }


    public IArena getArena() {
        return arena;
    }

    public SkiCompetition getSkiComp() {
        return skiComp;
    }

    public SnowboardCompetition getSnowboardComp() {
        return snowboardComp;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JLayeredPane getLayerimg() {
        return layerimg;
    }

    public void setLayerimg(JLayeredPane layerimg) {
        this.layerimg = layerimg;
    }


    public JTextField getTx_maxspeed() {
        return tx_maxspeed;
    }

    public void setTx_maxspeed(JTextField tx_maxspeed) {
        this.tx_maxspeed = tx_maxspeed;
    }

    public String getSelected_disciple() {
        return selected_disciple;
    }

    public void setSelected_disciple(String selected_disciple) {
        this.selected_disciple = selected_disciple;
    }

    public String getSelected_league() {
        return selected_league;
    }

    public void setSelected_league(String selected_league) {
        this.selected_league = selected_league;
    }

    public String getSelected_gender() {
        return selected_gender;
    }

    public void setSelected_gender(String selected_gender) {
        this.selected_gender = selected_gender;
    }
    public boolean check_for_competition(){
        if(snowboardComp==null && skiComp==null){
            return false;
        }
        else {
            return true;
        }
    }

    public int getMaxcompetitors() {
        return maxcompetitors;
    }

    public void setMaxcompetitors(int maxcompetitors) {
        this.maxcompetitors = maxcompetitors;
    }

    public ArrayList<JLabel> getCompetitorLabels() {
        return competitorLabels;
    }

    public void setCompetitorLabels(ArrayList<JLabel> competitorLabels) {
        this.competitorLabels = competitorLabels;
    }
    public void addCompetitorIcon(JLabel c){
        competitorLabels.add(c);
    }
    public void changecompetitorIcon(int index,JLabel c){
        layerimg.remove(competitorLabels.get(index));
        layerimg.add(c,Integer.valueOf(1));
        layerimg.revalidate();
        layerimg.repaint();
        competitorLabels.set(index,c);
    }

    public String getCompType() {
        return compType;
    }

    public void setCompType(String compType) {
        this.compType = compType;
    }

    public ImageIcon getFieldIcon() {
        return fieldIcon;
    }

    public void setFieldIcon(ImageIcon fieldIcon) {
        this.fieldIcon = fieldIcon;
    }

    public Image getFieldImage() {
        return fieldImage;
    }

    public void setFieldImage(Image fieldImage) {
        this.fieldImage = fieldImage;
    }

    public JLabel getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(JLabel fieldLabel) {
        this.fieldLabel = fieldLabel;
    }


    public boolean isComp_start() {
        return comp_start;
    }

    public void setComp_start(boolean comp_start) {
        this.comp_start = comp_start;
    }

    public boolean isComp_finished() {
        return comp_finished;
    }

    public void setComp_finished(boolean comp_finished) {
        this.comp_finished = comp_finished;
    }

    public JTable getInfoTable() {
        return infoTable;
    }

    public void setInfoTable(JTable infoTable) {
        this.infoTable = infoTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public ArrayList<Competitor> getComparr() {
        return comparr;
    }

    public void setComparr(ArrayList<Competitor> comparr) {
        this.comparr = comparr;
    }

    public JComboBox<String> getComp_to_copy() {
        return comp_to_copy;
    }

    public void setComp_to_copy(String name) {
        comp_to_copy.addItem(name);
    }

    public Color getChosen_icon_color() {
        return chosen_icon_color;
    }

    public void setChosen_icon_color(Color chosen_icon_color) {
        this.chosen_icon_color = chosen_icon_color;
    }

    public JComboBox<String> getSession_choice() {
        return session_choice;
    }

    public void setSession_choice(JComboBox<String> session_choice) {
        this.session_choice = session_choice;
    }

    public JTextField getTf_defaultmaxcomp() {
        return tf_defaultmaxcomp;
    }

    public void setTf_defaultmaxcomp(JTextField tf_defaultmaxcomp) {
        this.tf_defaultmaxcomp = tf_defaultmaxcomp;
    }

    public double getArenalength() {
        return arenalength;
    }

    public void setArenalength(double arenalength) {
        this.arenalength = arenalength;
    }
}
