package managedbeans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Conformance;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import org.hl7.fhir.instance.model.api.IBaseBundle;

/**
 *
 * @author a
 */
@Named(value = "serverbean")
@SessionScoped
public class serverbean implements Serializable {

    private Map<String, Patient> myPatients = new HashMap<String, Patient>();
    private List <Patient> patients =new ArrayList<Patient>();
    
    
    private int myNextId = 2;
    private String selectedserver;
    private String constatus;
    private String serverstatuscolor;
    private IGenericClient client;
    
    private String updatesearchvalue;
    private String updatepatientid;
    private String updatefindstatus;
    private String updatepatientgivenname;
    private String updatepatientfamilyname;
    
    private String updatefoundtext;
    private String updatedstatus;
    private String spinnercss;
    
    private String Jsonstring;
    private String selectedKeyword;
    private String keywordLabel = "Name";   
    private String keywordInput;

    public String getSpinnercss() {
        return spinnercss;
    }

    public void setSpinnercss(String spinnercss) {
        this.spinnercss = spinnercss;
    }

    public String getJsonstring() {
        return Jsonstring;
    }

    public void setJsonstring(String Jsonstring) {
        this.Jsonstring = Jsonstring;
    }
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
    public String getUpdatedstatus() {
        return updatedstatus;
    }

    public void setUpdatedstatus(String updatedstatus) {
        this.updatedstatus = updatedstatus;
    }
    private FhirContext ctx;
    private String updatesearchresult;

    public String getUpdatesearchresult() {
        return updatesearchresult;
    }

    public void setUpdatesearchresult(String updatesearchresult) {
        this.updatesearchresult = updatesearchresult;
    }

    public String getUpdatepatientgivenname() {
        return updatepatientgivenname;
    }

    public void setUpdatepatientgivenname(String updatepatientgivenname) {
        this.updatepatientgivenname = updatepatientgivenname;
    }

    public String getUpdatepatientfamilyname() {
        return updatepatientfamilyname;
    }

    public void setUpdatepatientfamilyname(String updatepatientfamilyname) {
        this.updatepatientfamilyname = updatepatientfamilyname;
    }

    public String getUpdatesearchvalue() {
        return updatesearchvalue;
    }

    public void setUpdatesearchvalue(String updatesearchvalue) {
        this.updatesearchvalue = updatesearchvalue;
    }

    public String getUpdatepatientid() {
        return updatepatientid;
    }

    public void setUpdatepatientid(String updatepatientid) {
        this.updatepatientid = updatepatientid;
    }

    public String getUpdatefindstatus() {
        return updatefindstatus;
    }

    public void setUpdatefindstatus(String updatefindstatus) {
        this.updatefindstatus = updatefindstatus;
    }

    public String getUpdatefoundtext() {
        return updatefoundtext;
    }

    public void setUpdatefoundtext(String updatefoundtext) {
        this.updatefoundtext = updatefoundtext;
    }

    public IGenericClient getClient() {
        return client;
    }

    public void setClient(IGenericClient client) {
        this.client = client;
    }

    public String getServerstatuscolor() {
        return serverstatuscolor;
    }

    public void setServerstatuscolor(String serverstatuscolor) {
        this.serverstatuscolor = serverstatuscolor;
    }

    public String getConstatus() {
        return constatus;
    }

    public void setConstatus(String constatus) {
        this.constatus = constatus;
    }

    public String getSelectedserver() {
        return selectedserver;
    }

    public void setSelectedserver(String selectedserver) {
        this.selectedserver = selectedserver;
    }

    public Map<String, Patient> getMyPatients() {
        return myPatients;
    }

    public void setMyPatients(Map<String, Patient> myPatients) {
        this.myPatients = myPatients;
    }

    public int getMyNextId() {
        return myNextId;
    }

    public void setMyNextId(int myNextId) {
        this.myNextId = myNextId;
    }

    public String getSelectedKeyword() {
        return selectedKeyword;
    }

    public void setSelectedKeyword(String selectedKeyword) {
        this.selectedKeyword = selectedKeyword;
    }

    public String getKeywordLabel() {
        return keywordLabel;
    }

    public void setKeywordLabel(String keywordLabel) {
        this.keywordLabel = keywordLabel;
    }

    public String getKeywordInput() {
        return keywordInput;
    }

    public void setKeywordInput(String keywordInput) {
        this.keywordInput = keywordInput;
    }

    public FhirContext getCtx() {
        return ctx;
    }

    public void setCtx(FhirContext ctx) {
        this.ctx = ctx;
    }
    

    private static Map<String, Object> servers;

    static {
        servers = new LinkedHashMap<String, Object>();

        servers.put("Grahame Server", "http://fhir2.healthintersections.com.au/"); //label, value
        servers.put("Philip's Server", "http://138.68.152.105:9090/baseDstu2/");
        servers.put("Publıc Health Server", "http://fhirtest.uhn.ca/baseDstu2/");
        servers.put("NP Program Ltd Server", "http://fhir-dstu2-nprogram.azurewebsites.net/");
        servers.put("hsp consortium","https://api2.hspconsortium.org/careplan2/open/");
      
    }

    public Map<String, Object> getServers() {
        return servers;
    }

    public void connect() {
        try {
            
            ctx = FhirContext.forDstu2();
            client = ctx.newRestfulGenericClient(getSelectedserver());
            setSpinnercss("fa fa-spinner fa-4x fa-spin");
            setServerstatuscolor("btn btn-lg btn-warning");
            client.forceConformanceCheck();
            
            setConstatus("Connected");
            setSpinnercss("fa fa-spinner fa-4x fa-spin invisible");
            setServerstatuscolor("btn btn-lg btn-success disabled");
           // history();
            //update();
        } catch (Exception e) {
            System.out.print("Error foundjj: " + e);
            setConstatus("Fail");
            setSpinnercss("fa fa-spinner fa-4x fa-spin invisible");
            setServerstatuscolor("btn btn-lg btn-danger disabled");
        }

    }

  

    /**
     * Creates a new instance of serverbean
     *
     */
    public void create() {
        Patient patient = new Patient();
        // ..populate the patient object..
        patient.addIdentifier().setSystem("urn:system").setValue("12345");
        patient.addName().addFamily("Smith").addGiven("John");

        MethodOutcome outcome = client.create()
                .resource(patient)
                .prettyPrint()
                .encodedJson()
                .execute();
        IdDt id = (IdDt) outcome.getId();
        System.out.println("Got ID: " + id.getValue());
    }

    public void update() {

        if (!"".equals(getUpdatepatientid()))
        {
            try{
                Patient patient = new Patient();

                // patient.addIdentifier().setSystem("urn:system").setValue("16531");
                
                patient.addName().addFamily(getUpdatepatientfamilyname()).addGiven(getUpdatepatientgivenname());
               
                patient.setId("Patient/" + getUpdatepatientid());

                // Invoke the server update method
                MethodOutcome outcome = client.update()
                        .resource(patient)
                        .encodedJson()
                        .execute();
                System.out.println(outcome.getId());
                // The MethodOutcome object will contain information about the
                // response from the server, including the ID of the created
                // resource, the OperationOutcome response, etc. (assuming that
                // any of these things were provided by the server! They may not
                // always be)
                IdDt id = (IdDt) outcome.getId();
                System.out.println("Got ID: " + id.getValue());
                        setUpdatedstatus("btn btn-success");
            }
            catch(Exception e)
            {
                setUpdatedstatus("btn btn-danger");
                System.out.println(e.toString());
            }
        }
        
    }

    public void search() {
        String keyword = getKeywordLabel();        
        if(keyword.equals("Name")){
            System.out.println(keyword);
            searchByName();
        }if(keyword.equals("Id")){
            System.out.println(keyword);
            searchById();
        }if(keyword.equals("dob")){
            System.out.println(keyword);
            searchByDob();
        }
    }
    
    
    public void searchByName(){
        String json = null;
        try {
            //Bundle for history in the server

            Bundle response = client.search()
            .forResource(Patient.class)
            .where(Patient.NAME.matches().value(getKeywordInput()))
            .returnBundle(Bundle.class)
            .execute();

            //patients=response.getAllPopulatedChildElementsOfType(Patient.class);

            setPatients(response.getAllPopulatedChildElementsOfType(Patient.class));
            IParser parser = ctx.newJsonParser().setPrettyPrint(true);
            json = parser.encodeResourceToString(response);
//            System.out.println(json);
//            System.out.println(patients.size());
//            Conformance conf = client.fetchConformance().ofType(Conformance.class).execute();
//            System.out.println(conf.getDescriptionElement().getValue());
//            System.out.println(patients.get(0).getName().get(0).getFamily());
            
        } catch (Exception e) {
            
        }
    }
    
    public void searchById(){
        System.out.println("search byid");
       String json = null;
        try {
            // Perform a search
            // search for patient 123 on example.com
            String url = getSelectedserver() + "/Patient/" + getKeywordInput();
           
            
            Bundle response = client.search()
            .forResource(Patient.class)
            .where(Patient.RES_ID.matches().value(getKeywordInput()))
            .returnBundle(ca.uhn.fhir.model.dstu2.resource.Bundle.class)
            .execute();     
            setPatients(response.getAllPopulatedChildElementsOfType(Patient.class));
            IParser parser = ctx.newJsonParser().setPrettyPrint(true);
            json = parser.encodeResourceToString(response);
            
    
           
        } catch (Exception e) {
            setUpdatesearchresult("invisible");
            setUpdatefindstatus("btn btn-danger");
            setUpdatefoundtext("Record could not find! Please check patient id");
            System.out.println("error" + e);
        }
    }
    
    public void searchByDob(){
        String json = null;
        
        try {
            //Bundle for history in the server

            Bundle response = client.search()
            .forResource(Patient.class)
            .where(Patient.BIRTHDATE.beforeOrEquals().day(getKeywordInput()))
            .returnBundle(ca.uhn.fhir.model.dstu2.resource.Bundle.class)
            .execute();

            //patients=response.getAllPopulatedChildElementsOfType(Patient.class);

            setPatients(response.getAllPopulatedChildElementsOfType(Patient.class));
            IParser parser = ctx.newJsonParser().setPrettyPrint(true);
            json = parser.encodeResourceToString(response);
            System.out.println(json);
            System.out.println(patients.size());
            Conformance conf = client.fetchConformance().ofType(Conformance.class).execute();
            System.out.println(conf.getDescriptionElement().getValue());
            System.out.println(patients.get(0).getName().get(0).getFamily());
            
        } catch (Exception e) {
            
        }
    }
    
    public void history()
    {
       String searchUrl = getSelectedserver()+"Patient/_history";
 

        
        String json = null;
        try{    
            //Bundle for history in the server

       Bundle response = client.search()
      .byUrl(searchUrl)
      .returnBundle(ca.uhn.fhir.model.dstu2.resource.Bundle.class)
      
      .execute();
            
//            Bundle response = client.history()
//               
//                .onType(Bundle.class)
//               .andReturnBundle(Bundle.class)
//       .preferResponseType(Patient.class)
//            
//              
//               .execute();
            setPatients(response.getAllPopulatedChildElementsOfType(Patient.class));
           
           
           
            IParser parser = ctx.newJsonParser().setPrettyPrint(true);
            json = parser.encodeResourceToString(response);
         // System.out.println(json);
//            System.out.println(patients.size());
            Conformance conf = client.fetchConformance().ofType(Conformance.class).execute();
//            System.out.println(conf.getDescriptionElement().getValue());
//            System.out.println(patients.get(0).getName().get(0).getFamily());
        }catch(Exception e)
        {
            System.out.println(e);
            
        }
        setJsonstring(json);
        
    }

    private static Map<String, Object> searchKeyword;
    static
    {
            searchKeyword = new LinkedHashMap<String, Object>();

            searchKeyword.put("Name", "name"); //label, value
            searchKeyword.put("Id", "id");
            searchKeyword.put("Date of Birth", "dob");
    }

    public Map<String, Object> getSearchKeyword()
    {
            return searchKeyword;
    }

    public void changeForm(){
        String key = getSelectedKeyword();
        if(key.equals("name")){
            setKeywordLabel("Name");
        }else if(key.equals("id")){
            setKeywordLabel("Id");
        }else{
            setKeywordLabel("Date Of Birth");
        }
    }   
    
    public serverbean() {

      
    }

}
