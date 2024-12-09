package com.crud.spring_crud_libros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.crud.spring_crud_libros.model.Books;
import com.crud.spring_crud_libros.repository.BooksRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; 

@Controller
public class BookController {

    @Autowired
    private BooksRepository bRepository;

    // Obtener la lista de libros
    @GetMapping("/index")
    public String getBooks(Model model) {

        List<Books> limbros = bRepository.findAll();
        model.addAttribute("libros", limbros);
        model.addAttribute("libro", new Books());
        return "listar_libros";
    }

    @PostMapping("/submitRegistrarLibro")
    public String submitRegistrarLibro(@ModelAttribute Books libro, Model model) {
        bRepository.save(libro);
        model.addAttribute("libro", libro);
        return "redirect:/index";
    }

    @GetMapping("/editarLibro/{id}")
    public String editarLibro(@PathVariable("id") Long id, Model model) {

        return bRepository.findById(id).map(libro -> {
            model.addAttribute("libro", libro);
            return "formulario_libros";
        }).orElse("redirect:/index");
    }

    @PostMapping("/submitEdicionLibro")
    public String submitEdicionLibro(@ModelAttribute Books libro, Model model) {
        bRepository.findById(libro.getId()).ifPresent(libroExistente -> {
            libroExistente.setNombre_del_libro(libro.getNombre_del_libro());
            libroExistente.setAño_publicacion(libro.getAño_publicacion());
            libroExistente.setAutor(libro.getAutor());
            bRepository.save(libroExistente);
            model.addAttribute("libro", libroExistente);
        });
        return "redirect:/index";
    }

    @GetMapping("/eliminarLibro/{id}")
    public String eliminarLibro(@PathVariable("id") Long id) {

        bRepository.findById(id).ifPresent(libro -> {
            bRepository.delete(libro);
        });
        return "redirect:/index";
    }

}
