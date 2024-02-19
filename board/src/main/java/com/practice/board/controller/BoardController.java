package com.practice.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.practice.board.dto.BoardDTO;
import com.practice.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;

	@GetMapping("/save")
	public String save() {
		return "save";
	}

	@PostMapping("/save") // 글 저장하기
	public String save(BoardDTO boardDTO) {
		System.out.println("boardDTO = " + boardDTO);
		boardService.save(boardDTO);
		return "redirect:/list";
	}

//	@PostMapping("/save2") // 글 저장하기
//	public String save(BoardDTO boardDTO) {
//		System.out.println("boardDTO = " + boardDTO);
//		boardService.save(boardDTO);
//		return "index";
//	}

	@GetMapping("/list") // 글 목록 페이지로 이동
	public String getList(Model model) {
		List<BoardDTO> boardDTOList = boardService.getList();
		model.addAttribute("boardList", boardDTOList);
		System.out.println("boardDTOList : " + boardDTOList);
		return "list";
	}

	@GetMapping("/{id}") // 게시글 조회
	public String getPost(@PathVariable("id") Long id, Model model) {
		// 조회수
		boardService.updateHits(id);
		// 상세내용
		BoardDTO boardDTO = boardService.findById(id);
		model.addAttribute("board", boardDTO);
		System.out.println("boardDTO = " + boardDTO);
		return "detail";
	}

	@GetMapping("/update/{id}")
	public String getUpdate(@PathVariable("id") Long id, Model model) {
		BoardDTO boardDTO = boardService.findById(id);
		model.addAttribute(boardDTO);
		return "update";
	}

	@PostMapping("/update/{id}")
	public String postUpdate(BoardDTO boardDTO, Model model) {
		boardService.update(boardDTO);
		BoardDTO dto = boardService.findById(boardDTO.getId());
		model.addAttribute("board", dto);
		return "detail";
	}
	
	@GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/list";
    }
	
	

}
