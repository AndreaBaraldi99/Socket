package es2;

public class Sala {
    
    private int posti;
    private String film;

    public Sala(String film){
        posti = (int)((Math.random()*200)+1);
        this.film = film;
    }

    public int prenotaPosti(int posti){
        if(posti > this.posti){
            return -1;
        }
        else{
            this.posti -= posti;
            return this.posti;
        }
    }

    public String getNomeFilm(){
        return film;
    }

    public int getPosti(){
        return posti;
    }
}
