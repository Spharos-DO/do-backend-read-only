package hobbiedo.board.kafka.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import hobbiedo.board.application.ReplicaBoardService;
import hobbiedo.board.kafka.dto.BoardCreateEventDto;
import hobbiedo.board.kafka.dto.BoardDeleteEventDto;
import hobbiedo.board.kafka.dto.BoardUpdateEventDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

	private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
	private final ReplicaBoardService replicaBoardService;

	/**
	 * 게시글 생성 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-create-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "createKafkaListenerContainerFactory")
	public void listenToBoardCreateTopic(BoardCreateEventDto eventDto) {

		log.info("Received board create event: {}", eventDto);

		replicaBoardService.createReplicaBoard(eventDto);
	}

	/**
	 * 게시글 삭제 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-delete-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "deleteKafkaListenerContainerFactory")
	public void listenToBoardDeleteTopic(BoardDeleteEventDto eventDto) {

		replicaBoardService.deleteReplicaBoard(eventDto);
	}

	/**
	 * 게시글 수정 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-update-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "updateKafkaListenerContainerFactory")
	public void listenToBoardUpdateTopic(BoardUpdateEventDto eventDto) {

		replicaBoardService.updateReplicaBoard(eventDto);
	}
}