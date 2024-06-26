package hobbiedo.board.domain;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

@Document(collection = "replica_board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplicaBoard {

	@Id
	private String id;
	private Long boardId;
	private Long crewId;
	private String content;
	private String writerUuid;
	private boolean pinned;
	private Instant createdAt;
	private boolean updated;
	private List<String> imageUrls;
	private Integer likeCount;
	private Integer commentCount;

	@Builder
	public ReplicaBoard(String id, Long boardId, Long crewId, String content, String writerUuid,
		boolean pinned,
		Instant createdAt, boolean updated, List<String> imageUrls, Integer likeCount,
		Integer commentCount) {
		this.id = id;
		this.boardId = boardId;
		this.crewId = crewId;
		this.content = content;
		this.writerUuid = writerUuid;
		this.pinned = pinned;
		this.createdAt = createdAt;
		this.updated = updated;
		this.imageUrls = imageUrls;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
	}
}
