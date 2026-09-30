using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Verticalizzazioni;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone
{
    public class AnagraficaDocErServiceManager
    {
        public static void Gestisci(IAmministrazioneAnagraficaVbg anagrafe, GestioneDocumentaleService wrapperGestDoc, VerticalizzazioniConfiguration vert)
        {
            if (String.IsNullOrEmpty(vert.TypeIdAnagraficaCustom))
                return;

            var response = wrapperGestDoc.SearchAnagrafica(anagrafe, vert);

            /*if (response == null)
                return;*/

            if (response == null ||response.Length == 0)
            {
                wrapperGestDoc.CreaAnagrafica(anagrafe, vert);
                return;
            }

            if (response.Length > 1)
                throw new System.Exception(String.Format("E' STATA TROVATA PIU' DI UNA ANAGRAFICA IN DOCER NELLA RICERCA DEL CODICE FISCALE / PARTITA IVA {0} NEL TYPE ID {1}", anagrafe.CodiceFiscalePartitaIva, vert.TypeIdAnagraficaCustom));
            
            wrapperGestDoc.UpdateAnagrafica(anagrafe, response[0].metadata, vert);
        }
    }
}
