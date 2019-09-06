using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.Insiel3.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari
{
    public class ProtocollazionePartenza : IProtocollazione
    {
        IDatiProtocollo _datiProto;
        ProtocolloService _srv;
        TipoGestioneAnagraficaEnum.TipoGestione _tipoGestioneAnagrafica;
        TipoGestioneAnagraficaEnum.TipoAggiornamento _tipoAggiornamento;
        ProtocolloLogs _logs;
        bool _inviaPec;
        string _iteratti;

        public ProtocollazionePartenza(IDatiProtocollo datiProto, ProtocolloService srv, InsielVerticalizzazioniConfiguration vert, ProtocolloLogs logs)
        {
            this._datiProto = datiProto;
            this._srv = srv;
            this._tipoGestioneAnagrafica = vert.TipoGestionePec;
            this._tipoAggiornamento = vert.TipoAggiornamentoAnagrafica;
            this._logs = logs;
            this._iteratti = vert.Iteratti;
            this._inviaPec = vert.InviaPec;
        }

        public MittenteInsProto[] GetMittenti()
        {
            return null;
        }

        public DestinatarioIOPInsProto[] GetDestinatari()
        {
            var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.GetDestinatarioIOPFromAnagrafe(_srv, _tipoGestioneAnagrafica, this._tipoAggiornamento, this._inviaPec, _logs));
            var amministrazione = _datiProto.AmministrazioniEsterne.Select(x => x.GetDestinatarioIOPFromAmministrazione(_srv, _tipoGestioneAnagrafica, this._tipoAggiornamento, this._inviaPec, _logs));

            var retVal = anagrafiche.Union(amministrazione);

            return retVal.ToArray();
        }

        public verso Flusso
        {
            get { return verso.P; }
        }


        public bool InvioTelematicoAttivo
        {
            get { return true; }
        }


        public UfficioInsProto[] GetUffici()
        {
            var ufficio = new UfficioInsProto
            {
                codice = _datiProto.Uo,
                giaInviato = true,
                giaInviatoSpecified = true
            };

            if (!String.IsNullOrEmpty(this._iteratti))
            {
                ufficio.tipo = this._iteratti;
            }

            return new UfficioInsProto[]
            {
                ufficio
            };
        }
    }
}
