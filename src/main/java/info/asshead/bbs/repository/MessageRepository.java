package info.asshead.bbs.repository;

import info.asshead.bbs.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepository extends PagingAndSortingRepository<Message, Integer> {
  Page<Message> findByUserIdAndStatus(int userId, int status, Pageable pageable);

  Page<Message> findByStatus(int status, Pageable pageable);
}
