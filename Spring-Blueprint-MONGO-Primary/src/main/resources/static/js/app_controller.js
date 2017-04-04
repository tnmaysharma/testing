'use strict';

App.controller('AppController', [
		'$scope',
		'UserService',
		function($scope, UserService, $route, $routeParams, $location) {
			var self = this;
			self.user = {
				id : null,
				artist : '',
				dob : '',
				country:''
			};
			self.album = {
				id : null,
				title : '',
				releaseYear : '',
				artist:''
			};
			self.selectedArtist="";
			self.loading=false;
			self.AlbumAdd=false;
			self.users = [];
			self.albums=[];
			self.backupModel="";
			self.isEditMode=false;


			self.fetchAllUsers = function() {
				var title=document.getElementById("title");
				var dob=document.getElementById("artist");
				var country=document.getElementById("country");
				dob.style.color= "black";
				artist.style.color= "black";
				country.style.color= "black";
				self.loading = true; 
				UserService.fetchAllUsers().then(function(d) {
					self.users = d;
					$("#dob").val('');
					$("#artist").val('');
					$("#country").val('');
					if(self.users[0]){
						self.selectedArtist=self.users[0].artist;
					}
					self.fetchAlbums(self.selectedArtist);
					self.loading = false; 
				}, function(errResponse) {
					console.error('Error while fetching artists');
					self.loading = false; 
				});
			};

			self.fetchAllUsers();

			//only on add / delete fetching albums list-make a call for getting albums as well
			//coz aded/deleted user may the first user during add and last user during delete
			self.setvalues=function(d){
					self.users = d;
					$("#dob").val('');
					$("#artist").val('');
					$("#country").val('');
					if(self.users[0]){
						self.selectedArtist=self.users[0].artist;
					}
					self.fetchAlbums(self.selectedArtist);
					self.loading = false; 
			}


			self.deleteArtist = function(id) {
				var title=document.getElementById("title");
				var dob=document.getElementById("artist");
				var country=document.getElementById("country");
				dob.style.color= "black";
				artist.style.color= "black";
				country.style.color= "black";
				self.loading = true; 
				UserService.deleteArtist(id).then(function(d){
							self.setvalues(d);
							self.isEditMode=false;
						},
						function(errResponse) {
							self.loading = false; 
							self.isEditMode=false;
							console.error('Error while deleting artist details.');
						});
				
			};

			self.remove = function(id) {
				self.isEditMode=true;
				self.deleteArtist(id);
			};

			self.AddArtist = function(user) {

				if($("#dob").val()!==''&& $("#artist").val()!==''&&$("#country").val()!==''){
					var title=document.getElementById("title");
					var dob=document.getElementById("artist");
					var country=document.getElementById("country");
					dob.style.color= "black";
					artist.style.color= "black";
					country.style.color= "black";
					self.loading = true; 
					UserService.AddArtist(user).then(function(d){
							self.setvalues(d);

						},
						function(errResponse) {
							self.loading = false; 
							console.error('Error while adding artist.');
						});
				}

			};

			self.UpdateArtist = function( id,user) {
				var edit = document.getElementById("edit"+user.id);
				var save=document.getElementById("save"+user.id);
				var cancel=document.getElementById("cancel"+user.id);
				var inputdiv=document.getElementById("inputdiv"+user.id);
				var doblbl=document.getElementById("doblbl"+user.id);
				var artistlbl=document.getElementById("artistlbl"+user.id);
				var countrylbl=document.getElementById("countrylbl"+user.id);
				var remove=document.getElementById("remove"+user.id);

				
				save.style.display="none";
				cancel.style.display="none";
				doblbl.style.display="block";
				artistlbl.style.display="block";
				countrylbl.style.display="block";
				inputdiv.style.display="none";
				remove.style.display="block";
				edit.style.display="block";
				self.loading = true; 
				UserService.UpdateArtist( id,user).then(function(d){
							self.users = d;
							self.loading = false; 
						},
						function(errResponse) {
							self.loading = false; 						
							console.error('Error while updating User.');
						});
				self.isEditMode=false;
			};

			self.submit = function() {
				console.log('submit .....');
				console.log("user",self.user)
				if (self.user.id === null) {
					console.log('Saving New User', self.user, 
							self.user.artist);
					self.AddArtist(self.user);
				}
				
				
			};

			self.submitedit=function(){
					var id=self.user.id;
					console.log("entering update....");
					var edit = document.getElementById("edit"+id);
					console.log("title......."+self.users);
					self.UpdateArtist(id,self.user);
					console.log("Updated user"+JSON.stringify(self.user));
					console.log('User updated with id ', self.user.id);
					self.reset(self.user.id,self.user.title);
					self.isEditMode=false;
			}

			
			
			self.edit = function(id,artist) {
				self.isEditMode=true;
				//self.reset(self.user.id,self.user.artist);
				
				var edit = document.getElementById("edit"+id);
				var save=document.getElementById("save"+id);
				var cancel=document.getElementById("cancel"+id);
				var inputdiv=document.getElementById("inputdiv"+id);
				var doblbl=document.getElementById("doblbl"+id);
				var artistlbl=document.getElementById("artistlbl"+id);
				var countrylbl=document.getElementById("countrylbl"+id);
				var remove=document.getElementById("remove"+id);
				var dob=document.getElementById("dob");
				var artist=document.getElementById("artist");
				var country=document.getElementById("country");
				
				edit.style.display = "none";
				dob.style.color= "transparent";
				artist.style.color= "transparent";
				country.style.color= "transparent";
				save.style.display="block";
				cancel.style.display="block";
				doblbl.style.display="none";
				artistlbl.style.display="none";
				countrylbl.style.display="none";
				inputdiv.style.display="block";
				remove.style.display="none";
				console.log("edit"+title);
				
				
				var add = document.getElementById("add"+title);
				
				console.log('id to be edited', id);
				 for (var i = 0; i < self.users.length; i++) {
					console.log("selfuSERS",self.users[i].id,id)
					if (self.users[i].id === id) {
					   console.log("selfusers"+JSON.stringify(self.users[i]));
					   console.log("onlyid"+id);
						self.user = angular.extend(self.users[i]);
						break;
					}
				} 

			};

			

			self.reset = function(id,title) {

				var edit = document.getElementById("edit"+id);
				var save=document.getElementById("save"+id);
				var cancel=document.getElementById("cancel"+id);
				var inputdiv=document.getElementById("inputdiv"+id);
				console.log(inputdiv);
				var doblbl=document.getElementById("doblbl"+id);
				var artistlbl=document.getElementById("artistlbl"+id);
				var countrylbl=document.getElementById("countrylbl"+id);
				var remove=document.getElementById("remove"+id);
				
				edit.style.display="block";
				save.style.display="none";
				cancel.style.display="none";
				doblbl.style.display="block";
				artistlbl.style.display="block";
				countrylbl.style.display="block";
				inputdiv.style.display="none";
				remove.style.display="block";
				console.log("edit"+title);
				
				self.user = {
					id : null,
					artist : '',
					dob : '',
					country:''
				};
				
				dob.style.color= "black";
				artist.style.color= "black";
				country.style.color= "black";
				//self.fetchAllUsers();
			}

			self.cancel=function(){
				self.isEditMode=true;
				/*temp*/
				UserService.fetchAllUsers().then(function(d) {
					self.users = d;
					self.isEditMode=false;
				});
				self.reset(self.user.id,self.user.artist);
				
			}


			/* album services start*/

			self.fetchAlbums = function(selectedArtist) {
				var title=document.getElementById("title");
				var releaseyear=document.getElementById("releaseyear");
				title.style.color= "black";
				releaseyear.style.color= "black";
				self.loading = true; 
				UserService.fetchAlbums(selectedArtist).then(function(d) {
					console.log("fetch albums inside")
					self.albums = d;
					$("#title").val('');
					$("#releaseyear").val('');
					self.loading = false; 
				}, function(errResponse) {
					self.loading = false; 
					console.error('Error while fetching Albums');
				});
			};

			

			self.submiteditAlbum=function(){
					var id=self.album.title;
					console.log("entering album update....");
					var edit = document.getElementById("edit"+id);
					self.UpdateAlbum(self.album.id, self.album);
					console.log("Updated album"+JSON.stringify(self.album));
					console.log('User updated with id ', self.album.id);
					
			}

			self.editAlbum=function(id,title) {
				var editAlbum = document.getElementById("editAlbum"+id);
				var removeAlbum=document.getElementById("removeAlbum"+id);
				var inputdivalbum=document.getElementById("inputdivalbum"+id);

				var titlelbl=document.getElementById("titlelbl"+id);
				var releaseyearlbl=document.getElementById("releaseyearlbl"+id);
				var title=document.getElementById("title");
				var releaseyear=document.getElementById("releaseyear");
				var albumdivedit=document.getElementById("albumdivedit"+id);

				title.style.color= "transparent";
				releaseyear.style.color= "transparent";

				titlelbl.style.display="none";
				releaseyearlbl.style.display="none";

				editAlbum.style.display = "none";
				removeAlbum.style.display="none";
				inputdivalbum.style.display="block";
				albumdivedit.style.display="block";
		
				var add = document.getElementById("add"+title);
				
				console.log('id to be edited', id);
				 for (var i = 0; i < self.albums.length; i++) {
					if (self.albums[i].id === id) {
						self.album = angular.extend(self.albums[i]);
						break;
					}
				} 
				self.backupModel = angular.copy(self.album);
				console.log("now album is ",self.album)
			};

			self.UpdateAlbum = function( id,album) {
				

				var editAlbum = document.getElementById("editAlbum"+id);
				var removeAlbum=document.getElementById("removeAlbum"+id);
				var inputdivalbum=document.getElementById("inputdivalbum"+id);

				var titlelbl=document.getElementById("titlelbl"+id);
				var releaseyearlbl=document.getElementById("releaseyearlbl"+id);
				var albumdivedit=document.getElementById("albumdivedit"+id);

				titlelbl.style.display="block";
				releaseyearlbl.style.display="block";

				editAlbum.style.display = "block";
				removeAlbum.style.display="block";
				inputdivalbum.style.display="none";
				albumdivedit.style.display="none";

				self.loading = true; 
				UserService.UpdateAlbum( album,id).then(function(d) {
							console.log('edit album inside----------------------------',d);
							self.albums = d;
							
							self.loading = false;
							}, 
						function(errResponse) {
							self.loading = false; 						
							console.error('Error while updating album.');
						});
				self.resetAlbum(self.album.id,self.album.title);
				console.log("only after here..........")
			};

			self.deleteAlbum = function(id,artist) {
				var title=document.getElementById("title");
				var releaseyear=document.getElementById("releaseyear");
				title.style.color= "black";
				releaseyear.style.color= "black";
				self.loading = true; 
				UserService.deleteAlbum(id,artist).then(function(d){
						console.log('delete album inside');
							self.albums = d;
							//self.resetAlbum(self.album.id,self.album.title);
							self.loading = false; 
						},
						function(errResponse) {
							self.loading = false; 
							console.error('Error while deleting album details.');
						});

			};

			self.removeAlbum = function(id,artist) {
				self.deleteAlbum(id,artist);
			};

			self.submitAlbum = function() {
				console.log('submit .....');
				console.log("album",self.album)
				if (self.album.id === null) {
					console.log('Saving New album', self.album, 
							self.user.artist);
					self.AddAlbum(self.album);
				}

			};

			self.AddAlbum=function(album){
				if($("#title").val()!==''&& $("#releaseyear").val()!==''){	
					var title=document.getElementById("title");
					var releaseyear=document.getElementById("releaseyear");
					title.style.color= "black";
					releaseyear.style.color= "black";
					self.loading = true; 
					UserService.AddAlbum(album,self.selectedArtist).then(function(d){
								self.albums = d;
								$("#title").val('');
								$("#releaseyear").val('');
								self.loading = false; 
							},
							function(errResponse) {
								self.loading = false; 
								console.error('Error while adding album.');
							});
					self.AlbumAdd=false;
				}

			}

			self.resetAlbum = function(id,title) {
				console.log("id",id)
				var editAlbum = document.getElementById("editAlbum"+id);
				var removeAlbum=document.getElementById("removeAlbum"+id);
				var inputdivalbum=document.getElementById("inputdivalbum"+id);

				var titlelbl=document.getElementById("titlelbl"+id);
				var releaseyearlbl=document.getElementById("releaseyearlbl"+id);
				var albumdivedit=document.getElementById("albumdivedit"+id);
				var title=document.getElementById("title");
				var releaseyear=document.getElementById("releaseyear");

				
				editAlbum.style.display="block";
				albumdivedit.style.display="none";
				titlelbl.style.display="block";
				releaseyearlbl.style.display="block";
				inputdivalbum.style.display="none";
				removeAlbum.style.display="block";
				
				self.album = {
					id : null,
					title : '',
					releaseYear : '',
					artist:''
				};
				title.style.color= "black";
				releaseyear.style.color= "black";
				//self.fetchAlbums(self.selectedArtist);
				console.log("coming here..........")
			}

			self.addalbumclick=function(){
				self.AlbumAdd=true;
			}

			self.albumrowClick=function(id,artist){
				if(!self.isEditMode){
					var elClass="albumrowinner";
					var myElclass = angular.element( document.querySelectorAll( '.'+elClass ) );
					myElclass.removeClass('artistSelect');
					var elId="albumlistinner"+id;
					var myEl = angular.element( document.querySelector( '#'+elId ) );
					myEl.addClass('artistSelect');
					self.selectedArtist=artist;
					self.fetchAlbums(self.selectedArtist);
				}
			}

			self.cancelAlbum=function(id,title){
				/*temp*/
				UserService.fetchAlbums(self.selectedArtist).then(function(d) {
					console.log("fetch albums inside cancel")
					self.albums = d;
				});
				/* copy to retrieve back old value*/
				for (var i = 0; i < self.albums.length; i++) {
					if (self.albums[i].id === id) {
						console.log("this.abckupppppp",self.backupModel)
						self.albums[i]===self.backupModel;
						break;
					}
				} 
				self.resetAlbum(self.album.id,self.album.title);
			}

			/*album services end*/

		}]);
