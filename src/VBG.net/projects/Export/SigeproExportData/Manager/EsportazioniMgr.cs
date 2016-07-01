using System;
using System.Collections;
using System.Data;
using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using Init.Utils;
using System.Collections.Generic;

namespace Init.SIGeProExport.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per EsportazioniMgr.
	/// </summary>
	public class EsportazioniMgr : BaseManager
	{
		public EsportazioniMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public ESPORTAZIONI GetById(String pIDCOMUNE, String pID)
		{
            return GetById(pIDCOMUNE, pID, false);
		}

        public ESPORTAZIONI GetById(String pIDCOMUNE, String pID, bool AddChild)
        {
            ESPORTAZIONI retVal = new ESPORTAZIONI();
            retVal.IDCOMUNE = pIDCOMUNE;
            retVal.ID = pID;

            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal, true, false);
            if (mydc.Count != 0)
            {
                retVal = (mydc[0]) as ESPORTAZIONI;

                if (AddChild)
                {
                    PARAMETRIESPORTAZIONE pe = new PARAMETRIESPORTAZIONE();
                    pe.IDCOMUNE = pIDCOMUNE;
                    pe.FK_ESP_ID = pID;
                    retVal.Parametri = new ParametriEsportazioneMgr(db).GetList(pe);

                    TRACCIATI t = new TRACCIATI();
                    t.IDCOMUNE = pIDCOMUNE;
                    t.FK_ESP_ID = pID;
                    retVal.Tracciati = new TracciatiMgr(db).GetList(t, AddChild);

                }

                return retVal;
            }

            return null;
        }

		public ESPORTAZIONI GetByClass( ESPORTAZIONI p_class )
		{
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(p_class,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as ESPORTAZIONI;

			return null;
		}

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(ESPORTAZIONI p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(ESPORTAZIONI p_class, ESPORTAZIONI p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}
		
		
		public ESPORTAZIONI Insert( ESPORTAZIONI p_class )
		{
            p_class.ID = ( findMax("ESPORTAZIONI", "ID", "IDCOMUNE = '" + p_class.IDCOMUNE + "'") + 1 ).ToString();
			db.Insert( p_class );
			return p_class;
		}

        public ESPORTAZIONI InsertAll(ESPORTAZIONI p_class)
        {
            p_class = Insert(p_class);

            foreach (PARAMETRIESPORTAZIONE pe in p_class.Parametri)
            {
                pe.IDCOMUNE = p_class.IDCOMUNE;
                pe.FK_ESP_ID = p_class.ID;

                new ParametriEsportazioneMgr(db).Insert(pe);
            }

            foreach (TRACCIATI t in p_class.Tracciati)
            {
                t.IDCOMUNE = p_class.IDCOMUNE;
                t.FK_ESP_ID = p_class.ID;

                new TracciatiMgr(db).InsertAll(t);
            }

            return p_class;
        }

		public ESPORTAZIONI Delete( ESPORTAZIONI p_class )
		{
            db.BeginTransaction();

            //cancello i parametri
            PARAMETRIESPORTAZIONE pe = new PARAMETRIESPORTAZIONE();
            pe.IDCOMUNE = p_class.IDCOMUNE;
            pe.FK_ESP_ID = p_class.ID;

            ParametriEsportazioneMgr pem = new ParametriEsportazioneMgr(db);
            List<PARAMETRIESPORTAZIONE> parametri = pem.GetList(pe);
            foreach (PARAMETRIESPORTAZIONE parametro in parametri)
                pem.Delete(parametro);


            //cancello i tracciati
            TRACCIATI t = new TRACCIATI();
            t.IDCOMUNE = p_class.IDCOMUNE;
            t.FK_ESP_ID = p_class.ID;

            TracciatiMgr tm = new TracciatiMgr(db);
            List<TRACCIATI> tracciati = tm.GetList(t);
            foreach (TRACCIATI tracciato in tracciati)
                tm.Delete(tracciato);

            //cancello l'esportazione
			db.Delete( p_class );
			
            db.CommitTransaction();

            return p_class;
		}

		public ESPORTAZIONI Update( ESPORTAZIONI p_class )
		{		
			db.Update( p_class );
			return p_class;
		}

        public ESPORTAZIONI ReplicaEsportazione(String idEnteOrigine, String idExportOrigine, String idEnteDestinazione) 
        {
            db.BeginTransaction();

            //inserisco la nuova esportazione copiandola dall'ente di origine
            ESPORTAZIONI exp = GetById(idEnteOrigine, idExportOrigine);
            exp.IDCOMUNE = idEnteDestinazione;
            exp.ID = null;
            exp = Insert(exp);

            //replico i parametri nella nuova esportazione
            ParametriEsportazioneMgr pem = new ParametriEsportazioneMgr( db );
            PARAMETRIESPORTAZIONE pe = new PARAMETRIESPORTAZIONE();
            pe.IDCOMUNE = idEnteOrigine;
            pe.FK_ESP_ID = idExportOrigine;
            List<PARAMETRIESPORTAZIONE> parametri = pem.GetList(pe);
            foreach (PARAMETRIESPORTAZIONE par in parametri)
            {
                par.IDCOMUNE = exp.IDCOMUNE;
                par.FK_ESP_ID = exp.ID;
                par.ID = null;

                pem.Insert(par);
            }

            //replico i tracciati nella nuova esportazione
            TracciatiMgr tm = new TracciatiMgr(db);
            TRACCIATI t = new TRACCIATI();
            t.IDCOMUNE = idEnteOrigine;
            t.FK_ESP_ID = idExportOrigine;
            List<TRACCIATI> tracciati = tm.GetList(t);
            foreach (TRACCIATI trac in tracciati)
            {
                //inserisco il tracciato
                TRACCIATI newTrac = (TRACCIATI)trac.Clone();
                newTrac.IDCOMUNE = exp.IDCOMUNE;
                newTrac.FK_ESP_ID = exp.ID;
                newTrac.ID = null;
                newTrac = tm.Insert(newTrac);

                //recupero i suoi dettagli
                TRACCIATIDETTAGLIO td = new TRACCIATIDETTAGLIO();
                td.IDCOMUNE = trac.IDCOMUNE;
                td.FK_TRACCIATI_ID = trac.ID;
                TracciatiDettMgr tdm = new TracciatiDettMgr(db);
                List<TRACCIATIDETTAGLIO> dettagli = tdm.GetList(td);

                //e li inserisco nel nuovo tracciato
                foreach (TRACCIATIDETTAGLIO dett in dettagli)
                {
                    dett.IDCOMUNE = newTrac.IDCOMUNE;
                    dett.FK_TRACCIATI_ID = newTrac.ID;
                    dett.ID = null;

                    tdm.Insert(dett);
                }
            }

            db.CommitTransaction();
            return exp;
        }

		#endregion
	}
}
