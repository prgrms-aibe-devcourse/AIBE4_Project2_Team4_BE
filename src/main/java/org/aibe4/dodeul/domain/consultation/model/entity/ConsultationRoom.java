package org.aibe4.dodeul.domain.consultation.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aibe4.dodeul.domain.common.model.entity.BaseEntity;
import org.aibe4.dodeul.domain.consultation.model.enums.ConsultationRoomStatus;

@Entity(name = "consultation_rooms")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultationRoom extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ConsultationRoomStatus status;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    //    @OneToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "matching_id", nullable = false, unique = true)
    //    private Matching matching;
    //
    //    @Builder
    //    public ConsultationRoom(Matching matching) {
    //        this.matching = matching;
    //        this.status = ConsultationRoomStatus.OPEN;
    //    }

    public void close() {
        if (this.status == ConsultationRoomStatus.CLOSED) {
            throw new IllegalStateException("이미 종료된 상담방입니다.");
        }
        this.status = ConsultationRoomStatus.CLOSED;
        this.closedAt = LocalDateTime.now();
    }
}
