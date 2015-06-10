package com.rebenko.gardenlikes.app;


public class AppConst {

	// Number of columns of Grid View
	// by default 2 but user can configure this in settings activity
	public static final int NUM_OF_COLUMNS = 3;

	// Gridview image padding
	public static final int GRID_PADDING = 4; // in dp

	// Gallery directory name to save wallpapers
	public static final String SDCARD_DIR_NAME = "Garden Likes";

	// Picasa/Google web album username
	public static final String PICASA_USER = "diy100ideas";

	// Public albums list url
	public static final String URL_PICASA_ALBUMS = "https://picasaweb.google.com/data/feed/api/user/_PICASA_USER_?kind=album&alt=json";

	// Picasa album photos url
	public static final String URL_ALBUM_PHOTOS = "https://picasaweb.google.com/data/feed/api/user/_PICASA_USER_/albumid/_ALBUM_ID_?alt=json";

	// Picasa recenlty added photos url
	public static final String URL_RECENTLY_ADDED = "https://picasaweb.google.com/data/feed/api/user/_PICASA_USER_?kind=photo&alt=json";

    // Admob unit ID
    public static final String ADD_IND_ID = "ca-app-pub-9595835527986323/9071399871";

    public static final String APP_NAME = "Garden Likes";
    public static final String APP_URL = "https://play.google.com/store/apps/details?id=com.rebenko.gardenlikes";
    public static final String APP_DEV_URL = "https://play.google.com/store/apps/developer?id=VeronicaR";
    public static final String MORE_IDEAS_URL = "http://homedesigndiy.com/";
    public static final String MORE_IDEAS_RUS_URL = "http://idealsad.com";

}