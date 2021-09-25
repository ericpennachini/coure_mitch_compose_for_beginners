package ar.com.mitch.composeforbeginners.app.domain.util

interface EntityMapper<Entity, DomainModel> {

    /**
     * Maps from an network entity to a domain model
     */
    fun mapFromEntity(entity: Entity): DomainModel

    /**
     * Maps from a domain model to an entity
     */
    fun mapToEntity(domainModel: DomainModel): Entity

}