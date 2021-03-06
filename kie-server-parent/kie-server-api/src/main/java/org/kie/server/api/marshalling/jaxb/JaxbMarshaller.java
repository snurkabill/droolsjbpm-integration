package org.kie.server.api.marshalling.jaxb;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.common.DefaultFactHandle;
import org.drools.core.runtime.impl.ExecutionResultImpl;
import org.kie.server.api.commands.CallContainerCommand;
import org.kie.server.api.commands.CommandScript;
import org.kie.server.api.commands.CreateContainerCommand;
import org.kie.server.api.commands.DisposeContainerCommand;
import org.kie.server.api.commands.GetContainerInfoCommand;
import org.kie.server.api.commands.GetScannerInfoCommand;
import org.kie.server.api.commands.GetServerInfoCommand;
import org.kie.server.api.commands.ListContainersCommand;
import org.kie.server.api.commands.UpdateReleaseIdCommand;
import org.kie.server.api.commands.UpdateScannerCommand;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallingException;
import org.kie.server.api.marshalling.ModelWrapper;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieContainerResourceList;
import org.kie.server.api.model.KieContainerStatus;
import org.kie.server.api.model.KieServerConfig;
import org.kie.server.api.model.KieServerConfigItem;
import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.ServiceResponsesList;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.definition.ProcessDefinitionList;
import org.kie.server.api.model.instance.NodeInstance;
import org.kie.server.api.model.instance.NodeInstanceList;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.ProcessInstanceList;
import org.kie.server.api.model.instance.TaskEventInstance;
import org.kie.server.api.model.instance.TaskEventInstanceList;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.api.model.instance.TaskSummaryList;
import org.kie.server.api.model.instance.VariableInstance;
import org.kie.server.api.model.instance.VariableInstanceList;
import org.kie.server.api.model.instance.WorkItemInstance;
import org.kie.server.api.model.instance.WorkItemInstanceList;
import org.kie.server.api.model.type.JaxbList;
import org.kie.server.api.model.type.JaxbMap;

public class JaxbMarshaller implements Marshaller {
    public static final Class<?>[] KIE_SERVER_JAXB_CLASSES;

    static {
        KIE_SERVER_JAXB_CLASSES = new Class<?>[]{
                CallContainerCommand.class,
                CommandScript.class,
                CreateContainerCommand.class,
                DisposeContainerCommand.class,
                ListContainersCommand.class,
                GetContainerInfoCommand.class,
                GetScannerInfoCommand.class,
                GetServerInfoCommand.class,
                UpdateScannerCommand.class,
                UpdateReleaseIdCommand.class,

                KieContainerResource.class,
                KieContainerResourceList.class,
                KieContainerStatus.class,
                KieServerInfo.class,
                ReleaseId.class,
                ServiceResponse.class,
                ServiceResponsesList.class,

                BatchExecutionCommandImpl.class,
                ExecutionResultImpl.class,
                DefaultFactHandle.class,

                KieServerConfig.class,
                KieServerConfigItem.class,

                JaxbList.class,
                JaxbMap.class,

                ProcessDefinition.class,
                ProcessDefinitionList.class,

                ProcessInstance.class,
                ProcessInstanceList.class,

                NodeInstance.class,
                NodeInstanceList.class,

                VariableInstance.class,
                VariableInstanceList.class,

                TaskInstance.class,
                TaskSummary.class,
                TaskSummaryList.class,

                TaskEventInstance.class,
                TaskEventInstanceList.class,

                WorkItemInstance.class,
                WorkItemInstanceList.class
        };
    }

    private final JAXBContext jaxbContext;

    private final ClassLoader classLoader;

    private final javax.xml.bind.Marshaller marshaller;
    private final Unmarshaller              unmarshaller;

    public JaxbMarshaller(Set<Class<?>> classes, ClassLoader classLoader) {
        this.classLoader = classLoader;
        try {
            Set<Class<?>> allClasses = new HashSet<Class<?>>();

            allClasses.addAll(Arrays.asList(KIE_SERVER_JAXB_CLASSES));
            if (classes != null) {
                allClasses.addAll(classes);
            }

            this.jaxbContext = JAXBContext.newInstance( allClasses.toArray(new Class[allClasses.size()]) );
            this.marshaller = jaxbContext.createMarshaller();
            this.unmarshaller = jaxbContext.createUnmarshaller();
        } catch ( JAXBException e ) {
            throw new MarshallingException( "Error while creating JAXB context from default classes!", e );
        }
    }

    @Override
    public String marshall(Object input) {
        StringWriter writer = new StringWriter();
        try {
            marshaller.marshal(ModelWrapper.wrap(input), writer );
        } catch ( JAXBException e ) {
            throw new MarshallingException( "Can't marshall input object: "+input, e );
        }
        return writer.toString();
    }

    @Override
    public <T> T unmarshall(String input, Class<T> type) {
        try {
            return (T) unmarshaller.unmarshal( new StringReader( input ) );
        } catch ( JAXBException e ) {
            throw new MarshallingException( "Can't unmarshall input string: "+input, e );
        }
    }


    @Override
    public void dispose() {

    }
}
