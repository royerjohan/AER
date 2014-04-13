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
package controllers.ajax.membre;

import models.Groupe;
import controllers.membre.SecuredMembre;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.membre.ajax.observation;
import views.html.membre.ajax.listeEspeces;

public class DeposerObservation extends Controller {
	
	@Security.Authenticated(SecuredMembre.class)
	public static Result main(Integer observation_position){
		return ok(observation.render(observation_position));
	}
	
	@Security.Authenticated(SecuredMembre.class)
	public static Result getListeEspeces(Integer groupe_id){
		Groupe groupe = Groupe.find.byId(groupe_id);
		return ok(listeEspeces.render(groupe));
	}
}
