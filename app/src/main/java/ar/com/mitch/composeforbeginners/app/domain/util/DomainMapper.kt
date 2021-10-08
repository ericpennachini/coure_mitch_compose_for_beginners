package ar.com.mitch.composeforbeginners.app.domain.util

interface DomainMapper<T, DomainModel> {

    /**
     * Maps from an network entity to a domain model
     */
    fun mapToDomainModel(model: T): DomainModel

    /**
     * Maps from a domain model to an entity
     */
    fun mapFromDomainModel(domainModel: DomainModel): T

}