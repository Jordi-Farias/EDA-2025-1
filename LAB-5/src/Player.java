public class Player {
    String Name;
    int Wins;
    int Draws;
    int Losses;

    Player(String Name) {
        this.Name = Name;
        this.Wins = 0;
        this.Draws = 0;
        this.Losses = 0;
    }

    public void addWins(){
        Wins++;
    }
    public void addDraws(){
        Draws++;
    }
    public void addLosses(){
        Losses++;
    }

    public float winRate(){
        float gamesPlayed = Wins+Draws+Losses;
        if(gamesPlayed!=0){
            float rate = Wins/gamesPlayed;
            return rate;
        }
        return 0;
    }

}
