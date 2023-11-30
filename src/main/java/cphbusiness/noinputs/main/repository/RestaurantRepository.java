package cphbusiness.noinputs.main.repository;

import cphbusiness.noinputs.main.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    @NonNull
    List<Restaurant> findAll();
}
