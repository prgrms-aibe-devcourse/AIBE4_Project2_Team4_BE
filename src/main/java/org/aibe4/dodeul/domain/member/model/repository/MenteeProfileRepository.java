package org.aibe4.dodeul.domain.member.model.repository;

import org.aibe4.dodeul.domain.member.model.entity.MenteeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenteeProfileRepository extends JpaRepository<MenteeProfile, Long> {

    Optional<MenteeProfile> findByMember_Id(Long memberId);

    boolean existsByMember_Id(Long memberId);
}
