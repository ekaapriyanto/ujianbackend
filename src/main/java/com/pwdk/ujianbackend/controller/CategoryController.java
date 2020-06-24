package com.pwdk.ujianbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private MoviesRepo moviesRepo;
	
	@GetMapping
	public Iterable<Categories> getCategory(){
		return categoryRepo.findAll();
	}
	
	@PostMapping
	private Categories addCategory(@RequestBody Categories categories) {
		categories.setId(0);
		return categoryRepo.save(categories);
	}
	
	@DeleteMapping("/{category_id}")
	public void deleteCategories(@PathVariable int categories_id) {
		Categories findCategories = categoryRepo.findById(categories_id).get();
		
		findCategories.getMovies().forEach(movies -> {
			List<Categories> MoviesCategories = movies.getCategories();
			MoviesCategories.remove(findCategories);
			moviesRepo.save(movies);
		});
		findCategories.setMovies(null);
		categoryRepo.deleteById(categories_id);
	}
	
	@DeleteMapping("/{categories_Id}/movies/{movies _id}")
	public Categories deleteCategoryMovies(@PathVariable int categories_id, @PathVariable int movies_id) {
		Categories findCategories = categoryRepo.findById(categories_id).get();
		Movies findMovies =moviesRepo.findById(movies_id).get();
		
		findCategories.getMovies().add(findMovies);
		return categoryRepo.save(findCategories);
	}
	
	@PutMapping("/editCategories")
	public Categories editCategories(@RequestBody Categories categories) {
		Categories findCategories = categoryRepo.findById(categories.getId()).get();
		categories.setMovies(findCategories.getMovies());
		return categoryRepo.save(categories);
	}
}
