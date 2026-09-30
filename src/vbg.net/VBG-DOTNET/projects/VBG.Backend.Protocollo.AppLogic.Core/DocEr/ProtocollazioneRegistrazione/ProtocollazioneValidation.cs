using System;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;


namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione
{
    public class ProtocollazioneValidation
    {
        public static void Valida(IDatiProtocollo datiProto)
        {
            if (!datiProto.ProtoIn.HaAllegati())
                throw new Exception("ALLEGATI NON PRESENTI, E' OBBLIGATORIO INSERIRE ALMENO UN FILE ALLEGATO");

            if (datiProto.Flusso == ProtocolloConstants.COD_INTERNO)
                return;

            var anagSenzaCodFiscePartIva = datiProto.AnagraficheProtocollo.Where(x => String.IsNullOrEmpty(x.CODICEFISCALE) && String.IsNullOrEmpty(x.PARTITAIVA));

            if (anagSenzaCodFiscePartIva.Count() > 0)
            {
                var sb = new StringBuilder();

                foreach (var item in anagSenzaCodFiscePartIva)
                    sb.Append(String.Concat(item.GetNomeCompleto(), " "));

                throw new Exception(String.Format("NON E' PRESENTE IL CODICE FISCALE / PARTITA IVA SULLE SEGUENTI ANAGRAFICHE: {0}", sb.ToString()));
            }

            var ammSenzaCodFiscePartIva = datiProto.AmministrazioniEsterne.Where(x => String.IsNullOrEmpty(x.PARTITAIVA));

            if (ammSenzaCodFiscePartIva.Count() > 0)
            {
                var sb = new StringBuilder();

                foreach (var item in ammSenzaCodFiscePartIva)
                    sb.Append(String.Concat(item.AMMINISTRAZIONE, " "));

                throw new Exception(String.Format("NON E' PRESENTE IL CODICE FISCALE / PARTITA IVA SULLE SEGUENTI AMMINISTRAZIONI: {0}", sb.ToString()));
            }

        }
    }
}
