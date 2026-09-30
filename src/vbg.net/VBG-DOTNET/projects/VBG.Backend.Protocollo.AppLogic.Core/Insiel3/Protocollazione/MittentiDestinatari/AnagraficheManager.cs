using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione.MittentiDestinatari
{
    public class AnagraficheManager
    {
        private readonly IAnagraficaAmministrazione _anagrafica;

        public string Nominativo { get; private set; }

        public AnagraficheManager(IAnagraficaAmministrazione a, TipoGestioneAnagraficaEnum.TipoGestione tipo)
        {
            this._anagrafica = a;
            this.Nominativo = a.Denominazione.Replace("  ", " ");

            if (tipo == TipoGestioneAnagraficaEnum.TipoGestione.PEC)
                this.Nominativo = String.Format("{0} ({1})", a.Denominazione.Replace("  ", " "), a.Pec);
            else if (tipo == TipoGestioneAnagraficaEnum.TipoGestione.CODICE_FISCALE)
            {
                var cfPiva = String.IsNullOrEmpty(a.CodiceFiscale) ? a.PartitaIva : a.CodiceFiscale;
                if (!String.IsNullOrEmpty(cfPiva))
                    this.Nominativo = String.Format("{0} ({1})", a.Denominazione.Replace("  ", " "), cfPiva);
            }
            else if (tipo == TipoGestioneAnagraficaEnum.TipoGestione.MONFALCONE)
            {
                if (a.ComuneResidenza == null && String.IsNullOrEmpty(a.Localita))
                    return;
                else if (a.ComuneResidenza == null && !String.IsNullOrEmpty(a.Localita))
                    this.Nominativo += a.Localita.ToUpper() == "MONFALCONE" ? " CITTA'" : String.Format(" {0}", a.Localita.ToUpper());
                else
                {
                    if (!String.IsNullOrEmpty(a.Localita) && a.Localita != a.ComuneResidenza.DenominazioneComune)
                        this.Nominativo += String.Format(" {0}", a.Localita.ToUpper());
                    else
                    {
                        if (a.ComuneResidenza.DenominazioneComune == "MONFALCONE")
                            this.Nominativo += " CITTA'";
                        else if (a.ComuneResidenza.DenominazioneComune == a.ComuneResidenza.Provincia)
                            this.Nominativo += String.Format(" {0}", a.ComuneResidenza.SiglaProvincia);
                        else
                            this.Nominativo += String.Format(" {0}", a.ComuneResidenza.DenominazioneComune);
                    }
                }
            }
        }

        public void Gestisci(ProtocolloService srv)
        {

            var responseLeggi = srv.LeggiAnagrafiche(new InterrogaAnagraficaRequest
            {
                anagrafica = new AnagraficaRicerca
                {
                    descAna = new AnagraficaRicercaDescAna
                    {
                        valore = this.Nominativo,
                        relazione = operatoreRelazionaleUIC.uguale,
                        relazioneSpecified = true
                    }
                }
            });

            if (responseLeggi == null)
            {
                var nuovaAnagrafica = new NuovaAnagrafica
                {
                    descAna = this.Nominativo,
                    nome = this._anagrafica.Nome.Replace("  ", " "),
                    cognome = this._anagrafica.Cognome.Replace("  ", " "),
                    codTipoAna = "EST",
                    disattivato = false,
                    disattivatoSpecified = true,
                };

                if (!String.IsNullOrEmpty(this._anagrafica.PartitaIva) && this._anagrafica.PartitaIva.Length == 11)
                    nuovaAnagrafica.piva = this._anagrafica.PartitaIva;

                if (!String.IsNullOrEmpty(this._anagrafica.CodiceFiscale) && this._anagrafica.CodiceFiscale.Length == 16)
                    nuovaAnagrafica.codfis = this._anagrafica.CodiceFiscale;

                if (!String.IsNullOrEmpty(this._anagrafica.Indirizzo))
                    nuovaAnagrafica.indirizzo = this._anagrafica.Indirizzo;

                if (!String.IsNullOrEmpty(this._anagrafica.Pec))
                {
                    nuovaAnagrafica.emailList = new EmailAnagrafica[]
                    {
                        new EmailAnagrafica
                        {
                            email = this._anagrafica.Pec,
                            principale = true,
                            principaleSpecified = true,
                            tipo = tipoEmailAnagrafica.pec
                        }
                    };
                }


                if (!String.IsNullOrEmpty(this._anagrafica.Sesso))
                {
                    nuovaAnagrafica.sesso = this._anagrafica.Sesso == "F" ? NuovaAnagraficaSesso.F : NuovaAnagraficaSesso.M;
                    nuovaAnagrafica.sessoSpecified = true;
                }

                var requestInsert = new NuovaAnagraficaRequest { anagrafica = nuovaAnagrafica };

                srv.InserisciAnagrafica(requestInsert);
            }
            else
            {
                if (String.IsNullOrEmpty(this._anagrafica.Pec))
                    return;

                var anagrafica = responseLeggi.First();

                var numeroEmailPresenti = anagrafica.emailList.Length;

                if (anagrafica.emailList.Where(x => x.email == this._anagrafica.Pec && x.tipo == tipoEmailAnagrafica.pec).Count() == 0)
                {
                    var requestAggiorna = new AggiornamentoAnagraficaRequest
                    {
                        idAnagrafica = new IdAnagrafica { descAna = this.Nominativo },
                        datiAggiornati = new NuovaAnagrafica
                        {
                            emailList = new EmailAnagrafica[]
                            {
                                new EmailAnagrafica
                                {
                                    email = this._anagrafica.Pec,
                                    tipo = tipoEmailAnagrafica.pec,
                                    principale = (numeroEmailPresenti == 0),
                                    principaleSpecified = true
                                }
                            }
                        }
                    };

                    srv.AggiornaAnagrafica(requestAggiorna);
                }
            }
        }
    }
}
