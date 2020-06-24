package com.pwdk.ujianbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pwdk.ujianbackend.dao.CategoryRepo;
import com.pwdk.ujianbackend.dao.MoviesRepo;
import com.pwdk.ujianbackend.entity.Categories;
import com.pwdk.ujianbackend.entity.Movies;

@RestController
@RequestMapping("/movies")
@CrossOrigin
public class MoviesController {
	
	@Autowired
	private MoviesRepo moviesRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@GetMapping
	public Iterable<Movies> getAllMovies(){
		return moviesRepo.findAll();
	}
	
	@PostMapping
	public Movies addMovie (@RequestBody Movies movies) {
		movies.setId(0);
		return moviesRepo.save(movies);
	}
	
	@DeleteMapping("/{movies_id}")
	public void deleteMovies(@PathVariable int movies_id) {
		Movies findMovies = moviesRepo.findById(movies_id).get();
		
		findMovies.getCategories().forEach(categories -> {
			List<Movies> categoriesMovies = categories.getMovies();
			categoriesMovies.remove(findMovies);
			categoryRepo.save(categories);
		});
		findMovies.setCategories(null);
		categoryRepo.deleteById(movies_id);
	}
	
	@DeleteMapping("/{moviesId}/categories/{categoriesId")
	public Movies deleteMoviesCategory(@PathVariable int movies_id, @PathVariable int categories_id) {
		Movies findMovies = moviesRepo.findById(movies_id).get();
		Categories findCategories =categoryRepo.findById(categories_id).get();
		
		findMovies.getCategories().add(findCategories);
		return moviesRepo.save(findMovies);
	}
	
	@PutMapping("/editMovies")
	public Movies editMovies(@RequestBody Movies movies) {
		Movies findMovies = moviesRepo.findById(movies.getId()).get();
		movies.setCategories(findMovies.getCategories());
		return moviesRepo.save(movies);
	}
}
