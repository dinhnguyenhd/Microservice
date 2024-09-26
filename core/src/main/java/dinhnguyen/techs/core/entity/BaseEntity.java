package dinhnguyen.techs.core.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class BaseEntity {

	@Version
	@Column(name = "version")
	private int version;

	@CreationTimestamp
	private LocalDateTime createdOn = LocalDateTime.now();

	@UpdateTimestamp
	private LocalDateTime lastUpdatedOn = LocalDateTime.now();

	@Override
	public int hashCode() {
		return Objects.hash(createdOn, lastUpdatedOn, version);
	}

	@Column(name = "approve")
	private String approve;

	@Column(name = "approve_by")
	private String approveBy;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		return Objects.equals(createdOn, other.createdOn) && Objects.equals(lastUpdatedOn, other.lastUpdatedOn)
				&& version == other.version;
	}

}
