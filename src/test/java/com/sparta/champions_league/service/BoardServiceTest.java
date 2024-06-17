package com.sparta.champions_league.service;

import com.sparta.champions_league.dto.BoardRequestDto;
import com.sparta.champions_league.dto.BoardResponseDto;
import com.sparta.champions_league.dto.BoardUpdateDto;
import com.sparta.champions_league.entity.Board;
import com.sparta.champions_league.entity.User;
import com.sparta.champions_league.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    BoardRepository boardRepository;

    @Test
    @DisplayName("게시물 작성 성공")
    void test1(){
        // given
        BoardRequestDto requestDto = new BoardRequestDto("제목", "게시글");
        User user = new User(); // 사용자 초기화
        Board board = new Board("제목", "게시글", user);
        when(boardRepository.save(any(Board.class))).thenReturn(board);
        BoardService boardService = new BoardService(boardRepository);

        // when
        BoardResponseDto responseDto = boardService.createBoard(requestDto, user);

        // then
        assertNotNull(responseDto);
        assertEquals("제목", responseDto.getTitle());
        verify(boardRepository, times(1)).save(any(Board.class));
    }

    @Test
    @DisplayName("게시물 전체 조회")
    void test2(){
        //given
        Long boardNum =1l;
        Board board = new Board("제목","게시글", new User());
        when(boardRepository.findById(boardNum)).thenReturn(Optional.of(board));
        BoardService boardService = new BoardService(boardRepository);

        // when
        BoardResponseDto responseDto = boardService.getBoard(boardNum);

        // then
        assertNotNull(responseDto);
        assertEquals("제목", responseDto.getTitle());
        System.out.println("게시물 조회 성공");
        verify(boardRepository, times(1)).findAllByOrderByCreatedAtDesc();
    }

    @Test
    @DisplayName("게시물 조회 실패")
    void test3(){
        // given
        when(boardRepository.findAllByOrderByCreatedAtDesc()).thenReturn(Collections.emptyList());
        BoardService boardService = new BoardService(boardRepository);

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            boardService.getAllBoards();
        });

        assertEquals("먼저 작성하여 소식을 알려보세요!", exception.getMessage());
        System.out.println("게시물 조회 실패");
        verify(boardRepository, times(1)).findAllByOrderByCreatedAtDesc();
    }
    @Test
    @DisplayName("게시물 단건 조회")
    void test4(){
        // given
        Long boardNum = 1L;
        Board board = new Board("제목", "게시글", new User());
        when(boardRepository.findById(boardNum)).thenReturn(Optional.of(board));
        BoardService boardService = new BoardService(boardRepository);

        // when
        BoardResponseDto responseDto = boardService.getBoard(boardNum);

        // then
        assertNotNull(responseDto);
        assertEquals("제목", responseDto.getTitle());
        verify(boardRepository, times(1)).findById(boardNum);
    }

    @Test
    @DisplayName("게시물 단건 조회 실패")
    void test5(){
        // given
        Long boardNum = 1L;
        when(boardRepository.findById(boardNum)).thenReturn(Optional.empty());
        BoardService boardService = new BoardService(boardRepository);

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            boardService.getBoard(boardNum);
        });

        assertEquals("해당 게시글을 찾을 수 없습니다.", exception.getMessage());
        verify(boardRepository, times(1)).findById(boardNum);
    }


    @Test
    @DisplayName("게시물 수정")
    void test6(){
        // given
        Long boardNum = 1L;
        BoardRequestDto requestDto = new BoardRequestDto("새 제목", "새 게시글");
        User user = new User(); // 사용자 초기화
        Board board = new Board("제목", "게시글", user);
        when(boardRepository.findById(boardNum)).thenReturn(Optional.of(board));
        BoardService boardService = new BoardService(boardRepository);

        // when
        BoardUpdateDto updateDto = boardService.updateBoard(boardNum, requestDto, user);

        // then
        assertNotNull(updateDto);
        assertEquals("새 제목", updateDto.getTitle());
        verify(boardRepository, times(1)).findById(boardNum);
    }
    @Test
    @DisplayName("게시물 수정 실패")
    void test7(){
        // given
        Long boardNum = 1L;
        BoardRequestDto requestDto = new BoardRequestDto("새 제목", "새 게시글");
        User user = new User(); // 사용자 초기화
        Board board = new Board("제목", "게시글", user);
        when(boardRepository.findById(boardNum)).thenReturn(Optional.of(board));
        BoardService boardService = new BoardService(boardRepository);

        // when
        BoardUpdateDto updateDto = boardService.updateBoard(boardNum, requestDto, user);

        // then
        assertNotNull(updateDto);
        assertEquals("새 제목", updateDto.getTitle());
        verify(boardRepository, times(1)).findById(boardNum);
    }

    @Test
    @DisplayName("게시물 삭제")
    void test8(){
        // given
        Long boardNum = 1L;
        Board board = new Board("제목", "게시글", new User());
        when(boardRepository.findById(boardNum)).thenReturn(Optional.of(board));
        BoardService boardService = new BoardService(boardRepository);

        // when
        boardService.deleteBoard(boardNum);

        // then
        verify(boardRepository, times(1)).delete(board);
    }
}
