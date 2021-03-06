package com.ssafy.api.reservation.service;

import com.ssafy.api.reservation.dto.request.ReservationAddRequest;
import com.ssafy.api.reservation.dto.request.ReservationDeleteRequest;
import com.ssafy.api.reservation.dto.response.ReservationResponse;
import com.ssafy.common.exception.CustomException;
import com.ssafy.common.exception.ErrorCode;
import com.ssafy.common.util.JwtTokenProvider;
import com.ssafy.domain.reservation.entity.Reservation;
import com.ssafy.domain.reservation.repository.ReservationRepository;
import com.ssafy.domain.room.entity.Room;
import com.ssafy.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    public static final String TJ_PREFIX = "TJ노래방";
    public static final String TJ_POSTFIX = " / TJ Karaoke";

    @Transactional
    @Override
    public List<ReservationResponse> add(ReservationAddRequest reservationAddRequest) {
        //db에 리퀘스트로 받은 데이터를 넣어준다.
        Room room = roomRepository.findById(reservationAddRequest.getRoom_seq())
                .orElseThrow(()->new CustomException(ErrorCode.ROOM_NOT_FOUND));
        //TJ PREFIX 삭제
        String newTitle = reservationAddRequest.getTitle().replace(TJ_PREFIX, "");
        newTitle = newTitle.replace(TJ_POSTFIX, "");
        newTitle = newTitle.replace("[] ", "");  //[TJ노래방]이었다면 []만 남아있을 테니까 삭제
        newTitle = newTitle.replace("  /", "");   //[  /반키내림]이었다면
        newTitle = newTitle.replace("/", "");   //[  /반키내림]이었다면
        Reservation reservation = Reservation.builder()
                .room(room)
                .song_no(reservationAddRequest.getSong_no())
                .title(newTitle)
                .build();
        reservationRepository.save(reservation);
        room.addReservation(reservation);
        return getReservationList(room.getSeq());
    }

    @Transactional
    @Override
    public List<ReservationResponse> delete(ReservationDeleteRequest reservationDeleteRequest) {
        Room room = roomRepository.findById(reservationDeleteRequest.getRoom_seq())
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        room.removeReservation(reservationDeleteRequest.getReservation_seq());
        reservationRepository.deleteById(reservationDeleteRequest.getReservation_seq());
        return getReservationList(reservationDeleteRequest.getRoom_seq());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationResponse> getReservationList(Long room_seq) {
        Room room = roomRepository.findById(room_seq)
                .orElseThrow(()->new CustomException(ErrorCode.ROOM_NOT_FOUND));
        List<Reservation> list = room.getReservations();
        return ReservationResponse.of(list);
    }

    @Transactional
    @Override
    public ReservationResponse getFirst(Long room_seq) {
        Room room = roomRepository.findById(room_seq)
                .orElseThrow(()->new CustomException(ErrorCode.ROOM_NOT_FOUND));
        Reservation reservation = room.getFirstReservation();
        ReservationResponse response = ReservationResponse.of(reservation);
        //delete해야함
        room.removeReservation(response.getReservation_seq());
        reservationRepository.deleteById(response.getReservation_seq());
        return response;
    }

    @Override
    public List<ReservationResponse> getTwo(Long room_seq) {
        Room room = roomRepository.findById(room_seq)
                .orElseThrow(()->new CustomException(ErrorCode.ROOM_NOT_FOUND));
        List<Reservation> response = room.getTwoReservation();
        return ReservationResponse.of(response);
    }
}
