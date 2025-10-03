public class PremiumUser extends User {
    public PremiumUser(String name, int userId){
        super(name, userId);
    }

    @Override
    public String logIn(){
        if (connected == true){
            return "You're already connected";
        }    
        else{
            connected = true;
            return "You're now on your Premium User account";
        }
    }

    public String createPlaylist(String[] list){
        Playlist playlist = new Playlist();
        playlist.list = list;
        return "Your playlist has been created";
    }

    public String addToPlaylist(Song song, Playlist playlist){
        String[] newList = new String[playlist.list.length + 1];
        for (int i = 0; i < playlist.list.length; i++){
            newList[i] = playlist.list[i];
        }
        newList[newList.length - 1] = song.name;
        playlist.list = newList;
        return song.name + " has been added to your playlist";
    }

    public String removeFromPlaylist(Song song, Playlist playlist){
        String[] newList = new String[playlist.list.length - 1];
        int index = 0;
        for (int i = 0; i < playlist.list.length; i++){
            if (!playlist.list[i].equals(song.name)){
                newList[index++] = playlist.list[i];
            }
        }
        playlist.list = newList;
        return song.name + " has been removed from your playlist";
    }

    public String playPlaylist(Playlist playlist){
        StringBuilder songs = new StringBuilder("Playing playlist: ");
        for (String songName : playlist.list){
            songs.append(songName).append(", ");
        }
        return songs.toString();
    }

    public String downloadSong(Song song){
        return song.name + " has been downloaded";
    }
}