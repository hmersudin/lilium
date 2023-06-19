package com.lilium.jpatutorial.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor", date = "2023-06-19T21:53:13.740+0200")
@StaticMetamodel(DistributedEntity.class)
public abstract class DistributedEntity_ {

	public static volatile SingularAttribute<DistributedEntity, Long> createdTimestamp;
	public static volatile SingularAttribute<DistributedEntity, Long> modifiedTimestamp;
	public static volatile SingularAttribute<DistributedEntity, String> id;

	public static final String CREATED_TIMESTAMP = "createdTimestamp";
	public static final String MODIFIED_TIMESTAMP = "modifiedTimestamp";
	public static final String ID = "id";

}

