using System.Data;
using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System.Xml.Serialization;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;

using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
namespace Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza
{


	partial class PresentazioneIstanzaDataSet
	{

		partial class AttivitaAtecoDataTable
		{
			public bool ContieneAttivitaPrimaria()
			{
				return GetIdAttivitaPrimaria() != null;
			}

			public int? GetIdAttivitaPrimaria()
			{
				var q = from AttivitaAtecoRow r in this
						where r.Primaria
						select r;

				if (q.Count() > 0)
					return q.First().IdAteco;

				return null;
			}
		}
			
		partial class RiepiloghiCategorieDataTable
		{
			/*
			[Inject]
			public IAllegatiDomandaFoRepository _allegatiDomandaFoRepository { get; set; }

			bool _isInjected = false;
			private void EnsureInjection()
			{
				if (!_isInjected)
				{
					FoKernelContainer.Inject(this);
					_isInjected = true;
				}
			}


			public bool ContieneRiepilogoPerCategoria(int codiceCategoria)
			{
				RiepiloghiCategorieRow row = FindByCodiceCategoria(codiceCategoria.ToString());

				return row != null;
			}

			public void AggiornaModelloRiepilogoPerCategoria(int codiceCategoria, int codiceOggetto, string descrizioneCategoria)
			{
				RiepiloghiCategorieRow row = FindByCodiceCategoria(codiceCategoria.ToString());

				if (row != null)
				{
					row.CodiceOggettoModello = codiceOggetto.ToString();
					row.NomeCategoria = descrizioneCategoria;
				}
			}



			public void AggiungiRiepilogoCategoria(int codCategoria, int codiceOggetto, string nomeFile, string nomeCategoria, bool richiedeFirma)
			{
				AddRiepiloghiCategorieRow(codCategoria.ToString(), nomeFile, String.Empty, codiceOggetto.ToString(), nomeCategoria, richiedeFirma);
			}

			public bool IsRiepilogoValorizzato(int codiceCategoria)
			{
				RiepiloghiCategorieRow row = FindByCodiceCategoria(codiceCategoria.ToString());

				if (row == null || String.IsNullOrEmpty(row.CodiceOggetto))
					return false;

				return true;
			}

			public RiepiloghiCategorieRow EliminaRiepilogoCategoria(string idComune, int idDomanda, int codiceCategoria)
			{
				EnsureInjection();

				RiepiloghiCategorieRow row = FindByCodiceCategoria(codiceCategoria.ToString());

				if (!String.IsNullOrEmpty(row.CodiceOggetto))
					_allegatiDomandaFoRepository.EliminaAllegato( idDomanda, Convert.ToInt32(row.CodiceOggetto) );

				row.CodiceOggetto = String.Empty;
				row.NomeFile = String.Empty;

				return row;
			}

			public void AggiornaRiepilogoCategoria(string idComune, int idPresentazione, int codCategoria, BinaryFile file)
			{
				EnsureInjection();

				RiepiloghiCategorieRow row = EliminaRiepilogoCategoria(idComune, idPresentazione, codCategoria);

				// Verifico la firma digitale
				bool verificaFirma = !row.IsRichiedeFirmaNull() && row.RichiedeFirma;

				int fileId = _allegatiDomandaFoRepository.SalvaAllegato(idComune, idPresentazione, file,verificaFirma);

				row.CodiceOggetto = fileId.ToString();
				row.NomeFile = file.FileName;

				row.AcceptChanges();

			}
			*/
		}
		
		partial class DatiDinamiciDataTable
		{
			public string GetValoreCampo(int idModello, string idCampo)
			{
				var set = from DatiDinamiciRow r in this
						  where r.IdModello == idModello && r.IdCampoDinamico == idCampo
						  select r;

				if (set.Count() == 0)
					return string.Empty;

				return set.First().Valore;
			}

		}
		

		partial class OGGETTIDataTable
		{
			/// <summary>
			/// Restituisce una riga della DataTable Oggetti a partire dal suo id univoco
			/// </summary>
			/// <param name="id">id della riga</param>
			/// <returns>riga della DataTable Oggetti</returns>
			public OGGETTIRow FindByID(int id)
			{
				var res = from OGGETTIRow r in this
						  where r.ID == id
						  select r;

				if (res.Count() == 0)
					return null;

				return res.First();
			}
		}

		partial class ANAGRAFERow
		{
			public override string ToString()
			{
				var sb = new StringBuilder();

				sb.Append(this.NOMINATIVO);

				if (!String.IsNullOrEmpty(this.NOME))
				{
					sb.Append(" ");
					sb.Append(this.NOME);
				}
				sb.Append(" [").Append( this.CODICEFISCALE ).Append("]");

				return sb.ToString();
			}
		}
	}
}
