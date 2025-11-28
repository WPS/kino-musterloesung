
artifact technischeschichtung
{
    // different architectures for different bounded contexts
    artifact layered
    {
        include "**/filmauswahl/**"
        
        relaxed artifact web
        {
            include "**/web/**"
        }
        
        relaxed artifact service
        {
            include "**/service/**"
        }
        
        relaxed artifact data
        {
            include "**/data/**"
        }
    }
    
    artifact hexagonal
    {
        include "**/kartenverkauf/**"
        
        artifact primary_adapters
        {
            include "**/adapters/primary/**"
            connect to primary_ports, domain.data
        }
        
        artifact secondary_adapters
        {
            include "**/adapters/secondary/**"
            connect to secondary_ports, domain.data
        }
        
        artifact application_services
        {
            include "**/application/services/**"
            include "**/application/fixtures/**"
            connect to primary_ports, secondary_ports, domain
        }
        
        artifact primary_ports
        {
            include "**/application/ports/primary/**"
            connect to domain.data
        }
        
        artifact secondary_ports
        {
            include "**/application/ports/secondary/**"
            connect to domain.data
        }
        
        artifact domain
        {
            include "**/application/domain/**"
            
            // data access required for two-way-mapping
            interface data
            {
                include "JavaHasAnnotation: org.jmolecules.ddd.annotation.AggregateRoot"
                include "JavaHasAnnotation: org.jmolecules.ddd.annotation.Entity"
                include "JavaHasAnnotation: org.jmolecules.ddd.annotation.ValueObject"
                include "JavaHasAnnotation: org.jmolecules.event.annotation.DomainEvent"
            }
        }
    }
    
    public artifact common
    {
        include "**/common/**"
    }
}

