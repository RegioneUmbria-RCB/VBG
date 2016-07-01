using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro.Manager
{ 	///<summary>
	/// Descrizione di riepilogo per FormeGiuridicheMgr.\n	/// </summary>
	public partial class FormeGiuridicheMgr : BaseManager
	{

		#region Metodi per l'accesso di base al DB


		public FormeGiuridiche GetById(String pCODICEFORMAGIURIDICA, String pIDCOMUNE)
		{
			FormeGiuridiche retVal = new FormeGiuridiche();

			retVal.CODICEFORMAGIURIDICA = pCODICEFORMAGIURIDICA;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal, true, false);
			if (mydc.Count != 0)
				return (mydc[0]) as FormeGiuridiche;

			return null;
		}



		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		//public ArrayList GetList(FormeGiuridiche p_class)
		//{
		//    return this.GetList(p_class,null);
		//}

		public ArrayList GetList(FormeGiuridiche p_class, FormeGiuridiche p_cmpClass)
		{
			return db.GetClassList(p_class, p_cmpClass, false, false);
		}

		public FormeGiuridiche GetByClass(FormeGiuridiche cls)
		{
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(cls, true, false);
			if (mydc.Count != 0)
				return (mydc[0]) as FormeGiuridiche;

			return null;
		}

		/// <summary>
		/// Verifica se si può cancellare la forma giuridica
		/// </summary>
		/// <param name="cls"></param>
		private void VerificaRecordCollegati(FormeGiuridiche cls)
		{
			if (recordCount("ANAGRAFE", "FORMAGIURIDICA", "where IDCOMUNE = '" + cls.IDCOMUNE + "' and FORMAGIURIDICA = " + cls.FORMAGIURIDICA) > 0)
				throw new ReferentialIntegrityException("ANAGRAFE");
		}

		#endregion
	}
}
