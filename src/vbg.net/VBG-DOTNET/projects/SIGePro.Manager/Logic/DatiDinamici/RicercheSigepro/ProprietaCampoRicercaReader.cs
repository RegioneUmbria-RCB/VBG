using System;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.Interfaces;

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
            public const string OrderBy = "OrderBy";
        }


        private readonly IDyn2ProprietaCampiManager _proprietaManager;
        private readonly int _idCampo;

        internal ProprietaCampoRicercaReader(int idCampo, IDyn2ProprietaCampiManager proprietaManager)
        {
            this._idCampo = idCampo;
            this._proprietaManager = proprietaManager;
        }


        internal ProprietaCampoRicerca GetProprieta()
        {
            var listaProprieta = this
                                    ._proprietaManager
                                    .GetProprietaCampo(this._idCampo)
                                    .ToDictionary(x => x.Proprieta, x => x.Valore);

            return new ProprietaCampoRicerca
            {
                CampiSelect = this.SafePropertyValue(listaProprieta, NomiProprieta.CampiSelect),
                CampoRicercaCodice = this.SafePropertyValue(listaProprieta, NomiProprieta.CampoRicercaCodice),
                CampoRicercaDescrizione = this.SafePropertyValue(listaProprieta, NomiProprieta.CampoRicercaDescrizione),
                CondizioneJoin = this.SafePropertyValue(listaProprieta, NomiProprieta.CondizioniJoin),
                CondizioniWhere = this.SafePropertyValue(listaProprieta, NomiProprieta.CondizioniWhere),
                Count = Convert.ToInt32(this.SafePropertyValue(listaProprieta, NomiProprieta.CompletionSetCount, "0")),
                NomeCampoTesto = this.SafePropertyValue(listaProprieta, NomiProprieta.NomeCampoTesto),
                NomeCampoValore = this.SafePropertyValue(listaProprieta, NomiProprieta.NomeCampoValore),
                TabelleSelect = this.SafePropertyValue(listaProprieta, NomiProprieta.TabelleSelect),
                TipoRicerca = this.SafePropertyValue(listaProprieta, NomiProprieta.TipoRicerca, "0") == "0" ? ProprietaCampoRicerca.TipoRicercaEnum.LeftLike : ProprietaCampoRicerca.TipoRicercaEnum.FullLike,
                CondizioniWhereAltriCampi = this.SafePropertyValue(listaProprieta, NomiProprieta.CondizioniWhereAltriCampi),
                OrderBy = this.SafePropertyValue(listaProprieta, NomiProprieta.OrderBy),
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
