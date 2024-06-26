package hobbiedo.crew.infrastructure;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import hobbiedo.crew.domain.Crew;

public interface ReplicaCrewRepository extends MongoRepository<Crew, String> {
	Optional<Crew> findByCrewId(long crewId);

	@Query("{ 'crewId' : ?0, 'crewMembers' : { $elemMatch: { 'uuid' : ?1 } } }")
	Optional<Crew> findCrewByCrewIdAndMemberUuid(Long crewId, String uuid);
}
