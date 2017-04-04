package com.starterkit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.starterkit.domain.Album;
import com.starterkit.service.AlbumService;

/**
 * This AlbumController class for providing service to incoming client requests
 * 
 * @author pulkit.bajpai@cognizant.com
 */
@RestController
public class AlbumController {

	@Autowired
	private AlbumService albumService;



	/**
	 * This method maps to GET type of HTTP request with /albums URL. Get the
	 * list of tasks from DB by calling the getAlbumList service filtering on
	 * the basis of artist name. Send the album list as a response
	 * 
	 * @param artist
	 * @return albums
	 */
	@RequestMapping(value = "/albums", method = RequestMethod.GET)
	public @ResponseBody List<Album> listAlbums(@RequestParam(value = "artist") String artist) {
		if (albumService != null) {
			List<Album> albums = (List<Album>) albumService.getAlbumList(artist);
			return albums;
		} else {
			return new ArrayList<Album>();
		}

	}

	/**
	 * This method maps to GET type of HTTP request with /deletealbum URL. Get
	 * the inputs from user via requestparam and delete the task from DB by
	 * calling removeAlbum service. Send the album list as a response
	 * 
	 * @param id
	 * @param artist
	 * @return albums
	 */
	@RequestMapping(value = "/deletealbum", method = RequestMethod.DELETE)
	public @ResponseBody List<Album> deleteAlbum(@RequestParam(value = "id") String id,
			@RequestParam(value = "artist") String artistName) {
		if (albumService != null) {
			albumService.removeAlbum(id);
			List<Album> albums = albumService.getAlbumList(artistName);
			return albums;
		} else {
			return new ArrayList<Album>();
		}
	}

	/**
	 * This method maps to POST type of HTTP request with /addAlbum URL. Get the
	 * inputs from user via requestparam and save the album into DB by calling
	 * saveAlbum service. Send the album list as a response
	 * 
	 * @param title
	 * @param artist
	 * @param releaseYear
	 * @return albums
	 */
	@RequestMapping(value = "/addAlbum", method = RequestMethod.POST)
	public @ResponseBody List<Album> addAlbum(@RequestParam(value = "title") String title,
			@RequestParam(value = "artist") String artist, @RequestParam(value = "releaseYear") String releaseYear,@RequestParam(value = "emailID") String emailID) {
		if (albumService != null) {
			albumService.saveAlbum(title, artist, releaseYear, emailID);
			List<Album> albums = albumService.getAlbumList(artist);
			return albums;
		} else {
			return new ArrayList<Album>();
		}
	}

	/**
	 * This method maps to POST type of HTTP request with /editAlbum URL. Get
	 * the inputs from user via requestparam and update the task into DB by
	 * calling editAlbum service. Send the album list as a response
	 * 
	 * @param title
	 * @param artist
	 * @param releaseYear
	 * @param id
	 * @return albums
	 */
	@RequestMapping(value = "/editAlbum", method = RequestMethod.PUT)
	public @ResponseBody List<Album> editAlbum(@RequestParam(value = "id") String id,
			@RequestParam(value = "title") String title, @RequestParam(value = "artist") String artist,
			@RequestParam(value = "releaseYear") String releaseYear,@RequestParam(value = "emailID") String emailID) {
		if (albumService != null) {
			albumService.editAlbum(id, title, artist, releaseYear,emailID);
			List<Album> albums = albumService.getAlbumList(artist);
			return albums;
		} else {
			return new ArrayList<Album>();
		}
	}
}
