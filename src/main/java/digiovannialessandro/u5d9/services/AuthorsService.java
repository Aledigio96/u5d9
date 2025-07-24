package digiovannialessandro.u5d9.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import digiovannialessandro.u5d9.entities.Author;
import digiovannialessandro.u5d9.exceptions.BadRequestException;
import digiovannialessandro.u5d9.exceptions.NotFoundException;
import digiovannialessandro.u5d9.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class AuthorsService {

    @Autowired
    private AuthorsRepository authorsRepository;
    @Autowired
    private Cloudinary imgUploader;

    public Author save(Author body) {
        authorsRepository.findByEmail(body.getEmail()).ifPresent(user -> {
            throw new BadRequestException("L'email " + body.getEmail() + " è già stata utilizzata");
        });
        body.setAvatar("https://ui-avatars.com/api/?name=" + body.getName().charAt(0) + "+" + body.getSurname().charAt(0));
        return authorsRepository.save(body);
    }

    public Page<Author> getAuthors(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return authorsRepository.findAll(pageable);
    }

    public Author findById(int id) {
        return authorsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) {
        Author found = this.findById(id);
        authorsRepository.delete(found);
    }

    public Author findByIdAndUpdate(int id, Author body) {

        Author found = this.findById(id);
        found.setEmail(body.getEmail());
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setDateOfBirth(body.getDateOfBirth());
        found.setAvatar(body.getAvatar());
        return authorsRepository.save(found);
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
