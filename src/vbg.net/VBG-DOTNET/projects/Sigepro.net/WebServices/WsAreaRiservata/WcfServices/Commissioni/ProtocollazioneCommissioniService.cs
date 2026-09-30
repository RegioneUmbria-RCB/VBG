using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione;
using it.gruppoinit.Protocollazione;
using Sigepro.net.WsProtocollo;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Linq;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Commissioni
{
    public class ProtocollazioneCommissioniService : IProtocollazioneCommissioneService
    {
        private readonly AuthenticationInfo _authInfo;
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly ProtocolloServiceCreator _protocolloServiceCreator;

        public ProtocollazioneCommissioniService(AuthenticationInfo authInfo, IVerticalizzazioniFactory verticalizzazioniFactory, ProtocolloServiceCreator protocolloServiceCreator)
        {
            this._authInfo = authInfo;
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._protocolloServiceCreator = protocolloServiceCreator;
        }

        public EsitoProtocollazioneCommissione ProtocollaParereEsterno(int codiceIstanza, IRisolviMittenteProtocolloCommissione mittente, IRisolviOggettoProtocolloCommissione oggetto, IRisolviAllegatiProtocolloCommissione allegati)
        {
            try
            {
                var istanza = this.GetIstanza(codiceIstanza);
                var software = istanza.SOFTWARE;
                var codiceComune = istanza.CODICECOMUNE;
                var dati = new DatiRequestType
                {
                    Classifica = null,      // PARAMETRI OBBLIGATORI: lascio risolvere al servizio di protocollazione
                    Destinatari = null,     //
                    TipoDocumento = null,   //

                    Oggetto = oggetto.OggettoProtocollo,

                    Mittenti = mittente.Mittente == null ? null : new DatiMittentiType
                    {
                        Anagrafe = mittente.Mittente.Anagrafiche.Select(x => new DatiAnagraficiType
                        {
                            Cod = x.CodiceAnagrafe.ToString()
                        }).ToArray(),

                        Amministrazione = mittente.Mittente.Amministrazioni.Select(x => new DatiAnagraficiType
                        {
                            Cod = x.CodiceAmministrazione.ToString()
                        }).ToArray()
                    },

                    Allegati = allegati.Allegati.Select(x => new AllegatoType
                    {
                        Cod = x.CodiceOggetto.ToString(),
                        Descrizione = x.NomeFile,
                        InviaTramitePec = false
                    }).ToArray()
                };

                var esito = this._protocolloServiceCreator.Call(ws => ws.ProtocollazioneXml(this._authInfo.Token, software, dati, codiceComune));


                //var mgr = new ProtocolloMgr(this._verticalizzazioniFactory, ProtocolloStoricoNonAttivo.Instance);
                //mgr.Initialize(this._authInfo, software, codiceComune, ambito, istanza);

                //var esito = mgr.Protocollazione(provenienza, dati, source);

                if (esito.Errore != null)
                {
                    return EsitoProtocollazioneCommissione.Fallito(esito.Errore.Descrizione, esito.Errore.StackTrace);
                }

                return EsitoProtocollazioneCommissione.Riuscito(esito.IdProtocollo, esito.NumeroProtocollo, esito.DataProtocollo);
            }
            catch (Exception ex)
            {
                return EsitoProtocollazioneCommissione.Fallito(ex);
            }
        }

        private Init.SIGePro.Data.Istanze GetIstanza(int codiceIstanza)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                return new IstanzeMgr(db).GetById(this._authInfo.IdComune, codiceIstanza);
            }
        }
    }
}