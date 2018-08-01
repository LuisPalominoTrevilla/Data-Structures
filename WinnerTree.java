import java.util.Arrays;

public class WinnerTree {
    
    private double[] teams;
    private int n;
    
    public WinnerTree(){
        
    }
    
    public WinnerTree(double[] teams){
        this.newMatch(teams);
    }
    
    public void newMatch(double[] teams){
        this.teams = new double[((teams.length % 2 == 0)?teams.length:teams.length+1)];
        for(int i = 0; i < teams.length; i++){
            this.teams[i] = teams[i];
        }
        if(teams.length % 2 != 0){
            this.teams[this.teams.length-1] = Double.POSITIVE_INFINITY;
        }
        this.n = this.teams.length;
    }
    
    public double getMatchVictor(){
        while(this.n > 1){
            int pos = 0;
            for(int i = 0; i < this.n-1; i+=2){
                this.teams[pos++] = Math.min(this.teams[i], this.teams[i+1]);
            }

            this.n = this.n/2;
            if(n != 1 && n%2 != 0){
                this.teams[n] = Double.POSITIVE_INFINITY;
                n+=1;
            }
        }
        return this.teams[0];
    }
    
    public void sort(double[] teams){
        double[] teamCopy = teams.clone();
        int occupied = 0;
        while(occupied < teams.length){
            this.newMatch(teamCopy);
            double winner = this.getMatchVictor();
            teams[occupied] = winner;
            for(int i = 0; i < teamCopy.length; i++){
                if(teamCopy[i] == winner){
                    teamCopy[i] = Double.POSITIVE_INFINITY;     // Quitar el numero del arreglo
                    break;
                }
            }
            
            occupied++;
        }
    }
    
    public static void main(String[] args) {
        double[] num = {2, 3, 6, 7, 1, 9, 8, -1, 200, 8, 8};
        WinnerTree wt = new WinnerTree();
        wt.sort(num);
        System.out.println(Arrays.toString(num));
    }

}
