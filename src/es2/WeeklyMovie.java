package es2;
public class WeeklyMovie {
    
    public String[] movieList = {"Il padrino", "Forrest Gump", "Schindler's List", 
    "Pulp Fiction", "Interstellar", "La vita è bella"};

    public String getMovieList(){
        String movies = "";
        for (String string : movieList) {
            movies += string + " ";
        }
        return movies;
    }
}
