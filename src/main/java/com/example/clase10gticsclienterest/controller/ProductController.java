package com.example.clase10gticsclienterest.controller;


import com.example.clase10gticsclienterest.dao.CategoryDao;
import com.example.clase10gticsclienterest.dao.ProductoDao;
import com.example.clase10gticsclienterest.dao.SupplierDao;
import com.example.clase10gticsclienterest.entity.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping(value = "/product")
public class ProductController {

    final ProductoDao productoDao;
    final CategoryDao categoryDao;
    final SupplierDao supplierDao;

    public ProductController(ProductoDao productoDao, CategoryDao categoryDao, SupplierDao supplierDao) {
        this.productoDao = productoDao;
        this.categoryDao = categoryDao;
        this.supplierDao = supplierDao;
    }

    @GetMapping({"/list", "", "/"})
    public String listarProductos(Model model) {
        //model.addAttribute("listaProductos", productRepository.findAll());
        model.addAttribute("listaProductos", productoDao.listar());
        return "product/list";
    }

    @GetMapping("/new")
    public String nuevoProductoFrm(@ModelAttribute("product") Product product, Model model) {
        //model.addAttribute("listaCategorias", categoryRepository.findAll());
        model.addAttribute("listaCategorias", categoryDao.listar());
        //model.addAttribute("listaProveedores", supplierRepository.findAll());
        model.addAttribute("listaProveedores", supplierDao.listar());
        return "product/form";
    }

    @PostMapping("/save")
    public String guardarProducto(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                                  Model model, RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            //model.addAttribute("listaCategorias", categoryRepository.findAll());
            //model.addAttribute("listaProveedores", supplierRepository.findAll());
            model.addAttribute("listaCategorias", categoryDao.listar());
            model.addAttribute("listaProveedores", supplierDao.listar());
            return "product/form";
        } else {
            String msg = "Producto " + (product.getId() == null ? "creado" : "actualizado") + " exitosamente";
            attr.addFlashAttribute("msg", msg);
            // productRepository.save(product);
            productoDao.guardar(product); //voy a hacer la validación de guardar o actualizar en el dao.
            return "redirect:/product";
        }
    }

    @GetMapping("/edit")
    public String editarTransportista(@ModelAttribute("product") Product product,
                                      Model model, @RequestParam("id") int id) {

        //Optional<Product> optProduct = productRepository.findById(id);
        Product product1 = productoDao.buscarPorId(id);

        //if (optProduct.isPresent()) {
        if(product1 != null) {
            //Product product = optProduct.get();
            product = product1;
            model.addAttribute("product", product);
            //model.addAttribute("listaCategorias", categoryRepository.findAll());
            //model.addAttribute("listaProveedores", supplierRepository.findAll());
            model.addAttribute("listaCategorias", categoryDao.listar());
            model.addAttribute("listaProveedores", supplierDao.listar());
            return "product/form";
        } else {
            return "redirect:/product";
        }
    }

    @GetMapping("/delete")
    public String borrarTransportista(Model model, @RequestParam("id") int id, RedirectAttributes attr) {

        //Optional<Product> optProduct = productRepository.findById(id);

        //if (optProduct.isPresent()) {
        //productRepository.deleteById(id);
        productoDao.deleteProductById(id);
        attr.addFlashAttribute("msg", "Producto borrado exitosamente");
        //}
        return "redirect:/product";

    }
}
