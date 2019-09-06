using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloSidUmbriaService;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Utils;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public static class AllegatiExtensions
    {
        public static allegato ToAllegato(this ProtocolloAllegati allegato, bool isPrincipale)
        {
            var base64 = Base64Utils.Base64Encode(allegato.OGGETTO);
            var base64delbase64 = Encoding.UTF8.GetBytes(base64);   //è giusto che la variabile si chiami così.

            var res = new allegato
            {
                id = allegato.CODICEOGGETTO,
                documento = base64delbase64,
                nome = allegato.NOMEFILE,
                nota = allegato.Descrizione,
                principale = isPrincipale
            };

            return res;
        }

    }
}
