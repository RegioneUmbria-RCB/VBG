﻿using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using System.Text.RegularExpressions;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Fascicolazione
{
    public class DettaglioFascicoloResponseAdapter
    {
        public DettaglioFascicoloResponseAdapter()
        {

        }

        public DatiProtocolloFascicolatoResponseType Adatta(DettaglioFascicoloResponse response)
        {
            string classifica = "";

            if (!String.IsNullOrEmpty(response.Dettaglio.CodiceRegistro))
            {
                classifica = Regex.Replace(response.Dettaglio.CodiceRegistro.Trim(), @"\s+", ".");
            }

            return new DatiProtocolloFascicolatoResponseType
            {
                AnnoFascicolo = response.Dettaglio.Anno.ToString(),
                DataFascicolo = response.Dettaglio.Data.ToString("dd/MM/yyyy"),
                Fascicolato = EnumFascicolatoType.si,
                NumeroFascicolo = response.Dettaglio.Numero.ToString(),
                Oggetto = response.Dettaglio.Oggetto,
                NoteFascicolo = response.Dettaglio.Note,
                Classifica = classifica
            };
        }
    }
}
