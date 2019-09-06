using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per IstanzeHummingbirdMgr.
	/// </summary>
	public class IstanzeHummingbirdMgr : BaseManager
	{

		public IstanzeHummingbirdMgr(DataBase dataBase ) : base( dataBase ) {}


		#region seleziona singolo elemento
		public IstanzeHummingbird GetByDocNum( string docNum )
		{
			IstanzeHummingbird retVal = new IstanzeHummingbird();
			retVal.DOCNUM = docNum;

			return GetUnknown(retVal);
		}

		public IstanzeHummingbird GetByIdPratica( string idPratica )
		{
			IstanzeHummingbird retVal = new IstanzeHummingbird();
			retVal.ID_PRATICA = idPratica;

			return GetUnknown( retVal );
		}

		public IstanzeHummingbird GetByCodiceIstanza( string idComune , string codiceIstanza )
		{
			IstanzeHummingbird retVal = new IstanzeHummingbird();
			retVal.IDCOMUNE = idComune;
			retVal.CODICEISTANZA = codiceIstanza;

			return GetUnknown( retVal );
		}

		private IstanzeHummingbird GetUnknown(IstanzeHummingbird retVal)
		{
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as IstanzeHummingbird;
	
			return null;
		}
		#endregion


		#region selezione lista
		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="istanzeHummingbird">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<IstanzeHummingbird> GetList(IstanzeHummingbird istanzeHummingbird)
		{
			return this.GetList(istanzeHummingbird,null);
		}

        public List<IstanzeHummingbird> GetList(IstanzeHummingbird istanzeHummingbird, IstanzeHummingbird cmpIstanzeHummingbird)
		{
            return db.GetClassList(istanzeHummingbird, cmpIstanzeHummingbird, false, false).ToList<IstanzeHummingbird>();
		}
		#endregion


		#region inserimento
		public IstanzeHummingbird Insert( IstanzeHummingbird istanzaHummingbird )
		{
			db.Insert( istanzaHummingbird );

			return istanzaHummingbird;
		}
		#endregion

        public void Delete(IstanzeHummingbird p_class)
        {
            VerificaRecordCollegati(p_class);

            EffettuaCancellazioneACascata(p_class);

            db.Delete(p_class);
        }

        private void VerificaRecordCollegati(IstanzeHummingbird cls)
        { 
        
        }

        private void EffettuaCancellazioneACascata(IstanzeHummingbird cls)
        { 
            #region MOVIMENTIHUMMINGBIRD
            MovimentiHummingbird mov_hum = new MovimentiHummingbird();
            mov_hum.IDCOMUNE = cls.IDCOMUNE;
            mov_hum.CODICEISTANZA = cls.CODICEISTANZA;

            List<MovimentiHummingbird> lMovimenti = new MovimentiHummingbirdMgr(db).GetList(mov_hum);
            foreach (MovimentiHummingbird movimento in lMovimenti)
            {
                MovimentiHummingbirdMgr mgr = new MovimentiHummingbirdMgr(db);
                mgr.Delete(movimento);
            }
            #endregion
        }
	}
}
