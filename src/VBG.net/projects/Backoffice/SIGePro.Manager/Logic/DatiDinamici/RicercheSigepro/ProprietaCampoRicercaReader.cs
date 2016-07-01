using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.RicercheSigepro
{
	internal class ProprietaCampoRicercaReader
	{
		private static class NomiProprieta
		{
			public const string Obbligatorio = "Obbligatorio";
			public const string ValueBoxColumns = "ValueBoxColumns";
			public const string DescriptionBoxColumns = "DescriptionBoxColumns";
			public const string TipoRicerca = "TipoRicerca";
			public const string CampiSelect = "CampiSelect";
			public const string TabelleSelect = "TabelleSelect";
			public const string CondizioniJoin = "CondizioniJoin";
			public const string CondizioniWhere = "CondizioniWhere";
			public const string NomeCampoValore = "NomeCampoValore";
			public const string NomeCampoTesto = "NomeCampoTesto";
			public const string CampoRicercaCodice = "CampoRicercaCodice";
			public const string CampoRicercaDescrizione = "CampoRicercaDescrizione";
			public const string CompletionSetCount = "CompletionSetCount";
            public const string CondizioniWhereAltriCampi = "CondizioniWhereAltriCampi";
		}


		private IDyn2ProprietaCampiManager _proprietaManager;
		private string _idComune;
		private int _idCampo;

		internal ProprietaCampoRicercaReader(string idComune, int idCampo, IDyn2ProprietaCampiManager proprietaManager)
		{
			this._idCampo = idCampo;
			this._idComune = idComune;
			this._proprietaManager = proprietaManager;
		}


		internal ProprietaCampoRicerca GetProprieta()
		{
			var listaProprieta = this
									._proprietaManager
									.GetProprietaCampo(this._idComune, this._idCampo)
									.ToDictionary( x => x.Proprieta, x => x.Valore);

			return new ProprietaCampoRicerca
			{
				CampiSelect = SafePropertyValue( listaProprieta, NomiProprieta.CampiSelect),
				CampoRicercaCodice = SafePropertyValue(listaProprieta, NomiProprieta.CampoRicercaCodice),
				CampoRicercaDescrizione = SafePropertyValue(listaProprieta, NomiProprieta.CampoRicercaDescrizione),
				CondizioneJoin = SafePropertyValue(listaProprieta, NomiProprieta.CondizioniJoin),
				CondizioniWhere = SafePropertyValue(listaProprieta, NomiProprieta.CondizioniWhere),
				Count = Convert.ToInt32(SafePropertyValue(listaProprieta, NomiProprieta.CompletionSetCount, "0")),
				NomeCampoTesto = SafePropertyValue(listaProprieta, NomiProprieta.NomeCampoTesto),
				NomeCampoValore = SafePropertyValue(listaProprieta, NomiProprieta.NomeCampoValore),
				TabelleSelect = SafePropertyValue(listaProprieta, NomiProprieta.TabelleSelect),
				TipoRicerca = SafePropertyValue(listaProprieta, NomiProprieta.TipoRicerca, "0") == "0" ? ProprietaCampoRicerca.TipoRicercaEnum.LeftLike : ProprietaCampoRicerca.TipoRicercaEnum.FullLike,
                CondizioniWhereAltriCampi = SafePropertyValue(listaProprieta, NomiProprieta.CondizioniWhereAltriCampi)
			};

		}

		private string SafePropertyValue(Dictionary<string, string> dict, string proprtyName, string defaultValue = "")
		{
			if (!dict.ContainsKey(proprtyName))
				return defaultValue;

			return dict[proprtyName];
		}
	}
}
