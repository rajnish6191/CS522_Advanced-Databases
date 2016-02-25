package std;

import id.UniID;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Client;

import com.sun.corba.se.pept.transport.Connection;
import com.sun.java.util.jar.pack.ConstantPool.Index;
import com.sun.org.apache.bcel.internal.Constants;

import es.LoadStatIndexer;



public final class BulkStdAndInsertMCA {
	
	private static final char SEP			= '¬';
	private static final char GIVEN_SEP 	= '|';
	private static final int COL 			= 31;
	private static final String LOAD_INDEX	= "load_stats";
	private static final long RESUME_FROM 	= UniQueKey.resumer();
	public static Cleansing cleaner = new Cleansing();
	
    public BulkStdAndInsertMCA() {};
		
	public static String removeMultipleSpaces(String data) {
		data = StringUtils.replaceChars(data, '.', ' ');
		Pattern pattern = Pattern.compile("\\s+");
		Matcher matcher = pattern.matcher(data);
		data = matcher.replaceAll(" ");
		data = StringUtils.trim(data);
		return data;
	}
		public static String replaceTermsInCompanyName(String CompanyName) {
		
		String arr[] = StringUtils.splitPreserveAllTokens(CompanyName.toUpperCase(), " ");
		for(int i=0; i<arr.length; i++) {
			
			if(cleaner.conversionLookUp.containsKey(arr[i])) {
				
				System.out.println("CompanyName" + cleaner.conversionLookUp.size());
				arr[i] = cleaner.conversionLookUp.get(arr[i]);
			}
		}
		
		StringBuilder builder = new StringBuilder();
		for(String s : arr) {
		    builder.append(s);
		    builder.append(" ");
		}
		
		return builder.toString();
		
	}
     	public static void main(final String filePath, final String indexName) {
		
		Client client = Connection.getConnection();
		long start = System.currentTimeMillis();
				
		int count 						= 0;
	    String resumeFrom				= "";
					
		
		String inquiryFileName = filePath.substring(0, filePath.lastIndexOf('.')) + "_Inquiry.txt";
		
		try {
			
			BufferedReader bufferedRdr = new BufferedReader(new FileReader(filePath));
			
			BufferedWriter inquiryWtr = new BufferedWriter(new FileWriter(inquiryFileName, true));

			String cnsRecord = "";
			List<UniCns> listCns = new ArrayList<UniCns>();
			
			while ((cnsRecord = bufferedRdr.readLine()) != null) {
				
				cnsRecord = cnsRecord.replace(GIVEN_SEP, SEP);
				
				cnsRecord = removeMultipleSpaces(cnsRecord);
			
				UniCns cns 				 = new UniCns();
				UniAddress addr 		 = new UniAddress();
				UniAddress corAddr		 = new UniAddress();
				UniName name 			 = new UniName();
				UniID id 				 = new UniID();
				UniDOB dob 				 = new UniDOB();
				UniPhone phn 			 = new UniPhone();
				UniDetails details 		 = new UniDetails();
				List<UniName> nameList   = new ArrayList<UniName>();
				 

				if (StringUtils.isNotBlank(cnsRecord)) {
					
					String arr[] = StringUtils.splitPreserveAllTokens(cnsRecord, SEP);
					count++;
					
			    	  if(arr.length != COL && arr.length != COL-1)
					{            
			    		  System.out.println("actual length" + arr.length);
			    		  
			    		  inquiryWtr.write(cnsRecord + SEP + resumeFrom);
			    		  inquiryWtr.newLine();
			    		  cns.setCns_key(resumeFrom);
			    		// name.setName(arr[1].toUpperCase());
			    		 // name.setName("");
			    		  name.setCnsType(Constants.CONSUMER);
			    		  
			    		  // if(arr.length == 24){
			    	      if(arr.length == 23 || arr.length == 24){
			    	    	  
			    	    	  System.out.println("mode 31 file" + arr.length);
			    	    	    
			    	    	    inquiryWtr.write(cnsRecord + SEP + resumeFrom);
								inquiryWtr.newLine();
							
							    cns.setCns_key(resumeFrom);
							    name.setName(arr[1].toUpperCase());
							    name.setCnsType(Constants.CONSUMER);
			    	    	    id.setUserGivenEM1(arr[17].replace(' ', '.'));//EMAIL
			    	    	    addr.setAddress(arr[11] + " " + arr[12] + " " + arr[13] + " " + arr[14] + " " + arr[16]);
								addr.setCity(arr[13]);
								addr.setZip(arr[16]);
								
			    	    	    details.setCin(arr[0]);
			    	    	//  arr[1] = replaceTermsInCompanyName(arr[1]);
			    	    	    details.setCompany_name(arr[1]);
								details.setRoc_code(arr[2]);
								details.setRegistration_number(arr[3]);
								details.setCompany_category(arr[4]);
								details.setCompany_subcategory(arr[5]);
								details.setClass_of_company(arr[6]);
								details.setAuthorised_capital(arr[7]);
								details.setPaid_up_capital(arr[8]);
								details.setNumber_of_memebers(arr[9]);
								details.setDate_of_incorporation(arr[10]);
								details.setCountry(arr[15]);
								details.setWhether_listed_or_not(arr[18]);
								details.setDate_of_last_AGM(arr[19]);
								details.setDate_of_balance_sheet(arr[20]);
								details.setComapny_status(arr[21]);
								 
								nameList.add(name);
				    	  		
								cns.setUniAddress(addr);
								cns.setCorresAddr(corAddr);
								cns.setUniDOB(dob);
								cns.setUniId(id);
								cns.setUniName(nameList);
								cns.setUniPhone(phn);
								cns.setUniDetails(details);
				    		  
				    		  CnsParse std = new CnsParse();
				    		  Iterator<UniCns> itr = std.parse(cns).iterator();
				    		  
				    		  while (itr.hasNext()) {
									listCns.add(itr.next());
							  } 
					
			    	      } else if(arr.length <= 10){
			    	    	  
			    	    	  System.out.println("mode 39 file" + arr.length);
			    	          
			    	    	  details.setCin(arr[0]);
			    	    	  id.setUserGivenOID(arr[1]);
			    	    	  details.setFull_name(arr[2]);
			    	    	  
			    	    	  if(arr.length > 3) {
			    	    		  
			    	    		  System.out.print("nullcheck"+ " " +arr.length);
			    	    		  details.setPresent_residential_address(arr[3]);
				    	    	  details.setDesignation(arr[4]);
				    	    	  details.setDate_of_appointment(arr[5]);
				    	    	  details.setWhether_dsc_registered(arr[6]);
				    	    	  details.setExpiry_date_of_dsc(arr[7]);
				    	    	  
				    	    	    nameList.add(name);
					    	  		
									cns.setUniAddress(addr);
									cns.setCorresAddr(corAddr);
									cns.setUniDOB(dob);
									cns.setUniId(id);
									cns.setUniName(nameList);
									cns.setUniPhone(phn);

									cns.setUniDetails(details);
					    		  
					    		  CnsParse std = new CnsParse();
					    		  Iterator<UniCns> itr = std.parse(cns).iterator();
					    		  
					    		  while (itr.hasNext()) {
										listCns.add(itr.next());
								  } 
				    	   	}
			    	     }
			           }  
			    	  
			    	  else if((StringUtils.isBlank(arr[10]))
								&&(StringUtils.isBlank(arr[17]))
								&&(StringUtils.isBlank(arr[11]+arr[12]+arr[13]+arr[14]+arr[16]))) {
							
							inquiryWtr.write(resumeFrom + SEP + cnsRecord  + SEP + (new Date(System.currentTimeMillis())).toString().replace("IST ", ""));
							inquiryWtr.newLine();
						}
			    	 else { 
					 	System.out.print("mode31mode39" +  " " + arr.length);
						inquiryWtr.write(cnsRecord + SEP + resumeFrom);
						inquiryWtr.newLine();
					
					    cns.setCns_key(resumeFrom);
						name.setName(arr[1].toUpperCase());
					    name.setCnsType(Constants.CONSUMER);
						name.setGender("");
						addr.setAddress(arr[11] + " " + arr[12] + " " + arr[13] + " " + arr[14] + " " + arr[16]);
						addr.setCity(arr[13]);
						addr.setZip(arr[16]);
						corAddr.setAddress("");
						corAddr.setCity("");
						corAddr.setZip("");
						dob.setDob("");
						dob.setAge("");
						dob.setAgeAsOn("");
						
						id.setUserGivenID1("");//DL
						id.setUserGivenID2("");//VOTER
						id.setUserGivenID3("");//PASSPORT
						id.setUserGivenID4("");//PAN
						id.setUserGivenID5("");//RATION
						id.setUserGivenID6("");//UID
						id.setUserGivenEM1(arr[17].replace(' ', '.'));//EMAIL
						id.setUserGivenEM2("");//EMAIL
						id.setUserGivenOID(arr[22]);//OTHER
						id.setUserGivenAcNo("");//ACCOUNT Number
						
						details.setCin(arr[0]);
				      //details.setCompany_name(replaceTermsInCompanyName(arr[1]));
						details.setCompany_name(arr[1]);
						details.setRoc_code(arr[2]);
						details.setRegistration_number(arr[3]);
						details.setCompany_category(arr[4]);
						details.setCompany_subcategory(arr[5]);
						details.setClass_of_company(arr[6]);
						details.setAuthorised_capital(arr[7]);
						details.setPaid_up_capital(arr[8]);
						details.setNumber_of_memebers(arr[9]);
						details.setDate_of_incorporation(arr[10]);
						details.setCountry(arr[15]);
						details.setWhether_listed_or_not(arr[18]);
						details.setDate_of_last_AGM(arr[19]);
						details.setDate_of_balance_sheet(arr[20]);
						details.setComapny_status(arr[21]);
						
						details.setFull_name(arr[23]);
						details.setPresent_residential_address(arr[24]);
						details.setDesignation(arr[25]);
						details.setDate_of_appointment(arr[26]);
						details.setWhether_dsc_registered(arr[27]);
						details.setExpiry_date_of_dsc(arr[28]);
						
			          	nameList.add(name);
	
						cns.setUniAddress(addr);
						cns.setCorresAddr(corAddr);
						cns.setUniDOB(dob);
						cns.setUniId(id);
						cns.setUniName(nameList);
						cns.setUniPhone(phn);
						cns.setUniDetails(details);

						CnsParse std = new CnsParse();
						Iterator<UniCns> itr = std.parse(cns).iterator();
						
						while (itr.hasNext()) {
							listCns.add(itr.next());
						                      }   
						}
             	}	
				if (count % 1000 == 0) {
					
					System.out.println(count + " completed. Time in mS " + (System.currentTimeMillis() - start));
					new Index().bulkIndexDoc(listCns, client, indexName);
					
					listCns = new ArrayList<UniCns>();
					inquiryWtr.flush();
					
					UniQueKey.resume_Writer(count);
				}
			}
				
			new Index().bulkIndexDoc(listCns, client, indexName);
		
			UniQueKey.resume_Writer(count);
			LoadStatIndexer.loadStatIndexer(client, LOAD_INDEX, filePath, new Date(System.currentTimeMillis()).toString().replace("IST ",""), count);

			
			bufferedRdr.close();
			inquiryWtr.close();
			
			System.out.println("Total inserted records : " + count + " in : " + ((System.currentTimeMillis() - start)/1000) + " Seconds");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		client.close();
	}
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	
	public static void main(String[] args) {	
	       main("./src/input/Output_2.txt", "mca");	
	}
}