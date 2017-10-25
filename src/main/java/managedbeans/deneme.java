/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;



import java.io.IOException;
import java.util.List;

import ca.uhn.fhir.context.FhirContext;

import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;

import ca.uhn.fhir.model.dstu2.resource.*;

import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;

import ca.uhn.fhir.model.dstu2.valueset.IdentifierUseEnum;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.client.api.IRestfulClient;
import com.google.gson.Gson;

import com.phloc.commons.typeconvert.TypeConverter;
import java.util.ArrayList;
import java.util.Set;

public class deneme {

@SuppressWarnings("unused")
public static void main(String[] args) throws DataFormatException, IOException {
 List<Patient> patients;

 IGenericClient client;

Patient patient = new Patient();
patient.addIdentifier().setUse(IdentifierUseEnum.OFFICIAL).setSystem("urn:hapitest:mrns").setValue("7000135");
patient.addIdentifier().setUse(IdentifierUseEnum.SECONDARY).setSystem("urn:hapitest:mrns").setValue("3287486");

patient.addName().addFamily("Mahmut").addGiven("Mahmut").addGiven("Q").addSuffix("Junior");

patient.setGender(AdministrativeGenderEnum.MALE);




FhirContext ctx = FhirContext.forDstu2(); 
client = ctx.newRestfulGenericClient("http://fhirtest.uhn.ca/baseDstu2/");
//Bundle for history in the server
Bundle response2 = client.history()
   .onServer()
   .andReturnBundle(ca.uhn.fhir.model.dstu2.resource.Bundle.class)
   .execute();

//BUndle return for search with patient id;
//Bundle response = client.search()
//      .forResource(Patient.class)
//      .where(Patient.RES_ID.matches().value("31478"))
//      
//      .returnBundle(Bundle.class)
//      .execute();
//FhirContext ctx2 = FhirContext.forDstu2();
//IGenericClient client2=ctx2.newRestfulGenericClient("http://ec2-35-164-207-196.us-west-2.compute.amazonaws.com:8080/hapi-fhir-jpaserver-example/baseDstu2/");
//String url = "http://ec2-35-164-207-196.us-west-2.compute.amazonaws.com:8080/hapi-fhir-jpaserver-example/baseDstu2/" + "/Patient/_history";
//            Patient patientd = client2.read()
//                    .resource(Patient.class)
//                    .withUrl(url)
//                    .execute();

            
Gson gson = new Gson();
patients= new ArrayList<Patient>();
List<Observation> obs= new ArrayList<Observation>();
//patients=patient.getAllPopulatedChildElementsOfType(Patient.class);

 obs=response2.getAllPopulatedChildElementsOfType(Observation.class);
 IParser parser = ctx.newJsonParser().setPrettyPrint(true);
 String json = parser.encodeResourceToString(response2);
        System.out.println(json);
    System.out.println(obs.size());
Conformance conf = client.fetchConformance().ofType(Conformance.class).execute();
System.out.println(conf.getDescriptionElement().getValue());
System.out.println(obs.get(0).getId().getIdPart());
    

//String xmlEncoded = ctx.newXmlParser().encodeResourceToString(patient);
//String jsonEncoded = ctx.newJsonParser().encodeResourceToString(patient);
//
//Set<String> list = ca.uhn.fhir.util.CollectionUtil.newSet(jsonEncoded);
//
//
//        
//   
//       System.out.println(patients.size());
response2 = (Bundle) client.history().onServer().andReturnBundle(ca.uhn.fhir.model.dstu2.resource.Bundle.class).execute();
}
	
public void ara()
{
}
public interface MyClientInterface extends IRestfulClient
{
  /** A FHIR search */
  @Search
  public List<Patient> findPatientsByIdentifier(@RequiredParam(name="identifier") IdentifierDt theIdentifier);
	
  /** A FHIR create */
  @Create
  public MethodOutcome createPatient(@ResourceParam Patient thePatient);
	
}




}