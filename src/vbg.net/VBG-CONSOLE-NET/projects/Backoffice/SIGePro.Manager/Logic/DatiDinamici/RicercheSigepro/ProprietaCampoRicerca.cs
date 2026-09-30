using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.RicercheSigepro
{
	internal class ProprietaCampoRicerca
	{
		internal enum TipoRicercaEnum
		{
			LeftLike,
			FullLike
		}

		//public string	PrefixText;
		private string _campiSelect;
		private string _tabelleSelect;
		private string _condizioneJoin;
		private string _condizioniWhere;
		private string _nomeCampoValore;
		private string _nomeCampoTesto;
		private TipoRicercaEnum _tipoRicerca;
		private string _campoRicercaCodice;
		private string _campoRicercaDescrizione;
        private string _condizioniWhereAltriCampi;
		private int _count;


		public string CampiSelect
		{
			get { return _campiSelect; }
			set { _campiSelect = value; }
		}
		

		public string TabelleSelect
		{
			get { return _tabelleSelect; }
			set { _tabelleSelect = value; }
		}
		
		public string CondizioneJoin
		{
			get { return _condizioneJoin; }
			set { _condizioneJoin = value; }
		}
		
		public string CondizioniWhere
		{
			get { return _condizioniWhere; }
			set { _condizioniWhere = value; }
		}
		
		public string NomeCampoValore
		{
			get { return _nomeCampoValore; }
			set { _nomeCampoValore = value; }
		}
		
		public string NomeCampoTesto
		{
			get { return _nomeCampoTesto; }
			set { _nomeCampoTesto = value; }
		}
		
		public TipoRicercaEnum TipoRicerca
		{
			get { return _tipoRicerca; }
			set { _tipoRicerca = value; }
		}
		

		public string CampoRicercaCodice
		{
			get { return String.IsNullOrEmpty(_campoRicercaCodice) ? this.NomeCampoValore : this._campoRicercaCodice; }
			set { _campoRicercaCodice = value; }
		}
		

		public string CampoRicercaDescrizione
		{
			get { return String.IsNullOrEmpty(this._campoRicercaDescrizione) ? this.NomeCampoTesto : this._campoRicercaDescrizione; }
			set { _campoRicercaDescrizione = value; }
		}
		

		public int Count
		{
			get { return _count; }
			set { _count = value; }
		}

        public string CondizioniWhereAltriCampi { get; set; }
    }
}
