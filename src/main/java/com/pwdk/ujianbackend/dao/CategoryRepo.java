package com.pwdk.ujianbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pwdk.ujianbackend.entity.Categories;

public interface CategoryRepo extends JpaRepository<Categories, Integer>{

}
