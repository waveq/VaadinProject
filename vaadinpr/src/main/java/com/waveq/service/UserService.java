package com.waveq.service;

import com.waveq.entity.Auction;
import com.waveq.entity.User;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 2015-03-24.
 */

@Singleton
public class UserService {

	private static List<User> users = new ArrayList<User>();

	public long addUser(User u) {
		users.add(u);
		return 1;
	}

	public List<User> getUsers() {
		return users;
	}

	public long deleteUser(User u) {
		users.remove(users.indexOf(u));
		return 1;
	}

	public boolean contains(User u) {
		for(User user : getUsers()) {
			System.out.println(user.getUsername()+" : "+u.getUsername());
			if (user.getUsername().equals(u.getUsername())) {
				return true;
			}
		}
		return false;
	}
}
