package com.crud.spring_crud_libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.spring_crud_libros.model.Books;

public interface BooksRepository extends JpaRepository<Books,Long>{

}
