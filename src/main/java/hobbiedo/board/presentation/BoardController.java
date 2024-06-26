package hobbiedo.board.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.board.application.ReplicaBoardService;
import hobbiedo.board.application.ReplicaCommentService;
import hobbiedo.board.dto.BoardDetailsResponseDto;
import hobbiedo.board.dto.CommentListResponseDto;
import hobbiedo.board.vo.BoardDetailsResponseVo;
import hobbiedo.board.vo.CommentListResponseVo;
import hobbiedo.board.vo.LatestBoardDetailsResponseVo;
import hobbiedo.global.api.ApiResponse;
import hobbiedo.global.api.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/crew/board")
@Tag(name = "Board", description = "소모임 게시판 서비스")
public class BoardController {

	private final ReplicaBoardService replicaBoardService;
	private final ReplicaCommentService boardInteractionService;

	// 게시글 상세 조회
	@GetMapping("/{boardId}")
	@Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회합니다.")
	public ApiResponse<BoardDetailsResponseVo> getPost(
		@PathVariable("boardId") Long boardId) {

		BoardDetailsResponseDto boardResponseDto = replicaBoardService.getBoard(boardId);

		return ApiResponse.onSuccess(
			SuccessStatus.GET_BOARD_SUCCESS,
			BoardDetailsResponseVo.boardDtoToDetailsVo(boardResponseDto)
		);
	}

	// 한 소모임의 최신 게시글 조회
	@GetMapping("/{crewId}/latest-board")
	@Operation(summary = "소모임 최신 게시글 조회", description = "소모임의 최신 게시글을 조회합니다.")
	public ApiResponse<LatestBoardDetailsResponseVo> getLatestPost(
		@PathVariable("crewId") Long crewId) {

		BoardDetailsResponseDto boardResponseDto = replicaBoardService.getLatestBoard(crewId);

		return ApiResponse.onSuccess(
			SuccessStatus.GET_LATEST_BOARD_SUCCESS,
			LatestBoardDetailsResponseVo.boardDtoToLatestDetailsVo(boardResponseDto)
		);
	}

	// 고정 게시글 조회
	@GetMapping("/{crewId}/pinned")
	@Operation(summary = "고정 게시글 조회", description = "해당 소모임의 고정 게시글을 조회합니다.")
	public ApiResponse<BoardDetailsResponseVo> getPinnedPost(
		@PathVariable("crewId") Long crewId) {

		BoardDetailsResponseDto boardResponseDto = replicaBoardService.getPinnedBoard(crewId);

		if (boardResponseDto == null) {
			return ApiResponse.onSuccess(
				SuccessStatus.GET_PINNED_BOARD_SUCCESS,
				null
			);
		}

		return ApiResponse.onSuccess(
			SuccessStatus.GET_PINNED_BOARD_SUCCESS,
			BoardDetailsResponseVo.boardDtoToDetailsVo(boardResponseDto)
		);
	}

	// 게시글 댓글 리스트 조회
	@GetMapping("/{boardId}/comment-list")
	@Operation(summary = "게시글 댓글 조회", description = "게시글에 등록된 댓글들을 조회합니다.")
	public ApiResponse<CommentListResponseVo> getCommentList(
		@PathVariable("boardId") Long boardId,
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable page) {

		CommentListResponseDto commentListResponseDto = boardInteractionService.getCommentList(
			boardId, page);

		return ApiResponse.onSuccess(
			SuccessStatus.GET_COMMENT_LIST_SUCCESS,
			CommentListResponseVo.commentListToVo(commentListResponseDto)
		);
	}
}
