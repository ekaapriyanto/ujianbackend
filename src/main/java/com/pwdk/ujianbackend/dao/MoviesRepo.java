package com.pwdk.ujianbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pwdk.ujianbackend.entity.Movies;

public interface MoviesRepo extends JpaRepository<Movies, Integer>{

}
