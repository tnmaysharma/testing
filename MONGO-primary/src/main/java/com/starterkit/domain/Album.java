package com.starterkit.domain;

import org.springframework.data.annotation.Id;

public class Album
{
    @Id
    private String id;
    private String title;
    private String artist;
    private String releaseYear;
    private String emailID;

    public Album() {
    }

    /**
     * @param title
     * @param artist
     * @param releaseYear
     */
    public Album(String title, String artist, String releaseYear, String emailID) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.emailID = emailID;
    }

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @param artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * @return
     */
    public String getReleaseYear() {
        return releaseYear;
    }

    /**
     * @param releaseYear
     */
    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

	/**
	 * @return emailID
	 */
	public String getEmailID() {
		return emailID;
	}

	/**
	 * @param emailID
	 */
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

    
}
