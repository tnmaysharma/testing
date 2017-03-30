package com.starterkit.domain;

import org.springframework.data.annotation.Id;

/**
 * This class is for entity creation
 * 
 * @author pulkit.bajpai@cognizant.com
 *
 */

public class Artist {

	
	@Id
	private String id;

	private String artist;
	private String DOB;
	private String country;

	/**
	 * @param artist
	 * @param DOB
	 * @param country
	 */
	public Artist(String artist, String DOB, String country) {
		this.artist = artist;
		this.DOB = DOB;
		this.country = country;
	}

//	/**
//	 * @param id
//	 * @param artist
//	 * @param DOB
//	 * @param country
//	 */
//	public Artist(Long id, String artist, String DOB, String country) {
//		this.artist = artist;
//		this.DOB = DOB;
//		this.country = country;
//		this.id = id;
//	}

	

	/**
	 * @return artist
	 */
	public String getArtist() {
		return artist;
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
	 * @param artist
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * @return DOB
	 */
	public String getDOB() {
		return DOB;
	}

	/**
	 * @param dOB
	 */
	public void setDOB(String dOB) {
		DOB = dOB;
	}

	/**
	 * @return country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Artist [id=" + id + ", artist=" + artist + ", DOB=" + DOB + ", country=" + country + "]";
	}

}
