package fr.findByDev.api.repositories.global;

import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Notice;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface NoticeRepository extends GenericRepository<Notice, Integer> {

}
