
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PlaceRepository;
import domain.Place;

@Service
@Transactional
public class PlaceService {

	public PlaceService() {
		super();
	}


	@Autowired
	private PlaceRepository	placeRepository;


	public Place create() {
		Place result;

		result = new Place();

		return result;
	}

	public Place findOne(final int placeId) {
		Place result;

		result = this.placeRepository.findOne(placeId);

		return result;
	}

	public Collection<Place> findAll() {
		Collection<Place> result;

		result = this.placeRepository.findAll();

		return result;
	}

	public Place save(final Place place) {
		Assert.notNull(place);

		Place result;

		result = this.placeRepository.save(place);

		return result;

	}

	public void delete(final Place place) {
		Assert.notNull(place);
		Assert.isTrue(this.placeRepository.exists(place.getId()));

		this.placeRepository.delete(place);
	}

}
