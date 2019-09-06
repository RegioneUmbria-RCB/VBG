using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per MovimentiHummingbirdMgr.
	/// </summary>
	public class MovimentiHummingbirdMgr : BaseManager
	{

		public MovimentiHummingbirdMgr(DataBase dataBase ) : base( dataBase ) {}


		#region seleziona singolo elemento
		public MovimentiHummingbird GetByDocNum( string docNum )
		{
			MovimentiHummingbird retVal = new MovimentiHummingbird();
			retVal.DOCNUM = docNum;

			return GetUnknown(retVal);
		}


		public MovimentiHummingbird GetByCodiceMovimento( string idComune , string codiceMovimento )
		{
			MovimentiHummingbird retVal = new MovimentiHummingbird();
			retVal.IDCOMUNE = idComune;
			retVal.CODICEMOVIMENTO = codiceMovimento;

			return GetUnknown( retVal );
		}

		private MovimentiHummingbird GetUnknown(MovimentiHummingbird retVal)
		{
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as MovimentiHummingbird;
	
			return null;
		}
		#endregion


		#region selezione lista
		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="movimentiHummingbird">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<MovimentiHummingbird> GetList(MovimentiHummingbird movimentiHummingbird)
		{
			return this.GetList(movimentiHummingbird,null);
		}

        public List<MovimentiHummingbird> GetList(MovimentiHummingbird movimentiHummingbird, MovimentiHummingbird cmpMovimentiHummingbird)
		{
            return db.GetClassList(movimentiHummingbird, cmpMovimentiHummingbird, false, false).ToList<MovimentiHummingbird>();
		}
		#endregion


		#region inserimento
		public MovimentiHummingbird Insert( MovimentiHummingbird movimentiHummingbird )
		{
			db.Insert( movimentiHummingbird );

			return movimentiHummingbird;
		}
		#endregion

        public void Delete(MovimentiHummingbird cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(MovimentiHummingbird cls)
        {

        }

        private void EffettuaCancellazioneACascata(MovimentiHummingbird cls)
        {
          
        }
	}
}