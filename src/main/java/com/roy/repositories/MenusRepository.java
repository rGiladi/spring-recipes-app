package com.roy.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.roy.models.Menu;

public interface MenusRepository extends CrudRepository<Menu, Long>{
	public List<Menu> findByUserId(long id);
	
	public List<Menu> findByPublished(int shared);

	public List<Menu> findByReported(boolean bool);
	
	public List<Menu> findByUsersLiked(long id);
	
	@Query(value = "SELECT * FROM menus WHERE published = 1 ORDER BY pub_date DESC LIMIT 20 OFFSET :starting", nativeQuery=true)
	public List<Menu> AllMenusPaging(@Param("starting") int s);
}
