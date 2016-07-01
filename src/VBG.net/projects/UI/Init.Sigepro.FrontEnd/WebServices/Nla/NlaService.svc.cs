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

namespace Init.Sigepro.FrontEnd.WebServices.Nla
{
	[ServiceBehavior(Namespace = "http://sigepro.init.it/rte/definitions")]
	public class NlaService : Nla
	{

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
					nlaXsdVersion = XsdNlaVersion.V_1_9,
					typesXsdVersion = XsdTypesVersion.V_1_9
				}
			};
		}

		public InserimentoAttivitaNLAResponse1 InserimentoAttivitaNLA(InserimentoAttivitaNLARequest1 request)
		{
			throw new NotImplementedException();
		}

		public AllegatoBinarioNLAResponse1 AllegatoBinarioNLA(AllegatoBinarioNLARequest1 request)
		{
			var aliasComune		= request.AllegatoBinarioNLARequest.sportelloDestinatario.idEnte;
			var software		= request.AllegatoBinarioNLARequest.sportelloDestinatario.idSportello;
			var codiceOggetto	= request.AllegatoBinarioNLARequest.riferimentiAllegato.idAllegato;

			var oggettiService =  OggettiService.CreaSuServizio(aliasComune,software);

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

		public AggiungiDocumentiNLAResponse AggiungiDocumentiNLA(AggiungiDocumentiNLARequest1 request)
		{
			throw new NotImplementedException();
		}

		#endregion



	}
}
