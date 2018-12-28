package info.asshead.bbs.repository;

import info.asshead.bbs.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface MessageRepository extends PagingAndSortingRepository<Message, Integer> {
  Page<Message> findByUserIdAndStatus(int userId, int status, Pageable pageable);

  Page<Message> findByStatus(int status, Pageable pageable);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE Message m SET m.status = 0 WHERE m.createdAt <= :overDay")
  void cleanByDate(@Param("overDay") Date overDay);
}
