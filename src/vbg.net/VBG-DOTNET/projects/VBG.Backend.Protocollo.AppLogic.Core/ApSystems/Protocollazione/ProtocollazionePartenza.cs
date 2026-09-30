using VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.Corrispondenti.Get;
using VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.Corrispondenti.Insert;
using System.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione
{
    public class ProtocollazionePartenza : IProtocollazioneApSystems
    {
        IEnumerable<IAnagraficaAmministrazione> _anagrafiche;
        CorrispondentiGetServiceWrapper _corrispondentGetSrv;
        CorrispondentiInsertServiceWrapper _corrispondentiInsertSrv;
        VerticalizzazioniWrapper.TipoProtocollazione _tipoProtocollazione;
        string _username;
        string _uo;

        public ProtocollazionePartenza(IEnumerable<IAnagraficaAmministrazione> anagrafiche, CorrispondentiGetServiceWrapper corrispondentGetSrv, CorrispondentiInsertServiceWrapper corrispondentiInsertSrv, string username, string uo, VerticalizzazioniWrapper.TipoProtocollazione tipoProtocollazione)
        {
            _anagrafiche = anagrafiche;
            _corrispondentGetSrv = corrispondentGetSrv;
            _corrispondentiInsertSrv = corrispondentiInsertSrv;
            _username = username;
            _uo = uo;
            _tipoProtocollazione = tipoProtocollazione;
        }

        public DatiProtocolloResponseType Protocolla(DataSet request, ProtocollazioneServiceWrapper wrapper)
        {
            if (_tipoProtocollazione == VerticalizzazioniWrapper.TipoProtocollazione.INSERIMENTO_BOZZA_INTERNA_PARTENZA)
                return wrapper.ProtocollaPartenzaBozza(request);
            else
                return wrapper.ProtocollaPartenza(request);
        }

        public void InserisciAllegato(string codiceProtocollo, string numeroProtocollo, string dataProtocollo, byte[] oggetto, string nomeFile, string codiceAllegato, Allegati.AllegatiServiceWrapper allegatiSrv)
        {
            allegatiSrv.InserisciAllegatoaProtocolloGenerale(codiceProtocollo, numeroProtocollo, dataProtocollo, oggetto, nomeFile, codiceAllegato);
        }

        public string Flusso
        {
            get { return "P"; }
        }


        public void SetMittenti(VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.DatiProtocollo.protocolli.mittenteDataTable dt)
        {
            try
            {
                var rc = _corrispondentGetSrv.GetCorrispondenteByCodiceUfficio(_uo);
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
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA VALORIZZAZIONE DEL MITTENTE DI UN PROTOCOLLO IN PARTENZA , {0}", ex.Message), ex);
            }
        }

        public void SetDestinatari(VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.DatiProtocollo.protocolli.destinatarioDataTable dt)
        {
            try
            {
                var corrGet = new Corrispondenti.Get.corrispondenti.corrispondenteDataTable();

                foreach (var a in _anagrafiche)
                {
                    var r = dt.NewdestinatarioRow();
                    if (a is AmministrazioneService)
                    {
                        var amm = (AmministrazioneService)a;
                        if (String.IsNullOrEmpty(amm.Uo) && String.IsNullOrEmpty(amm.PartitaIva))
                            throw new Exception(String.Format("PARTITA IVA DELL'AMMINISTRAZIONE {0} NON VALORIZZATA", amm.Denominazione));

                        if (!String.IsNullOrEmpty(amm.Uo))
                            corrGet = _corrispondentGetSrv.GetCorrispondenteByCodiceUfficio(amm.Uo).Table as Corrispondenti.Get.corrispondenti.corrispondenteDataTable;
                        else
                            corrGet = _corrispondentGetSrv.GetCorrispondenteByCodiceFiscale(amm.PartitaIva);
                    }
                    else
                        corrGet = _corrispondentGetSrv.GetCorrispondenteByCodiceFiscale(a.CodiceFiscalePartitaIva);

                    if (corrGet.Rows.Count == 0)
                    {
                        var rcInsert = _corrispondentiInsertSrv.InsertCorrispondente(a, _username);
                        var rc = _corrispondentGetSrv.GetCorrispondenteByCodice(rcInsert.codice);

                        r.codice = rc.codice;
                        r.descrizione = rc.descrizione;
                        r.indirizzo = rc.indirizzo;
                        r.cap = rc.cap;
                        r.codice_comune = rc.codice_comune;
                        r.descrizione_comune = rc.descrizione_comune;
                        r.codice_ufficio = _uo;
                        r.email = a.Pec;
                    }
                    else
                    {
                        var rc = corrGet[0];
                        r.codice = rc.codice;
                        r.descrizione = rc.descrizione;
                        r.indirizzo = rc.indirizzo;
                        r.cap = rc.cap;
                        r.codice_comune = rc.codice_comune;
                        r.descrizione_comune = rc.descrizione_comune;
                        r.codice_ufficio = _uo;
                        r.email = a.Pec;
                    }

                    if (!String.IsNullOrEmpty(a.MezzoInvio))
                    {
                        r.codice_spedizione = a.MezzoInvio;
                    }

                    dt.AdddestinatarioRow(r);
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA VALORIZZAZIONE DEI DESTINATARI DI UN PROTOCOLLO IN PARTENZA, {0}", ex.Message), ex);
            }
        }
    }
}
