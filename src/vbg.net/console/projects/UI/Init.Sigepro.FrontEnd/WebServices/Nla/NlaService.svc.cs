using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using log4net;

namespace Init.Sigepro.FrontEnd.WebServices.Nla
{
	[ServiceBehavior(Namespace = "http://sigepro.init.it/rte/definitions")]
	public class NlaService : Nla
	{
        ILog _log = LogManager.GetLogger(typeof(NlaService));
		#region INlaService Members

		public RichiestaPraticheListaNLAResponse1 RichiestaPraticheListaNLA(RichiestaPraticheListaNLARequest1 request)
		{
			throw new NotImplementedException();
		}

		public InserimentoPraticaNLAResponse1 InserimentoPraticaNLA(InserimentoPraticaNLARequest1 request)
		{
			throw new NotImplementedException();
		}

		public RichiestaPraticaNLAResponse1 RichiestaPraticaNLA(RichiestaPraticaNLARequest1 request)
		{
			return new RichiestaPraticaNLAResponse1
			{
				RichiestaPraticaNLAResponse = new RichiestaPraticaNLAResponse
				{
					dettaglioPratica = new DettaglioPraticaVisuraType
					{
						dettaglioPratica = new DettaglioPraticaType
						{
							idPratica = request.RichiestaPraticaNLARequest.rifPratica.idPratica,
							numeroPratica = request.RichiestaPraticaNLARequest.rifPratica.idPratica
						}
					}
				}
			};

		}

		public TestNLAResponse1 TestNLA(TestNLARequest1 request)
		{
			return new TestNLAResponse1
			{
				TestNLAResponse = new TestNLAResponse
				{
					nlaXsdVersion = XsdNlaVersion.V_1_10,
					typesXsdVersion = XsdTypesVersion.V_1_10
				}
			};
		}

		public InserimentoAttivitaNLAResponse1 InserimentoAttivitaNLA(InserimentoAttivitaNLARequest1 request)
		{
			throw new NotImplementedException();
		}

		public AllegatoBinarioNLAResponse1 AllegatoBinarioNLA(AllegatoBinarioNLARequest1 request)
		{
            try
            {
                this._log.InfoFormat("Richiesta AllegatoBinarioNLA: codiceOggettoEsteso=" + request.ToXmlString());

                //var aliasComune		= request.AllegatoBinarioNLARequest.sportelloDestinatario.idEnte;
                //var software		= request.AllegatoBinarioNLARequest.sportelloDestinatario.idSportello;
                var codiceOggettoEsteso = request.AllegatoBinarioNLARequest.riferimentiAllegato.idAllegato;
                var parts = codiceOggettoEsteso.Split('@');
                var alias = parts[0];
                var codiceOggetto = parts[1];

                var oggettiService = OggettiService.CreaSuServizio(alias);

                var file = oggettiService.GetById(Convert.ToInt32(codiceOggetto));

                return new AllegatoBinarioNLAResponse1
                {
                    AllegatoBinarioNLAResponse = new AllegatoBinarioNLAResponse
                    {
                        binaryData = file.FileContent,
                        fileName = file.FileName,
                        mimeType = file.MimeType
                    }
                };
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("Errore nella richiesta a AllegatoBinarioNLA: {0}", ex.ToString());

                throw;
            }
            
		}

		public AggiungiDocumentiNLAResponse AggiungiDocumentiNLA(AggiungiDocumentiNLARequest1 request)
		{
			throw new NotImplementedException();
		}

		#endregion



	}
}
