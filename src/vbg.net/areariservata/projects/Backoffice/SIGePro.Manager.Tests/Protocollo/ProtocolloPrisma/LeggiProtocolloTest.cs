using Init.SIGePro.Protocollo.Prisma.LeggiProtocollo;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.WsDataClass;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SIGePro.Manager.Tests.Protocollo.ProtocolloPrisma
{
    [TestClass]
    public class LeggiProtocolloTest
    {
        string _xmlResponse = @"<?xml version=""1.0"" encoding=""UTF-8""?>
                            <PROTOCOLLO>
	                            <DOC>
		                            <ID_DOCUMENTO>8916986</ID_DOCUMENTO>
		                            <IDRIF>316629</IDRIF>
		                            <ANNO>2018</ANNO>
		                            <NUMERO>138267</NUMERO>
		                            <TIPO_REGISTRO>PROT</TIPO_REGISTRO>
		                            <DESCRIZIONE_TIPO_REGISTRO>PROTOCOLLO GENERALE</DESCRIZIONE_TIPO_REGISTRO>
		                            <DATA>10/09/2018 12:00:00</DATA>
		                            <OGGETTO>COMUNICAZIONE INFORMATIVA A RICHIEDENTE, CONSORZIO, PM
                            PROT. ISTANZA N. 121724 DEL 08/08/2018
                            PIANCASTELLI PATRIZIA MATIS - S.A.S. DI PIANCASTELLI PATRIZIA E C.</OGGETTO>
		                            <DATA_DOCUMENTO/>
		                            <NUMERO_DOCUMENTO/>
		                            <CLASS_COD>08.04</CLASS_COD>
		                            <CLASS_DAL>01/01/2008 12:00:00</CLASS_DAL>
		                            <FASCICOLO_ANNO>2018</FASCICOLO_ANNO>
		                            <FASCICOLO_NUMERO>1469</FASCICOLO_NUMERO>
		                            <RISERVATO>N</RISERVATO>
		                            <STATO_PR>PR</STATO_PR>
		                            <DOCUMENTO_TRAMITE/>
		                            <TIPO_DOCUMENTO/>
		                            <DESCRIZIONE_TIPO_DOCUMENTO/>
		                            <UNITA_ESIBENTE/>
		                            <UNITA_PROTOCOLLANTE>551</UNITA_PROTOCOLLANTE>
		                            <UTENTE_PROTOCOLLANTE>VBG</UTENTE_PROTOCOLLANTE>
		                            <ANNULLATO/>
		                            <DATA_ANN/>
		                            <UTENTE_ANN/>
		                            <MODALITA>PAR</MODALITA>
	                            </DOC>
	                            <FILE_PRINCIPALE>
		                            <FILE>
			                            <ID_OGGETTO_FILE>3307993</ID_OGGETTO_FILE>
			                            <ID_DOCUMENTO>8916986</ID_DOCUMENTO>
			                            <FILENAME>AMB_ComunicazioneAlConsorzio_49456_12_51_37_9_8rtf.pdf.p7m</FILENAME>
		                            </FILE>
	                            </FILE_PRINCIPALE>
	                            <ALLEGATI/>
	                            <SMISTAMENTI>
		                            <SMISTAMENTO>
			                            <ID_DOCUMENTO>8916997</ID_DOCUMENTO>
			                            <DES_UFFICIO_SMISTAMENTO>UFFICIO PROTOCOLLO</DES_UFFICIO_SMISTAMENTO>
			                            <DES_UFFICIO_TRASMISSIONE>BACK OFFICE SUAP</DES_UFFICIO_TRASMISSIONE>
			                            <IDRIF>316629</IDRIF>
			                            <SMISTAMENTO_DAL>10/09/2018 12:00:00</SMISTAMENTO_DAL>
			                            <STATO_SMISTAMENTO>E</STATO_SMISTAMENTO>
			                            <TIPO_SMISTAMENTO>COMPETENZA</TIPO_SMISTAMENTO>
			                            <UFFICIO_SMISTAMENTO>16407</UFFICIO_SMISTAMENTO>
			                            <UFFICIO_TRASMISSIONE>552</UFFICIO_TRASMISSIONE>
			                            <UTENTE_TRASMISSIONE>1562</UTENTE_TRASMISSIONE>
		                            </SMISTAMENTO>
		                            <SMISTAMENTO>
			                            <ID_DOCUMENTO>8917707</ID_DOCUMENTO>
			                            <DES_UFFICIO_SMISTAMENTO>UOS EDILIZIA - COMMERCIO - AMBIENTE</DES_UFFICIO_SMISTAMENTO>
			                            <DES_UFFICIO_TRASMISSIONE>UFFICIO PROTOCOLLO</DES_UFFICIO_TRASMISSIONE>
			                            <IDRIF>316629</IDRIF>
			                            <SMISTAMENTO_DAL>10/09/2018 12:00:00</SMISTAMENTO_DAL>
			                            <STATO_SMISTAMENTO>R</STATO_SMISTAMENTO>
			                            <TIPO_SMISTAMENTO>COMPETENZA</TIPO_SMISTAMENTO>
			                            <UFFICIO_SMISTAMENTO>16428</UFFICIO_SMISTAMENTO>
			                            <UFFICIO_TRASMISSIONE>16407</UFFICIO_TRASMISSIONE>
			                            <UTENTE_TRASMISSIONE>806</UTENTE_TRASMISSIONE>
		                            </SMISTAMENTO>
		                            <SMISTAMENTO>
			                            <ID_DOCUMENTO>8916983</ID_DOCUMENTO>
			                            <DES_UFFICIO_SMISTAMENTO>BACK OFFICE SUAP</DES_UFFICIO_SMISTAMENTO>
			                            <DES_UFFICIO_TRASMISSIONE>FRONT OFFICE SUAP</DES_UFFICIO_TRASMISSIONE>
			                            <IDRIF>316629</IDRIF>
			                            <SMISTAMENTO_DAL>10/09/2018 12:00:00</SMISTAMENTO_DAL>
			                            <STATO_SMISTAMENTO>E</STATO_SMISTAMENTO>
			                            <TIPO_SMISTAMENTO>COMPETENZA</TIPO_SMISTAMENTO>
			                            <UFFICIO_SMISTAMENTO>552</UFFICIO_SMISTAMENTO>
			                            <UFFICIO_TRASMISSIONE>551</UFFICIO_TRASMISSIONE>
			                            <UTENTE_TRASMISSIONE>VBG</UTENTE_TRASMISSIONE>
		                            </SMISTAMENTO>
	                            </SMISTAMENTI>
	                            <RAPPORTI>
		                            <RAPPORTO>
			                            <ID_DOCUMENTO>8916984</ID_DOCUMENTO>
			                            <COGNOME_NOME>PIANCASTELLI PATRIZIA</COGNOME_NOME>
			                            <CODICE_FISCALE>PNCPRZ57R45C121O</CODICE_FISCALE>
			                            <EMAIL>LUCIANA.CASTANI@GMAIL.COM</EMAIL>
			                            <DENOMINAZIONE>PIANCASTELLI</DENOMINAZIONE>
			                            <INDIRIZZO/>
			                            <CAP/>
			                            <IDRIF>316629</IDRIF>
			                            <CONOSCENZA>N</CONOSCENZA>
			                            <COD_AMM/>
			                            <COD_AOO/>
			                            <COD_UO/>
			                            <DESCRIZIONE_AMM/>
			                            <DESCRIZIONE_AOO/>
		                            </RAPPORTO>
		                            <RAPPORTO>
			                            <ID_DOCUMENTO>8916985</ID_DOCUMENTO>
			                            <COGNOME_NOME>CONSORZIO IL MERCATO .</COGNOME_NOME>
			                            <CODICE_FISCALE>02069110365</CODICE_FISCALE>
			                            <EMAIL>CONSORZIOILMERCATO@LEGALMAIL.IT</EMAIL>
			                            <DENOMINAZIONE>CONSORZIO IL MERCATO</DENOMINAZIONE>
			                            <INDIRIZZO/>
			                            <CAP/>
			                            <IDRIF>316629</IDRIF>
			                            <CONOSCENZA>N</CONOSCENZA>
			                            <COD_AMM/>
			                            <COD_AOO/>
			                            <COD_UO/>
			                            <DESCRIZIONE_AMM/>
			                            <DESCRIZIONE_AOO/>
		                            </RAPPORTO>
		                            <RAPPORTO>
			                            <ID_DOCUMENTO>8916989</ID_DOCUMENTO>
			                            <COGNOME_NOME>castani.luciana@pec.it </COGNOME_NOME>
			                            <CODICE_FISCALE/>
			                            <EMAIL>CASTANI.LUCIANA@PEC.IT</EMAIL>
			                            <DENOMINAZIONE/>
			                            <INDIRIZZO/>
			                            <CAP/>
			                            <IDRIF>316629</IDRIF>
			                            <CONOSCENZA>N</CONOSCENZA>
			                            <COD_AMM/>
			                            <COD_AOO/>
			                            <COD_UO/>
			                            <DESCRIZIONE_AMM/>
			                            <DESCRIZIONE_AOO/>
		                            </RAPPORTO>
	                            </RAPPORTI>
                            </PROTOCOLLO>";

        string _xmlMemoInviatiInfoPec = @"<?xml version=""1.0"" encoding=""UTF-8""?>
                                            <PROTOCOLLO>
	                                            <DATI>
		                                            <ID_DOCUMENTO>8916986</ID_DOCUMENTO>
		                                            <IDRIF>316629</IDRIF>
		                                            <ANNO>2018</ANNO>
		                                            <NUMERO>138267</NUMERO>
		                                            <TIPO_REGISTRO>PROT</TIPO_REGISTRO>
		                                            <DESCRIZIONE_TIPO_REGISTRO>PROTOCOLLO GENERALE</DESCRIZIONE_TIPO_REGISTRO>
		                                            <DATA>10/09/2018 12:00:00</DATA>
		                                            <OGGETTO>COMUNICAZIONE INFORMATIVA A RICHIEDENTE, CONSORZIO, PM
                                            PROT. ISTANZA N. 121724 DEL 08/08/2018

                                            PIANCASTELLI PATRIZIA MATIS - S.A.S. DI PIANCASTELLI PATRIZIA E C.</OGGETTO>
		                                            <CLASS_COD>08.04</CLASS_COD>
		                                            <CLASS_DAL>01/01/2008 12:00:00</CLASS_DAL>
		                                            <FASCICOLO_ANNO>2018</FASCICOLO_ANNO>
		                                            <FASCICOLO_NUMERO>1469</FASCICOLO_NUMERO>
		                                            <UNITA_PROTOCOLLANTE>551</UNITA_PROTOCOLLANTE>
		                                            <UTENTE_PROTOCOLLANTE>VBG</UTENTE_PROTOCOLLANTE>
		                                            <MODALITA>PAR</MODALITA>
	                                            </DATI>
	                                            <MEMO_INVIATI>
		                                            <MEMO>
			                                            <ID_DOCUMENTO>8916990</ID_DOCUMENTO>
			                                            <DATA_SPEDIZIONE>10/09/2018</DATA_SPEDIZIONE>
			                                            <DESTINATARI>CASTANI.LUCIANA@PEC.IT;CONSORZIOILMERCATO@LEGALMAIL.IT;</DESTINATARI>
			                                            <FILE_ALLEGATI>
				                                            <FILE>
					                                            <ID_OGGETTO_FILE>3307996</ID_OGGETTO_FILE>
					                                            <ID_DOCUMENTO>8916991</ID_DOCUMENTO>
					                                            <FILENAME>daticert.xml</FILENAME>
				                                            </FILE>
				                                            <FILE>
					                                            <ID_OGGETTO_FILE>3308022</ID_OGGETTO_FILE>
					                                            <ID_DOCUMENTO>8917052</ID_DOCUMENTO>
					                                            <FILENAME>daticert.xml</FILENAME>
				                                            </FILE>
				                                            <FILE>
					                                            <ID_OGGETTO_FILE>3308048</ID_OGGETTO_FILE>
					                                            <ID_DOCUMENTO>8917066</ID_DOCUMENTO>
					                                            <FILENAME>daticert.xml</FILENAME>
				                                            </FILE>
			                                            </FILE_ALLEGATI>
		                                            </MEMO>
	                                            </MEMO_INVIATI>
                                            </PROTOCOLLO>";

        [TestMethod]
        public void Verifica_aggiunta_dati_pec_protocollo_prisma_partenzao()
        {
            var serializer = new ProtocolloSerializer(null);

            var response = serializer.Deserialize<LeggiProtocolloOutXML>(this._xmlResponse);
            var infoPec = serializer.Deserialize<LeggiPecOutXML>(this._xmlMemoInviatiInfoPec);

            var destinatari = new List<MittDestOut>();
            var destinatariPec = infoPec.MemoInviati.Memo.Destinatari.Split(';').Cast<string>();

            foreach (var destinatario in response.Rapporti.Rapporto)
            {
                var datoDaVisualizzare = destinatario.CognomeNome;

                if (destinatariPec.Contains(destinatario.Email))
                {
                    datoDaVisualizzare = $"{destinatario.CognomeNome} {destinatario.Email}";

                    if (!String.IsNullOrEmpty(infoPec.MemoInviati.Memo.DataSpedizione))
                    {
                        datoDaVisualizzare = $"{destinatario.CognomeNome} {destinatario.Email} Spedito il {infoPec.MemoInviati.Memo.DataSpedizione}";
                    }

                    destinatariPec.ToList().Remove(destinatario.Email);
                }

                destinatari.Add(new MittDestOut { CognomeNome = datoDaVisualizzare });
            }

            if (destinatariPec.Count() > 0)
            {
                foreach (var destinatariPecRimasti in destinatariPec)
                {
                    var datoDaVisualizzare = destinatariPecRimasti;
                    if (!String.IsNullOrEmpty(infoPec.MemoInviati.Memo.DataSpedizione))
                    {
                        datoDaVisualizzare = $"{destinatariPecRimasti} Spedito il {infoPec.MemoInviati.Memo.DataSpedizione}";
                    }

                    destinatari.Add(new MittDestOut { CognomeNome = datoDaVisualizzare });
                }
            }

            Assert.AreEqual("PIANCASTELLI PATRIZIA", destinatari[0].CognomeNome);
            Assert.AreEqual("CONSORZIO IL MERCATO . CONSORZIOILMERCATO@LEGALMAIL.IT Spedito il 10/09/2018", destinatari[1].CognomeNome);
            Assert.AreEqual("castani.luciana@pec.it  CASTANI.LUCIANA@PEC.IT Spedito il 10/09/2018", destinatari[2].CognomeNome);
        }

        [TestMethod]
        public void verifica_destinatari_rapporti_partenza()
        {
            var serializer = new ProtocolloSerializer(null);

            var response = serializer.Deserialize<LeggiProtocolloOutXML>(this._xmlResponse);
            var infoPec = serializer.Deserialize<LeggiPecOutXML>(this._xmlMemoInviatiInfoPec);

            var destinatari = new List<MittDestOut>();
            var destinatariPec = infoPec.MemoInviati.Memo.Destinatari.Split(';').Cast<string>();

            string test = "NON ENTRATO";


            foreach (var destinatario in response.Rapporti.Rapporto)
            {
                if (!String.IsNullOrEmpty(destinatario.Email) && destinatario.CognomeNome.Trim().ToUpper() == destinatario.Email.Trim().ToUpper())
                {
                    test = "ENTRATO";
                    continue;
                }

                var datoDaVisualizzare = destinatario.CognomeNome;

                if (destinatariPec.Contains(destinatario.Email))
                {
                    datoDaVisualizzare = $"{destinatario.CognomeNome} {destinatario.Email}";

                    if (!String.IsNullOrEmpty(infoPec.MemoInviati.Memo.DataSpedizione))
                    {
                        datoDaVisualizzare = $"{destinatario.CognomeNome} {destinatario.Email} Spedito il {infoPec.MemoInviati.Memo.DataSpedizione}";
                    }

                    destinatariPec.ToList().Remove(destinatario.Email);
                }

                destinatari.Add(new MittDestOut { CognomeNome = datoDaVisualizzare });
            }

            if (destinatariPec.Count() > 0)
            {
                foreach (var destinatariPecRimasti in destinatariPec)
                {
                    var datoDaVisualizzare = destinatariPecRimasti;
                    if (!String.IsNullOrEmpty(infoPec.MemoInviati.Memo.DataSpedizione))
                    {
                        datoDaVisualizzare = $"{destinatariPecRimasti} Spedito il {infoPec.MemoInviati.Memo.DataSpedizione}";
                    }

                    destinatari.Add(new MittDestOut { CognomeNome = datoDaVisualizzare });
                }
            }


            Assert.AreEqual("ENTRATO", test);

        }
    }
}
