package com.waveq.service;

import com.waveq.entity.Auction;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 2015-03-24.
 */

@Singleton
public class AuctionService {

	private static List auctions = new ArrayList<Auction>();

	public long addAuction(Auction a) {
		auctions.add(a);
		return 1;
	}

	public List<Auction> getAuctions() {
		return auctions;
	}

	public long deleteAuction(Auction a) {
		auctions.remove(auctions.indexOf(a));
		return 1;
	}

	public boolean contains(Auction a) {
		for(Auction auction : getAuctions()) {
			if (auction.getName().equals(a.getName())) {
				return true;
			}
		}
		return false;
	}

}
