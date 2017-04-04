package com.starterkit.repositories;


import com.starterkit.domain.Album;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author narendra.gurram@cognizant.com
 * @description JpaRepository reads system environment variables (VCAP_SERVICE) and establish a connection with DB using
 *   connection parameters. It creates a table 'Album' (domain class passed as an argument).
 *   Its having build-in methods for CRUD operation.
 *
 */
public interface JpaAlbumRepository extends MongoRepository<Album, String> {

}
