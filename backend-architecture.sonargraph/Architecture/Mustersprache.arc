
artifact mustersprache
{
    
    artifact controllers
    {
        include "JavaHasAnnotation: org.springframework.web.bind.annotation.RestController"
        connect to applicationservices, aggregates, events, valueobjects, dtos, dtomappers
    }
    
    artifact dtomappers
    {
        include "**/web/mappers/**"
        connect to aggregates, innerentities, valueobjects, dtos
    }
    
    artifact dtos
    {
        include "**/web/model/**"
    }
    
    artifact fixtures
    {
        include "**/fixtures/**"
        connect to repositories, aggregates, innerentities, valueobjects
    }
    
    artifact applicationservices
    {
        include "**/application/services/**" // implementation
        include "**/application/ports/primary/**" // interface
        include "JavaHasAnnotation: de.wps.ddd.kino.common.architecture.ApplicationService" // interface
        connect to services, factories, repositories, aggregates, events, valueobjects
    }
    
    artifact repositories
    {
        include "**/persistence/repositories/**" // implementation
        include "JavaHasAnnotation: org.jmolecules.ddd.annotation.Repository" // interface
        connect to aggregates, valueobjects, jpaentities, entitymappers
    }
    
    artifact entitymappers
    {
        include "**/persistence/mappers/**"
        connect to aggregates, innerentities, valueobjects, jpaentities
    }
    
    artifact jpaentities
    {
        include "**/persistence/model/**"
    }
    
    artifact factories
    {
        include "JavaHasAnnotation: org.jmolecules.ddd.annotation.Factory"
        connect to aggregates, valueobjects
    }
    
    artifact services
    {
        include "JavaHasAnnotation: org.jmolecules.ddd.annotation.Service"
        connect to aggregates, innerentities, valueobjects
    }
    
    artifact aggregates
    {
        
        include "JavaHasAnnotation: org.jmolecules.ddd.annotation.AggregateRoot"
        connect to innerentities, valueobjects
    }
    
    artifact innerentities
    {
        include "JavaHasAnnotation: org.jmolecules.ddd.annotation.Entity"
        connect to valueobjects
    }
    
    artifact events
    {
        include "JavaHasAnnotation: org.jmolecules.event.annotation.DomainEvent"
        connect to valueobjects
    }
    
    artifact valueobjects
    {
        include "JavaHasAnnotation: org.jmolecules.ddd.annotation.ValueObject"
    }
    
    artifact configs
    {
        include "**/config/**"
    }
}

