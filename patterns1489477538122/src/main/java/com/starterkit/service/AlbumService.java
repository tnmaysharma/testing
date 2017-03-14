/**
 * 
 */
package com.starterkit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starterkit.domain.Album;
import com.starterkit.repositories.JpaAlbumRepository;

/**
 * This class handle all service calls
 * 
 * @author pulkit.bajpai@cognizant.com
 *
 */
@Service
public class AlbumService {

	@Autowired
	private JpaAlbumRepository jpaAlbumRepository;

	/**
	 * Get all the task from 'Album' Table by searching on artistName basis.
	 * 
	 * @return List of Albums
	 */
	public List<Album> getAlbumList(String artist) {
		List<Album> albumlist = new ArrayList<Album>();
		for (Album newalbum : jpaAlbumRepository.findAll()) {
			if (newalbum.getArtist().equalsIgnoreCase(artist)) {
				albumlist.add(newalbum);
			}
		}
		return albumlist;
	}

	/**
	 * Remove the task from 'Album' table by calling delete method
	 * 
	 * @param id
	 */
	public void removeAlbum(String id) {
		jpaAlbumRepository.delete(id);
	}

	/**
	 * Save new task into 'Album' table by calling the save method
	 * 
	 * @param title
	 * @param artist
	 * @param releaseYear
	 */
	public void saveAlbum(String title, String artist, String releaseYear) {
		Album album = new Album(title, artist, releaseYear);
		jpaAlbumRepository.save(album);

	}

	/**
	 * Updated the album into 'Album' table by saving the album based on album
	 * title
	 * 
	 * @param id
	 * @param title
	 * @param artist
	 * @param releaseYear
	 */
	public void editAlbum(String id, String title, String artist, String releaseYear) {
		Album album = new Album(title, artist, releaseYear);
		album.setId(id);
		jpaAlbumRepository.save(album);

	}
}
