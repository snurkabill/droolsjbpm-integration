package org.kie.server.remote.rest.drools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.kie.server.services.api.KieContainerCommandService;
import org.kie.server.services.api.KieServerApplicationComponentsService;
import org.kie.server.services.api.SupportedTransports;

public class DroolsRestApplicationComponentsService implements KieServerApplicationComponentsService {

    private static final String OWNER_EXTENSION = "Drools";

    @Override
    public Collection<Object> getAppComponents( String extension, SupportedTransports type, Object... services ) {
        // skip calls from other than owning extension
        if ( !OWNER_EXTENSION.equals(extension) ) {
            return Collections.emptyList();
        }

        KieContainerCommandService batchCommandService = null;
       
        for( Object object : services ) { 
            if( KieContainerCommandService.class.isAssignableFrom(object.getClass()) ) { 
               batchCommandService = (KieContainerCommandService) object; 
               break;
            } 
        }
        
        List<Object> components = new ArrayList<Object>(1);
        if( SupportedTransports.REST.equals(type) ) {
            components.add(new CommandResource(batchCommandService));
        }
        
        return components;
    }

}
