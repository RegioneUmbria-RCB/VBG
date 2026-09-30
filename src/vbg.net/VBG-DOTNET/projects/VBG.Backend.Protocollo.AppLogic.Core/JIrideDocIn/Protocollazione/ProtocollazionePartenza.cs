using VBG.Shared.Infrastructure.ServiceModel;
using System.Text.RegularExpressions;
using VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.PEC;
using VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione.CreaCopie;
using VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione.LeggiProtocollo;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione
{
    public class ProtocollazionePartenza : IProtocollazioneJIrideDocIn
    {
        private readonly ProtocollazioneConfiguration _conf;
        private readonly IBindingFactory _bindingFactory;

        public ProtocollazionePartenza(ProtocollazioneConfiguration conf, IBindingFactory bindingFactory)
        {
            this._conf = conf;
            this._bindingFactory = bindingFactory;
        }
        public ProtocolloOutXml Inserisci(ProtocolloInXml request)
        {

            var service = new ProtocollazioneServiceWrapper(this._conf.Vert.Url, this._conf.Logs, this._conf.Serializer, this._bindingFactory, this._conf.Vert.CodiceAmministrazione, this._conf.Vert.Aoo);
            var retVal = service.InserisciDocumento(request);

            if (this._conf.Anagrafiche != null && this._conf.Anagrafiche.Count > 1)
            {

                var mittente = this._conf.Mittenti.Amministrazione.First();
                var destinatari = this._conf.Destinatari.Amministrazione;
                if (this._conf.Destinatari.Anagrafe.Count == 0 &&
                    this._conf.Destinatari.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).Count() == 0)
                {
                    destinatari = destinatari.Skip(1).ToList();
                }

                var creaCopieRequest = new CreaCopiePerAmministrazioniInterneRequestBuilder(this._conf.Vert, retVal, mittente, destinatari, this._conf.Operatore).Build();
                new CreaCopieService(this._conf.Logs, this._conf.Serializer, this._bindingFactory).CreaCopiePerAmministrazioniInterne(creaCopieRequest);
            }

            this._conf.Logs.InfoFormat($"URL PEC: -{this._conf.Vert.UrlPec}-, FLUSSO: PARTENZA");
            if (!String.IsNullOrEmpty(this._conf.Vert.UrlPec))
            {
                this.InviaPec(retVal);
            }

            return retVal;
        }

        private void InviaPec(ProtocolloOutXml protoOut)
        {
            this._conf.Logs.Info("INIZIO FUNZIONALITA' DI INVIO PEC");
            try
            {


                if (String.IsNullOrEmpty(this._conf.Mail.Oggetto))
                    this._conf.Logs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza dell'oggetto della mail, controllare l'oggetto di default in configurazione, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy"));
                else if (String.IsNullOrEmpty(this._conf.Vert.MittenteMailPec))
                    this._conf.Logs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza del mittente della mail, controllare il parametro MITTENTE_MAIL_PEC della verticalizzazione PROTOCOLLO_IRIDE, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy"));
                else if (String.IsNullOrEmpty(this._conf.Vert.UrlPec))
                    this._conf.Logs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza dell'url (end point) del servizio di invio mail PEC di Iride, controllare il parametro URL_PEC della verticalizzazione PROTOCOLLO_IRIDE, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy"));
                else
                {

                    IEnumerable<string> seriali;

                    if (protoOut.Allegati == null)
                    {
                        var leggiProtocolloRequest = new LeggiProtocolloBuilder(this._conf.Vert, protoOut.IdDocumento.ToString(), "", "", this._conf.Operatore, this._conf.Ruolo).Build();
                        var protocolloLetto = new LeggiProtocolloService(this._conf.Logs, this._conf.Serializer, this._bindingFactory).Leggi(leggiProtocolloRequest);
                        seriali = protocolloLetto.DocumentoOut.Allegati.Select(x => x.Serial.ToString());
                    }
                    else
                        seriali = protoOut.Allegati.Select(x => x.Serial.ToString());


                    if (this._conf.Vert.WarningPec)
                    {
                        var anagraficheNoPEC = this._conf.Anagrafiche.Where(x => String.IsNullOrEmpty(x.Pec)).Select(x => x.NomeCognome);
                        if (anagraficheNoPEC.Count() > 0)
                        {
                            this._conf.Logs.WarnFormat("I SEGUENTI DESTINATARI NON PRESENTANO UN INDIRIZZO PEC, AI QUALI NON E' STATA QUINDI INVIATA: {0}", String.Join(", ", anagraficheNoPEC));
                        }

                        var anagraficheNoMezzo = this._conf.Anagrafiche.Where(x => x.MezzoInvio != this._conf.Vert.MezzoPec).Select(x => x.NomeCognome);

                        if (anagraficheNoMezzo.Count() > 0)
                        {
                            this._conf.Logs.WarnFormat("I SEGUENTI DESTINATARI NON PRESENTANO IL MEZZO DI INVIO PEC, AI QUALI NON E' STATA QUINDI INVIATA: {0}", String.Join(", ", anagraficheNoMezzo));
                        }
                    }

                    var listaPec = this._conf.Anagrafiche.Where(y => !String.IsNullOrEmpty(y.Pec) && y.MezzoInvio == this._conf.Vert.MezzoPec).
                                                                        GroupBy(x => x.Pec.ToUpperInvariant()).
                                                                        Select(x => x.Key).ToArray();



                    this._conf.Logs.InfoFormat("NUMERO DESTINATARI PER INVIO PEC: {0}", listaPec.Length);

                    if (listaPec.Length > 0)
                    {
                        var adapter = new PecAdapter(this._conf.Serializer);

                        var oggetto = this._conf.Mail.Oggetto;
                        if (this._conf.Vert.UsaRifProtocolloOggettoPec)
                        {
                            oggetto = $"Pr. Num.: {protoOut.NumeroProtocollo}, Data Pr.: {protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy")} - {this._conf.Mail.Oggetto.Replace("\r\n", " ")}";
                        }

                        var requestXml = adapter.Adatta(listaPec, seriali, protoOut.IdDocumento.ToString(), Regex.Replace(this._conf.Mail.Oggetto, @"\r\n?|\n", "-"), this._conf.Mail.Corpo, this._conf.Vert.MittenteMailPec, this._conf.Operatore, this._conf.Ruolo, this._conf.Vert.UsaInvioInteroperabilePec);
                        var pecServiceWrapper = new PECServiceWrapper(this._conf.Vert.UrlPec, this._conf.Logs, this._conf.Serializer, this._bindingFactory);

                        if (this._conf.Vert.WarningPec)
                        {
                            this._conf.Logs.WarnFormat("PEC INVIATA CORRETTAMENTE AI SEGUENTI DESTINATARI: {0}", String.Join(", ", listaPec));
                        }

                        pecServiceWrapper.InviaPEC(requestXml, this._conf.Vert.CodiceAmministrazione, this._conf.Vert.Aoo);
                    }
                }
            }
            catch (Exception ex)
            {
                this._conf.Logs.WarnFormat(String.Format("PROBLEMA DURANTE LA FUNZIONALITA' DI INVIO PEC, ERRORE: {0}", ex.Message));
            }
            finally
            {
            }
        }
    }
}
