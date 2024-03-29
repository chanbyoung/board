package api.board.repository;

import api.board.entity.Heart;
import api.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    @Query("select p from Heart h join  h.post p join h.member m where m.id = :memberId")
    List<Post> findHeartPostByMemberId(@Param("memberId") Long memberId);
}
