package org.homemotion.dao.spi;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.homemotion.adapt.AbstractAdaptable;
import org.homemotion.dao.Identifiable;

@SuppressWarnings("unchecked")
@MappedSuperclass
public abstract class AbstractEntity extends AbstractAdaptable implements
		Identifiable {

	private static final long serialVersionUID = 7138564180438832596L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, updatable = false)
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdDT = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date updatedDT = createdDT;

	private String createdFrom = "N/A";

	private String updatedFrom = createdFrom;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public String getIdentifier() {
		return getClass().getName() + '-' + getId();
	}

	public String getName() {
		return name;
	}

	public void setName(String id) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public boolean isNew() {
		return this.id == null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + "]";
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getUpdatedDT() {
		return updatedDT;
	}

	public void setUpdatedDT(Date updatedDT) {
		if (updatedDT == null) {
			throw new IllegalArgumentException("updatedDT can not be null.");
		}
		this.updatedDT = updatedDT;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreatedDT() {
		return createdDT;
	}

	public void setCreatedDT(Date createdDT) {
		if (createdDT == null) {
			throw new IllegalArgumentException("createdDT can not be null.");
		}
		this.createdDT = createdDT;
	}

	@Column(nullable = false)
	public String getUpdatedFrom() {
		return updatedFrom;
	}

	@Column(nullable = false, updatable = false)
	public String getCreatedFrom() {
		return createdFrom;
	}

	void setCreatedFrom(String createdFrom) {
		this.createdFrom = createdFrom;
	}

	void setUpdatedFrom(String updatedFrom) {
		this.updatedFrom = updatedFrom;
	}

}
