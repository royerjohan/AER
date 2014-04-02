/*********************************************************************************
 * 
 *   Copyright 2014 BOUSSEJRA Malik Olivier, HALDEBIQUE Geoffroy, ROYER Johan
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   
 ********************************************************************************/

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.persistence.PersistenceException;

import models.Espece;

import org.junit.*;

import functions.credentials.Credentials;
import play.mvc.*;
import play.test.*;
import play.libs.F.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

	Map<String, Object> databaseConfiguration = new HashMap<String, Object>();

    /**
     * add your integration test here
     */
    @Test
    public void test() {
    	databaseConfiguration.put("db.default.url", "jdbc:mysql://localhost:3306/aer");
        running(testServer(3333, fakeApplication(databaseConfiguration)), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
            	//Teste que les bons départements sont trouvés par l'application
                //System.out.println(models.Departement.findDepartementsAER());
                
                //ajouteEspece();
                
                Credentials.creeHashEtMotDePassePourToutLeMonde();
            }
        });
    }
    
    private void ajouteEspece(){
    	Espece e = new Espece("Rasgus pipus","Malik Olivier Boussejra, 2014",54,"GA!");
    	System.out.println(e);
    	try {
			e.ajouterNouvelleEspece(true, "Sous-famille des Rasgus");
		} catch (PersistenceException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	Espece es = new Espece("Rasgus pipus2","Malik Olivier Boussejra, 2014",54,"GA2!");
    	System.out.println(es);
    	try {
			es.ajouterNouvelleEspece(true, "Lymantriinae");
		} catch (PersistenceException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	Espece esp = new Espece("Rasgus pipus3","Malik Olivier Boussejra, 2014",54,"GA3!");
    	System.out.println(esp);
    	try {
			esp.ajouterNouvelleEspece(false, "Lymantriinae");
		} catch (PersistenceException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	Espece espe = new Espece("Rasgus pipus4","Malik Olivier Boussejra, 2014",54,"GA4!");
    	System.out.println(espe);
    	try {
			espe.ajouterNouvelleEspece(false, "Calopterygidae");
		} catch (PersistenceException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

}
