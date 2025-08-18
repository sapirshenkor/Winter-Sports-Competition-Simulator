//package game;
//
//import game.competition.Competition;
//
//public class GameEngine {
//    private static GameEngine instance=null;
//
//    //constructor
//    private GameEngine(){}
//
//    //return the singleton object
//    public static GameEngine getInstance(){
//        if (instance == null) {
//            instance = new GameEngine();
//
//        }
//        return instance;
//    }
//
//    //receives a race and starts it
//    public void startRace(Competition competition){
//        int count=0;
//        while (competition.hasActiveCompetitors()){
//            competition.playTurn();
//            count++;
//        }
//        System.out.println("race finished in "+ count +" steps");
//        this.printResults(competition);
//    }
//    public void printResults(Competition competition){
//        System.out.println("Race results:");
//        for (int i = 0; i < competition.getFinishedCompetitors().size(); i++) {
//            System.out.println(i+1+". "+competition.getFinishedCompetitors().get(i).getClass().getSimpleName() +"  "+ competition.getFinishedCompetitors().get(i).getName() );
//        }
//    }
//
//
//
//
//}
