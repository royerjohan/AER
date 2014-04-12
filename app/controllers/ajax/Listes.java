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
package controllers.ajax;

import java.util.List;

import models.Commune;
import models.Membre;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.listeDesMembres;
import views.html.listeDesCommunes;
public class Listes extends Controller {
	
	public static Result membres(String string_id){
		return ok(listeDesMembres.render(string_id));
	}
	
	public static Result communes(String string_id){
		DynamicForm df= DynamicForm.form().bindFromRequest();
		String ville_nom_reel= df.get("ville_nom_reel");
		return ok(listeDesCommunes.render(string_id));
	}
	/**
	 * Renvoie une chaîne de caractère exploitable par la fonction
	 * d'autocomplétion
	 * @return
	 */
	public static String listeMembres(){
		List<Membre> membres = Membre.find.all();
		if(membres.isEmpty()){
			return "";
		}else{
			StringBuilder res = new StringBuilder();
			for(Membre m : membres){
				res.append("'"+m.toString().replaceAll("'","\\\\'")+"',");
			}
			res.deleteCharAt(res.length()-1);
			return res.toString();

		}
	}
	
	/**
	 *  * Renvoie une chaîne de caractère exploitable par la fonction
	 * d'autocomplétion
	 * 
	 * @return
	 */
	public static String listeCommunes(){
		List<Commune> communes = Commune.find.all();
		if(communes.isEmpty()){
			return "";
		}else{
			StringBuilder res = new StringBuilder();
			for(Commune c : communes){
				res.append("'"+c.toString().replaceAll("'","\\\\'")+"',");
			}
			res.deleteCharAt(res.length()-1);
			return res.toString();

		}
	}
}
