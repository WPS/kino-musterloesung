
artifact fachlicheschichtung
{
    include "backend/**"
    
    artifact filmauswahl
    {
        include "**/kino/filmauswahl/**"
    }
    
    artifact kartenverkauf
    {
        include "**/kino/kartenverkauf/**"
        
        relaxed artifact common
        {
            priority -1
            include "**"
        }
        
        artifact kartenverkauf
        {
            include "**/domain/kartenverkauf/**"
            connect to sitzplatzvergabe, vorstellungen
        }
        
        artifact sitzplatzvergabe
        {
            include "**/domain/sitzplatzvergabe/**"
            connect to vorstellungen
        }
        
        artifact vorstellungen
        {
            include "**/domain/vorstellungen/**"
        }
        
        artifact zahlung
        {
            include "**/domain/zahlung/**"
        }
    }
    
    public artifact common
    {
        include "**/kino/common/**"
    }
}
