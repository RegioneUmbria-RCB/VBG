using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService
{
    public static class IstanzeStradarioExtensions
    {
        public static NuovaLocalizzazione ToNuovaLocalizzazione(this IstanzeStradario istanzeStradario)
        {
            var codiceStradario = Convert.ToInt32(istanzeStradario.CODICESTRADARIO);
            var indirizzo = (istanzeStradario.Stradario != null) ? istanzeStradario.Stradario.PREFISSO + " " + istanzeStradario.Stradario.DESCRIZIONE : null;
            var civico = istanzeStradario.CIVICO;
            var esponente = istanzeStradario.ESPONENTE;

            var localizzazione = new NuovaLocalizzazione(codiceStradario, indirizzo, civico, esponente);

            localizzazione.Cap = istanzeStradario.CAP;
            localizzazione.Circoscrizione = istanzeStradario.CIRCOSCRIZIONE;
            localizzazione.CodiceCivico = istanzeStradario.CODICECIVICO;
            localizzazione.CodiceViario = istanzeStradario.Stradario?.CODVIARIO;
            localizzazione.Colore = istanzeStradario.COLORE;
            localizzazione.EsponenteInterno = istanzeStradario.ESPONENTEINTERNO;
            localizzazione.Fabbricato = istanzeStradario.FABBRICATO;
            localizzazione.Interno = istanzeStradario.INTERNO;
            localizzazione.Km = istanzeStradario.Km;
            localizzazione.Latitudine = istanzeStradario.Latitudine;
            localizzazione.Longitudine = istanzeStradario.Longitudine;
            localizzazione.Note = istanzeStradario.NOTE;
            localizzazione.Piano = istanzeStradario.Piano;
            localizzazione.Scala = istanzeStradario.SCALA;
            return localizzazione;
        }
    }
}
