import java.util;
import java.util.ArrayList;
import java.util.Arrays;

public class Playlist {
    public static void main(String[] args) {
        String[] favoriteSongs = {
            "Queen - Bohemian Rhapsody \n",
            "The Beatles - Hey Jude \n",
            "Led Zeppelin - Stairway to Heaven \n"
        };
        ArrayList<String> desertIslandPlaylist = new ArrayList<>();
        
        desertIslandPlaylist.add("Pink Floyd - Comfortably Numb \n");
        desertIslandPlaylist.add("David Bowie - Space Oddity \n");
        desertIslandPlaylist.add("The Rolling Stones - Paint It Black \n");
        desertIslandPlaylist.add("Nirvana - Smells Like Teen Spirit \n");
        desertIslandPlaylist.add("Bob Dylan - Like a Rolling Stone \n");
        
        desertIslandPlaylist.addAll(Arrays.asList(favoriteSongs)); 
               
        desertIslandPlaylist.remove(1);
        desertIslandPlaylist.remove(3);
        desertIslandPlaylist.remove(5);
        
        int SongA;
        int SongB;
        
        SongA = desertIslandPlaylist.indexOf("Nirvana - Smells Like Teen Spirit \n");
        SongB = desertIslandPlaylist.indexOf("Pink Floyd - Comfortably Numb ");
        
        System.out.println(SongA);
        System.out.println(SongB);
        
    }
    
}
