package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.model.Member;
import com.ifortex.bookservice.repository.MembersRepository;
import com.ifortex.bookservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMemberService implements MemberService {

    private final MembersRepository membersRepository;

    @Autowired
    public DefaultMemberService(MembersRepository membersRepository) {
        this.membersRepository = membersRepository;
    }

    @Override
    public Member findMember() {
        return membersRepository.findMembersByBookGenreOrderByRegistration("Romance").get(0);
    }

    @Override
    public List<Member> findMembers() {
        return membersRepository.findMembersByRegistrationYear(2023);
    }
}
