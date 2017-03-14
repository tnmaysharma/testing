package com.starterkit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.starterkit.domain.Artist;
import com.starterkit.service.ArtistService;

/**
 * This AlbumController class for providing service to incoming client requests
 * 
 * @author pulkit.bajpai@cognizant.com
 */

@RestController
public class ArtistController {

	@Autowired
	private ArtistService artistService;
	/**
	 * Default Welcome page
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView welcome(Model model) {
		ModelAndView view = new ModelAndView();
		view.setViewName("welcome");
		return view;
	}

	/**
	 * This method maps to GET type of HTTP request with /artists URL. Get the
	 * list of tasks from DB by calling the getArtistsList service. Send the
	 * artist list as a response
	 * 
	 * @return artist
	 */
	@RequestMapping(value = "/artists", method = RequestMethod.GET)
	public  List<Artist> listArtists() {
		if (artistService != null) {
			List<Artist> artists = (List<Artist>) artistService.getArtistsList();
			return artists;
		} else {
			return new ArrayList<Artist>();
		}

	}

	/**
	 * This method maps to DELETE type of HTTP request with /deleteArtists URL.
	 * Get the inputs from user via requestparam and delete the task from DB by
	 * calling removeAlbum service. Send the album list as a response
	 * 
	 * @param id
	 * @return artist
	 */
	@RequestMapping(value = "/deleteArtist", method = RequestMethod.DELETE)
	public  List<Artist> deleteArtist(@RequestParam(value = "id") String id) {
		if (artistService != null) {
			artistService.removeArtist(id);
			List<Artist> artists = artistService.getArtistsList();
			return artists;
		} else {
			return new ArrayList<Artist>();
		}
	}

	/**
	 * This method maps to POST type of HTTP request with /addArtists URL. Get
	 * the inputs from user via requestparam and save the album into DB by
	 * calling saveArtist service. Send the artist list as a response
	 * 
	 * @param artist
	 * @param DOB
	 * @param country
	 * @return artist
	 */
	@RequestMapping(value = "/addArtist", method = RequestMethod.POST)
	public  List<Artist> addArtist(@RequestParam(value = "artist") String artistName,
			@RequestParam(value = "DOB") String DOB, @RequestParam(value = "country") String country) {
		if (artistService != null) {
			artistService.saveArtist(artistName, DOB, country);
			List<Artist> artists = artistService.getArtistsList();
			return artists;
		} else {
			return new ArrayList<Artist>();
		}
	}

	/**
	 * This method maps to PUT type of HTTP request with /editArtists URL. Get
	 * the inputs from user via requestparam and update the task into DB by
	 * calling editAlbum service. Send the album list as a response
	 * 
	 * @param id
	 * @param artist
	 * @param DOB
	 * @param country
	 * @return artist
	 */
	@RequestMapping(value = "/editArtist", method = RequestMethod.PUT)
	public  List<Artist> editArtists(@RequestParam(value = "id") String id,@RequestParam(value = "artist") String artistName,
			@RequestParam(value = "DOB") String DOB, @RequestParam(value = "country") String country) {
		if (artistService != null) {
			artistService.editArtist(id,artistName, DOB, country);
			List<Artist> artists = artistService.getArtistsList();
			return artists;
		} else {
			return new ArrayList<Artist>();
		}
	}
}