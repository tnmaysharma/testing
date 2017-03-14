//Check the angular  exists or not
//Check the module myApp is defined or not

describe('angular', function () {
    it('should exist', function () {
        expect(window.angular).toBeDefined();
    });

    describe('module myApp', function () {
        var module;
        beforeEach(function () {
            module = angular.module('myApp');
        });
        it('should exist', function () {
            expect(module).toBeDefined();
        });
        describe('AppController', function () {
            it('should exist', function () {
                expect(module.controller('AppController')).toBeDefined();
            });
        });
    });
});

describe('userService tests', function (){
	  var UserService;
	  
	  // excuted before each "it" is run.
	  beforeEach(function (){
	    
	    // load the module.
	    module('myApp');
	    
	    // inject your service for testing.
	    // The _underscores_ are a convenience thing
	    // so you can have your variable name be the
	    // same as your injected service.
	    inject(function(_UserService_) {
	    	UserService = _UserService_;
	    });
	  });
	     
	  // check to see if it has the expected function
	  it('should have an fetchAllUsers function', function () { 
	    expect(angular.isFunction(UserService.fetchAllUsers)).toBe(true);
	  });
	  
	  it('should have an deleteArtist function', function () { 
		    expect(angular.isFunction(UserService.deleteArtist)).toBe(true);
		  });
	  it('should have an AddArtist function', function () { 
		    expect(angular.isFunction(UserService.AddArtist)).toBe(true);
		  });
	  it('should have an UpdateArtist function', function () { 
		    expect(angular.isFunction(UserService.UpdateArtist)).toBe(true);
		  });
	  it('should have an fetchAlbums function', function () { 
		    expect(angular.isFunction(UserService.fetchAlbums)).toBe(true);
		  });
	  it('should have an UpdateAlbum function', function () { 
		    expect(angular.isFunction(UserService.UpdateAlbum)).toBe(true);
		  });
	  it('should have an AddAlbum function', function () { 
		    expect(angular.isFunction(UserService.AddAlbum)).toBe(true);
		  });
	  it('should have an deleteAlbum function', function () { 
		    expect(angular.isFunction(UserService.deleteAlbum)).toBe(true);
		  });
	  
	
	});




describe('Album specs for crud operations', function () {
	  var UserService,
	      httpBackend;
	  
	  beforeEach(function (){  
	    // load the module.
	    module('myApp');
	    
	    // get your service, also get $httpBackend
	    // $httpBackend will be a mock, thanks to angular-mocks.js
	    inject(function($httpBackend, _UserService_) {
	    	UserService = _UserService_;      
	      httpBackend = $httpBackend;
	    });
	  });
	  
	  // make sure no expectations were missed in your tests.
	  // (e.g. expectGET or expectPOST)
	  afterEach(function() {
	    httpBackend.verifyNoOutstandingExpectation();
	    httpBackend.verifyNoOutstandingRequest();
	  });

	  it('get artists spec.', function (){
	    // set up some data for the http call to return and test later.
	    var returnData = { excited: true };
	    
	    // expectGET to make sure this is called once.
	    httpBackend.expectGET('http://primaryapp.apps.digifabric.cognizant.com/artists')
	    .respond(returnData);
	    //uncomment when full url not specified
	    //httpBackend.expectGET('/artists').respond(returnData);
	    
	    // make the call.
	    var returnedPromise = UserService.fetchAllUsers();
	    
	    // set up a handler for the response, that will put the result
	    // into a variable in this scope for you to test.
	    var result;
	    returnedPromise.then(function(response) {
	      result = response;
	    });
	    
	    // flush the backend to "execute" the request to do the expectedGET assertion.
	    httpBackend.flush();
	    
	    // check the result. 
	    // (after Angular 1.2.5: be sure to use `toEqual` and not `toBe`
	    // as the object will be a copy and not the same instance.)
	    expect(result).toEqual(returnData);
	  });  
	 
	
	  it('delete artist spec', function (){
		    // set up some data for the http call to return and test later.
		    var returnData = { excited: true };
		    
		    // expectGET to make sure this is called once.
		    httpBackend.expectDELETE('http://primaryapp.apps.digifabric.cognizant.com/deleteArtist?id=1').
		    respond(returnData);
		    //uncomment when full url not specified
		    //httpBackend.expectDELETE('/deleteArtist?id=1').respond(returnData);
		    
		    // make the call.
		    var returnedPromise = UserService.deleteArtist(1);
		    
		    // set up a handler for the response, that will put the result
		    // into a variable in this scope for you to test.
		    var result;
		    returnedPromise.then(function(response) {
		      result = response;
		    });
		    
		    // flush the backend to "execute" the request to do the expectedGET assertion.
		    httpBackend.flush();
		    
		    // check the result. 
		    // (after Angular 1.2.5: be sure to use `toEqual` and not `toBe`
		    // as the object will be a copy and not the same instance.)

		    expect(result).toEqual(returnData);
		  });  
	  
	  
	  it('Edit Artist Spec.', function (){
		    // set up some data for the http call to return and test later.
		    var returnData = { excited: true };
		    
		    // expectGET to make sure this is called once.
		    httpBackend.expectPUT('http://primaryapp.apps.digifabric.cognizant.com/editArtist?id=1&artist=RAM&DOB=1992&country=INDIA')
		    .respond(returnData);
		    //uncomment when full url not specified
		   // httpBackend.expectPUT('/editArtist?id=1&artist=RAM&DOB=1992&country=INDIA').respond(returnData);
		   var user = {
					id : 1,
					artist : 'RAM',
					dob : '1992',
					country:'INDIA'
				};
		    
		    // make the call.
		    var returnedPromise = UserService.UpdateArtist(1,user);
		    
		    // set up a handler for the response, that will put the result
		    // into a variable in this scope for you to test.
		    var result;
		    returnedPromise.then(function(response) {
		      result = response;
		    });
		    
		    // flush the backend to "execute" the request to do the expectedGET assertion.
		    httpBackend.flush();
		    
		    // check the result. 
		    // (after Angular 1.2.5: be sure to use `toEqual` and not `toBe`
		    // as the object will be a copy and not the same instance.)
		    expect(result).toEqual(returnData);
		  });  

	  
	  
	  it('Add artist Spec.', function (){
		    // set up some data for the http call to return and test later.
		    var returnData = { excited: true };
		    
		    // expectGET to make sure this is called once.
		    httpBackend.expectPOST('http://primaryapp.apps.digifabric.cognizant.com/addArtist?artist=RAM&DOB=1992&country=INDIA')
		    .respond(returnData);
		    //uncomment when full url not specified
		    // httpBackend.expectPOST('/addArtist?artist=RAM&DOB=1992&country=INDIA').respond(returnData);
		   	var user = {
					id : 1,
					artist : 'RAM',
					dob : '1992',
					country:'INDIA'
				};
		    
		    // make the call.
		    var returnedPromise = UserService.AddArtist(user);
		    
		    // set up a handler for the response, that will put the result
		    // into a variable in this scope for you to test.
		    var result;
		    returnedPromise.then(function(response) {
		      result = response;
		    });
		    
		    // flush the backend to "execute" the request to do the expectedGET assertion.
		    httpBackend.flush();
		    
		    // check the result. 
		    // (after Angular 1.2.5: be sure to use `toEqual` and not `toBe`
		    // as the object will be a copy and not the same instance.)
		    expect(result).toEqual(returnData);
		  });



		  it('get albums spec.', function (){
			    // set up some data for the http call to return and test later.
			    var returnData = { excited: true };
			    
			    // expectGET to make sure this is called once.
			    httpBackend.expectGET('http://primaryapp.apps.digifabric.cognizant.com/albums?artist=pp')
			    .respond(returnData);
			    //uncomment when full url not specified
			    //httpBackend.expectGET('/albums?artist=pp').respond(returnData);
			    
			    // make the call.
			    var returnedPromise = UserService.fetchAlbums('pp');
			    
			    // set up a handler for the response, that will put the result
			    // into a variable in this scope for you to test.
			    var result;
			    returnedPromise.then(function(response) {
			      result = response;
			    });
			    
			    // flush the backend to "execute" the request to do the expectedGET assertion.
			    httpBackend.flush();
			    
			    // check the result. 
			    // (after Angular 1.2.5: be sure to use `toEqual` and not `toBe`
			    // as the object will be a copy and not the same instance.)
			    expect(result).toEqual(returnData);
			  }); 


		it('Edit Album Spec.', function (){
		    // set up some data for the http call to return and test later.
		    var returnData = { excited: true };
		    
		    // expectGET to make sure this is called once.
		    httpBackend.expectPUT('http://primaryapp.apps.digifabric.cognizant.com/editAlbum?id=4&title=t1&artist=pp5&releaseYear=2')
		    .respond(returnData);
		    //uncomment when full url not specified
		   // httpBackend.expectPUT('/editAlbum?id=4&title=t1&artist=pp5&releaseYear=2').respond(returnData);
		   var album = {
				id : 4,
				title : 't1',
				releaseYear : 2,
				artist:'pp5'
			};
		    
		    // make the call.
		    var returnedPromise = UserService.UpdateAlbum(album,4);
		    
		    // set up a handler for the response, that will put the result
		    // into a variable in this scope for you to test.
		    var result;
		    returnedPromise.then(function(response) {
		      result = response;
		    });
		    
		    // flush the backend to "execute" the request to do the expectedGET assertion.
		    httpBackend.flush();
		    
		    // check the result. 
		    // (after Angular 1.2.5: be sure to use `toEqual` and not `toBe`
		    // as the object will be a copy and not the same instance.)
		    expect(result).toEqual(returnData);
		  });  


		it('Add album Spec.', function (){
		    // set up some data for the http call to return and test later.
		    var returnData = { excited: true };
		    
		    // expectGET to make sure this is called once.
		    httpBackend.expectPOST('http://primaryapp.apps.digifabric.cognizant.com/addAlbum?title=rock&artist=pp&releaseYear=2')
		    .respond(returnData);
		    //uncomment when full url not specified
		    // httpBackend.expectPOST('/addAlbum?title=rock&artist=pp&releaseYear=2').respond(returnData);
		   	var album = {
				id : 4,
				title : 'rock',
				releaseYear : 2,
				artist:'pp'
			};
			var selectedArtist="pp";
		    
		    // make the call.
		    var returnedPromise = UserService.AddAlbum(album,selectedArtist);
		    
		    // set up a handler for the response, that will put the result
		    // into a variable in this scope for you to test.
		    var result;
		    returnedPromise.then(function(response) {
		      result = response;
		    });
		    
		    // flush the backend to "execute" the request to do the expectedGET assertion.
		    httpBackend.flush();
		    
		    // check the result. 
		    // (after Angular 1.2.5: be sure to use `toEqual` and not `toBe`
		    // as the object will be a copy and not the same instance.)
		    expect(result).toEqual(returnData);
		  });


		 it('delete album spec', function (){
		    // set up some data for the http call to return and test later.
		    var returnData = { excited: true };
		    
		    // expectGET to make sure this is called once.
		    httpBackend.expectDELETE('http://primaryapp.apps.digifabric.cognizant.com/deletealbum?id=1&artist=pp5').
		    respond(returnData);
		    //uncomment when full url not specified
		    //httpBackend.expectDELETE('/deletealbum?id=1&artist=pp5').respond(returnData);
		    
		    // make the call.
		    var returnedPromise = UserService.deleteAlbum(1,'pp5');
		    
		    // set up a handler for the response, that will put the result
		    // into a variable in this scope for you to test.
		    var result;
		    returnedPromise.then(function(response) {
		      result = response;
		    });
		    
		    // flush the backend to "execute" the request to do the expectedGET assertion.
		    httpBackend.flush();
		    
		    // check the result. 
		    // (after Angular 1.2.5: be sure to use `toEqual` and not `toBe`
		    // as the object will be a copy and not the same instance.)

		    expect(result).toEqual(returnData);
		  });  
 
			  

	
	});











