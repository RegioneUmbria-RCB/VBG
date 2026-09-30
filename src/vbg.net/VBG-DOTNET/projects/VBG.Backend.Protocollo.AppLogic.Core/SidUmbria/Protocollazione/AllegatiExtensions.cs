using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Utils;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public static class AllegatiExtensions
    {
        public static allegato ToAllegato(this ProtocolloAllegati allegato, bool isPrincipale, bool isFtp, string prefix, VerticalizzazioniConfiguration vert, string username)
        {
            if (isFtp)
            {
                return ToAllegatoFtp(allegato, isPrincipale, prefix, vert, username);
            }
            else
            {
                return ToAllegatoBuffer(allegato, isPrincipale);
            }
        }

        private static allegato ToAllegatoFtp(this ProtocolloAllegati allegato, bool isPrincipale, string prefix, VerticalizzazioniConfiguration vert, string username)
        {


            if (vert.Versione == "2.0")
            {
                throw new Exception("INVIO FILE VIA FTP NON GESTITO NELLA VERSIONE 2.0");
            }

            var nomeFile = $"{allegato.CODICEOGGETTO}-{prefix}-{allegato.NOMEFILE}";

            var allegatiService = new AllegatiService(vert.FtpUrl, username, vert.FtpPassword);
            allegatiService.UploadSftp(allegato.OGGETTO, nomeFile);

            string descrizione = "";
            if (!String.IsNullOrEmpty(allegato.Descrizione))
            {
                //descrizione = Path.GetFileNameWithoutExtension(allegato.Descrizione);
                if (allegato.Descrizione.Length > 255)
                {
                    descrizione = $"{allegato.Descrizione.Substring(0, 252)}...";
                }
            }

            return new allegato
            {
                id = nomeFile,
                nota = descrizione,
                nome = nomeFile,
                principale = isPrincipale,
                hash = CryptUtils.GetSha1(allegato.OGGETTO)
            };

        }

        private static allegato ToAllegatoBuffer(this ProtocolloAllegati allegato, bool isPrincipale)
        {
            var base64 = Base64Utils.Base64Encode(allegato.OGGETTO);
            var base64delbase64 = Encoding.UTF8.GetBytes(base64);   //è giusto che la variabile si chiami così.

            string descrizione = "";
            if (!String.IsNullOrEmpty(allegato.Descrizione))
            {
                //descrizione = Path.GetFileNameWithoutExtension(allegato.Descrizione);
                //if (Path.GetFileNameWithoutExtension(allegato.Descrizione).Length > 255)
                //{
                //    descrizione = $"{Path.GetFileNameWithoutExtension(allegato.Descrizione).Substring(0, 252)}...";
                //}
                if (allegato.Descrizione.Length > 255)
                {
                    descrizione = $"{allegato.Descrizione.Substring(0, 252)}...";
                }
            }

            var res = new allegato
            {
                id = allegato.CODICEOGGETTO,
                documento = base64delbase64,
                nome = allegato.NOMEFILE,
                nota = descrizione,
                principale = isPrincipale
            };

            return res;
        }

    }
}
