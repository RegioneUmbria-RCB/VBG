
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Manager.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;
using VBG.DatiDinamici;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class IAttivitaDyn2DatiStoricoMgr //: IIAttivitaDyn2DatiStoricoManager
    {
      //  public IEnumerable<IAttivitaDyn2DatiStorico> GetValoriCampiDaIdModello(string idComune, int idAttivita, int idModello, int indiceModello, int idVersioneStorico)
      //  {
      //      var sql = $@"select 
						//	i_attivitadyn2dati_storico.* 
						//from 
						//	i_attivitadyn2dati_storico
						//WHERE
						//	i_attivitadyn2dati_storico.idcomune = {db.Specifics.QueryParameterName("idComune")} AND				
						//	i_attivitadyn2dati_storico.fk_d2mt_id = {db.Specifics.QueryParameterName("idModello")} and
						//	i_attivitadyn2dati_storico.fk_ia_id = {db.Specifics.QueryParameterName("idAttivita")} AND
      //                      i_attivitadyn2dati_storico.idversione = {db.Specifics.QueryParameterName("idVersioneStorico")} and
						//	i_attivitadyn2dati_storico.indice = {db.Specifics.QueryParameterName("indiceModello")}
						//order by 
						//	i_attivitadyn2dati_storico.indice_molteplicita asc";

      //      return this.db.ExecuteReader(sql,
      //          mp =>
      //          {
      //              mp.AddParameter("idComune", idComune);
      //              mp.AddParameter("idModello", idModello);
      //              mp.AddParameter("idAttivita", idAttivita);
      //              mp.AddParameter("idVersioneStorico", idVersioneStorico);
      //              mp.AddParameter("indiceModello", indiceModello);
      //          },
      //          dr => new IAttivitaDyn2DatiStorico
      //          {
      //              FkIaId = idAttivita,
      //              FkD2cId = dr.GetInt("fk_d2c_id"),
      //              FkD2mtId = idModello,
      //              Idcomune = idComune,
      //              Idversione = idVersioneStorico,
      //              Indice = indiceModello,
      //              IndiceMolteplicita = dr.GetInt("indice_molteplicita"),
      //              Valore = dr.GetString("valore")
      //          });
      //  }


        //public void SalvaStoricoModello(string idComune, int idAttivita, ModelloDinamicoBase modello)
        //{
        //    // Carico le righe modificate
        //    List<IAttivitaDyn2DatiStorico> righeStorico = new List<IAttivitaDyn2DatiStorico>();
        //    var mgr = new IAttivitaDyn2DatiMgr(this.db);

        //    foreach (var riga in modello.Righe)
        //    {
        //        for (int j = 0; j < riga.NumeroColonne; j++)
        //        {
        //            if (riga[j] == null) continue;

        //            var valoriDb = mgr.GetValoriCampoNoIndice(idComune, idAttivita, riga[j].Id);

        //            int indiceMin = int.MaxValue;

        //            if (valoriDb.Count == 0)
        //                indiceMin = 0;

        //            foreach (var valoreCampo in valoriDb)
        //            {
        //                var fkD2cId = valoreCampo.FkD2cId.Value;
        //                var indice = valoreCampo.Indice.GetValueOrDefault(0);
        //                var indiceMolteplicita = valoreCampo.IndiceMolteplicita.GetValueOrDefault(0);
        //                var valore = valoreCampo.Valore;
        //                var valoreDecodificato = valoreCampo.Valoredecodificato;

        //                if (indiceMin > indiceMolteplicita)
        //                    indiceMin = indiceMolteplicita;

        //                IAttivitaDyn2DatiStorico rigaStorico = new IAttivitaDyn2DatiStorico
        //                {
        //                    Idcomune = idComune,
        //                    FkIaId = idAttivita,
        //                    FkD2mtId = modello.IdModello,
        //                    FkD2cId = fkD2cId,
        //                    Indice = indice,
        //                    IndiceMolteplicita = indiceMolteplicita,
        //                    Valore = String.IsNullOrEmpty(valoreDecodificato) ? valore : valoreDecodificato

        //                };
        //                righeStorico.Add(rigaStorico);
        //            }
        //        }
        //    }

        //    // Se non è stata caricata nessuna riga allora non salvo la versione perchè sarebbe un modello vuoto
        //    if (righeStorico.Count == 0)
        //        return;

        //    // Preparo il salvataggio della testata
        //    var testataStoricoMgr = new IAttivitaDyn2ModelliTStoricoMgr(db);
        //    var righeStoricoMgr = new IAttivitaDyn2DatiStoricoMgr(db);

        //    // Salvo una nuova riga in IAttivitaDyn2Modellit_storico
        //    var testataStorico = new IAttivitaDyn2ModelliTStorico
        //    {
        //        Idcomune = idComune,
        //        FkIaId = idAttivita,
        //        FkD2mtId = modello.IdModello
        //    };

        //    testataStorico = testataStoricoMgr.Insert(testataStorico);

        //    for (int i = 0; i < righeStorico.Count; i++)
        //    {
        //        righeStorico[i].Idversione = testataStorico.Idversione;

        //        righeStoricoMgr.Insert(righeStorico[i]);
        //    }
        //}



        //      #region IIAttivitaDyn2DatiStoricoManager Members

        //      public List<IIAttivitaDyn2DatiStorico> GetValoriCampo(string idComune, int codiceAttivita, int codiceCampo, int indiceModello, int idVersioneStorico)
        //{
        //	var filtro = new IAttivitaDyn2DatiStorico
        //	{
        //		Idcomune = idComune,
        //		FkIaId = codiceAttivita,
        //		FkD2cId = codiceCampo,
        //		Indice = indiceModello,
        //		Idversione = idVersioneStorico,
        //		OrderBy = "indice_molteplicita asc"
        //	};

        //	var list = GetList(filtro);

        //	var rVal = new List<IIAttivitaDyn2DatiStorico>(list.Count);

        //	list.ForEach(x => rVal.Add(x));

        //	return rVal;
        //}

        //#endregion
    }
}
