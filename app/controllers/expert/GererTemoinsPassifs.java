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
package controllers.expert;

import models.Membre;
import controllers.admin.Admin;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.expert.gererTemoinsPassifs;

public class GererTemoinsPassifs extends Controller {

	public static Result main(){
		if(MenuExpert.isExpertConnected()){
			return ok(gererTemoinsPassifs.render(""));
		}else
			return Admin.menuAdmin();
	}

	public static Result ajouter(){
		if(MenuExpert.isExpertConnected()){
			DynamicForm df = DynamicForm.form().bindFromRequest();
			String civilite = df.get("civilite");
			String nom = df.get("nom");
			if(Membre.find.where().eq("membre_nom",nom).findUnique()!=null)
				return ok(gererTemoinsPassifs.render("Quelqu'un portant le nom '"+nom+"' est déjà référencé. Si par hasard ces deux personnes différentes ont le même nom, rajoutez (2) par exemple pour les différencier."));
			String email = df.get("email");
			if(!email.equals("")){
				if(Membre.find.where().eq("membre_email",email).findUnique()!=null)
					return ok(gererTemoinsPassifs.render("Cette adresse mail est déjà utilisée par un autre membre."));
			}
			String adresse = df.get("adresse");
			String complement = df.get("complement");
			String cp = df.get("cp");
			String ville = df.get("ville");
			String pays = df.get("pays");
			String journais = df.get("journais");
			String moisnais = df.get("moisnais");
			String annenais = df.get("annenais");
			String jourdece = df.get("jourdece");
			String moisdece = df.get("moisdece");
			String annedece = df.get("annedece");
			String biographie = df.get("biographie");
			new Membre(civilite,nom,email,adresse,complement,cp,ville,pays,journais,moisnais,annenais,jourdece,moisdece,annedece,biographie).save();
			return redirect("/gererTemoinsPassifs");
		}else
			return Admin.menuAdmin();
	}
}
