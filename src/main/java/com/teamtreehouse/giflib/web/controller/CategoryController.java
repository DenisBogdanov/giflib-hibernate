package com.teamtreehouse.giflib.web.controller;

import com.teamtreehouse.giflib.model.Category;
import com.teamtreehouse.giflib.service.CategoryService;
import com.teamtreehouse.giflib.web.Color;
import com.teamtreehouse.giflib.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Index of all categories
    @RequestMapping("/categories")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/index";
    }

    // Single category page
    @RequestMapping("/categories/{categoryId}")
    public String category(@PathVariable Long categoryId, Model model) {
        // TODO: Get the category given by categoryId
        Category category = null;

        model.addAttribute("category", category);
        return "category/details";
    }

    // Form for adding a new category
    @RequestMapping("categories/add")
    public String formNewCategory(Model model) {
        // Add model attributes needed for new form
        if (!model.containsAttribute("category")) {
            model.addAttribute("category", new Category());
        }
        model.addAttribute("colors", Color.values());
        model.addAttribute("action", "/categories");
        model.addAttribute("heading", "New Category");
        model.addAttribute("submit", "Add");

        return "category/form";
    }

    // Form for editing an existing category
    @RequestMapping("categories/{categoryId}/edit")
    public String formEditCategory(@PathVariable Long categoryId, Model model) {
        // Add model attributes needed for edit form
        if (!model.containsAttribute("category")) {
            model.addAttribute("category", categoryService.findById(categoryId));
        }
        model.addAttribute("colors", Color.values());
        model.addAttribute("action", "/categories/" + categoryId);
        model.addAttribute("heading", "Edit Category");
        model.addAttribute("submit", "Update");

        return "category/form";
    }

    // Update an existing category
    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.POST)
    public String updateCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        // Update category if valid data was received
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);

            // Add category if invalid data was received
            redirectAttributes.addFlashAttribute("category", category);

            // Redirect back to the form
            return String.format("redirect:/categories/%d/edit", category.getId());
        }
        categoryService.save(category);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Category successfully updated!", FlashMessage.Status.SUCCESS));

        // Redirect browser to /categories
        return "redirect:/categories";
    }

    // Add a category
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String addCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        // Add category if valid data was received
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);

            // Add category if invalid data was received
            redirectAttributes.addFlashAttribute("category", category);

            // Redirect back to the form
            return "redirect:/categories/add";
        }
        categoryService.save(category);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Category successfully added!", FlashMessage.Status.SUCCESS));

        // Redirect browser to /categories
        return "redirect:/categories";
    }

    // Delete an existing category
    @RequestMapping(value = "/categories/{categoryId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Long categoryId, RedirectAttributes redirectAttributes) {

        Category category = categoryService.findById(categoryId);

        // Delete category if it contains no GIFs
        if (category.getGifs().size() > 0) {
            redirectAttributes.addFlashAttribute("flash",
                    new FlashMessage("Only empty categories can be deleted.", FlashMessage.Status.FAILURE));
            return String.format("redirect:/categories/%d/edit", categoryId);
        }
        categoryService.delete(category);
        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Category deleted!", FlashMessage.Status.SUCCESS));

        // Redirect browser to /categories
        return "redirect:/categories";
    }
}
