using Init.SIGePro.Protocollo.Logs;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Civilia.Protocollazione
{
    public class ProtocollazioneAdapter
    {
        public ProtocollazioneAdapter()
        {

        }

        public PraticaWS Adatta(ProtocolloInfo info, ProtocolloLogs logs)
        {
            var allegati = info.Metadati.ProtoIn.Allegati.Select((x, y) => new AllegatoWS
            {
                Descrizione = x.Descrizione,
                NomeFile = x.NOMEFILE,
                File = x.OGGETTO,
                MimeType = x.MimeType,
                IsPrincipale = y == 0,
                Titolo = null
            });

            var corrispondenti = info.Anagrafiche.Select(x => new IndividuoProtocolloWS
            {
                Denominazione = x.NomeCognome,
                Email = x.Pec,
                TipoIndividuoProtocollo = "NONCERTIFICATO"
            });

            var retVal = new PraticaWS
            {
                Oggetto = info.Metadati.ProtoIn.Oggetto,
                Note = "",
                IsFromModificaAllegato = false,
                IsFromModificaCorrispondente = false,
                MotivoModifica = null,
                TipoProtocollo = info.Flusso.ToString(),
                DataConsegnaDocumento = null,
                IdCorrispondentiList = corrispondenti,
                CodiceLivelloOrganigramma = info.Metadati.Uo,
                NumeroProtocollo = null,
                DataRegistrazione = null,
                ProtocollatoDa = info.ProtocollatoDa
            };

            //if (!String.IsNullOrEmpty(info.ParametriRegola.CodiceAoo))
            //{
            //    retVal.IdCodiceAOO = info.ParametriRegola.CodiceAoo;
            //}

            var jsonRequest = JsonConvert.SerializeObject(retVal);
            logs.InfoFormat("REQUEST (SENZA LA PARTE RELATIVA AGLI ALLEGATI): {0}", jsonRequest);

            retVal.AllegatiList = allegati;

            return retVal;
        }
    }
}
