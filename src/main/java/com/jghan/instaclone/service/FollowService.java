package com.jghan.instaclone.service;

import com.jghan.instaclone.domain.follow.FollowRepositoy;
import com.jghan.instaclone.handler.ex.CustomApiException;
import com.jghan.instaclone.web.dto.follow.FollowDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepositoy followRepositoy;
    //팔로우 정보 조인쿼리는 반환타입이 Follow이 아니기 때문에
    //Repository 는 EntityManager를 구현해서 만들어져 있는 구현체
    private final EntityManager em;

    @Transactional(readOnly = true)
    public List<FollowDto> followList(int principalId, int pageUserId){
        //쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if((SELECT 1 FROM follow WHERE fromUserId = ? AND toUserId = u.id), 1, 0) followState, ");
        sb.append("if((? = u.id), 1, 0)equalUserState ");
        sb.append("FROM user u INNER JOIN follow f ");
        sb.append("ON u.id = f.toUserId ");
        sb.append("WHERE f.fromUserId = ? "); //세미콜론 첨부하면 안됨

        //쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        //1.물음표: principalId
        //2.물음표  :principalId
        //3. 물음표 :pageUserId

        //qlrm: db에서 result된 결과를 자바클래스에 매핑해주는 라이브러리
        //내가 리턴받을 결과가 모델이아닌, 새로운 조합의 데이터면, dto라면 네이티브쿼리를 써야한다. jpa 못쓴다.

        //쿼리 실행(qlrm 라이브러리 필요, dto에 db결과를 매핑하기 위해서)
        JpaResultMapper result = new JpaResultMapper();
        List<FollowDto> followDtos = result.list(query, FollowDto.class);//한건을 받을게 아니니까

        //(쿼리, 리턴받을 타입)

        return  followDtos;

    }

    @Transactional
    public void follow(int fromUserId, int toUserId){
        try {
            followRepositoy.mFollow(fromUserId,toUserId);
        } catch (Exception e){
            throw new CustomApiException("이미 팔로우 했습니다.");
        }
    }


    @Transactional
    public void unfollow(int fromUserId, int toUserId){
        followRepositoy.mUnFollow(fromUserId,toUserId);
    }
}
