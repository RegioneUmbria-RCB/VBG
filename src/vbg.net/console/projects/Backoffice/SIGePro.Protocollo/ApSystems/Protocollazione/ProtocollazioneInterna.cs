using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ApSystems.Protocollazione.Corrispondenti.Get;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.ApSystems.Protocollazione
{
    public class ProtocollazioneInterna : IProtocollazione
    {
        string _uoMittente;
        string _uoDestinatario;
        CorrispondentiGetServiceWrapper _corrispondentGetSrv;

        public ProtocollazioneInterna(string uoMittente, string uoDestinatario, CorrispondentiGetServiceWrapper corrispondentGetSrv)
        {
            _uoMittente = uoMittente;
            _uoDestinatario = uoDestinatario;
            _corrispondentGetSrv = corrispondentGetSrv;
        }

        public DatiProtocolloRes Protocolla(DataSet request, ProtocollazioneServiceWrapper wrapper)
        {
            return wrapper.ProtocollaInterno(request);
        }

        public void InserisciAllegato(string codiceProtocollo, string numeroProtocollo, string dataProtocollo, byte[] oggetto, string nomeFile, string codiceAllegato, Allegati.AllegatiServiceWrapper allegatiSrv)
        {
            allegatiSrv.InserisciAllegatoaProtocolloGenerale(codiceProtocollo, numeroProtocollo, dataProtocollo, oggetto, nomeFile, codiceAllegato);
        }

        public string Flusso
        {
            get { return "I"; }
        }

        public void SetMittenti(DatiProtocollo.protocolli.mittenteDataTable dt)
        {
            try
            {
                if (String.IsNullOrEmpty(_uoMittente))
                    throw new Exception("MITTENTE NON VALORIZZATO");

                var rc = _corrispondentGetSrv.GetCorrispondenteByCodiceUfficio(_uoMittente);

                var r = dt.NewmittenteRow();
                r.codice = rc.codice;
                r.descrizione = rc.descrizione;
                r.indirizzo = rc.indirizzo;
                r.cap = rc.cap;
                r.codice_comune = rc.codice_comune;
                r.descrizione_comune = rc.descrizione_comune;

                dt.AddmittenteRow(r);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA COMPILAZIONE DEL MITTENTE DI UN PROTOCOLLO INTERNO", ex.Message), ex);
            }
        }

        public void SetDestinatari(DatiProtocollo.protocolli.destinatarioDataTable dt)
        {
            try
            {
                if (String.IsNullOrEmpty(_uoDestinatario))
                    throw new Exception("DESTINATARIO NON VALORIZZATO");

                var rc = _corrispondentGetSrv.GetCorrispondenteByCodiceUfficio(_uoDestinatario);

                var r = dt.NewdestinatarioRow();
                r.codice = rc.codice;
                r.descrizione = rc.descrizione;
                r.indirizzo = rc.indirizzo;
                r.cap = rc.cap;
                r.codice_comune = rc.codice_comune;
                r.descrizione_comune = rc.descrizione_comune;
                r.codice_ufficio = rc.codice_ufficio;


                dt.AdddestinatarioRow(r);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA COMPILAZIONE DEL DESTINATARIO DI UN PROTOCOLLO INTERNO, {0}", ex.Message), ex);
            }
        }
    }
}
