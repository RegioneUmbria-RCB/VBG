using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari
{
    public class AnagraficheManager
    {
        IAnagraficaAmministrazione _anagrafica;

        public string Nominativo { get; private set; }

        public AnagraficheManager(IAnagraficaAmministrazione a, TipoGestioneAnagraficaEnum.TipoGestione tipo)
        {
            _anagrafica = a;
            Nominativo = a.Denominazione.Replace("  ", " ");

            if (tipo == TipoGestioneAnagraficaEnum.TipoGestione.PEC)
                Nominativo = String.Format("{0} ({1})", a.Denominazione.Replace("  ", " "), a.Pec);
            else if(tipo == TipoGestioneAnagraficaEnum.TipoGestione.CODICE_FISCALE)
            {
                string cfPiva = String.IsNullOrEmpty(a.CodiceFiscale) ? a.PartitaIva : a.CodiceFiscale;
                if (!String.IsNullOrEmpty(cfPiva))
                    Nominativo = String.Format("{0} ({1})", a.Denominazione.Replace("  ", " "), cfPiva);
            }
            else if (tipo == TipoGestioneAnagraficaEnum.TipoGestione.MONFALCONE)
            {
                if (a.ComuneResidenza == null && String.IsNullOrEmpty(a.Localita))
                    return;
                else if (a.ComuneResidenza == null && !String.IsNullOrEmpty(a.Localita))
                    Nominativo += a.Localita.ToUpper() == "MONFALCONE" ? " CITTA'" : String.Format(" {0}", a.Localita.ToUpper());
                else
                {
                    if (!String.IsNullOrEmpty(a.Localita) && a.Localita != a.ComuneResidenza.COMUNE)
                        Nominativo += String.Format(" {0}", a.Localita.ToUpper());
                    else
                    {
                        if (a.ComuneResidenza.COMUNE == "MONFALCONE")
                            Nominativo += " CITTA'";
                        else if (a.ComuneResidenza.COMUNE == a.ComuneResidenza.PROVINCIA)
                            Nominativo += String.Format(" {0}", a.ComuneResidenza.SIGLAPROVINCIA);
                        else
                            Nominativo += String.Format(" {0}", a.ComuneResidenza.COMUNE);
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
                        valore = Nominativo,
                        relazione = operatoreRelazionaleUIC.uguale,
                        relazioneSpecified = true
                    }
                }
            });

            if (responseLeggi == null)
            {
                var nuovaAnagrafica = new NuovaAnagrafica
                {
                    descAna = Nominativo,
                    nome = _anagrafica.Nome.Replace("  ", " "),
                    cognome = _anagrafica.Cognome.Replace("  ", " "),
                    codTipoAna = "EST",
                    disattivato = false,
                    disattivatoSpecified = true,
                };

                if (!String.IsNullOrEmpty(_anagrafica.PartitaIva) && _anagrafica.PartitaIva.Length == 11)
                    nuovaAnagrafica.piva = _anagrafica.PartitaIva;

                if (!String.IsNullOrEmpty(_anagrafica.CodiceFiscale) && _anagrafica.CodiceFiscale.Length == 16)
                    nuovaAnagrafica.codfis = _anagrafica.CodiceFiscale;

                if (!String.IsNullOrEmpty(_anagrafica.Indirizzo))
                    nuovaAnagrafica.indirizzo = _anagrafica.Indirizzo;

                if (!String.IsNullOrEmpty(_anagrafica.Pec))
                {
                    nuovaAnagrafica.emailList = new EmailAnagrafica[]
                    { 
                        new EmailAnagrafica
                        {
                            email = _anagrafica.Pec,
                            principale = true,
                            principaleSpecified = true,
                            tipo = tipoEmailAnagrafica.pec
                        }
                    };
                }


                if (!String.IsNullOrEmpty(_anagrafica.Sesso))
                {
                    nuovaAnagrafica.sesso = _anagrafica.Sesso == "F" ? NuovaAnagraficaSesso.F : NuovaAnagraficaSesso.M;
                    nuovaAnagrafica.sessoSpecified = true;
                }

                var requestInsert = new NuovaAnagraficaRequest { anagrafica = nuovaAnagrafica };

                srv.InserisciAnagrafica(requestInsert);
            }
            else
            {
                if (String.IsNullOrEmpty(_anagrafica.Pec))
                    return;

                var anagrafica = responseLeggi.First();

                var numeroEmailPresenti = anagrafica.emailList.Length;

                if (anagrafica.emailList.Where(x => x.email == _anagrafica.Pec && x.tipo == tipoEmailAnagrafica.pec).Count() == 0)
                {
                    var requestAggiorna = new AggiornamentoAnagraficaRequest
                    {
                        idAnagrafica = new IdAnagrafica { descAna = Nominativo },
                        datiAggiornati = new NuovaAnagrafica
                        {
                            emailList = new EmailAnagrafica[]
                            { 
                                new EmailAnagrafica
                                { 
                                    email = _anagrafica.Pec, 
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
