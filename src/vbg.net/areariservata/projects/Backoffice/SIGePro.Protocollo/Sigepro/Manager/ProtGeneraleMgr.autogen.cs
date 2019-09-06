
			
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;

using Init.SIGePro.Validator;
using PersonalLib2.Sql;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro.Manager
{

	///
	/// File generato automaticamente dalla tabella PROT_GENERALE per la classe ProtGenerale il 09/01/2009 12.31.46
	///
	///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
	///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
	/// -
	/// -
	/// -
	/// - 
	///
	///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
	///
	public partial class ProtGeneraleMgr : BaseManager
	{
		public ProtGeneraleMgr(DataBase dataBase) : base(dataBase) { }

		public ProtGenerale GetById(int pg_id, string idcomune)
		{
			ProtGenerale c = new ProtGenerale();
			
			
			c.Pg_Id = pg_id;
			c.Idcomune = idcomune;
			
			return (ProtGenerale)db.GetClass(c);
		}

		public List<ProtGenerale> GetList(ProtGenerale filtro)
		{
			return db.GetClassList( filtro ).ToList< ProtGenerale>();
		}

		public ProtGenerale Insert(ProtGenerale cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ProtGenerale)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
            for (int i = 0; i < ((ProtGenerale)cls).ProtAllegati.Count; i++)
            {
                if (string.IsNullOrEmpty(((ProtGenerale)cls).ProtAllegati[i].Idcomune))
                    ((ProtGenerale)cls).ProtAllegati[i].Idcomune = ((ProtGenerale)cls).Idcomune;
                else if (((ProtGenerale)cls).ProtAllegati[i].Idcomune != ((ProtGenerale)cls).Idcomune)
                    throw (new IncongruentDataException("PROT_ALLEGATIPROTOCOLLO.IDCOMUNE è diverso da PROT_GENERALE.IDCOMUNE"));

                if (((ProtGenerale)cls).ProtAllegati[i].Ad_Dlid.GetValueOrDefault(int.MinValue) == int.MinValue)
                    ((ProtGenerale)cls).ProtAllegati[i].Ad_Dlid = ((ProtGenerale)cls).Pg_Id;
                else if (((ProtGenerale)cls).ProtAllegati[i].Ad_Dlid != ((ProtGenerale)cls).Pg_Id)
                    throw (new IncongruentDataException("PROT_ALLEGATIPROTOCOLLO.AD_DLID è diverso da PROT_GENERALE.PG_ID"));
            }

            for (int i = 0; i < ((ProtGenerale)cls).ProtAltriDest.Count; i++)
            {
                if (string.IsNullOrEmpty(((ProtGenerale)cls).ProtAltriDest[i].Idcomune))
                    ((ProtGenerale)cls).ProtAltriDest[i].Idcomune = ((ProtGenerale)cls).Idcomune;
                else if (((ProtGenerale)cls).ProtAltriDest[i].Idcomune != ((ProtGenerale)cls).Idcomune)
                    throw (new IncongruentDataException("PROT_ALTRIDESTINATARI.IDCOMUNE è diverso da PROT_GENERALE.IDCOMUNE"));

                if (((ProtGenerale)cls).ProtAltriDest[i].Ad_Fkidprotocollo.GetValueOrDefault(int.MinValue) == int.MinValue)
                    ((ProtGenerale)cls).ProtAltriDest[i].Ad_Fkidprotocollo = ((ProtGenerale)cls).Pg_Id;
                else if (((ProtGenerale)cls).ProtAltriDest[i].Ad_Fkidprotocollo != ((ProtGenerale)cls).Pg_Id)
                    throw (new IncongruentDataException("PROT_ALTRIDESTINATARI.AD_FKIDPROTOCOLLO è diverso da PROT_GENERALE.PG_ID"));
            }

            for (int i = 0; i < ((ProtGenerale)cls).ProtAssegnazioni.Count; i++)
            {
                if (string.IsNullOrEmpty(((ProtGenerale)cls).ProtAssegnazioni[i].Idcomune))
                    ((ProtGenerale)cls).ProtAssegnazioni[i].Idcomune = ((ProtGenerale)cls).Idcomune;
                else if (((ProtGenerale)cls).ProtAssegnazioni[i].Idcomune != ((ProtGenerale)cls).Idcomune)
                    throw (new IncongruentDataException("PROT_ASSEGNAZIONI.IDCOMUNE è diverso da PROT_GENERALE.IDCOMUNE"));

                if (((ProtGenerale)cls).ProtAssegnazioni[i].As_Fkidprotocollo.GetValueOrDefault(int.MinValue) == int.MinValue)
                    ((ProtGenerale)cls).ProtAssegnazioni[i].As_Fkidprotocollo = ((ProtGenerale)cls).Pg_Id;
                else if (((ProtGenerale)cls).ProtAssegnazioni[i].As_Fkidprotocollo != ((ProtGenerale)cls).Pg_Id)
                    throw (new IncongruentDataException("PROT_ASSEGNAZIONI.AS_FKIDPROTOCOLLO è diverso da PROT_GENERALE.PG_ID"));
            }


			return cls;
		}

		private ProtGenerale ChildInsert(ProtGenerale cls)
		{
            for (int i = 0; i < cls.ProtAllegati.Count; i++)
            {
                ProtAllegatiProtocolloMgr pProtAllegati = new ProtAllegatiProtocolloMgr(this.db);
                pProtAllegati.Insert(cls.ProtAllegati[i]);
            }

            for (int i = 0; i < cls.ProtAltriDest.Count; i++)
            {
                ProtAltriDestinatariMgr pProtAltriDest = new ProtAltriDestinatariMgr(this.db);
                pProtAltriDest.Insert(cls.ProtAltriDest[i]);
            }

            for (int i = 0; i < cls.ProtAssegnazioni.Count; i++)
            {
                ProtAssegnazioniMgr pProtAss = new ProtAssegnazioniMgr(this.db);
                pProtAss.Insert(cls.ProtAssegnazioni[i]);
            }

			return cls;
		}

		private ProtGenerale DataIntegrations(ProtGenerale cls)
		{
            if (cls.Pg_Anno.GetValueOrDefault(int.MinValue) == int.MinValue)
                cls.Pg_Anno = DateTime.Now.Year;

            if (cls.Pg_Numero.GetValueOrDefault(int.MinValue) == int.MinValue)
                cls.Pg_Numero = GetNextNumeroProtocollo(cls.Pg_Anno.GetValueOrDefault(int.MinValue), cls.Idcomune, cls.Pg_Fkidaoo.GetValueOrDefault(int.MinValue));

            if (string.IsNullOrEmpty(cls.Pg_Dataregistrazione))
                cls.Pg_Dataregistrazione = DateTime.Now.ToString("yyyyMMdd");

            if (string.IsNullOrEmpty(cls.Pg_Oraregistrazione))
                cls.Pg_Oraregistrazione = DateTime.Now.ToString("HHmm");

            if (string.IsNullOrEmpty(cls.Pg_Annullato))
                cls.Pg_Annullato = "N";

            return cls;
		}
		

		public ProtGenerale Update(ProtGenerale cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ProtGenerale cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ProtGenerale cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ProtGenerale cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ProtGenerale cls, AmbitoValidazione ambitoValidazione)
		{
            //if (string.IsNullOrEmpty(cls.Pg_Oggetto))
            //    throw new RequiredFieldException("PROT_GENERALE.OGGETTO obbligatorio"); per annullamento protocollo

            RequiredFieldValidate(cls, ambitoValidazione);

            ForeignValidate(cls);
		}	
	}
}
			
			
			