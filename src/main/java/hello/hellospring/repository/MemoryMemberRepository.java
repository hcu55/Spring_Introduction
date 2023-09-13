package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;      // 0,1,2... 처럼 key값을 생성해 주는 애

    @Override
    public Member save(Member member) {
        member.setId(++sequence);       // id 세팅
        store.put(member.getId(), member);      // id 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));      // null을 대비해서 Optional로 감싼다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();         // 루프를 돌면서 하나를 찾으면 반환이 되는 것이다. null이면 Optional에 null이 포함되어 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());     // store에 있는 값을 반환하면 된다. = Member
    }

    public void clearStore() {
        store.clear();
    }
}