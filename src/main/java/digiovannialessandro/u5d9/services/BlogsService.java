package digiovannialessandro.u5d9.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import digiovannialessandro.u5d9.entities.Author;
import digiovannialessandro.u5d9.entities.Blogpost;
import digiovannialessandro.u5d9.exceptions.NotFoundException;
import digiovannialessandro.u5d9.payloads.NewBlogPostPayload;
import digiovannialessandro.u5d9.repositories.BlogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class BlogsService {
    @Autowired
    private BlogsRepository blogsRepository;
    @Autowired
    private AuthorsService authorsService;
    @Autowired
    private Cloudinary imgUploader;

    public Blogpost save(NewBlogPostPayload body) {
        Author author = authorsService.findById(body.getAuthorId());
        Blogpost newBlogPost = new Blogpost();
        newBlogPost.setReadingTime(body.getReadingTime());
        newBlogPost.setContent(body.getContent());
        newBlogPost.setTitle(body.getTitle());
        newBlogPost.setAuthor(author);
        newBlogPost.setCategory(body.getCategory());
        newBlogPost.setCover("http://picsum.photos/200/300");
        return blogsRepository.save(newBlogPost);
    }

    public List<Blogpost> getBlogs() {
        return blogsRepository.findAll();
    }

    public Blogpost findById(int id) {
        return blogsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) {
        Blogpost found = this.findById(id);
        blogsRepository.delete(found);
    }

    public Blogpost findByIdAndUpdate(int id, NewBlogPostPayload body) {
        Blogpost found = this.findById(id);

        found.setReadingTime(body.getReadingTime());
        found.setContent(body.getContent());
        found.setTitle(body.getTitle());
        found.setCategory(body.getCategory());

        if(found.getAuthor().getId()!= body.getAuthorId()) {
            Author newAuthor = authorsService.findById(body.getAuthorId());
            found.setAuthor(newAuthor);
        }

        return blogsRepository.save(found);
    }

    public List<Blogpost> findByAuthor(int authorId){
        Author author = authorsService.findById(authorId);
        return blogsRepository.findByAuthor(author);
    }
    public String uploadAvatar(MultipartFile file) {

        Map result = null;
        try {
            result = imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String imageURL = (String) result.get("url");

            return imageURL;

    }
}
