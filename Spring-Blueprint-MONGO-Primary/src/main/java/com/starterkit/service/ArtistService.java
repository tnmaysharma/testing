package com.starterkit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starterkit.domain.Artist;
import com.starterkit.repositories.JpaArtistRepository;

/**
 * This class handle all service calls
 * 
 * @author pulkit.bajpai@cognizant.com
 *
 */
@Service
public class ArtistService {

	@Autowired
	private JpaArtistRepository jpaArtistRepository;

	/**
	 * Get all the task from 'Album' Table by calling the findAll method
	 * 
	 * @return List of Albums
	 */
	public List<Artist> getArtistsList() {
		return (List<Artist>) jpaArtistRepository.findAll();
	}

	/**
	 * Remove the task from 'Album' table by calling delete method
	 * 
	 * @param artistName
	 */
	public void removeArtist(String id) {
		jpaArtistRepository.delete(id);
	}

	/**
	 * Save new task into 'Album' table by calling the save method
	 * 
	 * @param artistName
	 * @param DOB
	 * @param country
	 */
	public void saveArtist(String artistName, String DOB, String country) {
		Artist artist = new Artist(artistName, DOB, country);
		jpaArtistRepository.save(artist);

	}

	/**
	 * Updated the album into 'Album' table by saving the album based on album
	 * artistName
	 * 
	 * @param id
	 * @param artistName
	 * @param DOB
	 * @param country
	 */
	public void editArtist(String id, String artistName, String DOB, String country) {
		Artist artist = new Artist(artistName, DOB, country);
		artist.setId(id);
		jpaArtistRepository.save(artist);
	}
}
