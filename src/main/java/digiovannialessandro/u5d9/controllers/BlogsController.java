 package digiovannialessandro.u5d9.controllers;



import digiovannialessandro.u5d9.entities.Blogpost;
import digiovannialessandro.u5d9.payloads.NewBlogPostPayload;
import digiovannialessandro.u5d9.services.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogsController {
    @Autowired
    BlogsService blogsService;

    // 1. - POST http://localhost:3001/blogs (+ req.body)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Blogpost saveBlog(@Validated @RequestBody NewBlogPostPayload body, BindingResult validationResult) {
        return blogsService.save(body);
    }

    // 2. - GET http://localhost:3001/blogs
    @GetMapping("")
    public List<Blogpost> getBlogs(@RequestParam(required = false) Integer authorId) {
        if(authorId != null) return blogsService.findByAuthor(authorId);
        else return blogsService.getBlogs();
    }

    // 3. - GET http://localhost:3001/blogs/{id}
    @GetMapping("/{blogId}")
    public Blogpost findById(@PathVariable int blogId) {
        return blogsService.findById(blogId);
    }

    // 4. - PUT http://localhost:3001/blogs/{id} (+ req.body)
    @PutMapping("/{blogId}")
    public Blogpost findAndUpdate(@PathVariable int blogId, @Validated @RequestBody NewBlogPostPayload body,BindingResult validationResult) {
        return blogsService.findByIdAndUpdate(blogId, body);
    }

    // 5. - DELETE http://localhost:3001/blogs/{id
    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findAndDelete(@PathVariable int blogId) {
        blogsService.findByIdAndDelete(blogId);
    }

    @PatchMapping("/{blogsId}/avatar")
    public String uploadImage(@RequestParam("avatar") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        return this.blogsService.uploadAvatar(file);

    }
}
