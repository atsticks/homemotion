package org.homemotion.dao.spi;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.homemotion.dao.NamedItem;


@SuppressWarnings("unchecked")
@MappedSuperclass
public abstract class AbstractNamedItem extends AbstractEntity 
implements NamedItem{

	private static final long serialVersionUID = 4469018092518974036L;
	
	@Column(nullable=false)
	private String name;

	private String description;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [description=" + description
				+ ", id=" + getId() + ", name=" + name + "]";
	}


}
