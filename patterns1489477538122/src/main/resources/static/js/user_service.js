'use strict';
 
App.factory('UserService', ['$http', '$q', function($http, $q){
 
    return {
    	
    fetchAllUsers: function() {
            //return $http.get('/albums')
            return $http.get('/artists')
            .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching artist details');
                        return $q.reject(errResponse);
                    }
            );
        },
    
   deleteArtist: function(artistid){
        return $http.delete('/deleteArtist?id='
            +artistid)
        .then(
                function(response){
                    //console.log(JSON.stringify(response.data));
                    console.log('deleted artist details successfully')
                    return response.data;
                    $route.reload();
                }, 
                function(errResponse){
                    console.error('Error while deleting artist');
                    return $q.reject(errResponse);
                    
                }
        );
    },
      AddArtist: function(user){
            return $http.post('/addArtist?artist='
                +user.artist+'&DOB='+user.dob+'&country='+user.country)
            .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while Adding artist');
                        return $q.reject(errResponse);
                    }
            );
        },
      
       UpdateArtist: function(id, user){
             return $http.put(  '/editArtist?id='+user.id+
                '&artist='+user.artist+'&DOB='+user.dob+'&country='+user.country)
            .then(
                    function(response){
                        return response.data;
                        console.log("updated response"+response.data);
                    }, 
                    function(errResponse){
                        console.error('Error while updating Artist');
                        return $q.reject(errResponse);
                    }
            );
        },

        fetchAlbums: function(selectedArtist) {
            //return $http.get('/albums')
            return $http.get('/albums?artist='+selectedArtist)
            .then(
                    function(response){
                        console.log("response.data",response.data)
                        return response.data;

                    }, 
                    function(errResponse){
                        console.error('Error while fetching album details');
                        return $q.reject(errResponse);
                    }
            );
        },
        UpdateAlbum:function(album, id){
             return $http.put(  '/editAlbum?id='+album.id+
                '&title='+album.title+'&artist='+album.artist+'&releaseYear='+album.releaseYear)
            .then(
                    function(response){
                        return response.data;
                        console.log("updated response"+response.data);
                    }, 
                    function(errResponse){
                        console.error('Error while updating Artist');
                        return $q.reject(errResponse);
                    }
            );
        },
         AddAlbum: function(album,selectedArtist){
            return $http.post('/addAlbum?title='
                +album.title+'&artist='+selectedArtist+'&releaseYear='+album.releaseYear)
            .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while Adding album');
                        return $q.reject(errResponse);
                    }
            );
        },
        deleteAlbum: function(artistid,artist){
        return $http.delete('/deletealbum?id='
            +artistid+'&artist='+artist)
        .then(
                function(response){
                    //console.log(JSON.stringify(response.data));
                    console.log('deleted album details successfully')
                    return response.data;
                    $route.reload();
                }, 
                function(errResponse){
                    console.error('Error while deleting album');
                    return $q.reject(errResponse);
                    
                }
        );
    }
         
    };
 
}]);
