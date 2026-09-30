using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.NlaPec;
using Init.SIGePro.Manager.NlaPecService;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Manager
{
    public partial class ProtocolloMgr
    {
        internal class NlaPec
        {
            string _token;
            string _software;
            string _idComune;

            public NlaPec(string token, string software, string idComune)
            {
                _token = token;
                _software = software;
                _idComune = idComune;
            }

            public string GetIdentificativo(int codiceIstanza, DataBase db)
            {
                var domandeMgr = new DomandeStcMgr(db);
                var domande = domandeMgr.GetDomandeByIstanza(_idComune, codiceIstanza);

                if (domande != null && domande.Count() > 0)
                    return domande.ToList()[0].IdDomandaMitt;

                return "";
            }

            public List<ProtocolloAllegati> GetAllegatiEml(string corpo, DateTime dataDa, DateTime dataA)
            {
                var nlaPecWrapper = new NlaPecServiceWrapper();
                var filtri = new FiltroType[] 
                { 
                    new FiltroType { tipo = FiltroTypeTipo.CORPO, valore = corpo },
                    new FiltroType { tipo = FiltroTypeTipo.DATA_DA, valore = dataDa.ToString("dd/MM/yyyy") },
                    new FiltroType { tipo = FiltroTypeTipo.DATA_A, valore = dataA.ToString("dd/MM/yyyy") }
                };

                var listaPec = nlaPecWrapper.FindPec(_token, _software, filtri);

                if (listaPec == null || listaPec.Count() == 0)
                    return null;

                var retVal = listaPec.Select(x => 
                {
                    var messaggio = nlaPecWrapper.DownloadBinaryEml(_token, _software, x.identificativo);
                    return new ProtocolloAllegati
                    {
                        MimeType = messaggio.contentType,
                        Extension = "eml",
                        NOMEFILE = messaggio.nomeFile,
                        Descrizione = String.Format("PRATICA PEC: {0}", x.identificativo),
                        CODICEOGGETTO = "0",
                        IDCOMUNE = _idComune,
                        OGGETTO = messaggio.content
                    };
                });

                if (retVal.Count() == 0)
                    return null;

                return retVal.ToList();
            }
        }
    }
}
