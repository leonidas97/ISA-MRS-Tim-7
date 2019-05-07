package com.FlightsReservations.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.UserDTO;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	@Query(value="SELECT * FROM user u WHERE u.id IN (SELECT uf.friend_id FROM user_friends uf WHERE ?1 = uf.user_id)", nativeQuery = true)
	List<User> findFriends(Long id);
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO user_friends VALUES (?1, ?2)", nativeQuery = true)
	void addFriend(Long userId, Long friendId);
}
