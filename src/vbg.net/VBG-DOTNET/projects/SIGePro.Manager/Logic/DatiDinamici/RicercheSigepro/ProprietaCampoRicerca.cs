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

        private string _campoRicercaCodice;
        private string _campoRicercaDescrizione;
        private string _orderBy;

        public string CampiSelect { get; set; }

        public string TabelleSelect { get; set; }

        public string CondizioneJoin { get; set; }

        public string CondizioniWhere { get; set; }

        public string NomeCampoValore { get; set; }

        public string NomeCampoTesto { get; set; }

        public TipoRicercaEnum TipoRicerca { get; set; }
        public int Count { get; set; }

        public string CondizioniWhereAltriCampi { get; set; }

        public string OrderBy { get => String.IsNullOrEmpty(_orderBy) ? NomeCampoTesto : _orderBy; set => _orderBy = value?.Trim(); }

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



    }
}
