package fr.findByDev.api.controllers.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.Notice;
import fr.findByDev.api.repositories.global.NoticeRepository;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/notices")
public class NoticeController extends GenericController<Notice, Integer> {
    
    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    public NoticeController(NoticeRepository noticeRepository) {
        super(noticeRepository);
        this.noticeRepository = noticeRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Notice> all() {
        return noticeRepository.findAll();
    }
}
