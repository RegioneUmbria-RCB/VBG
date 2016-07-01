using System;
using System.IO;
using System.Reflection;

namespace SIGePro.Net.Data
{
	/// <summary>
	/// Descrizione di riepilogo per TextFileReader.
	/// </summary>
	public class TextFileReader
	{
		private Stream m_istream;

		public TextFileReader(Stream istream)
		{
			m_istream = istream;
		}

		public TextFileReader(string fileName)
		{
			m_istream = File.OpenText(fileName).BaseStream;
		}

		public ClassesBoxCollection ReadFile(bool p_readFirstLine)
		{
			ClassesBoxCollection retVal = new ClassesBoxCollection();

			using (StreamReader tr = new StreamReader(m_istream))
			{
				string line = null;
				int i = 0;
				while ((line = tr.ReadLine()) != null)
				{
					if (i > 0 || ! p_readFirstLine)
					{
						ClassesBox cb = new ClassesBox();
						cb.TmpImport = (TMP_IMPORT) PopolaClasse(line, typeof (TMP_IMPORT));
						/*
								cb.Impresa = ( IMPR_IMPRESA ) PopolaClasse( line , typeof( IMPR_IMPRESA ));
								cb.Attivita = ( ATTV_ATTIVITA ) PopolaClasse( line , typeof( ATTV_ATTIVITA ));
								cb.Cariche = ( PSCA_CARICHEPERSIMPRESA ) PopolaClasse( line , typeof( PSCA_CARICHEPERSIMPRESA ));
								cb.Comuni = ( COMU_COMUNI )	PopolaClasse( line , typeof( COMU_COMUNI ) );
								cb.Decodifiche = ( DECO_DECODIFICHE ) PopolaClasse( line , typeof( DECO_DECODIFICHE ) );
								cb.NaturaGiuridica = ( NAGI_NATURAGIURIDICA ) PopolaClasse( line , typeof( NAGI_NATURAGIURIDICA ) );
								cb.PersoneFisiche = ( PFIM_PERSONEFISICHEIMPRESA ) PopolaClasse( line ,typeof( PFIM_PERSONEFISICHEIMPRESA ) );
								cb.PgImpresa = ( PGIM_PGIMPRESA ) PopolaClasse( line ,typeof( PGIM_PGIMPRESA ) );
								cb.Province = ( PROV_PROVINCE ) PopolaClasse( line ,typeof( PROV_PROVINCE ) );
								cb.Sezioni = ( SEZI_SEZIONI ) PopolaClasse( line ,typeof( SEZI_SEZIONI ) );
								cb.SezioniSpeciali = ( SZSP_SEZIONISPECIALI ) PopolaClasse( line ,typeof( SZSP_SEZIONISPECIALI ) );
								cb.UnitaLocale = ( UNLO_UNITALOCALE ) PopolaClasse( line ,typeof( UNLO_UNITALOCALE ) );
							*/
						p_readFirstLine = false;
						retVal.Add(cb);
					}
					i++;
				}
			}


			return retVal;
		}

		private object PopolaClasse(string line, Type classType)
		{
			object retVal = Activator.CreateInstance(classType);
			PropertyInfo[] classProperties = (PropertyInfo[]) classType.GetProperties();

			for (int i = 0; i < classProperties.Length; i++)
			{
				TextFileInfoAttribute[] textFileInfoAttributes = (TextFileInfoAttribute[]) classProperties[i].GetCustomAttributes(typeof (TextFileInfoAttribute), true);

				if (textFileInfoAttributes != null && textFileInfoAttributes.Length > 0)
				{
					int startAt = textFileInfoAttributes[0].StartAt;
					int size = textFileInfoAttributes[0].Size;

					string val = line.Substring(startAt, size);

					classProperties[i].SetValue(retVal, val, null);
				}
			}

			return retVal;

		}
	}
}