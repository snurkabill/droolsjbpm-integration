package org.kie.services.client.serialization.jaxb.impl.process;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.jaxb.StringKeyStringValueMapXmlAdapter;
import org.kie.services.client.serialization.jaxb.rest.AbstractJaxbResponse;

@XmlRootElement(name="process-instance-with-vars-response")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(value={JaxbProcessInstance.class})
@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE, fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class JaxbProcessInstanceWithVariablesResponse extends AbstractJaxbResponse {

    @XmlElement
    @XmlJavaTypeAdapter(value=StringKeyStringValueMapXmlAdapter.class)
    private Map<String, String> variables;
    
    @XmlElement
    private JaxbProcessInstance processInstance;
    
    public JaxbProcessInstanceWithVariablesResponse() { 
        // Default Constructor
    }

    public JaxbProcessInstanceWithVariablesResponse(ProcessInstance processInstance, Map<String, String> vars) { 
        initialize(processInstance, vars);
    }

    public JaxbProcessInstanceWithVariablesResponse(ProcessInstance processInstance, Map<String, String> vars, String requestUrl) { 
        super(requestUrl);
        initialize(processInstance, vars);
    }
    
    protected void initialize(ProcessInstance processInstance, Map<String, String> vars) { 
        JaxbProcessInstance xmlProcessInstance;
        if( processInstance == null ) { 
            return;
        }
        if( ! (processInstance instanceof JaxbProcessInstance) ) { 
            xmlProcessInstance = new JaxbProcessInstance(processInstance);
        }  else { 
            xmlProcessInstance = (JaxbProcessInstance) processInstance;
        }
        this.processInstance = xmlProcessInstance;
        this.variables = vars;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public JaxbProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(JaxbProcessInstance jaxbProcessInstance) {
        this.processInstance = jaxbProcessInstance;
    }

}
