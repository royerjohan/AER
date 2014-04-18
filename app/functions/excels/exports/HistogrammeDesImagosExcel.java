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
package functions.excels.exports;

import java.io.IOException;
import java.util.Map;

import models.Espece;
import models.Groupe;
import models.SousGroupe;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import controllers.ajax.expert.requetes.calculs.HistogrammeDesImagos;
import functions.excels.Excel;

public class HistogrammeDesImagosExcel extends Excel {
	
	public HistogrammeDesImagosExcel(Map<String,String> info, HistogrammeDesImagos hdi) throws IOException{
		super();
		Sheet sheet = wb.createSheet("Histogramme des imagos");
		Espece espece = Espece.find.byId(Integer.parseInt(info.get("espece")));
		SousGroupe sous_groupe = SousGroupe.find.byId(Integer.parseInt(info.get("sous_groupe")));
		Groupe groupe = Groupe.find.byId(Integer.parseInt(info.get("groupe")));
		String maille = info.get("maille");
		String date1 = info.get("jour1")+"/"+info.get("mois1")+"/"+info.get("annee1");
		String date2 = info.get("jour2")+"/"+info.get("mois2")+"/"+info.get("annee2");
		String titre = "Diagramme représentant les imagos en fonction de la période de l'année ";
		if(espece!=null)
			titre+="de "+espece.espece_nom;
		else if(sous_groupe!=null)
			titre+="de "+sous_groupe;
		else if(groupe!=null)
			titre+="de "+groupe;
		if(!maille.equals(""))
			titre+=" dans la maille "+maille;
		titre+=" du "+date1+" au "+date2;
		titre+=" ("+hdi.getSomme()+" témoignages)";
		sheet.createRow(0).createCell(0).setCellValue(titre);
		sheet.addMergedRegion(new CellRangeAddress(
	            0, //first row (0-based)
	            0, //last row  (0-based)
	            0, //first column (0-based)
	            12  //last column  (0-based)
	    ));
		int i = 1;
		while(i<hdi.histogramme.length){
			Row row = sheet.createRow(i);
			row.createCell(0);
			row.createCell(1);
			sheet.getRow(i).getCell(0).setCellValue(hdi.legende.get(i));
			sheet.getRow(i).getCell(1).setCellValue(hdi.histogramme[i]);
			i++;
		}
	}
}