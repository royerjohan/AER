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

import java.io.IOException;

import javax.naming.NamingException;

import models.Espece;
import models.EspeceSynonyme;
import models.Groupe;
import models.Image;
import controllers.admin.Admin;
import functions.UploadImage;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.expert.editerInsectes;
import views.html.expert.ajax.editerInsectesAjax;

public class EditerInsectes extends Controller {

	public static Result main(Integer groupe_id){
		Groupe groupe = Groupe.find.byId(groupe_id);
		if(MenuExpert.isExpertOn(groupe))
			return ok(editerInsectes.render(groupe));
		else
			return Admin.nonAutorise();
	}

	public static Result edit(Integer groupe_id, Integer espece_id){
		Groupe groupe = Groupe.find.byId(groupe_id);
		if(MenuExpert.isExpertOn(groupe)){
			Espece espece = Espece.find.byId(espece_id);
			return ok(editerInsectesAjax.render(espece));
		}else
			return Admin.nonAutorise();
	}

	public static Result changerNom(Integer espece_id){
		if(MenuExpert.isExpertConnected()){
			Espece espece = Espece.find.byId(espece_id);
			DynamicForm df = DynamicForm.form().bindFromRequest();
			String nom = df.get("nom");
			if(Espece.find.where().eq("espece_nom",nom).findUnique()==null){
				espece.espece_nom=nom;
				espece.update();
				return redirect("/editerInsectes/"+espece.espece_sous_groupe.sous_groupe_groupe.groupe_id);
			}else{
				return badRequest("Un insecte portant le nom '"+nom+"' est déjà présent dans la base !");
			}

		}else
			return Admin.nonAutorise();
	}
	public static Result changerAuteur(Integer espece_id){
		if(MenuExpert.isExpertConnected()){
			Espece espece = Espece.find.byId(espece_id);
			DynamicForm df = DynamicForm.form().bindFromRequest();
			String auteur = df.get("auteur");
			espece.espece_auteur=auteur;
			espece.update();
			return redirect("/editerInsectes/"+espece.espece_sous_groupe.sous_groupe_groupe.groupe_id);
		}else
			return Admin.nonAutorise();
	}
	public static Result supprimerSynonyme(Integer synonyme_id){
		if(MenuExpert.isExpertConnected()){
			EspeceSynonyme synonyme = EspeceSynonyme.find.byId(synonyme_id);
			int groupe_id = synonyme.synonyme_espece.espece_sous_groupe.sous_groupe_groupe.groupe_id;
			synonyme.delete();
			return redirect("/editerInsectes/"+groupe_id);
		}else
			return Admin.nonAutorise();
	}
	public static Result ajouterSynonyme(Integer espece_id) throws NamingException{
		if(MenuExpert.isExpertConnected()){
			DynamicForm df = DynamicForm.form().bindFromRequest();
			String syn = df.get("synonyme");
			Espece espece = Espece.find.byId(espece_id);
			new EspeceSynonyme(syn,false,espece.espece_id).save();
			return redirect("/editerInsectes/"+espece.espece_sous_groupe.sous_groupe_groupe.groupe_id);
		}else
			return Admin.nonAutorise();
	}
	public static Result changerPhoto(Integer espece_id) throws IOException{
		if(MenuExpert.isExpertConnected()){
			MultipartFormData body = request().body().asMultipartFormData();
			Espece espece = Espece.find.byId(espece_id);
			FilePart fp = body.getFile("photo");
			Image photo = UploadImage.upload(fp);
			if(photo!=null){
				espece.espece_photo=photo;
				espece.update();
				return redirect("/editerInsectes/"+espece.espece_sous_groupe.sous_groupe_groupe.groupe_id);
			}else{
				return badRequest("Le fichier uploadé n'est pas un format d'image valide.");
			}
		}else
			return Admin.nonAutorise();
	}
}
